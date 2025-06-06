/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation;

import java.io.Serial;

/**
 * Exception raised if no Jakarta Validation provider could be found.
 *
 * @author Gunnar Morling
 *
 * @since 2.0
 */
public class NoProviderFoundException extends ValidationException {

	@Serial
	private static final long serialVersionUID = -2200340693485056605L;

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
