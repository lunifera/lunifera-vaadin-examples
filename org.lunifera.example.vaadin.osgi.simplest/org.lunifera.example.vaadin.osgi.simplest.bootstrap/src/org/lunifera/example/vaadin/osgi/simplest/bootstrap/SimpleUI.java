package org.lunifera.example.vaadin.osgi.simplest.bootstrap;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
@Theme(Reindeer.THEME_NAME)
public class SimpleUI extends UI {
	@Override
	protected void init(VaadinRequest request) {
		HorizontalLayout layout = new HorizontalLayout();
		setContent(layout);
		layout.setStyleName(Reindeer.LAYOUT_BLUE);
		layout.setSizeFull();

		Label label = new Label();
		label.setValue("<h1>Simple OSGi integration</h1>");
		label.setContentMode(ContentMode.HTML);
		layout.addComponent(label);
		layout.setComponentAlignment(label, Alignment.TOP_CENTER);
	}

}
