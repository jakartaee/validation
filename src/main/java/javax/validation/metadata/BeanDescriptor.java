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
package javax.validation.metadata;

import javax.validation.Valid;
import java.util.Set;

/**
 * Describes a constrained Java Bean and the constraints associated to it. All
 * objects returned by the methods of this descriptor (and associated objects
 * including {@link ConstraintDescriptor}s) are immutable.
 *
 * @author Emmanuel Bernard
 * @author Gunnar Morling
 */
public interface BeanDescriptor extends ElementDescriptor {

	/**
	 * Returns {@code true} if the bean involves validation:
	 * <ul>
	 *     <li>a constraint is hosted on the bean itself</li>
	 *     <li>a constraint is hosted on one of the bean properties</li>
	 *     <li>or a bean property is marked for cascaded validation ({@link Valid})</li>
	 * </ul>
	 * <p/>
	 * Constrained methods and constructors are ignored.
	 *
	 * @return {@code true} if the bean involves validation, {@code false} otherwise
	 */
	boolean isBeanConstrained();

	/**
	 * Returns the property descriptor for a given property.
	 * <p/>
	 * Returns {@code null} if the property does not exist or has no
	 * constraint nor is marked as cascaded (see {@link #getConstrainedProperties()})
	 * Properties of super types are considered.
	 *
	 * @param propertyName property evaluated
	 * @return the property descriptor for a given property
	 * @throws IllegalArgumentException if {@code propertyName} is {@code null}
	 */
	PropertyDescriptor getConstraintsForProperty(String propertyName);

	/**
	 * Returns a set of property descriptors having at least one constraint defined
	 * or marked as cascaded ({@link Valid}).
	 * <p/>
	 * If not property matches, an empty set is returned.
	 * Properties of super types are considered.
	 *
	 * @return the set of {@link PropertyDescriptor}s for the constraint properties; if
	 *         there are no constraint properties, the empty set is returned
	 */
	Set<PropertyDescriptor> getConstrainedProperties();

	/**
	 * Returns a method descriptor for the given method.
	 * <p/>
	 * Returns {@code null} if no method with the given name and parameter types
	 * exists or the specified method neither has parameter or return value constraints nor a parameter
	 * or return value marked for cascaded validation.
	 * Methods of super types are considered.
	 *
	 * @param methodName the name of the method
	 * @param parameterTypes the parameter types of the method
	 * @return a method descriptor for the given method
	 * @throws IllegalArgumentException if {@code methodName} is {@code null}
	 *
	 * @since 1.1
	 */
	MethodDescriptor getConstraintsForMethod(String methodName, Class<?>... parameterTypes);

	/**
	 * Returns a set with descriptors for the constrained methods of the bean
	 * represented by this descriptor.
	 * <p/>
	 * Constrained methods have at least one parameter or return value constraint
	 * or at least one parameter or return value marked for cascaded validation.
	 * Methods of super types are considered.
	 * <p/>
	 * Only methods matching the given method type(s) are considered.
	 *
	 * @param methodType method type to consider
	 * @param methodTypes remaining optional method types to consider
	 * @return a set with descriptors for the constrained methods of this bean;
	 *         will be empty if this bean has no constrained methods of the considered
	 *         method type(s) but never {@code null}
	 *
	 * @since 1.1
	 */
	Set<MethodDescriptor> getConstrainedMethods(MethodType methodType, MethodType... methodTypes);

	/**
	 * Returns a constructor descriptor for the given constructor.
	 * <p/>
	 * Returns {@code null} if no constructor with the given parameter types
	 * exists or the specified constructor neither has parameter or return value
	 * constraints nor a parameter or return value marked for cascaded
	 * validation.
	 * Constructor of super types are considered.
	 *
	 * @param parameterTypes the parameter types of the constructor
	 * @return a constructor descriptor for the given constructor
	 *
	 * @since 1.1
	 */
	ConstructorDescriptor getConstraintsForConstructor(Class<?>... parameterTypes);

	/**
	 * Returns a set with descriptors for the constrained constructors of the
	 * bean represented by this descriptor.
	 * <p/>
	 * Constrained constructors have at least one parameter or return value constraint
	 * or at least one parameter or return value marked for cascaded validation.
	 *
	 * @return a set with descriptors for the constrained constructor of this
	 *         bean; will be empty if this bea has no constrained constructor
	 *         but never {@code null}
	 *
	 * @since 1.1
	 */
	Set<ConstructorDescriptor> getConstrainedConstructors();
}
