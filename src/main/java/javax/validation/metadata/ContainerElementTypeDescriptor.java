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
