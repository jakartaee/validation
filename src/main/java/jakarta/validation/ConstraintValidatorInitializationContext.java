/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation;

import java.time.Clock;

/**
 * Provides contextual data and operations when initializing a constraint validator.
 *
 * @author Marko Bekhta
 * @since 4.0
 */
public interface ConstraintValidatorInitializationContext {

	/**
	 * Returns the provider for obtaining the current time in the form of a {@link Clock},
	 * e.g. when validating the {@code Future} and {@code Past} constraints.
	 *
	 * @return the provider for obtaining the current time, never {@code null}. If no
	 * specific provider has been configured during bootstrap, a default implementation using
	 * the current system time and the current default time zone as returned by
	 * {@link Clock#systemDefaultZone()} will be returned.
	 */
	ClockProvider getClockProvider();

	/**
	 * Returns an instance of the specified type allowing access to
	 * provider-specific APIs. If the Jakarta Validation provider
	 * implementation does not support the specified class,
	 * {@link ValidationException} is thrown.
	 *
	 * @param type the class of the object to be returned
	 * @param <T> the type of the object to be returned
	 * @return an instance of the specified class
	 * @throws ValidationException if the provider does not support the call
	 * @since 4.0
	 */
	<T> T unwrap(Class<T> type);
}
