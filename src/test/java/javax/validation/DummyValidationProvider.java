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
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;

/**
 * @author Hardy Ferentschik
 */
public class DummyValidationProvider implements ValidationProvider {
	public static List<SoftReference<DummyValidationProvider>> createdValidationProviders =  new ArrayList<SoftReference<DummyValidationProvider>>();

	public DummyValidationProvider() {
		createdValidationProviders.add( new SoftReference<DummyValidationProvider>( this ) );
	}

	public Configuration createSpecializedConfiguration(BootstrapState state) {
		return null;
	}

	public Configuration<?> createGenericConfiguration(BootstrapState state) {
		return new DummyConfiguration();
	}

	public ValidatorFactory buildValidatorFactory(ConfigurationState configurationState) {
		return null;
	}


	public static class DummyConfiguration implements Configuration {

		public Configuration ignoreXmlConfiguration() {
			return null;
		}

		public Configuration messageInterpolator(MessageInterpolator interpolator) {
			return null;
		}

		public Configuration traversableResolver(TraversableResolver resolver) {
			return null;
		}

		public Configuration constraintValidatorFactory(ConstraintValidatorFactory constraintValidatorFactory) {
			return null;
		}

		public Configuration parameterNameProvider(ParameterNameProvider parameterNameProvider) {
			return null;
		}

		public Configuration addMapping(InputStream stream) {
			return null;
		}

		public Configuration addProperty(String name, String value) {
			return null;
		}

		public MessageInterpolator getDefaultMessageInterpolator() {
			return null;
		}

		public TraversableResolver getDefaultTraversableResolver() {
			return null;
		}

		public ConstraintValidatorFactory getDefaultConstraintValidatorFactory() {
			return null;
		}

		public ParameterNameProvider getDefaultParameterNameProvider() {
			return null;
		}

		public BootstrapConfiguration getBootstrapConfiguration() {
			return null;
		}

		public ValidatorFactory buildValidatorFactory() {
			return null;
		}
	}
}
