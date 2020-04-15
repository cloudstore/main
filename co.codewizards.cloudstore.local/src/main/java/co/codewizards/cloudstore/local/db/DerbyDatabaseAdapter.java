package co.codewizards.cloudstore.local.db;

import static co.codewizards.cloudstore.core.util.DerbyUtil.*;
import static co.codewizards.cloudstore.core.util.StringUtil.*;
import static java.util.Objects.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.SortedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.codewizards.cloudstore.local.PersistencePropertiesEnum;
import co.codewizards.cloudstore.local.PersistencePropertiesProvider;

public class DerbyDatabaseAdapter extends AbstractDatabaseAdapter {
	private static final Logger logger = LoggerFactory.getLogger(DerbyDatabaseAdapter.class);

	private Map<String, String> persistenceProperties;

	private String connectionURL;

	private String connectionDriverName;

	private String connectionUserName;

	private String connectionPassword;

	@Override
	protected void createDatabase() throws Exception {
		if (connectionURL == null) {
			initProperties();
			initDriverClass();
		}
		connectionURL = requireNonNull(connectionURL, "connectionURL").trim() + ";create=true";
		Connection connection = createConnection();
		connection.close();
	}

	private void initDriverClass() {
		if (isEmpty(connectionDriverName))
			return;

		try {
			Class.forName(connectionDriverName);
		} catch (Throwable e) { // Might theoretically be a link error (i.e. a sub-class of Error instead of Exception) => catch Throwable
			logger.warn("initDriverClass: " + e, e);
		}
	}

	@Override
	public Connection createConnection() throws SQLException {
		if (connectionURL == null) {
			initProperties();
			initDriverClass();
		}
		if (isEmpty(connectionUserName) && isEmpty(connectionPassword))
			return DriverManager.getConnection(connectionURL);
		else
			return DriverManager.getConnection(connectionURL, connectionUserName, connectionPassword);
	}

	private void initProperties() {
		PersistencePropertiesProvider persistencePropertiesProvider = new PersistencePropertiesProvider(getRepositoryIdOrFail(), getLocalRootOrFail());
		persistenceProperties = persistencePropertiesProvider.getPersistenceProperties();

		connectionDriverName = persistenceProperties.get(PersistencePropertiesEnum.CONNECTION_DRIVER_NAME.key);
		connectionURL = persistenceProperties.get(PersistencePropertiesEnum.CONNECTION_URL.key);
		connectionUserName = persistenceProperties.get(PersistencePropertiesEnum.CONNECTION_USER_NAME.key);
		connectionPassword = persistenceProperties.get(PersistencePropertiesEnum.CONNECTION_PASSWORD.key);
	}

	@Override
	public void shutdownEmbeddedDatabase() {
		if (connectionURL == null) {
			initProperties();
			initDriverClass();
		}
		shutdownDerbyDatabase(connectionURL);
	}

	@Override
	public boolean passivateIdentityColumn(Connection connection, Table table, SortedMap<String, Column> columnName2Column) throws Exception {
		Column idCol = columnName2Column.get("ID");
		if (idCol == null)
			return false;

		if (! Boolean.TRUE.equals(idCol.autoIncrement))
			return false;

		try (Statement statement = connection.createStatement()) {
			String sql = String.format("alter table \"%s\" drop column \"id\" cascade", table.name);
			logger.info("passivateIdentityColumn: Executing: {}", sql);
			statement.executeUpdate(sql);

			sql = String.format("alter table \"%s\" add column \"id\" bigint primary key generated by default as identity", table.name);
			logger.info("passivateIdentityColumn: Executing: {}", sql);
			statement.executeUpdate(sql);
		}
		return true;
	}

	@Override
	public void activateIdentityColumn(Connection connection, Table table, SortedMap<String, Column> columnName2Column) throws Exception {
		try (Statement statement = connection.createStatement()) {
			long nextId;
			String sql = String.format("select max(\"id\") from \"%s\"", table.name);
			logger.info("activateIdentityColumn: Executing: {}", sql);
			try (ResultSet rs = statement.executeQuery(sql)) {
				if (! rs.next())
					throw new IllegalStateException("SELECT MAX(...) returned no row! Table: " + table.name);

				nextId = rs.getLong(1);
				if (rs.wasNull())
					nextId = 1;
				else
					nextId += 1;

				if (rs.next())
					throw new IllegalStateException("SELECT MAX(...) returned multiple rows! Table: " + table.name);
			}

			sql = String.format("alter table \"%s\" alter column \"id\" restart with %s", table.name, nextId);
			logger.info("activateIdentityColumn: Executing: {}", sql);
			statement.executeUpdate(sql);
		}
	}
}
