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
package org.lunifera.example.vaadin.lunifera.uiprovider;

import org.lunifera.runtime.web.vaadin.osgi.common.CustomOSGiUiProvider;
import org.lunifera.runtime.web.vaadin.osgi.common.IOSGiUiProviderFactory;

import com.vaadin.server.UICreateEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.Reindeer;

public class UiProviderFactory implements IOSGiUiProviderFactory {

	@Override
	public CustomOSGiUiProvider createUiProvider(String vaadinApplication,
			Class<? extends UI> uiClass) {
		if (vaadinApplication != null
				&& vaadinApplication.equals("VaadinStandaloneDemo")) {
			return new UIProvider(vaadinApplication, uiClass);
		}
		return null;
	}

	@SuppressWarnings("serial")
	private static class UIProvider extends CustomOSGiUiProvider {

		public UIProvider(String vaadinApplication, Class<? extends UI> uiClass) {
			super(vaadinApplication, uiClass);
		}

		public String getTheme(UICreateEvent event) {
			return Reindeer.THEME_NAME;
		}
	}

}
