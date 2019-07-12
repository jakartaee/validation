/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

import java.util.Set;

import javax.validation.executable.ExecutableValidator;
import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;

/**
 * Validates bean instances. Implementations of this interface must be thread-safe.
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 * @author Gunnar Morling
 */
public interface Validator {

	/**
	 * Validates all constraints on {@code object}.
	 *
	 * @param object object to validate
	 * @param groups the group or list of groups targeted for validation (defaults to
	 *        {@link Default})
	 * @param <T> the type of the object to validate
	 * @return constraint violations or an empty set if none
	 * @throws IllegalArgumentException if object is {@code null}
	 *         or if {@code null} is passed to the varargs groups
	 * @throws ValidationException if a non recoverable error happens
	 *         during the validation process
	 */
	<T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups);

	/**
	 * Validates all constraints placed on the property of {@code object}
	 * named {@code propertyName}.
	 *
	 * @param object object to validate
	 * @param propertyName property to validate (i.e. field and getter constraints)
	 * @param groups the group or list of groups targeted for validation (defaults to
	 *        {@link Default})
	 * @param <T> the type of the object to validate
	 * @return constraint violations or an empty set if none
	 * @throws IllegalArgumentException if {@code object} is {@code null},
	 *         if {@code propertyName} is {@code null}, empty or not a valid object property
	 *         or if {@code null} is passed to the varargs groups
	 * @throws ValidationException if a non recoverable error happens
	 *         during the validation process
	 */
	<T> Set<ConstraintViolation<T>> validateProperty(T object,
													 String propertyName,
													 Class<?>... groups);

	/**
	 * Validates all constraints placed on the property named {@code propertyName}
	 * of the class {@code beanType} would the property value be {@code value}.
	 * <p>
	 * {@link ConstraintViolation} objects return {@code null} for
	 * {@link ConstraintViolation#getRootBean()} and
	 * {@link ConstraintViolation#getLeafBean()}.
	 *
	 * @param beanType the bean type
	 * @param propertyName property to validate
	 * @param value property value to validate
	 * @param groups the group or list of groups targeted for validation (defaults to
	 *        {@link Default}).
	 * @param <T> the type of the object to validate
	 * @return constraint violations or an empty set if none
	 * @throws IllegalArgumentException if {@code beanType} is {@code null},
	 *         if {@code propertyName} is {@code null}, empty or not a valid object property
	 *         or if {@code null} is passed to the varargs groups
	 * @throws ValidationException if a non recoverable error happens
	 *         during the validation process
	 */
	<T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,
												  String propertyName,
												  Object value,
												  Class<?>... groups);

	/**
	 * Returns the descriptor object describing bean constraints.
	 * <p>
	 * The returned object (and associated objects including
	 * {@link ConstraintDescriptor}s) are immutable.
	 *
	 * @param clazz class or interface type evaluated
	 * @return the bean descriptor for the specified class
	 * @throws IllegalArgumentException if clazz is {@code null}
	 * @throws ValidationException if a non recoverable error happens
	 *         during the metadata discovery or if some
	 *         constraints are invalid.
	 */
	BeanDescriptor getConstraintsForClass(Class<?> clazz);

	/**
	 * Returns an instance of the specified type allowing access to
	 * provider-specific APIs.
	 * <p>
	 * If the Jakarta Bean Validation provider implementation does not support
	 * the specified class, {@link ValidationException} is thrown.
	 *
	 * @param type the class of the object to be returned
	 * @param <T> the type of the object to be returned
	 * @return an instance of the specified class
	 * @throws ValidationException if the provider does not support the call
	 */
	<T> T unwrap(Class<T> type);

	/**
	 * Returns the contract for validating parameters and return values of methods
	 * and constructors.
	 *
	 * @return contract for method and constructor validation
	 *
	 * @since 1.1
	 */
	ExecutableValidator forExecutables();
}
