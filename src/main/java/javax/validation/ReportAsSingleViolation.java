/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A constraint annotation hosting this annotation will return the
 * composed annotation error report if any of the composing annotations fail.
 * The error reports of each individual composing constraint are ignored.
 * <p>
 * Note: Evaluation of composed constraints stops on the first validation
 * error in case the composing constraint is annotated with
 * {@code @ReportAsSingleViolation}.
 *
 * @author Emmanuel Bernard
 */
@Target({ ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface ReportAsSingleViolation {
}
