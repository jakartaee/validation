/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty.List;

/**
 * The annotated element must not be {@code null} nor empty.
 * <p>
 * Supported types are:
 * <ul>
 * <li>{@code CharSequence} (length of character sequence is evaluated)</li>
 * <li>{@code Collection} (collection size is evaluated)</li>
 * <li>{@code Map} (map size is evaluated)</li>
 * <li>Array (array length is evaluated)</li>
 * </ul>
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 *
 * @since 2.0
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
@Documented
@Constraint
public @interface NotEmpty {

	String message() default "{jakarta.validation.constraints.NotEmpty.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * Defines several {@code @NotEmpty} constraints on the same element.
	 *
	 * @see NotEmpty
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		NotEmpty[] value();
	}
}
