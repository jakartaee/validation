/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.executable;

/**
 * Defines the types of executables targeted by an operation.
 *
 * @author Emmanuel Bernard
 * @since 1.1
 */
public enum ExecutableType {

	/**
	 * If the annotation using {@code ExecutableType} is on a type (class or interface),
	 * the behavior is equivalent to the annotation not being present.
	 * <p>
	 * If on a constructor, it is equivalent to {@link #CONSTRUCTORS}.
	 * <p>
	 * If on a non-getter method, it is equivalent to {@link #NON_GETTER_METHODS}.
	 * <p>
	 * If on a getter method, it is equivalent to {@link #GETTER_METHODS}.
	 */
	IMPLICIT,

	/**
	 * None of the executables.
	 * <p>
	 * Note that this option is equivalent to an empty list of executable types
	 * and is present to improve readability. If {@code NONE} and other types of executables
	 * are present in a list, {@code NONE} is ignored.
	 */
	NONE,

	/**
	 * All constructors.
	 */
	CONSTRUCTORS,

	/**
	 * All methods except the ones following the getter pattern. A getter according to the
	 * JavaBeans specification is a method whose:
	 * <ul>
	 *     <li>name starts with get, has a return type but no parameter</li>
	 *     <li>name starts with is, has a return type and is returning {@code boolean}.</li>
	 * </ul>
	 */
	NON_GETTER_METHODS,

	/**
	 * All methods following the getter pattern. A getter according to the
	 * JavaBeans specification is a method whose:
	 * <ul>
	 *     <li>name starts with get, has a return type but no parameter</li>
	 *     <li>name starts with is, has a return type and is returning {@code boolean}.</li>
	 * </ul>
	 */
	GETTER_METHODS,

	/**
	 * All executables (constructors and methods).
	 */
	ALL
}
