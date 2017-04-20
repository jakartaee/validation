/*
 * Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.metadata;

/**
 * Describes a validated container element type.
 *
 * @author Guillaume Smet
 * @since 2.0
 */
public interface ContainerElementTypeDescriptor extends ElementDescriptor, CascadableDescriptor, ContainerDescriptor {

	/**
	 * @return the index of the type argument corresponding to this container element type
	 */
	Integer getTypeArgumentIndex();
}
