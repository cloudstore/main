package co.codewizards.cloudstore.local.persistence;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import co.codewizards.cloudstore.core.repo.local.LocalRepoManager;
import co.codewizards.cloudstore.core.repo.local.LocalRepoTransaction;
import co.codewizards.cloudstore.local.AbstractTest;

public class PersistenceTest extends AbstractTest {

	private static final int modificationCount = 10000;

	@BeforeClass
	public static void beforeClass() {
		System.setProperty(LocalRepoManager.SYSTEM_PROPERTY_CLOSE_DEFERRED_MILLIS, "0");
	}

	@AfterClass
	public static void afterClass() {
		System.clearProperty(LocalRepoManager.SYSTEM_PROPERTY_CLOSE_DEFERRED_MILLIS);
	}

	@Test
	public void getModifications() throws Exception {
		File remoteRoot = newTestRepositoryLocalRoot("remote");
		remoteRoot.mkdirs();
		LocalRepoManager localRepoManager = localRepoManagerFactory.createLocalRepoManagerForNewRepository(remoteRoot);
		assertThat(localRepoManager).isNotNull();
		LocalRepoTransaction transaction = localRepoManager.beginWriteTransaction();
		try {
			RemoteRepository remoteRepository = new RemoteRepository();
			remoteRepository.setLocalPathPrefix("");
			remoteRepository.setPublicKey(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 });
			remoteRepository = transaction.getDAO(RemoteRepositoryDAO.class).makePersistent(remoteRepository);

			CopyModificationDAO copyModificationDAO = transaction.getDAO(CopyModificationDAO.class);
			for (int i = 0; i < modificationCount; ++i) {
				CopyModification copyModification = new CopyModification();
				copyModification.setRemoteRepository(remoteRepository);
				copyModification.setFromPath("/from/" + i);
				copyModification.setToPath("/to/" + i);
				copyModification.setLength(100000);
				copyModification.setSha1("TEST" + i);
				copyModificationDAO.makePersistent(copyModification);
			}
			transaction.commit();
		} finally {
			transaction.rollbackIfActive();
		}

		localRepoManager.close();
		localRepoManager = localRepoManagerFactory.createLocalRepoManagerForExistingRepository(remoteRoot);

		transaction = localRepoManager.beginReadTransaction();
		try {
			RemoteRepository remoteRepository = transaction.getDAO(RemoteRepositoryDAO.class).getObjects().iterator().next();
			ModificationDAO modificationDAO = transaction.getDAO(ModificationDAO.class);
			Collection<Modification> modifications = modificationDAO.getModifications(remoteRepository);
			assertThat(modifications).hasSize(modificationCount);
			System.out.println("*** Accessing fromPath and toPath ***");
			for (Modification modification : modifications) {
				if (modification instanceof CopyModification) {
					CopyModification copyModification = (CopyModification)modification;
					System.out.println(String.format("%s => %s",
							copyModification.getFromPath(),
							copyModification.getToPath()));
				}
			}
			transaction.commit();
		} finally {
			transaction.rollbackIfActive();
		}

		localRepoManager.close();
	}
}