/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.groups.Default;

/**
 * Defines group sequence.
 * <p>
 * The interface hosting {@code @GroupSequence} is representing
 * the group sequence.
 * When hosted on a class, represents the {@link Default} group
 * for that class.
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
@Target({ TYPE })
@Retention(RUNTIME)
@Documented
public @interface GroupSequence {

	Class<?>[] value();
}
