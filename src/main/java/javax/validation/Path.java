// $Id$
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

import javax.validation.metadata.ConstructorDescriptor;
import javax.validation.metadata.MethodDescriptor;
import javax.validation.metadata.ParameterDescriptor;

/**
 * Represent the navigation path from an object to another
 * in an object graph.
 * Each path element is represented by a <code>Node</code>.
 *
 * The path corresponds to the succession of nodes
 * in the order they are returned by the <code>Iterator</code>
 *
 * @author Emmanuel Bernard
 * @author Gunnar Morling
 */
public interface Path extends Iterable<Path.Node> {

	/**
	 * Represents an element of a navigation path
	 */
	interface Node {
		/**
		 * Property name the node represents
		 * or null if representing an entity on the leaf node
		 * (in particular the node in a <code>Path</code> representing
		 * the root object has its name null).
		 * 
		 * @return property name the node represents
		 */
		String getName();

		/**
		 * @return true if the node represents an object contained in an Iterable
		 * or in a Map.
		 */
		boolean isInIterable();

		/**
		 * @return The index the node is placed in if contained
		 * in an array or List. Null otherwise.
		 */
		Integer getIndex();

		/**
		 * @return The key the node is placed in if contained
		 * in a Map. Null otherwise.
		 */
		Object getKey();

		/**
		 * @return A descriptor for the method which the node represents or null
		 *         if the node doesn't represent a method.
		 */
		MethodDescriptor getMethodDescriptor();

		/**
		 * @return A descriptor for the constructor which the node represents or
		 *         null if the node doesn't represent a constructor.
		 */
		ConstructorDescriptor getConstructorDescriptor();

		/**
		 * @return A descriptor for the method or constructor parameter which
		 *         the node represents or null if the node doesn't represent a
		 *         method or constructor parameter. Can only be not null, if
		 *         either {@link Node#getMethodDescriptor()} or
		 *         {@link Node#getConstructorDescriptor()} return a non-null
		 *         value.
		 */
		ParameterDescriptor getParameterDescriptor();
	}
}
