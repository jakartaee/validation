/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation;

import java.io.Serial;

/**
 * Exception raised in the case that the constraint validator resolution
 * cannot determine a suitable validator for a given type.
 *
 * @author Hardy Ferentschik
 */
public class UnexpectedTypeException extends ConstraintDeclarationException {

	@Serial
	private static final long serialVersionUID = 9142309406302395818L;

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
