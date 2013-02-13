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

import java.util.List;

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
		 * Returns the name of the element which the node represents:
		 * <ul>
		 *     <li>{@code null} if it is a leaf node which represents an entity / bean.
		 *     In particular, the node representing the root object.</li>
		 *     <li>The property name for a property.</li>
		 *     <li>The method name for a method.</li>
		 *     <li>The unqualified name of the type declaring the constructor
		 *     for a constructor.</li>
		 *     <li>The parameter named as defined by the {@link ParameterNameProvider}
		 *     for a method or constructor parameter.</li>
		 *     <li>The literal {@code <cross-parameter>} for a method or constructor
		 *     cross-parameter.</li>
		 *     <li>The literal {@code <return value>} for a method or constructor return
		 *     value.</li>
		 * </ul>
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
		 * The kind of element represented by the node. The following relationship
		 * between an {@link javax.validation.ElementKind} and its {@code Node} subtype exists:
		 * <ul>
		 *     <li>{@code ElementKind.BEAN}: {@link BeanNode}</li>
		 *     <li>{@code ElementKind.PROPERTY}: {@link PropertyNode}</li>
		 *     <li>{@code ElementKind.METHOD}: {@link MethodNode}</li>
		 *     <li>{@code ElementKind.CONSTRUCTOR}: {@link ConstructorNode}</li>
		 *     <li>{@code ElementKind.PARAMETER}: {@link ParameterNode}</li>
		 *     <li>{@code ElementKind.CROSS_PARAMETER}: {@link CrossParameterNode}</li>
		 *     <li>{@code ElementKind.RETURN_VALUE}: {@link ReturnValueNode}</li>
		 * </ul>
		 *
		 * This is useful to narrow down the Node type and access node specific
		 * information:
		 * <pre>{@code
		 * switch(node.getKind() {
		 * case METHOD:
		 *     name = node.getName();
		 *     params = node.as(MethodNode.class).getParameterTypes();
		 * case PARAMETER:
		 *     index = node.as(ParameterNode.class).getParameterIndex();
		 * [...]
		 * }}</pre>
		 * @since 1.1
		 */
		ElementKind getKind();

		/**
		 * Narrows the type of this node down to the given type. The appropriate
		 * type should be checked before by calling {@link getKind()}.
		 *
		 * @param <T> The type to narrow down to
		 * @param nodeType Class object representing the descriptor type to narrow down
		 *                 to.
		 *
		 * @return This node narrowed down to the given type.
		 *
		 * @throws ClassCastException If this node is not assignable to the type {@code T}
		 * @since 1.1
		 */
		<T extends Node> T as(Class<T> nodeType);
	}

	/**
	 * Node representing a method
	 *
	 * @since 1.1
	 */
	interface MethodNode extends Node {

		/**
		 * @return The list of parameter types
		 */
		List<Class<?>> getParameterTypes();
	}

	/**
	 * Node representing a constructor
	 *
	 * @since 1.1
	 */
	interface ConstructorNode extends Node {

		/**
		 * @return The list of parameter types
		 */
		List<Class<?>> getParameterTypes();
	}

	/**
	 * Node representing the return value of a method or constructor
	 *
	 * @since 1.1
	 */
	interface ReturnValueNode extends Node {
	}

	/**
	 * Node representing a parameter of a method or constructor
	 *
	 * @since 1.1
	 */
	interface ParameterNode extends Node {

		/**
		 * @return The parameter index in the method or constructor definition
		 */
		int getParameterIndex();
	}

	/**
	 * Node representing the element holding cross-parameter constraints
	 * of a method or constructor.
	 *
	 * @since 1.1
	 */
	interface CrossParameterNode extends Node {
	}

	/**
	 * Node representing a bean
	 *
	 * @since 1.1
	 */
	interface BeanNode extends Node {
	}

	/**
	 * Node representing a property
	 *
	 * @since 1.1
	 */
	interface PropertyNode extends Node {
	}
}
