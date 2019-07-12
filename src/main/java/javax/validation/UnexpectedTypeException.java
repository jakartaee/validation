/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

/**
 * Exception raised in the case that the constraint validator resolution
 * cannot determine a suitable validator for a given type.
 *
 * @author Hardy Ferentschik
 */
public class UnexpectedTypeException extends ConstraintDeclarationException {
	public UnexpectedTypeException(String message) {
		super( message );
	}

	public UnexpectedTypeException() {
		super();
	}

	public UnexpectedTypeException(String message, Throwable cause) {
		super( message, cause );
	}

	public UnexpectedTypeException(Throwable cause) {
		super( cause );
	}
}
