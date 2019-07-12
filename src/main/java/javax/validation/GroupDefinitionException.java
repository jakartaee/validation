/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

/**
 * Exception raised if a group definition is not legal.
 *
 * @author Emmanuel Bernard
 */
public class GroupDefinitionException extends ValidationException {
	public GroupDefinitionException(String message) {
		super( message );
	}

	public GroupDefinitionException() {
		super();
	}

	public GroupDefinitionException(String message, Throwable cause) {
		super( message, cause );
	}

	public GroupDefinitionException(Throwable cause) {
		super( cause );
	}
}
