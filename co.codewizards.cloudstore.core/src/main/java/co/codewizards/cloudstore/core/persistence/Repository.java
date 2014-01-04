package co.codewizards.cloudstore.core.persistence;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.PersistenceCapable;

import co.codewizards.cloudstore.core.dto.EntityID;

/**
 * A {@code Repository} represents a repository inside the database.
 * <p>
 * Every repository consists of a directory including all its sub-directories and files together with one
 * meta-data-directory containing a database. Inside this database, the local repository itself is represented
 * by a {@link LocalRepository} instance.
 * <p>
 * The local repository can be connected as a client to zero or more remote repositories. For each such remote
 * repository there is one {@link RemoteRepository} instance in the database.
 * @author Marco หงุ่ยตระกูล-Schulze - marco at codewizards dot co
 */
@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.VALUE_MAP)
public abstract class Repository extends Entity
{
	private long revision;

	public Repository() { }

	protected Repository(EntityID entityID) {
		super(entityID);
	}

	public long getRevision() {
		return revision;
	}
	public void setRevision(long revision) {
		this.revision = revision;
	}
}