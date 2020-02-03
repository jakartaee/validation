/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Provides names for method and constructor parameters.
 * <p>
 * Used by the Jakarta Bean Validation runtime when creating constraint violation
 * objects for violated method constraints.
 * <p>
 * Implementations must be thread-safe.
 *
 * @author Gunnar Morling
 * @since 1.1
 */
public interface ParameterNameProvider {

	/**
	 * Returns the names of the parameters of the given constructor.
	 *
	 * @param constructor the constructor for which the parameter names shall be
	 *        retrieved; never {@code null}
	 * @return a list containing the names of the parameters of the given
	 *         constructor; may be empty but never {@code null}
	 */
	List<String> getParameterNames(Constructor<?> constructor);

	/**
	 * Returns the names of the parameters of the given method.
	 *
	 * @param method the method for which the parameter names shall be retrieved;
	 *        never {@code null}
	 * @return a list containing the names of the parameters of the given method;
	 *         may be empty but never {@code null}
	 */
	List<String> getParameterNames(Method method);
}
