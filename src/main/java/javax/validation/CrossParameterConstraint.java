/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual contributors
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
package javax.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Link between a cross-parameter constraint annotation and its constraint
 * validation implementation.
 *
 * @author Gunnar Morling
 * @since 1.1
 */
@Documented
@Target({ ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface CrossParameterConstraint {
	/**
	 * Returns the {@link ConstraintValidator} class for the annotated
	 * cross-parameter constraint. The validator's type parameter {@code T} must
	 * resolve to {@code Object[]}.
	 *
	 * @return Constraint validator class implementing the constraint
	 */
	Class<? extends ConstraintValidator<?, ?>> validatedBy();
}
