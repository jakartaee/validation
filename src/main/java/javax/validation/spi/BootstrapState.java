package javax.validation.spi;

import javax.validation.ValidationProviderResolver;

/**
 * Defines the state used to bootstrap the Configuration
 *
 * @author Emmanuel Bernard
 * @author Sebastian Thomschke 
 */
public interface BootstrapState {
	/**
	 * User defined ValidationProviderResolver strategy instance or <code>null</code>
	 * if undefined.
	 *
	 * @return ValidationProviderResolver instance or null
	 */
	ValidationProviderResolver getValidationProviderResolver();

	/**
	 * Specification default ValidationProviderResolver strategy instance
	 * 
	 * @return default implementation of ValidationProviderResolver
	 */
	ValidationProviderResolver getDefaultValidationProviderResolver();
}
