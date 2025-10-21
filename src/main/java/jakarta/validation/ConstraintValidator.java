/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation;

import java.lang.annotation.Annotation;

import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.metadata.ConstraintDescriptor;

/**
 * Defines the logic to validate a given constraint {@code A}
 * for a given object type {@code T}.
 * <p>
 * Implementations must comply to the following restriction:
 * <ul>
 *     <li>{@code T} must resolve to a non parameterized type</li>
 *     <li>or generic parameters of {@code T} must be unbounded
 *     wildcard types</li>
 * </ul>
 * <p>
 * The annotation {@link SupportedValidationTarget} can be put on a
 * {@code ConstraintValidator} implementation to mark it as supporting
 * cross-parameter constraints. Check out {@link SupportedValidationTarget}
 * and {@link Constraint} for more information.
 *
 * @param <A> the annotation type handled by an implementation
 * @param <T> the target type supported by an implementation
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public interface ConstraintValidator<A extends Annotation, T> {

	/**
	 * Initializes the validator in preparation for
	 * {@link #isValid(Object, ConstraintValidatorContext)} calls.
	 * The constraint descriptor for a given constraint declaration and the initialization
	 * context are passed.
	 * <p>
	 * Validation providers must use only this overload to initialize this instance
	 * before any use for validation.
	 * <p>
	 * The default implementation delegates to {@link #initialize(Annotation)} with
	 * the annotation returned by {@link ConstraintDescriptor#getAnnotation()}.
	 * An override can call that method explicitly to reuse its initialization logic.
	 *
	 * @param constraintDescriptor descriptor for a given constraint declaration
	 * @param context context in which the constraint is initialized, never {@code null}
	 * @since 4.0
	 */
	default void initialize(ConstraintDescriptor<A> constraintDescriptor, ConstraintValidatorInitializationContext context) {
		initialize( constraintDescriptor.getAnnotation() );
	}

	/**
	 * Initializes the validator in preparation for
	 * {@link #isValid(Object, ConstraintValidatorContext)} calls.
	 * The constraint annotation for a given constraint declaration
	 * is passed.
	 * <p>
	 * Validation providers initialize validators through
	 * {@link #initialize(ConstraintDescriptor, ConstraintValidatorInitializationContext)}.
	 * When that overload's default implementation is used, it calls this method
	 * before any use of this instance for validation.
	 * <p>
	 * The default implementation is a no-op.
	 *
	 * @param constraintAnnotation annotation instance for a given constraint declaration
	 */
	default void initialize(A constraintAnnotation) {
	}

	/**
	 * Implements the validation logic.
	 * The state of {@code value} must not be altered.
	 * <p>
	 * This method can be accessed concurrently, thread-safety must be ensured
	 * by the implementation.
	 *
	 * @param value object to validate
	 * @param context context in which the constraint is evaluated
	 *
	 * @return {@code false} if {@code value} does not pass the constraint
	 */
	boolean isValid(T value, ConstraintValidatorContext context);
}
