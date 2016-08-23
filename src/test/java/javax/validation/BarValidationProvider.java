/*
* JBoss, Home of Professional Open Source
* Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package javax.validation;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import javax.validation.BarValidationProvider.DummyConfiguration;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;

/**
 * @author Hardy Ferentschik
 */
public class BarValidationProvider implements ValidationProvider<DummyConfiguration> {
	public static List<SoftReference<BarValidationProvider>> createdValidationProviders = new ArrayList<SoftReference<BarValidationProvider>>();

	public BarValidationProvider() {
		createdValidationProviders.add( new SoftReference<BarValidationProvider>( this ) );
	}

	@Override
	public DummyConfiguration createSpecializedConfiguration(BootstrapState state) {
		return null;
	}

	@Override
	public DummyConfiguration createGenericConfiguration(BootstrapState state) {
		return new DummyConfiguration();
	}

	@Override
	public ValidatorFactory buildValidatorFactory(ConfigurationState configurationState) {
		return new DummyValidatorFactory();
	}


	public static class DummyConfiguration implements Configuration<DummyConfiguration> {

		@Override
		public DummyConfiguration ignoreXmlConfiguration() {
			return null;
		}

		@Override
		public DummyConfiguration messageInterpolator(MessageInterpolator interpolator) {
			return null;
		}

		@Override
		public DummyConfiguration traversableResolver(TraversableResolver resolver) {
			return null;
		}

		@Override
		public DummyConfiguration constraintValidatorFactory(ConstraintValidatorFactory constraintValidatorFactory) {
			return null;
		}

		@Override
		public DummyConfiguration parameterNameProvider(ParameterNameProvider parameterNameProvider) {
			return null;
		}

		@Override
		public DummyConfiguration addMapping(InputStream stream) {
			return null;
		}

		@Override
		public DummyConfiguration addProperty(String name, String value) {
			return null;
		}

		@Override
		public MessageInterpolator getDefaultMessageInterpolator() {
			return null;
		}

		@Override
		public TraversableResolver getDefaultTraversableResolver() {
			return null;
		}

		@Override
		public ConstraintValidatorFactory getDefaultConstraintValidatorFactory() {
			return null;
		}

		@Override
		public ParameterNameProvider getDefaultParameterNameProvider() {
			return null;
		}

		@Override
		public BootstrapConfiguration getBootstrapConfiguration() {
			return null;
		}

		@Override
		public ValidatorFactory buildValidatorFactory() {
			return new DummyValidatorFactory();
		}
	}

	public static class DummyValidatorFactory implements ValidatorFactory {
		@Override
		public Validator getValidator() {
			return null;
		}

		@Override
		public ValidatorContext usingContext() {
			return null;
		}

		@Override
		public MessageInterpolator getMessageInterpolator() {
			return null;
		}

		@Override
		public TraversableResolver getTraversableResolver() {
			return null;
		}

		@Override
		public ConstraintValidatorFactory getConstraintValidatorFactory() {
			return null;
		}

		@Override
		public ParameterNameProvider getParameterNameProvider() {
			return null;
		}

		@Override
		public <T> T unwrap(Class<T> type) {
			return null;
		}

		@Override
		public void close() {
		}
	}
}
