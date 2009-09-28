package javax.validation.bootstrap;

import javax.validation.ValidationProviderResolver;
import javax.validation.Configuration;
import javax.validation.spi.ValidationProvider;

/**
 * Defines the state used to bootstrap Bean Validation and
 * creates a provider specific Configuration <code>T</code>.
 * <p/>
 * The specific Configuration is linked to the provider via the generic
 * parameter of the ValidationProvider implementation.
 * <p/>
 * The requested provider is the first provider instance assignable to
 * the requested provider type (known when ProviderSpecificBootstrap is built). 
 * The list of providers evaluated is returned by {@link ValidationProviderResolver}.
 * If no ValidationProviderResolver is defined, the
 * default ValidationProviderResolver strategy is used.
 *
 * @author Emmanuel Bernard
 */
public interface ProviderSpecificBootstrap<T extends Configuration<T>> {

	/**
	 * Optionally define the provider resolver implementation used.
	 * If not defined, use the default ValidationProviderResolver
	 *
	 * @param resolver ValidationProviderResolver implementation used
	 *
	 * @return <code>this</code> following the chaining method pattern
	 */
	public ProviderSpecificBootstrap<T> providerResolver(ValidationProviderResolver resolver);

	/**
	 * Determine the provider implementation suitable for T and delegate
	 * the creation of this specific Configuration subclass to the provider.
	 *
	 * @return a Configuration sub interface implementation
	 * @throws javax.validation.ValidationException if the Configuration object cannot be built
	 *                        this is generally due to an issue with the ValidationProviderResolver
	 */
	public T configure();
}
