package javax.validation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;
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
	 * @param <T> The type hosting the method to validate.
	 * @param object The object on which the method to validate was invoked.
	 * @param method The method for which the parameter constraints shall be validated.
	 * @param parameterValues The values provided by the caller for the given method's
	 * parameters.
	 * @param groups The group or list of groups targeted for validation (defaults to
	 * {@link Default}).
	 *
	 * @return A set with the constraint violations caused by this validation.
	 *         Will be empty, if no error occurs, but never {@code null}.
	 *
	 * @throws IllegalArgumentException if {@code null} is passed for any of the parameters
	 * @throws ValidationException if a non recoverable error happens during the
	 * validation process
	 */
	<T> Set<ConstraintViolation<T>> validateParameters(T object, Method method, Object[] parameterValues, Class<?>... groups);

	/**
	 * Validates all return value constraints of the given method.
	 *
	 * @param <T> The type hosting the method to validate.
	 * @param object The object on which the method to validate was invoked.
	 * @param method The method for which the return value constraints shall be validated.
	 * @param returnValue The value returned by the given method.
	 * @param groups The group or list of groups targeted for validation (defaults to
	 * {@link Default}).
	 *
	 * @return A set with the constraint violations caused by this validation.
	 *         Will be empty, if no error occurs, but never {@code null}.
	 *
	 * @throws IllegalArgumentException if {@code null} is passed for any of the object,
	 * method or groups parameters
	 * @throws ValidationException if a non recoverable error happens during the
	 * validation process
	 */
	<T> Set<ConstraintViolation<T>> validateReturnValue(T object, Method method, Object returnValue, Class<?>... groups);

	/**
	 * Validates all constraints placed on the parameters of the given constructor.
	 *
	 * @param <T> The type hosting the constructor to validate.
	 * @param constructor The constructor for which the parameter constraints shall be validated.
	 * @param parameterValues The values provided by the caller for the given constructor's
	 * parameters.
	 * @param groups The group or list of groups targeted for validation (defaults to
	 * {@link Default}).
	 *
	 * @return A set with the constraint violations caused by this validation.
	 *         Will be empty, if no error occurs, but never {@code null}.
	 *
	 * @throws IllegalArgumentException if {@code null} is passed for any of the parameters
	 * @throws ValidationException if a non recoverable error happens during the
	 * validation process
	 */
	<T> Set<ConstraintViolation<T>> validateConstructorParameters(Constructor<? extends T> constructor, Object[] parameterValues, Class<?>... groups);

	/**
	 * Validates all return value constraints of the given constructor.
	 *
	 * @param <T> The type hosting the constructor to validate.
	 * @param constructor The constructor for which the return value constraints shall be validated.
	 * @param createdObject The object instantiated by the given method.
	 * @param groups The group or list of groups targeted for validation (defaults to
	 * {@link Default}).
	 *
	 * @return A set with the constraint violations caused by this validation.
	 *         Will be empty, if no error occurs, but never {@code null}.
	 *
	 * @throws IllegalArgumentException if {@code null} is passed for any of the parameters
	 * @throws ValidationException if a non recoverable error happens during the
	 * validation process
	 */
	<T> Set<ConstraintViolation<T>> validateConstructorReturnValue(Constructor<? extends T> constructor, T createdObject, Class<?>... groups);
}
