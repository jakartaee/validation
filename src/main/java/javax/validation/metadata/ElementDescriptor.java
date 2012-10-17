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
package javax.validation.metadata;

import java.lang.annotation.ElementType;
import java.util.Set;

/**
 * Describes a validated element (class, property, method etc.).
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 * @author Gunnar Morling
 */
public interface ElementDescriptor {

	/**
	 * The kind of an {@link ElementDescriptor}.
	 *
	 * @author Gunnar Morling
	 * @since 1.1
	 */
	public enum Kind {

		/**
		 * A Java Bean.
		 */
		BEAN,

		/**
		 * A property of a Java Bean.
		 */
		PROPERTY,

		/**
		 * A method.
		 */
		METHOD,

		/**
		 * A constructor.
		 */
		CONSTRUCTOR,

		/**
		 * A parameter of a method or constructor.
		 */
		PARAMETER,

		/**
		 * The return value of a method or constructor.
		 */
		RETURN_VALUE
	}

	/**
	 * @return Return {@code true} if at least one constraint declaration is present
	 *         for this element in the class hierarchy, {@code false} otherwise.
	 */
	boolean hasConstraints();

	/**
	 * @return Statically defined returned type.
	 */
	Class<?> getElementClass();

	/**
	 * Return all constraint descriptors for this element in the class hierarchy
	 * or an empty {@code Set} if none are present.
	 *
	 * @return {@code Set} of constraint descriptors for this element
	 */
	Set<ConstraintDescriptor<?>> getConstraintDescriptors();

	/**
	 * Find constraints and potentially restricts them to certain criteria.
	 *
	 * @return ConstraintFinder object.
	 */
	ConstraintFinder findConstraints();

	/**
	 * Returns the kind of this descriptor.
	 *
	 * @return The kind of this descriptor.
	 *
	 * @since 1.1
	 */
	Kind getKind();

	/**
	 * Narrows the type of this descriptor down to the given type. The type
	 * should be checked before by calling {@link ElementDescriptor#getKind()}.
	 *
	 * @param <T> The type to narrow down to.
	 * @param descriptorType Class object representing the descriptor type to narrow down
	 * to.
	 *
	 * @return This descriptor narrowed down to the given type.
	 *
	 * @throws ClassCastException If this descriptor is not assignable to the type
	 * {@code T}.
	 * @since 1.1
	 */
	<T extends ElementDescriptor> T as(Class<T> descriptorType);

	/**
	 * Declare restrictions on retrieved constraints.
	 * Restrictions are cumulative.
	 *
	 * A {@code ConstraintFinder} is not thread-safe. The set of matching
	 * {@code ConstraintDescriptor} is.
	 */
	interface ConstraintFinder {
		/**
		 * Restrict to the constraints matching a given set of groups for this element
		 *
		 * This method respects group conversion, group sequences
		 * and group inheritance (including class-level {@code Default} group
		 * overriding) but does not return {@code ConstraintDescriptor}s
		 * in any particular order.
		 * Specifically, ordering of the group sequence is not respected.
		 *
		 * @param groups groups targeted
		 *
		 * @return {@code this} following the chaining method pattern
		 */
		ConstraintFinder unorderedAndMatchingGroups(Class<?>... groups);

		/**
		 * Restrict to the constraints matching the provided scope for this element.
		 *
		 * Defaults to {@code Scope.HIERARCHY}
		 *
		 * @param scope expected scope
		 *
		 * @return {@code this} following the chaining method pattern
		 */
		ConstraintFinder lookingAt(Scope scope);

		/**
		 * Restrict to the constraints hosted on the listed {@code types}
		 * for a given element.
		 *
		 * Default to all possible types of the element.
		 *
		 * Typically used to restrict to fields ({@code FIELD})
		 * or getters ({@code METHOD})
		 *
		 * @param types targeted types
		 *
		 * @return {@code this} following the chaining method pattern
		 */
		ConstraintFinder declaredOn(ElementType... types);

		/**
		 * Retrieve the constraint descriptors following the defined
		 * restrictions and hosted on the element described by
		 * {@code ElementDescriptor}
		 *
		 * @return matching constraint descriptors
		 */
		Set<ConstraintDescriptor<?>> getConstraintDescriptors();

		/**
		 * Returns {@code true} if at least one constraint declaration
		 * matching the restrictions is present on the element,
		 * {@code false} otherwise.
		 *
		 * @return is there any constraint
		 */
		boolean hasConstraints();
	}
}