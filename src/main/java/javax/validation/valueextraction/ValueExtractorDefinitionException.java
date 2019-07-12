/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.valueextraction;

import javax.validation.ValidationException;

/**
 * Raised if a {@link ValueExtractor} definition is illegal, e.g. because it doesn't declare
 * its extracted value using {@link ExtractedValue}.
 *
 * @author Gunnar Morling
 *
 * @since 2.0
 */
public class ValueExtractorDefinitionException extends ValidationException {

	public ValueExtractorDefinitionException() {
		super();
	}

	public ValueExtractorDefinitionException(String message) {
		super( message );
	}

	public ValueExtractorDefinitionException(Throwable cause) {
		super( cause );
	}

	public ValueExtractorDefinitionException(String message, Throwable cause) {
		super( message, cause );
	}
}
