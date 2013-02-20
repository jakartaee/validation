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

import javax.validation.groups.Default;
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
	 * @return returns {@code true} if at least one constraint declaration is present
	 *         for this element in the class hierarchy, {@code false} otherwise
	 */
	boolean hasConstraints();

	/**
	 * @return the statically defined returned type
	 */
	Class<?> getElementClass();

	/**
	 * Returns all constraint descriptors for this element in the class hierarchy
	 * or an empty {@code Set} if none are present.
	 *
	 * @return {@code Set} of constraint descriptors for this element
	 */
	Set<ConstraintDescriptor<?>> getConstraintDescriptors();

	/**
	 * Finds constraints and potentially restricts them to certain criteria.
	 *
	 * @return {@code ConstraintFinder} object
	 */
	ConstraintFinder findConstraints();

	/**
	 * Declares restrictions on retrieved constraints.
	 * Restrictions are cumulative.
	 * <p/>
	 * A {@code ConstraintFinder} is not thread-safe. The set of matching
	 * {@link ConstraintDescriptor} is.
	 */
	interface ConstraintFinder {

		/**
		 * Restricts to the constraints matching a given set of groups for this element.
		 * <p/>
		 * This method respects group conversion, group sequences
		 * and group inheritance (including class-level {@link Default} group
		 * overriding) but does not return {@link ConstraintDescriptor}s
		 * in any particular order.
		 * Specifically, ordering of the group sequence is not respected.
		 *
		 * @param groups groups targeted
		 * @return {@code this} following the chaining method pattern
		 */
		ConstraintFinder unorderedAndMatchingGroups(Class<?>... groups);

		/**
		 * Restricts to the constraints matching the provided scope for this element.
		 *
		 * Defaults to {@link Scope#HIERARCHY}
		 *
		 * @param scope expected scope
		 * @return {@code this} following the chaining method pattern
		 */
		ConstraintFinder lookingAt(Scope scope);

		/**
		 * Restricts to the constraints hosted on the listed {@code types}
		 * for a given element.
		 * <p/>
		 * Defaults to all possible types of the element.
		 * <p/>
		 * Typically used to restrict to fields ({@code FIELD})
		 * or getters ({@code METHOD}).
		 *
		 * @param types targeted types
		 *
		 * @return {@code this} following the chaining method pattern
		 */
		ConstraintFinder declaredOn(ElementType... types);

		/**
		 * Retrieves the constraint descriptors following the defined
		 * restrictions and hosted on the element described by
		 * {@link ElementDescriptor}.
		 *
		 * @return matching constraint descriptors
		 */
		Set<ConstraintDescriptor<?>> getConstraintDescriptors();

		/**
		 * Returns {@code true} if at least one constraint declaration
		 * matching the restrictions is present on the element,
		 * {@code false} otherwise.
		 *
		 * @return {@code true} if there is at least one constraint
		 */
		boolean hasConstraints();
	}
}