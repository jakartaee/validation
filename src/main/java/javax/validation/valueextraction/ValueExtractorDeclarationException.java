/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.valueextraction;

import javax.validation.ValidationException;

/**
 * Raised if the configuration of {@link ValueExtractor} is illegal, e.g. because multiple
 * extractors for the same type and type use have been configured in
 * {@code META-INF/validation.xml}.
 *
 * @author Gunnar Morling
 *
 * @since 2.0
 */
public class ValueExtractorDeclarationException extends ValidationException {

	public ValueExtractorDeclarationException() {
		super();
	}

	public ValueExtractorDeclarationException(String message) {
		super( message );
	}

	public ValueExtractorDeclarationException(Throwable cause) {
		super( cause );
	}

	public ValueExtractorDeclarationException(String message, Throwable cause) {
		super( message, cause );
	}
}
