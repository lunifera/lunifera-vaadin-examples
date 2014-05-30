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
package org.lunifera.example.vaadin.ecview;

import org.eclipse.emf.ecp.ecview.common.context.ContextException;
import org.lunifera.example.vaadin.ecview.samples.ECViewEntityGenerator;
import org.lunifera.runtime.web.vaadin.databinding.VaadinObservables;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
@Theme(Reindeer.THEME_NAME)
public class ECViewDemoUI extends UI {
	@Override
	protected void init(VaadinRequest request) {
		setStyleName(Reindeer.LAYOUT_BLACK);

		VaadinObservables.getRealm(getUI());

		CssLayout layout = new CssLayout();
		layout.setSizeFull();
		setContent(layout);

		TabSheet tabsheet = new TabSheet();
		tabsheet.setSizeFull();
		try {
			// tabsheet.addTab(new ECViewVerticalLayout(), "ECView Vertical");
			// tabsheet.addTab(new ECViewHorizontalLayout(),
			// "ECView Horizontal");
			// tabsheet.addTab(new ECViewGridLayout(), "ECView Gridlayout");
			// tabsheet.addTab(new ECViewTabSheet(), "ECView Tabsheet");
			// tabsheet.addTab(new ECViewDatabinding(), "ECView Bindings");
			tabsheet.addTab(new ECViewEntityGenerator(),
					"ECView Entity Generator");
			// tabsheet.addTab(new ECViewDtoGenerator(),
			// "ECView Dto Generator");
		} catch (ContextException e) {
			e.printStackTrace();
		}
		layout.addComponent(tabsheet);
	}

}
