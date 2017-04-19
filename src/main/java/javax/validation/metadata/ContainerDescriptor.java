package javax.validation.metadata;

import java.util.Set;

import javax.validation.Valid;

/**
 * Represents an element that might be a container, thus allowing container element constraints.
 *
 * @author Guillaume Smet
 * @since 2.0
 */
public interface ContainerDescriptor {

	/**
	 * Returns the container element type descriptor for a given type argument.
	 * <p>
	 * Returns {@code null} if the container element type does not exist or has no constraint nor is marked as cascaded
	 * (see {@link #getConstrainedContainerElementTypes()})
	 *
	 * @param typeArgumentIndex type argument index evaluated
	 * @return the container element type descriptor
	 */
	ContainerElementTypeDescriptor getConstraintsForContainerElementType(int typeArgumentIndex);

	/**
	 * Returns a set of container element type descriptors having at least one constraint defined or marked as cascaded
	 * ({@link Valid}).
	 * <p>
	 * If no container element type matches, an empty set is returned.
	 *
	 * @return the set of {@link ContainerElementTypeDescriptor}s
	 */
	Set<ContainerElementTypeDescriptor> getConstrainedContainerElementTypes();
}
