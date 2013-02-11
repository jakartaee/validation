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

import javax.validation.metadata.ElementDescriptor;

/**
 * Represents the navigation path from an object to another
 * in an object graph.
 * Each path element is represented by a {@code Node}.
 *
 * The path corresponds to the succession of nodes
 * in the order they are returned by the {@code Iterator}.
 *
 * @author Emmanuel Bernard
 * @author Gunnar Morling
 */
public interface Path extends Iterable<Path.Node> {

	/**
	 * Represents an element of a navigation path.
	 */
	interface Node {
		/**
		 * Returns the name of the element which the node represents. The name
		 * will be {@code null}, if it represents an entity on the leaf node. In
		 * particular the node representing the root object has {@code null} as
		 * name. The name of a method or constructor return value is the literal
		 * {@code <return value>}.
		 *
		 * @return Name of the element which the node represents.
		 */
		String getName();

		/**
		 * @return {@code true} if the node represents an object contained in an
		 *         {@code Iterable} or in a Map, {@code false} otherwise.
		 */
		boolean isInIterable();

		/**
		 * @return The index the node is placed in if contained in an array or
		 *         {@code List}. {@code null} otherwise.
		 */
		Integer getIndex();

		/**
		 * @return The key the node is placed in if contained in a {@code Map}.
		 *         {@code null} otherwise.
		 */
		Object getKey();

		/**
		 * Returns a descriptor for the element (bean, property, method etc.)
		 * represented by this node. The specific type of the element can be
		 * determined using {@link ElementDescriptor#getKind()}.
		 *
		 * @return An element descriptor for this node.
		 *
		 * @since 1.1
		 */
		ElementDescriptor getElementDescriptor();
	}
}
