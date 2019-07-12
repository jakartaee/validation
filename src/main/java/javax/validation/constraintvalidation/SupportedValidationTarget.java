/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.constraintvalidation;

import javax.validation.ConstraintValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Defines the target(s) a {@link ConstraintValidator} can validate.
 * <p>
 * A {@code ConstraintValidator} can target the (returned) element
 * annotated by the constraint, the array of parameters of a method
 * or constructor (aka cross-parameter) or both.
 * <p>
 * If {@code @SupportedValidationTarget} is not present, the
 * {@code ConstraintValidator} targets the (returned) element annotated
 * by the constraint.
 * <p>
 * A {@code ConstraintValidator} targeting cross-parameter must accept
 * {@code Object[]} (or {@code Object}) as the type of object it validates.
 *
 * @author Emmanuel Bernard
 * @since 1.1
 */
@Documented
@Target({ TYPE })
@Retention(RUNTIME)
public @interface SupportedValidationTarget {

	ValidationTarget[] value();
}
