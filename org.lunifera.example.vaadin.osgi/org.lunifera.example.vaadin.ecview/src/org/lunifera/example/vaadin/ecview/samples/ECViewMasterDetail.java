package org.lunifera.example.vaadin.ecview.samples;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.eclipse.emf.ecp.ecview.common.context.ContextException;
import org.eclipse.emf.ecp.ecview.common.model.binding.YBindingSet;
import org.eclipse.emf.ecp.ecview.common.model.core.CoreModelFactory;
import org.eclipse.emf.ecp.ecview.common.model.core.YBeanSlot;
import org.eclipse.emf.ecp.ecview.common.model.core.YBeanSlotValueBindingEndpoint;
import org.eclipse.emf.ecp.ecview.common.model.core.YView;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YDateTime;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YHorizontalLayout;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YList;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YNumericField;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YSelectionType;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YTable;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YTextField;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YVerticalLayout;
import org.eclipse.emf.ecp.ecview.extension.model.extension.util.SimpleExtensionModelFactory;
import org.lunifera.runtime.web.ecview.presentation.vaadin.VaadinRenderer;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CssLayout;

/**
 * Demonstrates databinding between fields based on ECView and EMF bindings.
 */
@SuppressWarnings("serial")
public class ECViewMasterDetail extends CssLayout {

	private SimpleExtensionModelFactory factory = new SimpleExtensionModelFactory();

	private AbsoluteLayout layout;
	private YBeanSlot ySelectionSlot;

	public ECViewMasterDetail() throws ContextException {
		setSizeFull();
		init();
	}

	public void init() throws ContextException {

		// Vaadin basis ...
		layout = new AbsoluteLayout();
		layout.setSizeFull();
		addComponent(layout);

		YView yView = factory.createView();
		YVerticalLayout yLayout = factory.createVerticalLayout();
		yView.setContent(yLayout);

//		// create bindingset
//		YBindingSet yBindingSet = yView.getOrCreateBindingSet();

		// create layout
		prepareColumn1(yLayout, yBindingSet);
	}

