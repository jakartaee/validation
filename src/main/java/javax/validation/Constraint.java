/*
* JBoss, Home of Professional Open Source
* Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual contributors
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
 * Link between a constraint annotation and its constraint validation implementations.
 * <p/>
 * A given constraint annotation should be annotated by a {@code @Constraint}
 * annotation which refers to its list of constraint validation implementations.
 *
 * @author Emmanuel Bernard
 * @author Gavin King
 * @author Hardy Ferentschik
 */
@Documented
@Target({ ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface Constraint {
	/**
	 * {@code ConstraintValidator} classes must reference distinct target types.
	 * If two {@code ConstraintValidator}s refer to the same type,
	 * an exception will occur.
	 *
	 * @return array of (@code ConstraintValidator} classes implementing the constraint
	 */
	Class<? extends ConstraintValidator<?, ?>>[] validatedBy();
}
