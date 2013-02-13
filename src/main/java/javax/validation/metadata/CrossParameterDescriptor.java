package javax.validation.metadata;

/**
 * Describes an element holding cross-parameter constraints of a method or constructor
 *
 * @author Emmanuel Bernard <emmanuel@hibernate.org>
 * @since 1.1
 */
public interface CrossParameterDescriptor extends ElementDescriptor {

	/**
	 * @return {@code Object[].class} - the array of parameters
	 */
	@Override
	Class<?> getElementClass();
}
