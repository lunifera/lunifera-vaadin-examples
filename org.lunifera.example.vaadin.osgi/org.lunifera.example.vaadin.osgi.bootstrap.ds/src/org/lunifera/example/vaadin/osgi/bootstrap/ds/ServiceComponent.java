/**
 * Copyright 2013 Lunifera GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lunifera.example.vaadin.osgi.bootstrap.ds;

import javax.servlet.ServletException;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

/**
 * The service will look for the HttpService and registers the vaadin servlet at
 * it.
 */
public class ServiceComponent implements BundleListener {

	private HttpService httpService;
	private ResourceProvider resourceProvider;

	/**
	 * Called by OSGi DS if the component is activated.
	 * 
	 * @param context
	 */
	protected void activate(ComponentContext context) {
		handleStartedBundles(context);
		context.getBundleContext().addBundleListener(this);
	}
	
	/**
	 * Called by OSGi DS if the component is deactivated.
	 * 
	 * @param context
	 */
	protected void deactivate(ComponentContext context) {
		context.getBundleContext().removeBundleListener(this);
		resourceProvider = null;
	}

	/**
	 * Returns the resource provider to access static resources.
	 * 
	 * @return
	 */
	private ResourceProvider getResourceProvider() {
		if (resourceProvider == null) {
			resourceProvider = new ResourceProvider();
		}
		return resourceProvider;
	}

	/**
	 * Binds the http service to this component. Called by OSGi-DS.
	 * 
	 * @param service
	 */
	protected void bindHttpService(HttpService service) {
		httpService = service;

		try {
			// register the servlet at the http service
			httpService.registerServlet("/", new SimpleVaadinServlet(), null,
					getResourceProvider());
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (NamespaceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Unbinds the http service from this component. Called by OSGi-DS.
	 * 
	 * @param service
	 */
	protected void unbindHttpService(HttpService service) {
		// unregister the servlet from the http service
		httpService.unregister("/");
	}

	/**
	 * Tries to find proper started bundles and adds them to resource provider.
	 * Since bundle changed listener will not find them.
	 * 
	 * @param context
	 */
	protected void handleStartedBundles(ComponentContext context) {
		for (Bundle bundle : context.getBundleContext().getBundles()) {
			String name = bundle.getSymbolicName();
			if (bundle.getState() == Bundle.ACTIVE
					&& name.startsWith("com.vaadin")) {
				getResourceProvider().add(bundle);
			}
		}
	}

	@Override
	public void bundleChanged(BundleEvent event) {
		// tracks the starting and stopping of vaadin bundles. If a bundle is a
		// vaadin bundle it will be added to the resource provider for lookups.
		String name = event.getBundle().getSymbolicName();
		if (name.startsWith("com.vaadin")) {
			if (event.getType() == BundleEvent.STARTED) {
				getResourceProvider().add(event.getBundle());
			} else if (event.getType() == BundleEvent.STOPPED) {
				getResourceProvider().remove(event.getBundle());
			}
		}
	}

}
