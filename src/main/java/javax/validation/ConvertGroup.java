package javax.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Convert group {@code from} to group {@code to} during cascading.
 *
 * @author Emmanuel Bernard
 * @since 1.1
 */
@Target({ TYPE, METHOD, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface ConvertGroup {
	Class<?> from();
	Class<?> to();

	/**
	 * Defines several {@code ConvertGroup} annotations
	 * on the same element
	 */
	public @interface List {
		ConvertGroup[] value();
	}
}
