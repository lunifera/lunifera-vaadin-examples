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
package org.lunifera.example.vaadin.osgi.contentprovider;

import org.lunifera.example.vaadin.osgi.bootstrap.extendable.api.IContentProvider;

import com.vaadin.data.Property;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Slider;

public class ContentProvider_1 implements IContentProvider {

	private AbsoluteLayout main;
	private ISharkyController controller;
	private Slider speed;

	@SuppressWarnings("serial")
	@Override
	public Component getContent() {
		main = new AbsoluteLayout();
		main.setSizeFull();

		AbsoluteLayout buttonPanel = new AbsoluteLayout();
		buttonPanel.setWidth("320px");
		buttonPanel.setHeight("270px");

		final Button left = new Button("left");
		final Button top = new Button("top");
		final Button right = new Button("right");
		final Button bottom = new Button("bottom");
		final Button stop = new Button("STOP");
		final IntensityBar leftIntensity = new IntensityBar();
		final IntensityBar topIntensity = new IntensityBar();
		final IntensityBar rightIntensity = new IntensityBar();
		final IntensityBar bottomIntensity = new IntensityBar();

		speed = new Slider(0, 10);
		speed.setOrientation(SliderOrientation.VERTICAL);

		left.addShortcutListener(new ShortcutListener("Left",
				ShortcutAction.KeyCode.ARROW_LEFT, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				left();
			}
		});

		top.addShortcutListener(new ShortcutListener("Top",
				ShortcutAction.KeyCode.ARROW_UP, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				top();
			}
		});

		right.addShortcutListener(new ShortcutListener("Right",
				ShortcutAction.KeyCode.ARROW_RIGHT, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				right();
			}
		});

		left.addShortcutListener(new ShortcutListener("Bottom",
				ShortcutAction.KeyCode.ARROW_DOWN, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				bottom();
			}
		});

		stop.addShortcutListener(new ShortcutListener("STOP",
				ShortcutAction.KeyCode.ESCAPE, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				stop();
			}
		});

		speed.addShortcutListener(new ShortcutListener("Speed up",
				ShortcutAction.KeyCode.W, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				speed.setValue(speed.getValue().doubleValue() + 1);
			}
		});

		speed.addShortcutListener(new ShortcutListener("Speed down",
				ShortcutAction.KeyCode.Y, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				speed.setValue(speed.getValue().doubleValue() - 1);
			}
		});

		left.setWidth("75px");
		leftIntensity.setWidth("75px");
		leftIntensity.setValue(0.3f);
		top.setWidth("75px");
		topIntensity.setWidth("75px");
		topIntensity.setValue(0.3f);
		right.setWidth("75px");
		rightIntensity.setWidth("75px");
		rightIntensity.setValue(0.3f);
		bottom.setWidth("75px");
		bottomIntensity.setWidth("75px");
		bottomIntensity.setValue(0.3f);
		stop.setWidth("60px");
		speed.setHeight("180px");

		left.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				left();
			}
		});

		top.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				top();
			}
		});

		right.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				right();
			}
		});

		bottom.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				bottom();
			}
		});

		stop.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				stop();
			}
		});

		speed.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				foreward();
			}
		});

		buttonPanel.addComponent(left, "top:100px;left:0px");
		buttonPanel.addComponent(leftIntensity, "top:130px;left:0px");

		buttonPanel.addComponent(top, "top:10px;left:100px");
		buttonPanel.addComponent(topIntensity, "top:40px;left:100px");
		
		buttonPanel.addComponent(right, "top:100px;left:200px");
		buttonPanel.addComponent(rightIntensity, "top:130px;left:200px");
		
		buttonPanel.addComponent(bottom, "top:200px;left:100px");
		buttonPanel.addComponent(bottomIntensity, "top:230px;left:100px");
		
		buttonPanel.addComponent(stop, "top:100px;left:107px");

		buttonPanel.addComponent(speed, "top:20px;right:10px");

		main.addComponent(buttonPanel, "top:100px;left:100px");
		return main;
	}

	private void left() {
		if (controller != null)
			controller.left(controller.getParameter().getLeft() + 1);
	}

	private void top() {
		if (controller != null)
			controller.top(controller.getParameter().getTop() + 1);
	}

	private void right() {
		if (controller != null)
			controller.right(controller.getParameter().getRight() + 1);
	}

	private void bottom() {
		if (controller != null)
			controller.bottom(controller.getParameter().getBottom() + 1);
	}

	private void stop() {
		if (controller != null)
			controller.stop();
	}

	private void foreward() {
		if (controller != null)
			controller.foreward(speed.getValue().intValue());
	}

	// @SuppressWarnings("serial")
	// @Override
	// public Component getContent() {
	// if (main == null) {
	// main = new VerticalLayout();
	// main.setMargin(true);
	//
	// JavaScript.getCurrent().addFunction("com.example.foo.myfunc",
	// new JavaScriptFunction() {
	// @Override
	// public void call(JSONArray arguments)
	// throws JSONException {
	// try {
	// String message = arguments.getString(0);
	// int value = arguments.getInt(1);
	// Notification.show("Message: " + message
	// + ", value: " + value);
	// } catch (JSONException e) {
	// Notification.show("Error: " + e.getMessage());
	// }
	// }
	// });
	// Link link = new Link("Send Message", new ExternalResource(
	// "javascript:com.example.foo.myfunc(prompt('Message'), 42)"));
	// main.addComponent(link);
	// }
	// return main;
	// }

}
