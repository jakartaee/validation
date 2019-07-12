/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.metadata;

import java.util.Set;

import javax.validation.Valid;

/**
 * Represents an element that might be a container, thus allowing container element
 * constraints.
 *
 * @author Guillaume Smet
 * @since 2.0
 */
public interface ContainerDescriptor {

	/**
	 * If this element is of a container type, e.g. {@code List} or {@code Map}, a set of
	 * descriptors of those container element types is returned, which are constrained or
	 * marked with {@link Valid}. A container element type is constrained, if it hosts at
	 * least one constraint.
	 * <p>
	 * In the context of properties and method return values, container element types of
	 * super-types are considered.
	 *
	 * @return the set of descriptors representing the container element types that are
	 * constrained or are marked with {@code Valid}. An empty set will be returned if this
	 * element is not of a container type or is of a container type but there are no
	 * container element types hosting constraints or marked with {@code Valid}.
	 */
	Set<ContainerElementTypeDescriptor> getConstrainedContainerElementTypes();
}
