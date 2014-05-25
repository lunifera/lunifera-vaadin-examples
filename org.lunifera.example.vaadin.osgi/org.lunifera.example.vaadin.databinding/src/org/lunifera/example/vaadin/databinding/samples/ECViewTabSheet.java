package org.lunifera.example.vaadin.databinding.samples;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecp.ecview.common.context.ContextException;
import org.eclipse.emf.ecp.ecview.common.model.core.YView;
import org.eclipse.emf.ecp.ecview.common.model.datatypes.YDatadescription;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YNumericField;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YTab;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YTabSheet;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YVerticalLayout;
import org.eclipse.emf.ecp.ecview.extension.model.extension.util.SimpleExtensionModelFactory;
import org.lunifera.runtime.web.ecview.presentation.vaadin.VaadinRenderer;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public class ECViewTabSheet extends CssLayout {

	private SimpleExtensionModelFactory factory = new SimpleExtensionModelFactory();
	private CssLayout rootLayout = new CssLayout();

	private AbsoluteLayout layout;
	private DataBindingContext dbc;

	public ECViewTabSheet() throws ContextException {
		setSizeFull();
		init();
	}

	@SuppressWarnings("rawtypes")
	public void init() throws ContextException {

		dbc = new DataBindingContext();

		layout = new AbsoluteLayout();
		addComponent(layout);

		YView yView = factory.createView();
		YVerticalLayout yLayout = factory.createVerticalLayout();
		yLayout.setMargin(true);
		yView.setContent(yLayout);
		YTabSheet yTabsheet = factory.createTabSheet();
		yLayout.getElements().add(yTabsheet);

		// tab 1
		YTab yTab1 = factory.createTab();
		YDatadescription yDatadescription1 = factory.createDatadescription();
		yDatadescription1.setLabel("Tab 1");
		yTab1.setDatadescription(yDatadescription1);

		// tab 1 content
		YVerticalLayout yTab1Content = factory.createVerticalLayout();
		yTab1Content.setFillVertical(false);
		yTab1.setEmbeddable(yTab1Content);
		yTab1Content.addElement(factory.createTextField());
		yTab1Content.addElement(factory.createTextField());
		yTab1Content.addElement(factory.createTextField());
		yTab1Content.addElement(factory.createTextField());
		yTabsheet.getTabs().add(yTab1);

		// tab 2
		YTab yTab2 = factory.createTab();
		YDatadescription yDatadescription2 = factory.createDatadescription();
		yDatadescription2.setLabel("Tab 2");
		yTab2.setDatadescription(yDatadescription2);

		// tab 2 content
		YVerticalLayout yTab2Content = factory.createVerticalLayout();
		yTab2Content.setFillVertical(false);
		yTab2.setEmbeddable(yTab2Content);
		yTab2Content.addElement(factory.createNumericField());
		yTab2Content.addElement(factory.createTextField());
		yTab2Content.addElement(factory.createDateTime());
		yTabsheet.getTabs().add(yTab2);

		VaadinRenderer renderer = new VaadinRenderer();
		renderer.render(layout, yView, null);

	}

}
