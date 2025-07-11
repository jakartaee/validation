/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation;

import java.io.Serial;

/**
 * Exception raised if a constraint definition is not legal.
 *
 * @author Emmanuel Bernard
 */
public class ConstraintDefinitionException extends ValidationException {

	@Serial
	private static final long serialVersionUID = 7844159745017246719L;

	public ConstraintDefinitionException(String message) {
		super( message );
	}

	public ConstraintDefinitionException() {
		super();
	}

	public ConstraintDefinitionException(String message, Throwable cause) {
		super( message, cause );
	}

	public ConstraintDefinitionException(Throwable cause) {
		super( cause );
	}
}
