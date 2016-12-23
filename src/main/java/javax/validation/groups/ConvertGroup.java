/*
 * Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.groups;

import javax.validation.Valid;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Converts group {@code from} to group {@code to} during cascading.
 * <p>
 * Can be used everywhere {@link Valid} is used and must be on an element
 * annotated with {@code Valid}.
 *
 * @author Emmanuel Bernard
 * @since 1.1
 */
@Target({ TYPE, METHOD, FIELD, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
public @interface ConvertGroup {

	/**
	 * Specifies from which group to convert.
	 */
	Class<?> from() default Default.class;

	/**
	 * Specifies to which group to convert.
	 */
	Class<?> to() default Default.class;

	/**
	 * Defines several {@link ConvertGroup} annotations
	 * on the same element.
	 */
	@Target({ TYPE, METHOD, FIELD, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {

		ConvertGroup[] value();
	}
}
