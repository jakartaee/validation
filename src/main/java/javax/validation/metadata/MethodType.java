/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.metadata;

/**
 * Represents the type of a method: getter or non getter.
 *
 * @author Emmanuel Bernard
 * @since 1.1
 */
public enum MethodType {

	/**
	 * A method following the getter pattern. A getter according to the
	 * JavaBeans specification is a method whose:
	 * <ul>
	 *     <li>name starts with get, has a return type but no parameter</li>
	 *     <li>name starts with is, has a return type and is returning {@code boolean}.</li>
	 * </ul>
	 */
	GETTER,

	/**
	 * A method that does not follow the getter pattern. A getter according to the
	 * JavaBeans specification is a method whose:
	 * <ul>
	 *     <li>name starts with get, has a return type but no parameter</li>
	 *     <li>name starts with is, has a return type and is returning {@code boolean}.</li>
	 * </ul>
	 */
	NON_GETTER
}
