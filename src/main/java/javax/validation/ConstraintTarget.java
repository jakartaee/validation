package javax.validation;

/**
 * Defines the constraint target
 *
 * @since 1.1
 * @author Emmanuel Bernard <emmanuel@hibernate.org>
 */
public enum ConstraintTarget {
	/**
	 * Discover the type when no ambiguity is present
	 * <ul>
	 *     <li>if neither on a method nor a constructor, it implies the annotated element (type, field etc).</li>
	 *     <li>if on a method or constructor with no parameter, it implies {@code RETURN_VALUE}</li>
	 *     <li>if on a method with no return value ({@code void}), it implies {@code PARAMETERS}</li>
	 * </ul>
	 * Otherwise, {@code IMPLICIT} is not accepted and either {@code RETURN_VALUE} or
	 * {@code PARAMETERS} is required. This is the case for constructors with parameters
	 * and methods with parameters and return value.
	 */
	IMPLICIT,

	/**
	 * Constraint applies to the return value of a method or a constructor.
	 */
	RETURN_VALUE,

	/**
	 * Constraint applies to the parameters of a method or a constructor
	 */
	PARAMETERS
}
