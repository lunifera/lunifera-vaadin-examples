package org.lunifera.example.vaadin.databinding.samples;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.lunifera.runtime.web.vaadin.databinding.VaadinObservables;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;

public class LocaleBindings extends CssLayout {

	private AbsoluteLayout layout;
	private DataBindingContext dbc;
	private ComboBox localesCombobox;
	private WritableList localesContainer;

	public LocaleBindings() {
		setSizeFull();
		init();
	}

	public void init() {

		// Vaadin basis
		layout = new AbsoluteLayout();
		layout.setSizeFull();
		addComponent(layout);

		// binding context
		dbc = new DataBindingContext();

		createUI();
	}

	private void createUI() {
		localesCombobox = new ComboBox("Locales");
		localesCombobox.setWidth("200px");
		localesCombobox.setPropertyDataSource(new ObjectProperty<LocaleBean>(
				null, LocaleBean.class));
		localesCombobox.setContainerDataSource(new BeanItemContainer<>(
				LocaleBean.class));
		localesCombobox.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		localesCombobox.setItemCaptionPropertyId("name");
		localesCombobox.setItemIconPropertyId("iconResource");
		localesCombobox.setImmediate(true);
		layout.addComponent(localesCombobox, "top:50px;left:50px");

		// Bind the locales to the combobox
		localesContainer = new WritableList(LocaleProvider.getLocales(),
				LocaleBean.class);
		dbc.bindList(VaadinObservables.observeContainerItemSetContents(
				localesCombobox, LocaleBean.class), localesContainer);

		// Bind the selection to observer pojo
		SelectionObserver observer = new SelectionObserver();
		dbc.bindValue(PojoObservables.observeValue(observer, "selection"),
				VaadinObservables.observeValue(localesCombobox));

	}

	public static class LocaleProvider {

		public static List<LocaleBean> getLocales() {
			List<LocaleBean> locales = new ArrayList<>();

			for (Locale locale : Locale.getAvailableLocales()) {
				String iconPath = "icons/flags/"
						+ locale.getCountry().toLowerCase() + ".gif";
				locales.add(new LocaleBean(locale, locale.getDisplayName(),
						iconPath));
			}

			return locales;
		}

	}

	public static class LocaleBean {

		private Locale locale;
		private String name;
		private String icon;

		public LocaleBean(Locale locale, String name, String icon) {
			super();
			this.locale = locale;
			this.name = name;
			this.icon = icon;
		}

		/**
		 * @return the locale
		 */
		public Locale getLocale() {
			return locale;
		}

		/**
		 * @param locale
		 *            the locale to set
		 */
		public void setLocale(Locale locale) {
			this.locale = locale;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the icon
		 */
		public String getIcon() {
			return icon;
		}

		/**
		 * @return the icon resource
		 */
		public Resource getIconResource() {
			return new ThemeResource(icon);
		}

		/**
		 * @param icon
		 *            the icon to set
		 */
		public void setIcon(String icon) {
			this.icon = icon;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((locale == null) ? 0 : locale.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LocaleBean other = (LocaleBean) obj;
			if (locale == null) {
				if (other.locale != null)
					return false;
			} else if (!locale.equals(other.locale))
				return false;
			return true;
		}

	}

	public class SelectionObserver {
		private LocaleBean selection;
		private boolean updating;

		/**
		 * @return the selection
		 */
		public LocaleBean getSelection() {
			return selection;
		}

		/**
		 * @param selection
		 *            the selection to set
		 */
		public void setSelection(LocaleBean selection) {
			this.selection = selection;

			// TODO notify an eventlistener here
			if (!updating && selection != null) {
				changeLocale();
			}
		}

		private void changeLocale() {
			try {
				updating = true;
				LocaleBean currentSelection = selection;

				Locale.setDefault(selection.getLocale());

				localesContainer.clear();
				localesContainer.addAll(LocaleProvider.getLocales());
				localesCombobox.setValue(currentSelection);

			} finally {
				updating = false;
			}

		}

	}

}