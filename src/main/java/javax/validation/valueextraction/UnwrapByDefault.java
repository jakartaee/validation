/*
 * Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.valueextraction;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation used on a {@link ValueExtractor} to indicate that the value should be unwrapped by default.
 * <p>
 * This behavior can be changed per constraint using {@code &#064;Constraint(validateWrappedValue = NO )}.
 *
 * @author Guillaume Smet
 * @since 2.0
 */
@Target({ TYPE })
@Retention(RUNTIME)
@Documented
public @interface UnwrapByDefault {

}
