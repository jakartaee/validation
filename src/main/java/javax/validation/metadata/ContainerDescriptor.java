/*
 * Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.metadata;

import java.util.List;

/**
 * Represents an element that might be a container, thus allowing container element constraints.
 *
 * @author Guillaume Smet
 * @since 2.0
 */
public interface ContainerDescriptor {

	/**
	 * Returns the list of descriptors representing the container element
	 * types (e.g. type arguments of a generic type) of this container, if any,
	 * in the order of their declaration.
	 *
	 * @return the list of descriptors representing the container element types of
	 *         this container; an empty list will be returned if there are  no
	 *         container element types, but never {@code null}
	 */
	List<ContainerElementTypeDescriptor> getContainerElementTypes();
}
