/*
* JBoss, Home of Professional Open Source
* Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual contributors
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
package javax.validation.constraintvalidation;

import javax.validation.ConstraintValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Defines the target(s) a {@link ConstraintValidator} can validate.
 * <p/>
 * A {@code ConstraintValidator} can target the (returned) element
 * annotated by the constraint, the array of parameters of a method
 * or constructor (aka cross-parameter) or both.
 * <p/>
 * If {@code @SupportedValidationTarget} is not present, the
 * {@code ConstraintValidator} targets the (returned) element annotated
 * by the constraint.
 * <p/>
 * A {@code ConstraintValidator} targeting cross-parameter must accept
 * {@code Object[]} (or {@code Object}) as the type of object it validates.
 *
 * @author Emmanuel Bernard
 * @since 1.1
 */
@Documented
@Target({ TYPE })
@Retention(RUNTIME)
public @interface SupportedValidationTarget {

	ValidationTarget[] value();
}
