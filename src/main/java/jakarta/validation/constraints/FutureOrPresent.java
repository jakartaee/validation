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
import java.time.Year;

import jakarta.validation.ClockProvider;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.FutureOrPresent.List;

/**
 * The annotated element must be an instant, date or time in the present or in the future.
 * <p>
 * <i>Now</i> is defined by the {@link ClockProvider} attached to the {@link Validator} or
 * {@link ValidatorFactory}. The default {@code clockProvider} defines the current time
 * according to the virtual machine, applying the current default time zone if needed.
 * <p>
 * The notion of present here is defined relatively to the type on which the constraint is
 * used. For instance, if the constraint is on a {@link Year}, present would mean the whole
 * current year.
 * <p>
 * Supported types are:
 * <ul>
 *     <li>{@code java.util.Date}</li>
 *     <li>{@code java.util.Calendar}</li>
 *     <li>{@code java.time.Instant}</li>
 *     <li>{@code java.time.LocalDate}</li>
 *     <li>{@code java.time.LocalDateTime}</li>
 *     <li>{@code java.time.LocalTime}</li>
 *     <li>{@code java.time.MonthDay}</li>
 *     <li>{@code java.time.OffsetDateTime}</li>
 *     <li>{@code java.time.OffsetTime}</li>
 *     <li>{@code java.time.Year}</li>
 *     <li>{@code java.time.YearMonth}</li>
 *     <li>{@code java.time.ZonedDateTime}</li>
 *     <li>{@code java.time.chrono.HijrahDate}</li>
 *     <li>{@code java.time.chrono.JapaneseDate}</li>
 *     <li>{@code java.time.chrono.MinguoDate}</li>
 *     <li>{@code java.time.chrono.ThaiBuddhistDate}</li>
 * </ul>
 * <p>
 * {@code null} elements are considered valid.
 *
 * @author Guillaume Smet
 * @since 2.0
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
@Documented
@Constraint
public @interface FutureOrPresent {

	String message() default "{jakarta.validation.constraints.FutureOrPresent.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * Defines several {@link FutureOrPresent} annotations on the same element.
	 *
	 * @see FutureOrPresent
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	@interface List {

		FutureOrPresent[] value();
	}
}
