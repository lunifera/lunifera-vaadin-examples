 /**
 * Copyright 2013 Lunifera GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lunifera.example.vaadin.osgi.connectionpool;

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
			System.out.println("Consumer using pool!");
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
