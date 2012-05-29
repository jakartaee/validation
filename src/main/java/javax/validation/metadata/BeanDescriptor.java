/*
* JBoss, Home of Professional Open Source
* Copyright 2009-2011, Red Hat, Inc. and/or its affiliates, and individual contributors
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
package javax.validation.metadata;

import java.util.Set;

/**
 * Describes a constrained Java Bean and the constraints associated to it. All
 * objects returned by the methods of this descriptor (and associated objects
 * including {@code ConstraintDescriptor}s) are immutable.
 *
 * @author Emmanuel Bernard
 * @author Gunnar Morling
 */
public interface BeanDescriptor extends ElementDescriptor {
	/**
	 * Returns {@code true} if the bean involves validation:
	 * <ul>
	 * <li> a constraint is hosted on the bean itself </li>
	 * <li> a constraint is hosted on one of the bean properties</li>
	 * <li> a bean property is marked for cascaded validation ({@code @Valid})</li>
	 * <li> a constraint is hosted on the return value or a method parameter of one of the bean's methods</li>
	 * <li> or the return value or a method parameter of one of the bean's methods is marked for cascaded validation ({@code @Valid})</li>
	 * </ul>
	 *
	 * @return {@code true} if the bean involves validation, {@code false} otherwise.
	 */
	boolean isBeanConstrained();

	/**
	 * Return the property descriptor for a given property.
	 * Return {@code null} if the property does not exist or has no
	 * constraint nor is marked as cascaded (see {@link #getConstrainedProperties()} )
	 * <p/>
	 *
	 * @param propertyName property evaluated
	 *
	 * @return the property descriptor for a given property.
	 *
	 * @throws IllegalArgumentException if propertyName is {@code null}
	 */
	PropertyDescriptor getConstraintsForProperty(String propertyName);

	/**
	 * Returns a set of property descriptors having at least one constraint defined
	 * or marked as cascaded ({@code @Valid}). If not property matches,
	 * an empty set is returned.
	 *
	 * @return Returns the set of {@code PropertyDescriptor}s for the constraint properties. If there are no
	 *         constraint properties the empty set is returned
	 */
	Set<PropertyDescriptor> getConstrainedProperties();

	/**
	 * Returns a method descriptor for the given method. Returns {@code null} if
	 * no method with the given name and parameter types exists or the specified
	 * method neither has parameter or return value constraints nor a parameter
	 * or return value marked for cascaded validation.
	 *
	 * @param methodName The name of the method.
	 * @param parameterTypes The parameter types of the method.
	 *
	 * @return A method descriptor for the given method.
	 *
	 * @throws IllegalArgumentException if methodName is {@code null}
	 * @since 1.1
	 */
	MethodDescriptor getConstraintsForMethod(String methodName, Class<?>... parameterTypes);

	/**
	 * Returns a set with descriptors for the constrained methods of the type
	 * represented by this descriptor. Constrained are all those methods which
	 * have at least one parameter or return value constraint or at least one
	 * parameter or return value marked for cascaded validation.
	 *
	 * @return A set with descriptors for the constrained methods of this type.
	 *         Will be empty if this type has no constrained methods but never
	 *         {@code null}.
	 *
	 * @since 1.1
	 */
	Set<MethodDescriptor> getConstrainedMethods();

	/**
	 * Returns a constructor descriptor for the given constructor. Returns
	 * {@code null} if no constructor with the given parameter types exists or
	 * the specified constructor neither has parameter or return value
	 * constraints nor a parameter or return value marked for cascaded
	 * validation.
	 *
	 * @param parameterTypes The parameter types of the constructor.
	 *
	 * @return A constructor descriptor for the given constructor.
	 *
	 * @since 1.1
	 */
	ConstructorDescriptor getConstraintsForConstructor(Class<?>... parameterTypes);

	/**
	 * Returns a set with descriptors for the constrained constructors of the
	 * type represented by this descriptor. Constrained are all those
	 * constructors which have at least one parameter or return value constraint
	 * or at least one parameter or return value marked for cascaded validation.
	 *
	 * @return A set with descriptors for the constrained constructor of this
	 *         type. Will be empty if this type has no constrained constructor
	 *         but never {@code null}.
	 *
	 * @since 1.1
	 */
	Set<ConstructorDescriptor> getConstrainedConstructors();
}
