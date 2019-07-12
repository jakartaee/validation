/*
 * Jakarta Bean Validation API
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
 * Marks a {@link ValueExtractor} definition so that it is applied automatically when
 * detecting constraints declared on the container type supported by the extractor, causing
 * the constraints to be applied to the container's elements instead of the container.
 * <p>
 * If needed, this behavior can be changed per constraint using {@link Unwrapping.Skip},
 * causing the constraints to be applied to the container itself:
 *
 * <pre>
 * &#064;SomeConstraint(payload = Unwrapping.Skip.class)
 * SomeContainerType container;
 * </pre>
 *
 * @author Guillaume Smet
 * @since 2.0
 */
@Target({ TYPE })
@Retention(RUNTIME)
@Documented
public @interface UnwrapByDefault {

}
