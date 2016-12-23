/*
 * Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.constraints;

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

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Future.List;

/**
 * The annotated element must be a date in the future.
 * Now is defined as the current time according to the virtual machine
 * The calendar used if the compared type is of type {@code Calendar}
 * is the calendar based on the current time zone and the current locale.
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
 * @author Emmanuel Bernard
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
@Documented
@Constraint(validatedBy = { })
public @interface Future {

	String message() default "{javax.validation.constraints.Future.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * The notion of present here is defined relatively to the type on which the constraint is used. For instance, if
	 * the constraint is on a {@link Year}, present would mean the whole current year.
	 *
	 * @return {@code true} if the present date should be considered valid
	 */
	boolean orPresent() default false;

	/**
	 * Defines several {@link Future} annotations on the same element.
	 *
	 * @see Future
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	@interface List {

		Future[] value();
	}
}
