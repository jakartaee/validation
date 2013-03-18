/*
* JBoss, Home of Professional Open Source
* Copyright 2012-2013, Red Hat, Inc. and/or its affiliates, and individual contributors
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
package javax.validation.executable;

/**
 * Defines the types of executables targeted by an operation.
 *
 * @author Emmanuel Bernard
 * @since 1.1
 */
public enum ExecutableType {

	/**
	 * If the annotation using {@code ExecutableType} is on a type (class or interface),
	 * the behavior is equivalent to the annotation not being present.
	 * <p/>
	 * If on a constructor, it is equivalent to {@link #CONSTRUCTORS}.
	 * <p/>
	 * If on a non-getter method, it is equivalent to {@link #NON_GETTER_METHODS}.
	 * <p/>
	 * If on a getter method, it is equivalent to {@link #GETTER_METHODS}.
	 */
	IMPLICIT,

	/**
	 * None of the executables.
	 * <p/>
	 * Note that this option is equivalent to an empty list of executable types
	 * and is present to improve readability. If {@code NONE} and other types of executables
	 * are present in a list, {@code NONE} is ignored.
	 */
	NONE,

	/**
	 * All constructors.
	 */
	CONSTRUCTORS,

	/**
	 * All methods except the ones following the getter pattern. A getter according to the
	 * JavaBeans specification is a method whose:
	 * <ul>
	 *     <li>name starts with get, has a return type but no parameter</li>
	 *     <li>name starts with is, has a return type and is returning {@code boolean}.</li>
	 * </ul>
	 */
	NON_GETTER_METHODS,

	/**
	/**
	 * All methods following the getter pattern. A getter according to the
	 * JavaBeans specification is a method whose:
	 * <ul>
	 *     <li>name starts with get, has a return type but no parameter</li>
	 *     <li>name starts with is, has a return type and is returning {@code boolean}.</li>
	 * </ul>
	 */
	GETTER_METHODS,

	/**
	 * All executables (constructors and methods).
	 */
	ALL
}
