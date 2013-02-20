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
package javax.validation.metadata;

import java.util.Set;

/**
 * Represents a cascadable element.
 *
 * @author Gunnar Morling
 * @since 1.1
 */
public interface CascadableDescriptor {

	/**
	 * Whether this element is marked for cascaded validation or not.
	 *
	 * @return {@code true}, if this element is marked for cascaded validation,
	 *         {@code false} otherwise
	 */
	boolean isCascaded();

	/**
	 * Returns the group conversions configured for this element.
	 *
	 * @return a set containing this element's group conversions; an empty set
	 *         may be returned if no conversions are configured but never
	 *         {@code null}
	 */
	Set<GroupConversionDescriptor> getGroupConversions();
}
