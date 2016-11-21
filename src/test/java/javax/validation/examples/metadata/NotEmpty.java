/*
 * Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.examples.metadata;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Emmanuel Bernard
 */
@Documented
@NotNull
@Size(min=1)
@ReportAsSingleViolation
@Constraint(validatedBy = NotEmpty.NotEmptyValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface NotEmpty {
    String message() default "{com.acme.constraint.NotEmpty.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotEmpty[] value();
    }

    class NotEmptyValidator implements ConstraintValidator<NotEmpty, String> {
        @Override
        public void initialize(NotEmpty constraintAnnotation) {}

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return true;
        }
    }
}
