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
package org.lunifera.example.vaadin.osgi.jpacontainer;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.lunifera.example.vaadin.osgi.jpacontainer.service.JPAContainerFactoryOSGiAware;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator,
		ServiceTrackerCustomizer<EntityManagerFactory, EntityManagerFactory> {

	private static BundleContext context;
	private static Activator instance;

	private ServiceTracker<EntityManagerFactory, EntityManagerFactory> tracker;
	private Map<String, EntityManagerFactory> emfs = new HashMap<String, EntityManagerFactory>();

	public static Activator getInstance() {
		return instance;
	}

	public static JPAContainerFactoryOSGiAware getJPAContainerFactory() {
		return new JPAContainerFactoryOSGiAware();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		Activator.context = context;
		instance = this;

		tracker = new ServiceTracker<EntityManagerFactory, EntityManagerFactory>(
				context, EntityManagerFactory.class, this);
		tracker.open();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		tracker.close();
		tracker = null;
		instance = null;
		Activator.context = null;
	}

	public EntityManagerFactory getEMF(String pu) {
		return emfs.get(pu);
	}

	@Override
	public EntityManagerFactory addingService(
			ServiceReference<EntityManagerFactory> reference) {
		EntityManagerFactory emf = context.getService(reference);
		String unitName = (String) reference
				.getProperty(EntityManagerFactoryBuilder.JPA_UNIT_NAME);
		emfs.put(unitName, emf);

		if (unitName.equals("addressbook")) {
			DemoDataGenerator.create(emf);
		}

		return emf;
	}

	@Override
	public void modifiedService(
			ServiceReference<EntityManagerFactory> reference,
			EntityManagerFactory service) {
	}

	@Override
	public void removedService(
			ServiceReference<EntityManagerFactory> reference,
			EntityManagerFactory service) {
		String unitName = (String) reference
				.getProperty(EntityManagerFactoryBuilder.JPA_UNIT_NAME);
		emfs.remove(unitName);
	}
}
