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
package javax.validation.spi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation for a type or method indicating that method constraints shall be
 * validated upon invocation of the given method or any methods of the given type.
 * <p>
 * Note:</br>
 * This annotation is not intended to be used by a user directly. Instead method validation
 * integrators like CDI use it to internally mark methods and types which need validation.
 * For users the activation of method validation should be implicit by just placing method constraints on the
 * corresponding method.
 * </p>
 *
 * @author Gunnar Morling
 * @author Hardy Ferentschik
 * @since 1.1
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodValidated {
}

