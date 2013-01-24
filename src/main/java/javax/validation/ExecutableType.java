package javax.validation;

/**
 * Defines the types of executables targeted by an operation.
 *
 * @since 1.1
 * @author Emmanuel Bernard <emmanuel@hibernate.org>
 */
public enum ExecutableType {
	/**
	 * None of the executables.
	 * Note that this option is equivalent to an empty list of executable types
	 * and is present to improve readability. If {@code NONE} and other types of executables
	 * are present in a list, {@code NONE} is ignored.
	 */
	NONE,

	/**
	 * All constructors
	 */
	CONSTRUCTORS,

	/**
	 * All methods except the ones following the getter pattern. A getter according ot the
	 * JavaBeans specification is a method whose:
	 * <ul>
	 *     <li>name starts with get, has a return type but no parameter</li>
	 *     <li>name starts with is, has a return type and is returning {@code Boolean}.</li>
	 * </ul>
	 */
	NON_GETTER_METHODS,

	/**
	/**
	 * All methods following the getter pattern. A getter according ot the
	 * JavaBeans specification is a method whose:
	 * <ul>
	 *     <li>name starts with get, has a return type but no parameter</li>
	 *     <li>name starts with is, has a return type and is returning {@code Boolean}.</li>
	 * </ul>
	 */
	GETTER_METHODS,

	/**
	 * All executables (constructors and methods).
	 */
	ALL
}
