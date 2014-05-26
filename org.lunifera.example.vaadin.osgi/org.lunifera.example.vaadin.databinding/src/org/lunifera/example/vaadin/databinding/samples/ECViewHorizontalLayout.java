package org.lunifera.example.vaadin.databinding.samples;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecp.ecview.common.context.ContextException;
import org.eclipse.emf.ecp.ecview.common.model.core.YView;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YHorizontalLayout;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YNumericField;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YTab;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YTabSheet;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YTextField;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YVerticalLayout;
import org.eclipse.emf.ecp.ecview.extension.model.extension.util.SimpleExtensionModelFactory;
import org.lunifera.runtime.web.ecview.presentation.vaadin.VaadinRenderer;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

/**
 * Demonstrates a horizontal layout done with ECView.
 * 
 */

public class ECViewHorizontalLayout extends CssLayout {

	private SimpleExtensionModelFactory factory = new SimpleExtensionModelFactory();
	private CssLayout rootLayout = new CssLayout();

	private AbsoluteLayout layout;

	public ECViewHorizontalLayout() throws ContextException {
		setSizeFull();
		init();
	}

	public void init() throws ContextException {

		// Vaadin basis
		layout = new AbsoluteLayout();
		layout.setSizeFull();
		addComponent(layout);
		
		// EVCiew starts here: define elements ...
		YView yView = factory.createView();
		YHorizontalLayout yLayout = factory.createHorizontalLayout();
		yView.setContent(yLayout);

		YTextField yTextfield1 = factory.createTextField();
		yTextfield1.setLabel("Item");
		YTextField yTextfield2 = factory.createTextField();
		yTextfield2.setLabel("Group");
		YTextField yTextfield3 = factory.createTextField();
		yTextfield3.setLabel("Serial Nr.");
		YTextField yTextfield4 = factory.createTextField();
		yTextfield4.setLabel("Owner");

		// ... add elements ...
		yLayout.getElements().add(yTextfield1);
		yLayout.getElements().add(yTextfield2);
		yLayout.getElements().add(yTextfield3);
		yLayout.getElements().add(yTextfield4);

		
		// ... and render
		VaadinRenderer renderer = new VaadinRenderer();
		renderer.render(layout, yView, null);

	}

}
