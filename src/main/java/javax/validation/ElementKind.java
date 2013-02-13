package javax.validation;

/**
 * Enum of possible kinds of elements encountered in Bean Validation
 * Mostly elements that can be constrained and described in the metadata
 * but also elements that can be part of a {@link Path} and represented
 * by a {@link Path.Node}
 *
 * @author Emmanuel Bernard <emmanuel@hibernate.org>
 * @author Gunnar Morling
 *
 * @since 1.1
 */
public enum ElementKind {
	/**
	 * A Java Bean.
	 */
	BEAN,

	/**
	 * A property of a Java Bean.
	 */
	PROPERTY,

	/**
	 * A method.
	 */
	METHOD,

	/**
	 * A constructor.
	 */
	CONSTRUCTOR,

	/**
	 * A parameter of a method or constructor.
	 */
	PARAMETER,

	/**
	 * The return value of a method or constructor.
	 */
	RETURN_VALUE
}
