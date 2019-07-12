/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.metadata;

/**
 * Describes a Java Bean property hosting validation constraints.
 *
 * Constraints placed on the attribute and the getter of a given property
 * are all referenced.
 *
 * @author Emmanuel Bernard
 */
public interface PropertyDescriptor extends ElementDescriptor, CascadableDescriptor, ContainerDescriptor {

	/**
	 * Name of the property according to the Java Bean specification.
	 *
	 * @return property name
	 */
	String getPropertyName();
}
