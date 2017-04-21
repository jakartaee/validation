/*
 * Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.metadata;

import javax.validation.valueextraction.ValueExtractor;

/**
 * The unwrapping behavior that can be applied to a specific constraint.
 *
 * @author Guillaume Smet
 * @since 2.0
 */
public enum ValidateUnwrappedValue {

	/**
	 * Respects the default behavior of the {@link ValueExtractor}.
	 */
	DEFAULT,

	/**
	 * The value is unwrapped before validation.
	 */
	YES,

	/**
	 * The value is not unwrapped before validation.
	 */
	NO;

}
