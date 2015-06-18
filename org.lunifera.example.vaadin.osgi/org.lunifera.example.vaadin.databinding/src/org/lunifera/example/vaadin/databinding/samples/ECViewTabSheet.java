package org.lunifera.example.vaadin.databinding.samples;

import org.lunifera.ecview.core.common.context.ContextException;
import org.lunifera.ecview.core.common.model.core.YView;
import org.lunifera.ecview.core.extension.model.extension.YButton;
import org.lunifera.ecview.core.extension.model.extension.YDateTime;
import org.lunifera.ecview.core.extension.model.extension.YLabel;
import org.lunifera.ecview.core.extension.model.extension.YNumericField;
import org.lunifera.ecview.core.extension.model.extension.YTab;
import org.lunifera.ecview.core.extension.model.extension.YTabSheet;
import org.lunifera.ecview.core.extension.model.extension.YTextField;
import org.lunifera.ecview.core.extension.model.extension.YVerticalLayout;
import org.lunifera.ecview.core.extension.model.extension.listener.YButtonClickListener;
import org.lunifera.ecview.core.extension.model.extension.util.SimpleExtensionModelFactory;
import org.lunifera.runtime.web.ecview.presentation.vaadin.VaadinRenderer;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CssLayout;

/**
 *  Demonstrates a tabsheet with various elements done with ECView.
 *
 */
public class ECViewTabSheet extends CssLayout {

	private SimpleExtensionModelFactory factory = new SimpleExtensionModelFactory();
	private CssLayout rootLayout = new CssLayout();

	private AbsoluteLayout layout;
	private YLabel yLabel;

	public ECViewTabSheet() throws ContextException {
		setSizeFull();
		init();
	}

	@SuppressWarnings("rawtypes")
	public void init() throws ContextException {

		// Vaadin basis
		layout = new AbsoluteLayout();
		addComponent(layout);
		
		// ECView starts here: Create tabsheet ...
		YView yView = factory.createView();
		YVerticalLayout yLayout = factory.createVerticalLayout();
		yView.setContent(yLayout);
		YTabSheet yTabsheet = factory.createTabSheet();
		yLayout.getElements().add(yTabsheet);

		prepareTab1(yTabsheet);
		prepareTab2(yTabsheet);
		prepareTab3(yTabsheet);

		// ... and render
		VaadinRenderer renderer = new VaadinRenderer();
		renderer.render(layout, yView, null);

	}

	private void prepareTab3(YTabSheet yTabsheet) {
		// tab 3
		YTab yTab3 = factory.createTab();
		yTab3.setLabel("Tab 3");

		// tab 3 content
		YVerticalLayout yTab3Content = factory.createVerticalLayout();
		yTab3.setEmbeddable(yTab3Content);
		yTab3Content.addElement(factory.createBrowser());

		YLabel yLabel = factory.createLabel();
		yLabel.setLabel("This is a trap ;-)");
		yTab3Content.addElement(yLabel);

		YButton yButton = factory.createButton();
		yButton.setLabel("Push me");
		yButton.addClickListener(new YButtonClickListener() {
			@Override
			public void clicked(YButton yButton) {
				yButton.setEnabled(false);
			}
		});
		yTab3Content.addElement(yButton);
		yTabsheet.getTabs().add(yTab3);
	}

	private void prepareTab2(YTabSheet yTabsheet) {
		// tab 2
		YTab yTab2 = factory.createTab();
		yTab2.setLabel("Tab 2");

		// tab 2 content
		YVerticalLayout yTab2Content = factory.createVerticalLayout();
		yTab2.setEmbeddable(yTab2Content);
		YTextField yTextfield5 = factory.createTextField();
		yTextfield5.setLabel("Name");
		yTab2Content.addElement(yTextfield5);
		YNumericField yNumericField = factory.createNumericField();
		yNumericField.setLabel("Value");
		yTab2Content.addElement(yNumericField);
		YDateTime YDateTime = factory.createDateTime();
		YDateTime.setLabel("Valid until");
		yTab2Content.addElement(YDateTime);
		yTabsheet.getTabs().add(yTab2);
	}

	private void prepareTab1(YTabSheet yTabsheet) {
		// tab 1
		YTab yTab1 = factory.createTab();
		yTab1.setLabel("Tab 1");

		// tab 1 content
		YVerticalLayout yTab1Content = factory.createVerticalLayout();
		yTab1.setEmbeddable(yTab1Content);
		YTextField yTextfield1 = factory.createTextField();
		yTextfield1.setLabel("Name");
		yTab1Content.addElement(yTextfield1);
		YTextField yTextfield2 = factory.createTextField();
		yTextfield2.setLabel("First Name");
		yTab1Content.addElement(yTextfield2);
		YTextField yTextfield3 = factory.createTextField();
		yTextfield3.setLabel("Company");
		yTab1Content.addElement(yTextfield3);
		YTextField yTextfield4 = factory.createTextField();
		yTextfield4.setLabel("Job Title");
		yTab1Content.addElement(yTextfield4);

		yTabsheet.getTabs().add(yTab1);
	}

}
