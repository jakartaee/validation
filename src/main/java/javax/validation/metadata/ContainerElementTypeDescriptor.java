/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.metadata;

import javax.validation.Valid;

/**
 * Describes a validated container element type, e.g. the element type of {@code List} if it
 * hosts at least one constraint or is marked with {@link Valid}.
 *
 * @author Guillaume Smet
 * @since 2.0
 */
public interface ContainerElementTypeDescriptor extends ElementDescriptor, CascadableDescriptor, ContainerDescriptor {

	/**
	 * Returns the index of the type argument corresponding to this container element type.
	 * @return the index of the type argument corresponding to this container element type
	 */
	Integer getTypeArgumentIndex();

	/**
	 * Returns the container class hosting this container element type.
	 * @return the container class hosting this container element type
	 */
	Class<?> getContainerClass();
}
