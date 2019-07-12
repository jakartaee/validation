/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.metadata;

/**
 * Describes a validated method or constructor parameter.
 *
 * @author Gunnar Morling
 * @since 1.1
 */
public interface ParameterDescriptor extends ElementDescriptor, CascadableDescriptor,
		ContainerDescriptor {

	/**
	 * Returns this parameter's index within the parameter array of the method
	 * or constructor holding it.
	 *
	 * @return this parameter's index
	 */
	int getIndex();

	/**
	 * Returns this parameter's name as retrieved by the current parameter name
	 * resolver.
	 *
	 * @return this parameter's name
	 */
	String getName();
}
