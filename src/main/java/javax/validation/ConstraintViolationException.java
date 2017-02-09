/*
 * Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Reports the result of constraint violations.
 *
 * @author Emmanuel Bernard
 * @author Gunnar Morling
 */
public class ConstraintViolationException extends ValidationException {

	private final Set<ConstraintViolation<?>> constraintViolations;

	/**
	 * Creates a constraint violation report.
	 *
	 * @param message error message
	 * @param constraintViolations {@code Set} of {@link ConstraintViolation}
	 */
	public ConstraintViolationException(String message,
										Set<? extends ConstraintViolation<?>> constraintViolations) {
		super( message );

		if ( constraintViolations == null ) {
			this.constraintViolations = null;
		}
		else {
			this.constraintViolations = new HashSet<>( constraintViolations );
		}
	}

	/**
	 * Creates a constraint violation report.
	 *
	 * @param constraintViolations {@code Set} of {@link ConstraintViolation}
	 */
	public ConstraintViolationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
		this( toString( constraintViolations ), constraintViolations );
	}

	/**
	 * Set of constraint violations reported during a validation.
	 *
	 * @return {@code Set} of {@link ConstraintViolation}
	 */
	public Set<ConstraintViolation<?>> getConstraintViolations() {
		return constraintViolations;
	}

	private static String toString(Set<? extends ConstraintViolation<?>> constraintViolations) {
		return constraintViolations.stream()
			.map( cv -> cv == null ? "null" : cv.getPropertyPath() + ": " + cv.getMessage() )
			.collect( Collectors.joining( ", " ) );
	}
}
