/*
 * Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

import java.io.InputStream;

import javax.validation.NonRegisteredValidationProvider.NonRegisteredConfiguration;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;
import javax.validation.valueextraction.ValueExtractor;

/**
 * A validation provider which is not registered with a service file and thus cannot be used with the default provider
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
		public NonRegisteredConfiguration clockProvider(ClockProvider clockProvider) {
			throw new UnsupportedOperationException( "Not implemented" );
		}

		@Override
		public NonRegisteredConfiguration addValueExtractor(ValueExtractor<?> extractor) {
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
		public ClockProvider getDefaultClockProvider() {
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
