/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.bootstrap;

import javax.validation.Configuration;
import javax.validation.ValidationException;
import javax.validation.ValidationProviderResolver;
import javax.validation.spi.ValidationProvider;

/**
 * Defines the state used to bootstrap Jakarta Bean Validation and
 * creates a provider specific {@link Configuration}
 * of type {@code T}.
 * <p>
 * The specific {@code Configuration} is linked to the provider via the generic
 * parameter of the {@link ValidationProvider} implementation.
 * <p>
 * The requested provider is the first provider instance assignable to
 * the requested provider type (known when {@link ProviderSpecificBootstrap} is built).
 * The list of providers evaluated is returned by {@link ValidationProviderResolver}.
 * If no {@code ValidationProviderResolver} is defined, the
 * default {@code ValidationProviderResolver} strategy is used.
 *
 * @param <T> the provider specific {@link Configuration} type
 *
 * @author Emmanuel Bernard
 */
public interface ProviderSpecificBootstrap<T extends Configuration<T>> {

	/**
	 * Optionally defines the provider resolver implementation used.
	 * If not defined, use the default {@link ValidationProviderResolver}
	 *
	 * @param resolver {@code ValidationProviderResolver} implementation used
	 *
	 * @return {@code this} following the chaining method pattern
	 */
	public ProviderSpecificBootstrap<T> providerResolver(
			ValidationProviderResolver resolver);

	/**
	 * Determines the provider implementation suitable for {@code T} and delegates
	 * the creation of this specific {@link Configuration} subclass to the provider.
	 *
	 * @return {@code Configuration} sub interface implementation
	 *
	 * @throws ValidationException if the {@code Configuration} object cannot be built;
	 *         this is generally due to an issue with the {@code ValidationProviderResolver}
	 */
	public T configure();
}
