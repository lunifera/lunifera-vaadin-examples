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

public class ECViewHorizontalLayout extends CssLayout {

	private SimpleExtensionModelFactory factory = new SimpleExtensionModelFactory();
	private CssLayout rootLayout = new CssLayout();

	private AbsoluteLayout layout;
	private DataBindingContext dbc;
	public ECViewHorizontalLayout() throws ContextException {
		setSizeFull();
		init();
	}

	public void init() throws ContextException {

		dbc = new DataBindingContext();

		layout = new AbsoluteLayout();
		layout.setSizeFull();
		addComponent(layout);

		YView yView = factory.createView();
		YHorizontalLayout yLayout = factory.createHorizontalLayout();
		yLayout.setSpacing(true);
		yLayout.setMargin(true);
		yView.setContent(yLayout);
		
		YTextField yText1 = factory.createTextField();
		YTextField yText2 = factory.createTextField();
		YTextField yText3 = factory.createTextField();
		YTextField yText4 = factory.createTextField();

		yLayout.getElements().add(yText1);
		yLayout.getElements().add(yText2);
		yLayout.getElements().add(yText3);
		yLayout.getElements().add(yText4);

		VaadinRenderer renderer = new VaadinRenderer();
		renderer.render(layout, yView, null);

	}

}
