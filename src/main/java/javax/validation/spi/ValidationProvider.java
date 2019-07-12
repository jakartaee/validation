/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.spi;

import javax.validation.Configuration;
import javax.validation.ValidationException;
import javax.validation.ValidationProviderResolver;
import javax.validation.ValidatorFactory;

/**
 * Contract between the validation bootstrap mechanism and the provider engine.
 * <p>
 * Implementations must have a public no-arg constructor. The construction of a provider
 * should be as "lightweight" as possible.
 *
 * @param <T> the provider specific Configuration subclass which typically host provider's
 * additional configuration methods
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public interface ValidationProvider<T extends Configuration<T>> {

	/**
	 * Returns a {@link Configuration} instance implementing {@code T},
	 * the {@code Configuration} sub-interface.
	 * The returned {@code Configuration} instance must use the current provider
	 * ({@code this}) to build the {@code ValidatorFactory} instance.
	 *
	 * @param state bootstrap state
	 * @return specific {@code Configuration} implementation
	 */
	T createSpecializedConfiguration(BootstrapState state);

	/**
	 * Returns a {@link Configuration} instance. This instance is not bound to
	 * use the current provider. The choice of provider follows the algorithm described
	 * in {@code Configuration}
	 * <p>
	 * The {@link ValidationProviderResolver} used by {@code Configuration}
	 * is provided by {@code state}.
	 * If null, the default {@code ValidationProviderResolver} is used.
	 *
	 * @param state bootstrap state
	 * @return non specialized Configuration implementation
	 */
	Configuration<?> createGenericConfiguration(BootstrapState state);

	/**
	 * Build a {@link ValidatorFactory} using the current provider implementation.
	 * <p>
	 * The {@code ValidatorFactory} is assembled and follows the configuration passed
	 * via {@link ConfigurationState}.
	 * <p>
	 * The returned {@code ValidatorFactory} is properly initialized and ready for use.
	 *
	 * @param configurationState the configuration descriptor
	 * @return the instantiated {@code ValidatorFactory}
	 * @throws ValidationException if the {@code ValidatorFactory} cannot be built
	 */
	ValidatorFactory buildValidatorFactory(ConfigurationState configurationState);
}
