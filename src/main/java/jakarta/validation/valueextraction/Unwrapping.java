/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation.valueextraction;

import jakarta.validation.Payload;

/**
 * Set of interfaces used in the {@code payload()} of a constraint to indicate if a value
 * should be unwrapped before validation.
 * <p>
 * This is used to overwrite the default configuration defined on the {@link ValueExtractor}.
 *
 * @author Guillaume Smet
 * @since 2.0
 */
public interface Unwrapping {

	/**
	 * Unwrap the value before validation.
	 *
	 * @since 2.0
	 */
	public interface Unwrap extends Payload {
	}

	/**
	 * Skip the unwrapping if it has been enabled on the {@link ValueExtractor} by the
	 * {@link UnwrapByDefault}
	 * annotation.
	 *
	 * @since 2.0
	 */
	public interface Skip extends Payload {
	}

}
