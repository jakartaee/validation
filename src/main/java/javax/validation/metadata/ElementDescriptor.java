// $Id$
/*
* JBoss, Home of Professional Open Source
* Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
 * Describes a validated element (class, field or property).
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public interface ElementDescriptor {

	/**
	 * @return <code>true</code> if at least one constraint declaration is
	 *         present on the element, <code>false</code> otherwise.
	 */
	boolean hasConstraints();

	/**
	 * @return Statically defined returned type.
	 */
	Class<?> getElementClass();

	/**
	 * Return all constraint descriptors for this element or an
	 * empty Set if none are present.
	 *
	 * @return Set of constraint descriptors for this element
	 */
	Set<ConstraintDescriptor<?>> getConstraintDescriptors();

	/**
	 * Return the list of matching constraints for a given set of groups for this element.
	 *
	 * This method respects group sequences and group inheritance (including
	 * class-level Default group overriding) but does not return ConstraintDescriptors
	 * in any particular order. Specifically, ordering of the group sequence is not
	 * respected.
	 *
	 * @param groups groups targeted
	 * @return list of matching ConstraintDescriptors
	 */
	Set<ConstraintDescriptor<?>> getUnorderedConstraintDescriptorsMatchingGroups(Class<?>... groups);
}
