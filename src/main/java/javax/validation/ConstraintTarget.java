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
package javax.validation;

/**
 * Defines the constraint target.
 *
 * @author Emmanuel Bernard
 * @since 1.1
 */
public enum ConstraintTarget {

	/**
	 * Discover the type when no ambiguity is present
	 * <ul>
	 *     <li>if neither on a method nor a constructor, it implies the annotated element
	 *     (type, field etc),</li>
	 *     <li>if on a method or constructor with no parameter, it implies
	 *     {@code RETURN_VALUE},</li>
	 *     <li>if on a method with no return value ({@code void}), it implies
	 *     {@code PARAMETERS}.</li>
	 * </ul>
	 * Otherwise, {@code IMPLICIT} is not accepted and either {@code RETURN_VALUE} or
	 * {@code PARAMETERS} is required. This is the case for constructors with parameters
	 * and methods with parameters and return value.
	 */
	IMPLICIT,

	/**
	 * Constraint applies to the return value of a method or a constructor.
	 */
	RETURN_VALUE,

	/**
	 * Constraint applies to the parameters of a method or a constructor
	 */
	PARAMETERS
}
