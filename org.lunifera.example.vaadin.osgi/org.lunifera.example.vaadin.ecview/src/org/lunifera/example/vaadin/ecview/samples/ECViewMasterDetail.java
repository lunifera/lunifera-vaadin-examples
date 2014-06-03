package org.lunifera.example.vaadin.ecview.samples;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.eclipse.emf.ecp.ecview.common.context.ContextException;
import org.eclipse.emf.ecp.ecview.common.model.core.YBeanSlot;
import org.eclipse.emf.ecp.ecview.common.model.core.YView;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YDateTime;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YGridLayout;
import org.eclipse.emf.ecp.ecview.extension.model.extension.YMasterDetail;
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

	private YTextField yTextfield3;

	private YNumericField yNumericField;

	private YDateTime yDateTime;

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

		YMasterDetail yMasterDetail = factory.createMasterDetail();
		yMasterDetail.setType(TablePojo.class);
		yLayout.addElement(yMasterDetail);

		// Create master element and detail element
		YTable yTable = createMasterElement();
		yTable.setType(TablePojo.class);
		yMasterDetail.setMasterElement(yTable);
		YGridLayout yDetailLayout = createDetailElement();
		yMasterDetail.setDetailElement(yDetailLayout);

		// ... and render
		VaadinRenderer renderer = new VaadinRenderer();
		renderer.render(layout, yView, null);

		// Set the values
		setValues(yMasterDetail);

	}

	protected YGridLayout createDetailElement() {
		YGridLayout yDetailLayout = factory.createGridLayout();
		yDetailLayout.setColumns(2);

		yTextfield3 = factory.createTextField();
		yTextfield3.setLabel("Name:");
		yDetailLayout.addElement(yTextfield3);

		yNumericField = factory.createNumericField();
		yNumericField.setLabel("Age:");
		yDetailLayout.addElement(yNumericField);

		yDateTime = factory.createDateTime();
		yDateTime.setLabel("Birthdate:");
		yDetailLayout.addElement(yDateTime);
		return yDetailLayout;
	}

	protected YTable createMasterElement() {
		YTable yTable = factory.createTable();
		yTable.setType(TablePojo.class);
		yTable.setSelectionType(YSelectionType.SINGLE);
		return yTable;
	}

	private void setValues(YMasterDetail yMasterDetail) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy, MM, dd");
		try {
			yMasterDetail
					.getCollection()
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
