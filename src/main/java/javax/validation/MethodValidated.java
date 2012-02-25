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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.groups.Default;

/**
 * Marker for a type or method indicating in which way method-level constraints shall be
 * validated upon invocation of the given method or any methods of the given type.
 *
 * @author Gunnar Morling
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodValidated {

    /**
     * Returns the group(s) to validate. Defaults to
     * {@link Default}.
     *
     * @return The group(s) to validate.
     */
    Class<?>[] groups() default { };

    /**
     * Returns the validation mode. Defaults to {@link ValidationMode#ALL}.
     *
     * @return The validation mode.
     */
    ValidationMode validationMode() default ValidationMode.ALL;

    /**
     * A mode of method-level validation. Can be used to specify the validation
     * behavior on a type and method level.
     *
     * @author Gunnar Morling
     */
    public enum ValidationMode {

        /**
         * Indicates that method and constructor parameters shall be validated automatically
         * upon invocation of methods or constructors.
         */
        PARAMETERS,

        /**
         * Indicates that the return value shall be validated automatically
         * upon invocation of methods or constructors.
         */
        RETURN_VALUE,

        /**
         * Indicates that method and constructor parameters as well as the return value shall be validated automatically
         * upon invocation of methods or constructors.
         */
        ALL,

        /**
         * Indicates that neither method or constructor parameters nor the return value shall be validated automatically
         * upon invocation of methods or constructors.
         */
        NONE;
    }
}

