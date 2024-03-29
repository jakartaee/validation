/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation;

import java.util.List;

import jakarta.validation.spi.ValidationProvider;

/**
 * Determines the list of Jakarta Validation providers available in the runtime environment
 * <p>
 * Jakarta Validation providers are identified by the presence of
 * {@code META-INF/services/jakarta.validation.spi.ValidationProvider}
 * files following the Service Provider pattern described
 * <a href="http://docs.oracle.com/javase/8/docs/technotes/guides/jar/jar.html#Service_Provider">here</a>.
 * <p>
 * Each {@code META-INF/services/jakarta.validation.spi.ValidationProvider} file contains the
 * list of {@link ValidationProvider} implementations each of them representing a provider.
 * <p>
 * Implementations must be thread-safe.
 *
 * @author Emmanuel Bernard
 */
public interface ValidationProviderResolver {

	/**
	 * Returns a list of {@link ValidationProvider} available in the runtime environment.
	 *
	 * @return list of validation providers
	 */
	List<ValidationProvider<?>> getValidationProviders();
}
