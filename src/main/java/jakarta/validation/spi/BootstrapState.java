/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation.spi;

import jakarta.validation.Configuration;
import jakarta.validation.ValidationProviderResolver;

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

	/**
	 * Returns a {@link ValidationPackageOpener} that can relay JPMS package-opens
	 * through the {@code jakarta.validation} module to the validation
	 * provider module discovered during bootstrap.
	 * <p>
	 * In non-modular (classpath) environments, the returned opener is a
	 * no-op and can be safely invoked without effect.
	 *
	 * @return a {@code ValidationPackageOpener} instance; never {@code null}
	 * @since 4.0
	 */
	ValidationPackageOpener getPackageOpener();
}
