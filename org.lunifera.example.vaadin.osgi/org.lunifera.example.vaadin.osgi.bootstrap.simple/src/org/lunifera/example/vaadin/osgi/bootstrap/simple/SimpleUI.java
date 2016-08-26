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
package org.lunifera.example.vaadin.osgi.bootstrap.simple;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
@Theme(Reindeer.THEME_NAME)
public class SimpleUI extends UI {
	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout layout = new VerticalLayout();
		setContent(layout);
		layout.setStyleName(Reindeer.LAYOUT_BLUE);
		layout.setSizeFull();

		CssLayout layout1 = new CssLayout(new Label("panel1"));
		layout1.addLayoutClickListener(e -> System.out.println("panel1"));
		layout1.setSizeFull();
		Panel panel1 = new Panel(layout1);
		panel1.setSizeFull();
		
		CssLayout layout2 = new CssLayout(new Label("panel2"));
		layout2.addLayoutClickListener(e -> System.out.println("panel2"));
		layout2.setSizeFull();
		Panel panel2 = new Panel(layout2);
		panel2.setSizeFull();
		
		VerticalLayout content = new VerticalLayout(new Label("masterpanel"), panel1, panel2);
		content.addLayoutClickListener(e -> System.out.println("masterPanel"));
		content.setSizeFull();
		Panel masterPanel = new Panel(content);
		masterPanel.setSizeFull();
		layout.addComponent(masterPanel);
	}

}
