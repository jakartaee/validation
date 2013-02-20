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

import javax.validation.groups.ConvertGroup;

/**
 * A group conversion rule to be applied during cascaded validation. Two group
 * conversion descriptors are considered equal if they have the same
 * {@code from} and {@code to} group respectively.
 *
 * @author Gunnar Morling
 * @see ConvertGroup
 * @since 1.1
 */
public interface GroupConversionDescriptor {

	/**
	 * Returns the source group of this conversion rule.
	 *
	 * @return the source group of this conversion rule
	 */
	Class<?> getFrom();

	/**
	 * Returns the target group of this conversion rule.
	 *
	 * @return the target group of this conversion rule
	 */
	Class<?> getTo();
}
