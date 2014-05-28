package org.lunifera.example.vaadin.databinding.samples;

import java.util.Arrays;

import org.eclipse.emf.ecp.ecview.common.context.ContextException;
import org.eclipse.emf.ecp.ecview.common.model.core.YView;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YAlignment;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YDateTime;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YGridLayout;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YGridLayoutCellStyle;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YList;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YSelectionType;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YTextField;
import org.eclipse.emf.ecp.ecview.extension.model.extension.util.SimpleExtensionModelFactory;
import org.lunifera.runtime.web.ecview.presentation.vaadin.VaadinRenderer;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CssLayout;

/**
 * Demonstrates a vertical layout done with ECView.
 * 
 */
public class ECViewGridLayout extends CssLayout {

	private SimpleExtensionModelFactory factory = new SimpleExtensionModelFactory();
	private CssLayout rootLayout = new CssLayout();

	private AbsoluteLayout layout;

	public ECViewGridLayout() throws ContextException {
		setSizeFull();
		init();
	}

	public void init() throws ContextException {

		// Vaadin basis
		layout = new AbsoluteLayout();
		layout.setSizeFull();
		addComponent(layout);

		// ECView: define elements ...
		YView yView = factory.createView();
		YGridLayout yLayout = factory.createGridLayout();
		yLayout.setColumns(3);
		yLayout.setFillVertical(false);
		yView.setContent(yLayout);

		// create textfields
		YTextField yTextfield1 = factory.createTextField();
		yTextfield1.setLabel("Item");
		YTextField yTextfield2 = factory.createTextField();
		yTextfield2.setLabel("Group");
		YTextField yTextfield3 = factory.createTextField();
		yTextfield3.setLabel("Serial Nr.");
		YTextField yTextfield4 = factory.createTextField();
		yTextfield4.setLabel("Owner");
		yLayout.getElements().add(yTextfield1);
		yLayout.getElements().add(yTextfield2);
		yLayout.getElements().add(yTextfield3);
		yLayout.getElements().add(yTextfield4);

		// fill a list into the grid
		YList yList1 = factory.createList();
		yList1.setType(String.class);
		yList1.setSelectionType(YSelectionType.MULTI);
		yList1.setLabel("List of items:");
		yLayout.getElements().add(yList1);
		yList1.getCollection().addAll(
				Arrays.asList("Row no 1", "Row no 2", "Row no 3", "Row no 4",
						"Row no 5"));
		
		// add a datefield
		YDateTime yDatefield1 = factory.createDateTime();
		yDatefield1.setLabel("Date:");
		yLayout.getElements().add(yDatefield1);
		
		// a few more textfields
		YTextField yTextfield5 = factory.createTextField();
		yTextfield5.setLabel("Colour");
		YTextField yTextfield6 = factory.createTextField();
		yTextfield6.setLabel("Size");
		YTextField yTextfield7 = factory.createTextField();
		yTextfield7.setLabel("Weight");
		YTextField yTextfield8 = factory.createTextField();
		yTextfield8.setLabel("Comment");
		yLayout.getElements().add(yTextfield5);
		yLayout.getElements().add(yTextfield6);
		yLayout.getElements().add(yTextfield7);
		yLayout.getElements().add(yTextfield8);

		// TODO: This here doesn't work as it should
//		YGridLayoutCellStyle yListStyle = yLayout
//				.addGridLayoutCellStyle(yList1);
//		yListStyle.setAlignment(YAlignment.UNDEFINED);
//		yListStyle.setAlignment(YAlignment.FILL_FILL);

		// ... and render
		VaadinRenderer renderer = new VaadinRenderer();
		renderer.render(layout, yView, null);

	}

}
