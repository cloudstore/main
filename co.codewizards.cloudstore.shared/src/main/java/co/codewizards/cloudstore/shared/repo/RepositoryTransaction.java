package co.codewizards.cloudstore.shared.repo;

import static co.codewizards.cloudstore.shared.util.Util.*;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import co.codewizards.cloudstore.shared.persistence.LocalRepository;
import co.codewizards.cloudstore.shared.persistence.LocalRepositoryDAO;

public class RepositoryTransaction {

	private final RepositoryManager repositoryManager;
	private final PersistenceManagerFactory persistenceManagerFactory;
	private PersistenceManager persistenceManager;
	private Transaction jdoTransaction;
	private LocalRepository localRepository;
	private long localRevision = -1;

	public RepositoryTransaction(RepositoryManager repositoryManager) {
		this.repositoryManager = assertNotNull("repositoryManager", repositoryManager);
		this.persistenceManagerFactory = assertNotNull("repositoryManager.persistenceManagerFactory", repositoryManager.getPersistenceManagerFactory());
		begin();
	}

	private synchronized void begin() {
		if (isActive()) {
			throw new IllegalStateException("Transaction is already active!");
		}
		try {
			persistenceManager = persistenceManagerFactory.getPersistenceManager();
			hookLifecycleListeners();
			jdoTransaction = persistenceManager.currentTransaction();
			jdoTransaction.begin();

			jdoTransaction.setSerializeRead(true);
			LocalRepository lr = new LocalRepositoryDAO().persistenceManager(persistenceManager).getLocalRepositoryOrFail();
			jdoTransaction.setSerializeRead(null);
			localRevision = lr.getRevision() + 1;
			lr.setRevision(localRevision);
			persistenceManager.flush();
			localRepository = lr;
		} finally {
			if (localRepository == null) {
				if (jdoTransaction != null) {
					if (jdoTransaction.isActive())
						jdoTransaction.rollback();

					jdoTransaction = null;
				}
				if (persistenceManager != null) {
					persistenceManager.close();
					persistenceManager = null;
				}
			}
		}
	}

	private void hookLifecycleListeners() {
		persistenceManager.addInstanceLifecycleListener(new AutoTrackLifecycleListener(this), (Class[]) null);
	}

	public synchronized void commit() {
		if (!isActive()) {
			throw new IllegalStateException("Transaction is not active!");
		}
		jdoTransaction.commit();
		persistenceManager.close();
		jdoTransaction = null;
		persistenceManager = null;
		localRevision = -1;
	}

	public synchronized boolean isActive() {
		return jdoTransaction != null && jdoTransaction.isActive();
	}

	public synchronized void rollback() {
		if (!isActive()) {
			throw new IllegalStateException("Transaction is not active!");
		}
		jdoTransaction.rollback();
		persistenceManager.close();
		jdoTransaction = null;
		persistenceManager = null;
		localRevision = -1;
	}

	public synchronized void rollbackIfActive() {
		if (isActive())
			rollback();
	}

	public PersistenceManager getPersistenceManager() {
		if (!isActive()) {
			throw new IllegalStateException("Transaction is not active!");
		}
		return persistenceManager;
	}

	public long getLocalRevision() {
		return localRevision;
	}

	public RepositoryManager getRepositoryManager() {
		return repositoryManager;
	}
}
