/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation.bootstrap;

import jakarta.validation.Configuration;
import jakarta.validation.NoProviderFoundException;
import jakarta.validation.ValidationException;
import jakarta.validation.ValidationProviderResolver;
import jakarta.validation.ValidatorFactory;

/**
 * Defines the state used to bootstrap Jakarta Validation and
 * creates a provider agnostic {@link Configuration}.
 *
 * @author Emmanuel Bernard
 */
public interface GenericBootstrap {

	/**
	 * Defines the provider resolution strategy.
	 * This resolver returns the list of providers evaluated
	 * to build the {@link Configuration}.
	 * <p>
	 * If no resolver is defined, the default {@link ValidationProviderResolver}
	 * implementation is used.
	 *
	 * @param resolver the {@code ValidationProviderResolver} to use for bootstrapping
	 * @return {@code this} following the chaining method pattern
	 */
	GenericBootstrap providerResolver(ValidationProviderResolver resolver);

	/**
	 * Returns a generic {@link Configuration} implementation.
	 * At this stage the provider used to build the {@link ValidatorFactory}
	 * is not defined.
	 * <p>
	 * The {@code Configuration} implementation is provided by the first provider
	 * returned by the {@link ValidationProviderResolver} strategy.
	 *
	 * @return a {@code Configuration} implementation compliant with the bootstrap state
	 * @throws NoProviderFoundException if no Jakarta Validation provider was found
	 * @throws ValidationException if a Jakarta Validation provider was found but the
	 *         {@code Configuration} object cannot be built; this is generally due to an
	 *         issue with the {@code ValidationProviderResolver}
	 */
	Configuration<?> configure();
}
