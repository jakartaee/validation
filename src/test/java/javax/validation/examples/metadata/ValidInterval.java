/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009-2013, Red Hat, Inc. and/or its affiliates, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

/**
 * @author Emmanuel Bernard
 */
@Documented
@Constraint(validatedBy = ValidInterval.Validator.class)
@Target({ METHOD, ANNOTATION_TYPE, CONSTRUCTOR })
@Retention(RUNTIME)
public @interface ValidInterval {
    String message() default "{com.acme.constraint.ValidInterval.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
    int startParameter();
    int endParameter();

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ValidInterval[] value();
    }

    @SupportedValidationTarget(ValidationTarget.PARAMETERS)
    class Validator implements ConstraintValidator<ValidInterval, Object[]> {
        
        private int start;
        private int end;

        @Override
        public void initialize(ValidInterval constraintAnnotation) {
            this.start = constraintAnnotation.startParameter();
            this.end = constraintAnnotation.endParameter();
        }

        @Override
        public boolean isValid(Object[] value, ConstraintValidatorContext context) {
            return Integer.parseInt( String.valueOf( value[start] ) ) < 
                   Integer.parseInt( String.valueOf( value[end] ) );
        }
    }
}
