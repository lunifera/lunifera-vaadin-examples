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
package org.lunifera.example.vaadin.databinding;

import org.lunifera.example.vaadin.databinding.samples.SelectionBindings;
import org.lunifera.example.vaadin.databinding.samples.ValueBindings;
import org.lunifera.runtime.web.vaadin.databinding.VaadinObservables;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
@Theme(Reindeer.THEME_NAME)
public class DatabindingDemoUI extends UI {
	@Override
	protected void init(VaadinRequest request) {

		VaadinObservables.getRealm(getUI());

		CssLayout layout = new CssLayout();
		layout.setSizeFull();
		setContent(layout);

		TabSheet tabsheet = new TabSheet();
		tabsheet.setSizeFull();
		tabsheet.addTab(new ValueBindings(), "Value bindings");
		tabsheet.addTab(new SelectionBindings(), "Selection bindings");
		layout.addComponent(tabsheet);
	}

}
