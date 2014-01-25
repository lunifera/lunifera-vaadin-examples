package org.lunifera.example.vaadin.osgi.contentprovider;

import com.vaadin.ui.ProgressBar;

@SuppressWarnings("serial")
public class IntensityBar extends ProgressBar {

	public IntensityBar() {
		setStyleName("sharky-inensitybar");
	}

	@Override
	public void setValue(Float newValue) {
		super.setValue(1 - newValue);
	}

}
