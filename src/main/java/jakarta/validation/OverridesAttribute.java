/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.OverridesAttribute.List;

/**
 * Marks an attribute as overriding the attribute of a composing constraint.
 * Both attributes must share the same type.
 *
 * @author Emmanuel Bernard
 */
@Documented
@Retention(RUNTIME)
@Target({ METHOD })
@Repeatable(List.class)
public @interface OverridesAttribute {

	/**
	 * @return constraint type the attribute is overriding
	 */
	Class<? extends Annotation> constraint();

	/**
	 * Name of the Constraint attribute overridden.
	 * Defaults to the name of the attribute hosting {@code @OverridesAttribute}.
	 *
	 * @return name of constraint attribute overridden
	 */
	String name() default "";

	/**
	 * The index of the targeted constraint declaration when using
	 * multiple constraints of the same type.
	 * <p>
	 * The index represents the index of the constraint in the
	 * {@code value()} array.
	 * <p>
	 * By default, no index is defined and the single constraint declaration
	 * is targeted.
	 *
	 * @return constraint declaration index if multivalued annotation is used
	 */
	int constraintIndex() default -1;

	/**
	 * Defines several {@link OverridesAttribute} annotations on the same element
	 *
	 * @see jakarta.validation.OverridesAttribute
	 */
	@Documented
	@Target({ METHOD })
	@Retention(RUNTIME)
	public @interface List {

		OverridesAttribute[] value();
	}
}
