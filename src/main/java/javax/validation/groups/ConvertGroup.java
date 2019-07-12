/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.groups;

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

import javax.validation.Valid;
import javax.validation.groups.ConvertGroup.List;

/**
 * Converts group {@code from} to group {@code to} during cascading.
 * <p>
 * Can be used everywhere {@link Valid} is used and must be on an element
 * annotated with {@code Valid}.
 *
 * @author Emmanuel Bernard
 * @since 1.1
 */
@Target({ METHOD, FIELD, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
@Documented
public @interface ConvertGroup {

	/**
	 * The source group of this conversion.
	 * @return the source group of this conversion
	 */
	Class<?> from() default Default.class;

	/**
	 * The target group of this conversion.
	 * @return the target group of this conversion
	 */
	Class<?> to();

	/**
	 * Defines several {@link ConvertGroup} annotations
	 * on the same element.
	 */
	@Target({ METHOD, FIELD, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	public @interface List {

		ConvertGroup[] value();
	}
}
