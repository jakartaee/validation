/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation;

import java.util.Locale;

import jakarta.validation.metadata.ConstraintDescriptor;

/**
 * Interpolates a given constraint violation message.
 * <p>
 * Implementations should be as tolerant as possible on syntax errors.
 * Implementations must be thread-safe.
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public interface MessageInterpolator {

	/**
	 * Interpolates the message template based on the constraint validation context.
	 * <p>
	 * The locale is defaulted according to the {@code MessageInterpolator}
	 * implementation. See the implementation documentation for more detail.
	 *
	 * @param messageTemplate the message to interpolate
	 * @param context contextual information related to the interpolation
	 *
	 * @return interpolated error message
	 */
	String interpolate(String messageTemplate, Context context);

	/**
	 * Interpolates the message template based on the constraint validation context.
	 * The {@code Locale} used is provided as a parameter.
	 *
	 * @param messageTemplate the message to interpolate
	 * @param context contextual information related to the interpolation
	 * @param locale the locale targeted for the message
	 *
	 * @return interpolated error message
	 */
	String interpolate(String messageTemplate, Context context,  Locale locale);

	/**
	 * Information related to the interpolation context.
	 */
	interface Context {
		/**
		 * @return {@link ConstraintDescriptor} corresponding to the constraint being
		 * validated
		 */
		ConstraintDescriptor<?> getConstraintDescriptor();

		/**
		 * @return value being validated
		 */
		Object getValidatedValue();

		/**
		 * Returns an instance of the specified type allowing access to
		 * provider-specific APIs. If the Jakarta Bean Validation provider
		 * implementation does not support the specified class,
		 * {@link ValidationException} is thrown.
		 *
		 * @param type the class of the object to be returned
		 * @param <T> the type of the object to be returned
		 * @return an instance of the specified class
		 * @throws ValidationException if the provider does not support the call
		 *
		 * @since 1.1
		 */
		<T> T unwrap(Class<T> type);
	}
}
