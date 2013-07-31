/**
 * Copyright 2000-2013 Vaadin Ldt.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contribution: Lunifera Gmbh - Added OSGI stuff. Original code from com.vaadin.data.validator.BeanValidator
 */
package org.lunifera.example.vaadin.osgi.beanvalidation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator.Context;
import javax.validation.Validation;
import javax.validation.ValidationProviderResolver;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.spi.ValidationProvider;

import org.apache.bval.jsr303.ApacheValidationProvider;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.BeanValidator;

@SuppressWarnings("serial")
public class OSGiAwareBeanValidator implements Validator {

	private static final long serialVersionUID = 1L;
	private static ValidatorFactory factory;

	private transient javax.validation.Validator javaxBeanValidator;
	private String propertyName;
	private Class<?> beanClass;
	private Locale locale;

	/**
	 * Simple implementation of a message interpolator context that returns
	 * fixed values.
	 */
	protected static class SimpleContext implements Context, Serializable {

		private final Object value;
		private final ConstraintDescriptor<?> descriptor;

		/**
		 * Create a simple immutable message interpolator context.
		 * 
		 * @param value
		 *            value being validated
		 * @param descriptor
		 *            ConstraintDescriptor corresponding to the constraint being
		 *            validated
		 */
		public SimpleContext(Object value, ConstraintDescriptor<?> descriptor) {
			this.value = value;
			this.descriptor = descriptor;
		}

		@Override
		public ConstraintDescriptor<?> getConstraintDescriptor() {
			return descriptor;
		}

		@Override
		public Object getValidatedValue() {
			return value;
		}
	}

	/**
	 * Creates a Vaadin {@link Validator} utilizing JSR-303 bean validation.
	 * 
	 * @param beanClass
	 *            bean class based on which the validation should be performed
	 * @param propertyName
	 *            property to validate
	 */
	public OSGiAwareBeanValidator(Class<?> beanClass, String propertyName) {
		this.beanClass = beanClass;
		this.propertyName = propertyName;
		locale = Locale.getDefault();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Validator#validate(java.lang.Object)
	 */
	@Override
	public void validate(final Object value) throws InvalidValueException {
		Set<?> violations = getJavaxBeanValidator().validateValue(beanClass,
				propertyName, value);
		if (violations.size() > 0) {
			List<String> exceptions = new ArrayList<String>();
			for (Object v : violations) {
				final ConstraintViolation<?> violation = (ConstraintViolation<?>) v;
				String msg = getJavaxBeanValidatorFactory()
						.getMessageInterpolator().interpolate(
								violation.getMessageTemplate(),
								new SimpleContext(value, violation
										.getConstraintDescriptor()), locale);
				exceptions.add(msg);
			}
			StringBuilder b = new StringBuilder();
			for (int i = 0; i < exceptions.size(); i++) {
				if (i != 0) {
					b.append("<br/>");
				}
				b.append(exceptions.get(i));
			}
			throw new InvalidValueException(b.toString());
		}
	}

	/**
	 * Sets the locale used for validation error messages.
	 * 
	 * Revalidation is not automatically triggered by setting the locale.
	 * 
	 * @param locale
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * Gets the locale used for validation error messages.
	 * 
	 * @return locale used for validation
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Returns the underlying JSR-303 bean validator factory used. A factory is
	 * created using {@link Validation} if necessary.
	 * 
	 * @return {@link ValidatorFactory} to use
	 */
	protected static ValidatorFactory getJavaxBeanValidatorFactory() {
		if (factory == null) {
			Configuration<?> config = Validation.byDefaultProvider()
					.providerResolver(new ValidationProviderResolver() {
						@Override
						public List<ValidationProvider<?>> getValidationProviders() {
							List<ValidationProvider<?>> providers = new ArrayList<>();
							providers.add(new ApacheValidationProvider());
							return providers;
						}
					}).configure();

			factory = config.buildValidatorFactory();
		}

		return factory;
	}

	/**
	 * Returns a shared Validator instance to use. An instance is created using
	 * the validator factory if necessary and thereafter reused by the
	 * {@link BeanValidator} instance.
	 * 
	 * @return the JSR-303 {@link javax.validation.Validator} to use
	 */
	protected javax.validation.Validator getJavaxBeanValidator() {
		if (javaxBeanValidator == null) {
			javaxBeanValidator = getJavaxBeanValidatorFactory().getValidator();
		}

		return javaxBeanValidator;
	}
}