	private void setValues(YList yList2, YTable yTable) {
		// set the values of the list and table
		yList2.getCollection().addAll(
				Arrays.asList("Row no 1", "Row no 2", "Row no 3", "Row no 4",
						"Row no 5"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy, MM, dd");
		try {
			yTable.getCollection()
					.addAll(Arrays.asList(
							new TablePojo("Alfred", 35, sdf
									.parse("1978, 10, 14")),
							new TablePojo("Bert", 25, sdf.parse("1988, 07, 30")),
							new TablePojo("Charlie", 15, sdf
									.parse("1999, 04, 17")), new TablePojo(
									"Daniel", 45, sdf.parse("1969, 02, 22"))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private YTable prepareColumn4(YView yView, YHorizontalLayout yLayout,
			YBindingSet yBindingSet) {
		
		
		
		// Master-Detail-Binding from table to fields
		YVerticalLayout yLayoutGH = factory.createVerticalLayout();
		yLayoutGH.setFillVertical(false);
		yLayout.addElement(yLayoutGH);

		YTable yTable = factory.createTable();
		yTable.setType(TablePojo.class);
		yTable.setSelectionType(YSelectionType.SINGLE);
		yLayoutGH.addElement(yTable);

		YTextField yTextfield3 = factory.createTextField();
		yTextfield3.setLabel("Name:");
		yLayoutGH.addElement(yTextfield3);

		YNumericField yNumericField = factory.createNumericField();
		yNumericField.setLabel("Age:");
		yLayoutGH.addElement(yNumericField);

		YDateTime yDateTime = factory.createDateTime();
		yDateTime.setLabel("Birthdate:");
		yLayoutGH.addElement(yDateTime);

		// create a beanslot that keeps the selected bean from the table
		ySelectionSlot = CoreModelFactory.eINSTANCE.createYBeanSlot();
		ySelectionSlot.setName("table-selection");
		ySelectionSlot.setValueType(TablePojo.class);
		yView.getBeanSlots().add(ySelectionSlot);

		// Bind table selection to beanslot
		YBeanSlotValueBindingEndpoint ySelectionSlotEndpoint = CoreModelFactory.eINSTANCE
				.createYBeanSlotValueBindingEndpoint();
		ySelectionSlotEndpoint.setBeanSlot(ySelectionSlot);
		ySelectionSlotEndpoint.setAttributePath("value");
		yBindingSet.addBinding(ySelectionSlotEndpoint,
				yTable.createSelectionEndpoint());

		// Bind bean slot to nameTextField
		YBeanSlotValueBindingEndpoint ySelection_NameEndpoint = CoreModelFactory.eINSTANCE
				.createYBeanSlotValueBindingEndpoint();
		ySelection_NameEndpoint.setBeanSlot(ySelectionSlot);
		ySelection_NameEndpoint.setAttributePath("value.name");
		yBindingSet.addBinding(yTextfield3.createValueEndpoint(),
				ySelection_NameEndpoint);

		// Bind bean slot to ageTextField
		YBeanSlotValueBindingEndpoint ySelection_AgeEndpoint = CoreModelFactory.eINSTANCE
				.createYBeanSlotValueBindingEndpoint();
		ySelection_AgeEndpoint.setBeanSlot(ySelectionSlot);
		ySelection_AgeEndpoint.setAttributePath("value.age");
		yBindingSet.addBinding(yNumericField.createValueEndpoint(),
				ySelection_AgeEndpoint);

		// Bind bean slot to dateField
		YBeanSlotValueBindingEndpoint ySelection_DateEndpoint = CoreModelFactory.eINSTANCE
				.createYBeanSlotValueBindingEndpoint();
		ySelection_DateEndpoint.setBeanSlot(ySelectionSlot);
		ySelection_DateEndpoint.setAttributePath("value.birthdate");
		yBindingSet.addBinding(yDateTime.createValueEndpoint(),
				ySelection_DateEndpoint);
		return yTable;
	}

	private YList prepareColumn3(YHorizontalLayout yLayout,
			YBindingSet yBindingSet) {
		// Lists
		YVerticalLayout yLayoutEF = factory.createVerticalLayout();
		yLayoutEF.setFillVertical(false);
		yLayout.addElement(yLayoutEF);

		// list 1
		YList yList1 = factory.createList();
		yList1.setType(String.class);
		yList1.setSelectionType(YSelectionType.MULTI);
		yList1.setLabel("E (binds to F):");
		yLayoutEF.getElements().add(yList1);
		// list 2
		YList yList2 = factory.createList();
		yList2.setType(String.class);
		yList2.setSelectionType(YSelectionType.MULTI);
		yList2.setLabel("F (binds to E):");
		yLayoutEF.getElements().add(yList2);

		// register the bindings
		yBindingSet.addBinding(yList1.createCollectionEndpoint(),
				yList2.createCollectionEndpoint());
		yBindingSet.addBinding(yList1.createMultiSelectionEndpoint(),
				yList2.createMultiSelectionEndpoint());
		return yList2;
	}

	private void prepareColumn2(YHorizontalLayout yLayout,
			YBindingSet yBindingSet) {
		// Datefields
		YVerticalLayout yLayoutCD = factory.createVerticalLayout();
		yLayoutCD.setFillVertical(false);
		yLayout.addElement(yLayoutCD);

		YDateTime yDatefield1 = factory.createDateTime();
		yDatefield1.setLabel("C (binds to D):");
		YDateTime yDatefield2 = factory.createDateTime();
		yDatefield2.setLabel("D (binds to C):");
		yLayoutCD.getElements().add(yDatefield1);
		yLayoutCD.getElements().add(yDatefield2);

		// dateField1 <--> dateField2
		yBindingSet.addBinding(yDatefield1.createValueEndpoint(),
				yDatefield2.createValueEndpoint());
	}

	private void prepareColumn1(YHorizontalLayout yLayout,
			YBindingSet yBindingSet) {
		// Textfields
		YVerticalLayout yLayoutAB = factory.createVerticalLayout();
		yLayoutAB.setFillVertical(false);
		yLayout.addElement(yLayoutAB);

		YTextField yTextfield1 = factory.createTextField();
		yTextfield1.setLabel("A (binds to B):");
		YTextField yTextfield2 = factory.createTextField();
		yTextfield2.setLabel("B (binds to A):");
		yLayoutAB.getElements().add(yTextfield1);
		yLayoutAB.getElements().add(yTextfield2);

		// textField1 <--> textField2
		yBindingSet.addBinding(yTextfield1.createValueEndpoint(),
				yTextfield2.createValueEndpoint());
	}

	public static class TablePojo {

		private String name;
		private int age;
		private Date birthdate;

		public TablePojo(String name, int age, Date birthdate) {
			super();
			this.name = name;
			this.age = age;
			this.birthdate = birthdate;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public Date getBirthdate() {
			return birthdate;
		}

		public void setBirthdate(Date birthdate) {
			this.birthdate = birthdate;
		}

	}

}
