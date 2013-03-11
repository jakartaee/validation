/*
* JBoss, Home of Professional Open Source
* Copyright 2012-2013, Red Hat, Inc. and/or its affiliates, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package javax.validation.executable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.groups.Default;

/**
 * Validates parameters and return values of methods and constructors.
 * Implementations of this interface must be thread-safe.
 *
 * @author Gunnar Morling
 * @since 1.1
 */
public interface ExecutableValidator {

	/**
	 * Validates all constraints placed on the parameters of the given method.
	 *
	 * @param <T> the type hosting the method to validate
	 * @param object the object on which the method to validate is invoked
	 * @param method the method for which the parameter constraints is validated
	 * @param parameterValues the values provided by the caller for the given method's
	 *        parameters
	 * @param groups the group or list of groups targeted for validation (defaults to
	 *        {@link Default})
	 * @return a set with the constraint violations caused by this validation;
	 *         will be empty if no error occurs, but never {@code null}
	 * @throws IllegalArgumentException if {@code null} is passed for any of the parameters
	 *         or if parameters don't match with each other
	 * @throws ValidationException if a non recoverable error happens during the
	 *         validation process
	 */
	<T> Set<ConstraintViolation<T>> validateParameters(T object,
													   Method method,
													   Object[] parameterValues,
													   Class<?>... groups);

	/**
	 * Validates all return value constraints of the given method.
	 *
	 * @param <T> the type hosting the method to validate
	 * @param object the object on which the method to validate is invoked
	 * @param method the method for which the return value constraints is validated
	 * @param returnValue the value returned by the given method
	 * @param groups the group or list of groups targeted for validation (defaults to
	 *        {@link Default})
	 * @return a set with the constraint violations caused by this validation;
	 *         will be empty if no error occurs, but never {@code null}
	 * @throws IllegalArgumentException if {@code null} is passed for any of the object,
	 *         method or groups parameters or if parameters don't match with each other
	 * @throws ValidationException if a non recoverable error happens during the
	 *         validation process
	 */
	<T> Set<ConstraintViolation<T>> validateReturnValue(T object,
														Method method,
														Object returnValue,
														Class<?>... groups);

	/**
	 * Validates all constraints placed on the parameters of the given constructor.
	 *
	 * @param <T> the type hosting the constructor to validate
	 * @param constructor the constructor for which the parameter constraints is validated
	 * @param parameterValues the values provided by the caller for the given constructor's
	 *        parameters
	 * @param groups the group or list of groups targeted for validation (defaults to
	 *        {@link Default})
	 * @return a set with the constraint violations caused by this validation;
	 *         Will be empty if no error occurs, but never {@code null}
	 * @throws IllegalArgumentException if {@code null} is passed for any of the parameters
	 *         or if parameters don't match with each other
	 * @throws ValidationException if a non recoverable error happens during the
	 *         validation process
	 */
	<T> Set<ConstraintViolation<T>> validateConstructorParameters(Constructor<? extends T> constructor,
																  Object[] parameterValues,
																  Class<?>... groups);

	/**
	 * Validates all return value constraints of the given constructor.
	 *
	 * @param <T> the type hosting the constructor to validate
	 * @param constructor the constructor for which the return value constraints is validated
	 * @param createdObject the object instantiated by the given method
	 * @param groups the group or list of groups targeted for validation (defaults to
	 *        {@link Default})
	 * @return a set with the constraint violations caused by this validation;
	 *         will be empty, if no error occurs, but never {@code null}
	 * @throws IllegalArgumentException if {@code null} is passed for any of the parameters
	 *         or if parameters don't match with each other
	 * @throws ValidationException if a non recoverable error happens during the
	 *         validation process
	 */
	<T> Set<ConstraintViolation<T>> validateConstructorReturnValue(Constructor<? extends T> constructor,
																   T createdObject,
																   Class<?>... groups);
}
