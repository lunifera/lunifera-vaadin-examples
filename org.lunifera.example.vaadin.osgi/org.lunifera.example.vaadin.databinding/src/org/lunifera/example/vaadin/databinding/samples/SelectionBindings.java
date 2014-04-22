package org.lunifera.example.vaadin.databinding.samples;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.databinding.DataBindingContext;
import org.lunifera.runtime.web.vaadin.databinding.VaadinObservables;

import com.vaadin.data.Container;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.ListSelect;

public class SelectionBindings extends CssLayout {

	private AbsoluteLayout layout;
	private DataBindingContext dbc;
	private CheckBox readonly;
	private CheckBox enabled;
	private ListSelect list1;
	private ListSelect list2;

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

		// Text fields
		list1 = new ListSelect();
		list1.setMultiSelect(true);
		list1.setValue(new ArrayList<>());
		list1.setPropertyDataSource(new ObjectProperty<Set>(new HashSet<>(),
				Set.class));
		list1.setImmediate(true);
		list2 = new ListSelect();
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

		Container ds = list1.getContainerDataSource();
		ds.addItem("Row 1");
		ds.addItem("Row 2");
		ds.addItem("Row 3");
		ds.addItem("Row 4");
		ds.addItem("Row 5");
		ds.addItem("Row 6");
		ds.addItem("Row 7");

	}

	// protected static class ValueBean()

}
