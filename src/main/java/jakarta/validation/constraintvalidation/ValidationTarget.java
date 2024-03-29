/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation.constraintvalidation;

import jakarta.validation.ConstraintValidator;

/**
 * List of possible targets for a {@link ConstraintValidator}.
 *
 * @author Emmanuel Bernard
 * @since 1.1
 */
public enum ValidationTarget {

	/**
	 * (Returned) element annotated by the constraint.
	 */
	ANNOTATED_ELEMENT,

	/**
	 * Array of parameters of the annotated method or constructor (aka cross-parameter).
	 */
	PARAMETERS
}
