/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.spi;

import javax.validation.Configuration;
import javax.validation.ValidationProviderResolver;

/**
 * Defines the state used to bootstrap the {@link Configuration}.
 *
 * @author Emmanuel Bernard
 * @author Sebastian Thomschke
 */
public interface BootstrapState {

	/**
	 * User defined {@code ValidationProviderResolver} strategy
	 * instance or {@code null} if undefined.
	 *
	 * @return ValidationProviderResolver instance or null
	 */
	ValidationProviderResolver getValidationProviderResolver();

	/**
	 * Specification default {@code ValidationProviderResolver}
	 * strategy instance.
	 *
	 * @return default implementation of ValidationProviderResolver
	 */
	ValidationProviderResolver getDefaultValidationProviderResolver();
}
