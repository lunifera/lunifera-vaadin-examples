/**
 * Based on org.eclipse.gemini.dbaccess.samples.DatasourceClientExample (EPL and APL)
 */
package org.lunifera.example.vaadin.osgi.simplest.connectionpool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.jdbc.DataSourceFactory;

import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;

@SuppressWarnings("serial")
public class OSGiConnectionPool implements JDBCConnectionPool {

	private int initialConnections = 5;
	private int maxConnections = 20;

	private transient Set<Connection> availableConnections;
	private transient Set<Connection> reservedConnections;
	private boolean initialized;

	private ComponentContext context;
	private DataSource ds;
	private DataSourceFactory dsFactory;

	public OSGiConnectionPool() throws SQLException {
	}

	/**
	 * Called by OSGi-DS to activate the component.
	 * 
	 * @param context
	 */
	protected void activate(ComponentContext context) {
		this.context = context;

		@SuppressWarnings("rawtypes")
		Dictionary props = context.getProperties();
		if (props.get("initialConnections") != null) {
			initialConnections = (int) props.get("initialConnections");
		}
		if (props.get("maxConnections") != null) {
			maxConnections = (int) props.get("maxConnections");
		}
	}

	/**
	 * Called by OSGi-DS to deactivate the component.
	 * 
	 * @param context
	 */
	protected void deactivate(ComponentContext context) {
		this.context = null;

		destroy();
	}

	/**
	 * Called by OSGi-DS. Binds the factory. Type is filtered in factory.xml.
	 * 
	 * @param dsFactory
	 */
	protected void bindDataSourceFactory(DataSourceFactory dsFactory,
			Map<String, String> properties) {
		this.dsFactory = dsFactory;
	}

	/**
	 * Called by OSGi-DS. Unbinds the factory.
	 * 
	 * @param dsFactory
	 */
	protected void unbindDataSourceFactory(DataSourceFactory dsFactory) {
		destroy();

		this.dsFactory = null;
		this.ds = null;
	}

	/**
	 * Ensures that the datasource is setup properly if available.
	 */
	@SuppressWarnings("unchecked")
	private void ensureDatasource() {

		if (ds != null) {
			return;
		}

		if (context == null || dsFactory == null) {
			return;
		}

		try {
			ds = dsFactory.createDataSource(toProperties(context
					.getProperties()));
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void initializeConnections() throws SQLException {
		ensureDatasource();

		availableConnections = new HashSet<Connection>(initialConnections);
		reservedConnections = new HashSet<Connection>(initialConnections);
		for (int i = 0; i < initialConnections; i++) {
			availableConnections.add(ds.getConnection());
		}
		initialized = true;
	}

	/**
	 * Maps the dictionary to map.
	 * 
	 * @param properties
	 * @return
	 */
	private static Properties toProperties(Dictionary<String, Object> properties) {
		Properties props = new Properties();
		props.put("url", properties.get("url"));
		return props;
	}

	@Override
	public synchronized Connection reserveConnection() throws SQLException {
		if (!initialized) {
			initializeConnections();
		}
		if (availableConnections.isEmpty()) {
			if (reservedConnections.size() < maxConnections) {
				availableConnections.add(ds.getConnection());
			} else {
				throw new SQLException("Connection limit has been reached.");
			}
		}

		Connection c = availableConnections.iterator().next();
		availableConnections.remove(c);
		reservedConnections.add(c);

		return c;
	}

	@Override
	public synchronized void releaseConnection(Connection conn) {
		if (conn == null || !initialized) {
			return;
		}

		/* Try to roll back if necessary */
		try {
			if (!conn.getAutoCommit()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			/* Roll back failed, close and discard connection */
			try {
				conn.close();
			} catch (SQLException e1) {
				/* Nothing needs to be done */
			}
			reservedConnections.remove(conn);
			return;
		}
		reservedConnections.remove(conn);
		availableConnections.add(conn);
	}

	@Override
	public void destroy() {
		for (Connection c : availableConnections) {
			try {
				c.close();
			} catch (SQLException e) {
				// No need to do anything
			}
		}
		for (Connection c : reservedConnections) {
			try {
				c.close();
			} catch (SQLException e) {
				// No need to do anything
			}
		}
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		initialized = false;
		out.defaultWriteObject();
	}
}
