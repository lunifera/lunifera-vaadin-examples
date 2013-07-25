package org.lunifera.example.vaadin.osgi.simplest.push.bootstrap;

import com.vaadin.annotations.Push;
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
@Push
public class SimplePushUI extends UI {
	private Label label;

	@Override
	protected void init(VaadinRequest request) {
		HorizontalLayout layout = new HorizontalLayout();
		setContent(layout);
		layout.setStyleName(Reindeer.LAYOUT_BLUE);
		layout.setSizeFull();

		label = new Label();
		label.setContentMode(ContentMode.HTML);
		layout.addComponent(label);
		layout.setComponentAlignment(label, Alignment.TOP_CENTER);

		new Thread(new Runnable() {
			int counter = 0;

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					counter++;

					label.getUI().access(new Runnable() {
						@Override
						public void run() {
							label.setValue(String.format(
									"<h1>Running for %s seconds</h1>",
									Integer.toString(counter)));
						}
					});
				}
			}
		}).start();

	}

}
