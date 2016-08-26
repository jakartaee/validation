/*
* JBoss, Home of Professional Open Source
* Copyright 2016, Red Hat, Inc. and/or its affiliates, and individual contributors
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

import javax.validation.NonRegisteredValidationProvider.NonRegisteredConfiguration;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;

/**
 * A validation provide which is not registered with a service file and thus cannot be used with the default provider
 * resolver.
 *
 * @author Gunnar Morling
 */
public class NonRegisteredValidationProvider implements ValidationProvider<NonRegisteredConfiguration> {

	@Override
	public NonRegisteredConfiguration createSpecializedConfiguration(BootstrapState state) {
		return new NonRegisteredConfiguration();
	}

	@Override
	public NonRegisteredConfiguration createGenericConfiguration(BootstrapState state) {
		return new NonRegisteredConfiguration();
	}

	@Override
	public ValidatorFactory buildValidatorFactory(ConfigurationState configurationState) {
		return null;
	}

	public static class NonRegisteredConfiguration implements Configuration<NonRegisteredConfiguration> {

		@Override
		public NonRegisteredConfiguration ignoreXmlConfiguration() {
			throw new UnsupportedOperationException( "Not implemented" );
		}

		@Override
		public NonRegisteredConfiguration messageInterpolator(MessageInterpolator interpolator) {
			throw new UnsupportedOperationException( "Not implemented" );
		}

		@Override
		public NonRegisteredConfiguration traversableResolver(TraversableResolver resolver) {
			throw new UnsupportedOperationException( "Not implemented" );
		}

		@Override
		public NonRegisteredConfiguration constraintValidatorFactory(ConstraintValidatorFactory constraintValidatorFactory) {
			throw new UnsupportedOperationException( "Not implemented" );
		}

		@Override
		public NonRegisteredConfiguration parameterNameProvider(ParameterNameProvider parameterNameProvider) {
			throw new UnsupportedOperationException( "Not implemented" );
		}

		@Override
		public NonRegisteredConfiguration addMapping(InputStream stream) {
			throw new UnsupportedOperationException( "Not implemented" );
		}

		@Override
		public NonRegisteredConfiguration addProperty(String name, String value) {
			throw new UnsupportedOperationException( "Not implemented" );
		}

		@Override
		public MessageInterpolator getDefaultMessageInterpolator() {
			throw new UnsupportedOperationException( "Not implemented" );
		}

		@Override
		public TraversableResolver getDefaultTraversableResolver() {
			throw new UnsupportedOperationException( "Not implemented" );
		}

		@Override
		public ConstraintValidatorFactory getDefaultConstraintValidatorFactory() {
			throw new UnsupportedOperationException( "Not implemented" );
		}

		@Override
		public ParameterNameProvider getDefaultParameterNameProvider() {
			throw new UnsupportedOperationException( "Not implemented" );
		}

		@Override
		public BootstrapConfiguration getBootstrapConfiguration() {
			throw new UnsupportedOperationException( "Not implemented" );
		}

		@Override
		public ValidatorFactory buildValidatorFactory() {
			throw new UnsupportedOperationException( "Not implemented" );
		}
	}
}
