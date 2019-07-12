/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

/**
 * Base exception of all Jakarta Bean Validation "unexpected" problems.
 *
 * @author Emmanuel Bernard
 */
public class ValidationException extends RuntimeException {
	public ValidationException(String message) {
		super( message );
	}

	public ValidationException() {
		super();
	}

	public ValidationException(String message, Throwable cause) {
		super( message, cause );
	}

	public ValidationException(Throwable cause) {
		super( cause );
	}
}
