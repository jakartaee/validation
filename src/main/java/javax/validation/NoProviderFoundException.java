/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

/**
 * Exception raised if no Jakarta Bean Validation provider could be found.
 *
 * @author Gunnar Morling
 *
 * @since 2.0
 */
public class NoProviderFoundException extends ValidationException {

	public NoProviderFoundException() {
		super();
	}

	public NoProviderFoundException(String message) {
		super( message );
	}

	public NoProviderFoundException(Throwable cause) {
		super( cause );
	}

	public NoProviderFoundException(String message, Throwable cause) {
		super( message, cause );
	}
}
