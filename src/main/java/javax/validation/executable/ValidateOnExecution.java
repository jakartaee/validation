/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.executable;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.ValidationException;

/**
 * Expresses which executables (methods or constructors) should have their parameters
 * and return value validated upon execution. Can be on executable (method, constructor)
 * or type level (with the former taking precedence).
 * <p>
 * If not present for a given executable, the default configuration from
 * {@code META-INF/validation.xml} and finally the implicit default
 * validated executable types (constructors and non-getters) are taken into account to
 * determine whether a given executable is validated upon execution or not.
 * <p>
 * The following describes the formal rules for deciding whether an executable is validated.
 * They are applied in decreasing order:
 * <ul>
 *     <li>the executable is validated if it is annotated with {@code @ValidateOnExecution}
 *     and the {@code type} attribute contains the executable type or
 *     {@link ExecutableType#IMPLICIT}. If the {@code type} attribute does neither contain
 *     the executable type nor {@code IMPLICIT}, the executable is not validated.</li>
 *     <li>otherwise the executable is validated if the type (class, interface) on which it
 *     is declared is annotated with {@code @ValidateOnExecution} and the {@code type}
 *     attribute contains the executable type. If the {@code type} attribute contains
 *     {@code IMPLICIT}, then this rule is ignored and the behavior is equivalent to
 *     {@code ValidateOnExecution} not being present. If the {@code type} attribute does not
 *     contain the executable type, the executable is not validated.</li>
 *     <li>otherwise the executable is validated if the global executable validation setting
 *     contains the executable type. If the global setting does not contain the executable
 *     type, the executable is not validated.</li>
 *     <li>The rules above do not apply to methods overriding a superclass method or
 *     implementing an interface method. In this case, the method inherits the behavior
 *     of the method it overrides or implements. Out of the box, a conforming implementation
 *     raises a {@link ValidationException} if the overriding / implementing method hosts
 *     the {@code ValidateOnExecution} annotation.</li>
 * </ul>
 * <p>
 * Note that you can exclude an executable from validation by making sure the rules above do
 * not match or by annotating the executable with {@code @ValidateOnExecution(NONE)}.
 *
 * @author Emmanuel Bernard
 * @since 1.1
 */
@Target({ CONSTRUCTOR, METHOD, TYPE, PACKAGE })
@Retention(RUNTIME)
@Documented
public @interface ValidateOnExecution {

	/**
	 * List of executable types to be validated when called.
	 * Defaults to the types discovered implicitly (see {@link ExecutableType#IMPLICIT}).
	 *
	 * @return array of {@code ExecutableType}s to be validated
	 */
	ExecutableType[] type() default {ExecutableType.IMPLICIT};
}
