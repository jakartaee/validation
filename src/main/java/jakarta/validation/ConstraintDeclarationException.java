/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation;

import java.io.Serial;

/**
 * Exception raised if a constraint declaration is not legal.
 *
 * @author Emmanuel Bernard
 */
public class ConstraintDeclarationException extends ValidationException {

	@Serial
	private static final long serialVersionUID = 1535558847569422150L;

	public ConstraintDeclarationException(String message) {
		super( message );
	}

	public ConstraintDeclarationException() {
		super();
	}

	public ConstraintDeclarationException(String message, Throwable cause) {
		super( message, cause );
	}

	public ConstraintDeclarationException(Throwable cause) {
		super( cause );
	}
}
