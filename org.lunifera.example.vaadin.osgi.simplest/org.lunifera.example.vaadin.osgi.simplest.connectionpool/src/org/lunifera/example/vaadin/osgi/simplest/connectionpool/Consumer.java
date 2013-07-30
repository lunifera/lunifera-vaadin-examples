package org.lunifera.example.vaadin.osgi.simplest.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;

import org.osgi.service.component.ComponentContext;

import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;

public class Consumer {

	private JDBCConnectionPool pool;

	protected void activate(ComponentContext context) {

		// This code block uses the connection pool to get access to a
		// connection
		try {
			Connection c = pool.reserveConnection();
			c.getMetaData();
			pool.releaseConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Called by OSGi-DS.
	 * 
	 * @param pool
	 */
	protected void bindConnectionPool(JDBCConnectionPool pool) {
		this.pool = pool;
	}

	/**
	 * Called by OSGi-DS.
	 * 
	 * @param pool
	 */
	protected void unbindConnectionPool(JDBCConnectionPool pool) {
		this.pool = null;
	}

}
