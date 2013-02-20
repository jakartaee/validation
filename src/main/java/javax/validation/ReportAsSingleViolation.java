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
package javax.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A constraint annotation hosting this annotation will return the
 * composed annotation error report if any of the composing annotations fail.
 * The error reports of each individual composing constraint are ignored.
 * <p/>
 * Note: Evaluation of composed constraints stops on the first validation
 * error in case the composing constraint is annotated with
 * {@code @ReportAsSingleViolation}.
 *
 * @author Emmanuel Bernard
 */
@Target({ ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ReportAsSingleViolation {
}
