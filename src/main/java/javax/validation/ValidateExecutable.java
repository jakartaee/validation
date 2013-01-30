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

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Expresses which executable (method or constructor) should have its parameters
 * and return value validated upon execution.
 *
 * The settings for a given executable is resolved as followed.
 * A given executable is validated upon execution:
 * <ul>
 *     <li>if it is annotated with {@code @ValidateExecutable} and the {@code type} attribute
 *     contains the executable type. If the {@code type} attribute does not contain the
 *     executable type, the executable is not validated.</li>
 *     <li>otherwise if,
 *     the type (class, interface) on which the executable is defined
 *     is annotated with {@code @ValidateExecutable} and the {@code type} attribute
 *     contains the executable type. If the {@code type} attribute does not contain the
 *     executable type, the executable is not validated.</li>
 *     <li>otherwise if the global executable validation setting contains the executable
 *     type. If the global setting does not contain the executable type, the executable
 *     is not validated.</li>
 *     <li>The rules above do not apply to methods overriding a superclass method or
 *     implementing an interface method. In this case, the method inherits the behavior
 *     of the method it overrides / implements</li>
 * </ul>
 *
 * @author Emmanuel Bernard
 * @since 1.1
 */
@Target({ CONSTRUCTOR, METHOD, TYPE, PACKAGE })
@Retention(RUNTIME)
public @interface ValidateExecutable {
	/**
	 * List of executable types to be validated when called
	 */
	ExecutableType[] type();
}
