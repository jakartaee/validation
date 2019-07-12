/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.metadata;

/**
 * Describes an element holding cross-parameter constraints of a method or constructor
 *
 * @author Emmanuel Bernard
 * @since 1.1
 */
public interface CrossParameterDescriptor extends ElementDescriptor {

	/**
	 * @return {@code Object[].class} - the type of the parameter array
	 */
	@Override
	Class<?> getElementClass();
}
