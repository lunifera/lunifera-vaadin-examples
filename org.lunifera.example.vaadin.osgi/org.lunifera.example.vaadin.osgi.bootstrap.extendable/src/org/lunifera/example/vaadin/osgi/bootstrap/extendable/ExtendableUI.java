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
package org.lunifera.example.vaadin.osgi.bootstrap.extendable;

import org.lunifera.example.vaadin.osgi.bootstrap.extendable.api.IContentProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
@Theme(Reindeer.THEME_NAME)
@Push
public class ExtendableUI extends UI implements
		ServiceTrackerCustomizer<IContentProvider, IContentProvider> {

	private ServiceTracker<IContentProvider, IContentProvider> tracker;
	private BundleContext context = FrameworkUtil.getBundle(getClass())
			.getBundleContext();
	private CssLayout main;

	@Override
	protected void init(VaadinRequest request) {
		main = new CssLayout();
		main.setSizeFull();
		setContent(main);

		// start a service tracker to find all content provider
		tracker = new ServiceTracker<IContentProvider, IContentProvider>(
				context, IContentProvider.class, this);
		tracker.open();
	}

	/**
	 * Called by service tracker if a provider was found.
	 */
	@Override
	public IContentProvider addingService(
			ServiceReference<IContentProvider> reference) {
		final IContentProvider provider = context.getService(reference);
		accessSynchronously(new Runnable() {
			@Override
			public void run() {
				Component c = provider.getContent();
				c.setSizeFull();
				main.addComponent(c);
			}
		});
		return provider;
	}

	@Override
	public void modifiedService(ServiceReference<IContentProvider> reference,
			IContentProvider service) {
	}

	/**
	 * Called by service tracker if a service was stopped.
	 */
	@Override
	public void removedService(ServiceReference<IContentProvider> reference,
			IContentProvider service) {
		if (main.getComponent(0) == service.getContent()) {
			accessSynchronously(new Runnable() {
				@Override
				public void run() {
					main.removeAllComponents();
				}
			});
		}
	}

}
