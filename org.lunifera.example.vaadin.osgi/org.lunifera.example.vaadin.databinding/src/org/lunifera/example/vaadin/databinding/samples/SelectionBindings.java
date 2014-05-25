package org.lunifera.example.vaadin.databinding.samples;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.databinding.DataBindingContext;
import org.lunifera.runtime.web.vaadin.components.fields.NumberField;
import org.lunifera.runtime.web.vaadin.databinding.VaadinObservables;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public class SelectionBindings extends CssLayout {

	private AbsoluteLayout layout;
	private DataBindingContext dbc;
	private CheckBox readonly;
	private CheckBox enabled;
	private ListSelect list1;
	private ListSelect list2;
	private Table table;
	private TextField text1;
	private TextField text2;
	private TextField text3;
	private ListSelect list3;

	public SelectionBindings() {
		setSizeFull();
		init();
	}

	@SuppressWarnings("rawtypes")
	public void init() {

		dbc = new DataBindingContext();

		layout = new AbsoluteLayout();
		layout.setSizeFull();
		addComponent(layout);

		readonly = new CheckBox("Readonly");
		readonly.setImmediate(true);
		enabled = new CheckBox("Enabled");
		enabled.setValue(true);
		enabled.setImmediate(true);
		layout.addComponent(readonly, "top:25px;left:800px");
		layout.addComponent(enabled, "top:25px;left:900px");

		// lists
		list1 = new ListSelect("Synchronized multi selection");
		list1.setMultiSelect(true);
		list1.setWidth("200px");
		list1.setValue(new ArrayList<>());
		list1.setPropertyDataSource(new ObjectProperty<Set>(new HashSet<>(),
				Set.class));
		list1.setImmediate(true);
		list2 = new ListSelect();
		list2.setWidth("200px");
		list2.setMultiSelect(true);
		list2.setPropertyDataSource(new ObjectProperty<Set>(new HashSet<>(),
				Set.class));
		list2.setImmediate(true);

		layout.addComponent(list1, "top:50px;left:50px");
		layout.addComponent(list2, "top:50px;left:500px");

		// bind the content of list1 to list2 and vise verse
		dbc.bindList(VaadinObservables.observeContainerItemSetContents(list1,
				String.class), VaadinObservables
				.observeContainerItemSetContents(list2, String.class));

		// bind the selection of list1 to list2 and vise verse
		dbc.bindSet(VaadinObservables.observeMultiSelectionAsSet(list1,
				String.class), VaadinObservables.observeMultiSelectionAsSet(
				list2, String.class));

		// bind readonly and enabled
		dbc.bindValue(VaadinObservables.observeReadonly(list2),
				VaadinObservables.observeValue(readonly));
		dbc.bindValue(VaadinObservables.observeEnabled(list2),
				VaadinObservables.observeValue(enabled));

		Container ds1 = list1.getContainerDataSource();
		ds1.addItem("Row 1");
		ds1.addItem("Row 2");
		ds1.addItem("Row 3");
		ds1.addItem("Row 4");
		ds1.addItem("Row 5");
		ds1.addItem("Row 6");
		ds1.addItem("Row 7");

		// table
		table = new Table("Master-Detail binding");
		table.setImmediate(true);
		table.setHeight("200px");
		table.setSelectable(true);
		table.setWidth("400px");
		table.setPageLength(7);
		table.setPropertyDataSource(new ObjectProperty<Bar>(null, Bar.class));
		layout.addComponent(table, "top:250px;left:50px");

		BeanItemContainer<Bar> ds2 = new BeanItemContainer<>(Bar.class);
		ds2.addBean(new Bar("Joe", 1234, new Foo("Jackman")));
		ds2.addBean(new Bar("Frank", 2345, new Foo("Falkner")));
		ds2.addBean(new Bar("Alice", 3456, new Foo("Aaronson")));
		ds2.addBean(new Bar("Bob", 4567, new Foo("Builder")));
		ds2.addBean(new Bar("Eve", 6789, new Foo("Evans")));
		ds2.addBean(new Bar("Marvin", 7777, new Foo("Miller")));
		ds2.addBean(new Bar("Tim", 99999, new Foo("Taylor")));
		table.setContainerDataSource(ds2);
		table.setVisibleColumns("name", "number");

		// Text fields
		text1 = new TextField("Bar#name");
		text1.setNullRepresentation("");
		text1.setImmediate(true);
		text1.setPropertyDataSource(new ObjectProperty<String>(null,
				String.class));
		text2 = new NumberField("Bar#number");
		text2.setNullRepresentation("");
		text2.setImmediate(true);
		text2.setPropertyDataSource(new ObjectProperty<Integer>(0,
				Integer.class));
		text3 = new TextField("Bar -> Foo#name");
		text3.setNullRepresentation("");
		text3.setImmediate(true);
		text3.setPropertyDataSource(new ObjectProperty<String>(null,
				String.class));

		layout.addComponent(text1, "top:250px;left:500px");
		layout.addComponent(text2, "top:300px;left:500px");
		layout.addComponent(text3, "top:350px;left:500px");

		// bind the selection of list1 to list2 and vise verse
		dbc.bindValue(VaadinObservables.observeSingleSelectionDetailValue(
				table, Bar.class, "name"), VaadinObservables
				.observeValue(text1));
		dbc.bindValue(VaadinObservables.observeSingleSelectionDetailValue(
				table, Bar.class, "number"), VaadinObservables
				.observeValue(text2));
		dbc.bindValue(VaadinObservables.observeSingleSelectionDetailValue(
				table, Bar.class, "myfoo.name"), VaadinObservables
				.observeValue(text3));

		// bind readonly and enabled
		dbc.bindValue(VaadinObservables.observeReadonly(table),
				VaadinObservables.observeValue(readonly));
		dbc.bindValue(VaadinObservables.observeEnabled(table),
				VaadinObservables.observeValue(enabled));

		// lists
		list3 = new ListSelect("List options bound to list caption");
		list3.setWidth("200px");
		list3.setPropertyDataSource(new ObjectProperty<String>("", String.class));
		list3.setImmediate(true);

		layout.addComponent(list3, "top:500px;left:50px");
		Container ds3 = list3.getContainerDataSource();
		ds3.addItem("The caption of this list");
		ds3.addItem("follows the selection");
		ds3.addItem("in this box here ...");

		dbc.bindValue(
				VaadinObservables.observeSingleSelection(list3, String.class),
				VaadinObservables.observeCaption(list3));
	}

	// protected static class ValueBean()

}
