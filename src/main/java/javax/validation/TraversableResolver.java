/*
 * Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

import java.lang.annotation.ElementType;

import static javax.validation.Path.*;

/**
 * Contract determining if a property can be accessed by the Bean Validation provider.
 * This contract is called for each property that is being either validated or cascaded.
 * <p>
 * A traversable resolver implementation must be thread-safe.
 *
 * @author Emmanuel Bernard
 */
public interface TraversableResolver {
	/**
	 * Determines if the Bean Validation provider is allowed to reach the property state.
	 *
	 * @param traversableObject object hosting {@code traversableProperty}
	 *        or {@code null} if {@code validateValue} is called
	 * @param traversableProperty the traversable property
	 * @param rootBeanType type of the root object passed to the Validator
	 *        or hosting the method or constructor validated
	 * @param pathToTraversableObject path from the root object to
	 *        {@code traversableObject}
	 *        (using the path specification defined by Bean Validator)
	 * @param elementType either {@code FIELD} or {@code METHOD}
	 * @return {@code true} if the Bean Validation provider is allowed to
	 *         reach the property state, {@code false} otherwise
	 */
	boolean isReachable(Object traversableObject,
						Node traversableProperty,
						Class<?> rootBeanType,
						Path pathToTraversableObject,
						ElementType elementType);

	/**
	 * Determines if the Bean Validation provider is allowed to cascade validation on
	 * the bean instance returned by the property value
	 * marked as {@code @Valid}.
	 * <p>
	 * Note that this method is called only if
	 * {@link #isReachable(Object, javax.validation.Path.Node, Class, Path, java.lang.annotation.ElementType)}
	 * returns {@code true} for the same set of arguments and if the property
	 * is marked as {@link Valid}.
	 *
	 * @param traversableObject object hosting {@code traversableProperty}
	 *        or {@code null} if {@code validateValue} is called
	 * @param traversableProperty the traversable property
	 * @param rootBeanType type of the root object passed to the Validator
	 *        or hosting the method or constructor validated
	 * @param pathToTraversableObject path from the root object to
	 *        {@code traversableObject}
	 *        (using the path specification defined by Bean Validator)
	 * @param elementType either {@code FIELD} or {@code METHOD}
	 * @return {@code true} if the Bean Validation provider is allowed to
	 *         cascade validation, {@code false} otherwise
	 */
	boolean isCascadable(Object traversableObject,
						 Node traversableProperty,
						 Class<?> rootBeanType,
						 Path pathToTraversableObject,
						 ElementType elementType);
}
