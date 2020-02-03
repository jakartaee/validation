/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.BootstrapConfiguration;
import jakarta.validation.ClockProvider;
import jakarta.validation.Configuration;
import jakarta.validation.ConstraintValidatorFactory;
import jakarta.validation.MessageInterpolator;
import jakarta.validation.ParameterNameProvider;
import jakarta.validation.TraversableResolver;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorContext;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.FooValidationProvider.DummyConfiguration;
import jakarta.validation.spi.BootstrapState;
import jakarta.validation.spi.ConfigurationState;
import jakarta.validation.spi.ValidationProvider;
import jakarta.validation.valueextraction.ValueExtractor;

/**
 * @author Hardy Ferentschik
 */
public class FooValidationProvider implements ValidationProvider<DummyConfiguration> {
	public static List<SoftReference<FooValidationProvider>> createdValidationProviders = new ArrayList<>();

	public FooValidationProvider() {
		createdValidationProviders.add( new SoftReference<>( this ) );
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
		public DummyConfiguration clockProvider(ClockProvider clockProvider) {
			return null;
		}

		@Override
		public DummyConfiguration addValueExtractor(ValueExtractor<?> extractor) {
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
		public ClockProvider getDefaultClockProvider() {
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
		public ClockProvider getClockProvider() {
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
