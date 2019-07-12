/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

import java.util.List;

import javax.validation.valueextraction.ValueExtractor;

/**
 * Represents the navigation path from an object to another
 * in an object graph.
 * Each path element is represented by a {@code Node}.
 * <p>
 * The path corresponds to the succession of nodes
 * in the order they are returned by the {@code Iterator}.
 *
 * @author Emmanuel Bernard
 * @author Gunnar Morling
 * @author Guillaume Smet
 */
public interface Path extends Iterable<Path.Node> {

	/**
	 * Returns a human-readable representation of this path.
	 * <p>
	 * Clients should not rely on any specific structure of the returned value. Instead they
	 * should iterate through the path nodes and obtain any required information by calling
	 * the methods on {@link Node} and its sub-types.
	 *
	 * @return a human-readable representation of this path
	 * @since 2.0
	 */
	@Override
	String toString();

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
		 *     <li>The node name as defined by the {@link ValueExtractor} for a container
		 *     element; specifically, the literal {@code <list element>} for elements
		 *     stored in a list, the literal {@code <iterable element>} for elements
		 *     stored in an {@code Iterable}, the literal {@code <map key>} for the keys
		 *     stored in a {@code Map} and the literal {@code <map value>} for the values
		 *     stored in a {@code Map}.
		 * </ul>
		 *
		 * @return name of the element which the node represents
		 */
		String getName();

		/**
		 * @return {@code true} if the node represents an object contained in
		 * a multi-valued container such as {@code Iterable} or {@code Map} or an array,
		 * {@code false} otherwise
		 */
		boolean isInIterable();

		/**
		 * @return the index the node is placed in if contained in an array, a {@code List}
		 *         or any other container supporting indexed access, {@code null} otherwise
		 */
		Integer getIndex();

		/**
		 * @return the key the node is placed in if contained in a {@code Map} or any
		 *         other container supporting keyed access, {@code null} otherwise
		 */
		Object getKey();

		/**
		 * The kind of element represented by the node. The following relationship
		 * between an {@link ElementKind} and its {@code Node} subtype exists:
		 * <ul>
		 *     <li>{@link ElementKind#BEAN}: {@link BeanNode}</li>
		 *     <li>{@link ElementKind#PROPERTY}: {@link PropertyNode}</li>
		 *     <li>{@link ElementKind#METHOD}: {@link MethodNode}</li>
		 *     <li>{@link ElementKind#CONSTRUCTOR}: {@link ConstructorNode}</li>
		 *     <li>{@link ElementKind#PARAMETER}: {@link ParameterNode}</li>
		 *     <li>{@link ElementKind#CROSS_PARAMETER}: {@link CrossParameterNode}</li>
		 *     <li>{@link ElementKind#RETURN_VALUE}: {@link ReturnValueNode}</li>
		 *     <li>{@link ElementKind#CONTAINER_ELEMENT}: {@link ContainerElementNode}</li>
		 * </ul>
		 * <p>
		 * This is useful to narrow down the {@code Node} type and access node specific
		 * information:
		 * <pre>
		 * switch(node.getKind() {
		 * case METHOD:
		 *     name = node.getName();
		 *     params = node.as(MethodNode.class).getParameterTypes();
		 * case PARAMETER:
		 *     index = node.as(ParameterNode.class).getParameterIndex();
		 * [...]
		 * }
		 * </pre>
		 *  @return the {@code ElementKind}
		 *
		 * @since 1.1
		 */
		ElementKind getKind();

		/**
		 * Narrows the type of this node down to the given type. The appropriate
		 * type should be checked before by calling {@link #getKind()}.
		 *
		 * @param <T> the type to narrow down to
		 * @param nodeType class object representing the descriptor type to narrow down to
		 *                 to
		 *
		 * @return this node narrowed down to the given type.
		 *
		 * @throws ClassCastException if this node is not assignable to the type {@code T}
		 * @since 1.1
		 */
		<T extends Node> T as(Class<T> nodeType);

		/**
		 * Returns a human-readable representation of this node.
		 * <p>
		 * Clients should not rely on any specific structure of the returned value. Instead
		 * they should obtain any required information by calling the methods on this
		 * interface and its sub-types.
		 *
		 * @return a human-readable representation of this node
		 * @since 2.0
		 */
		@Override
		String toString();
	}

	/**
	 * Node representing a method.
	 *
	 * @since 1.1
	 */
	interface MethodNode extends Node {

		/**
		 * @return the list of parameter types
		 */
		List<Class<?>> getParameterTypes();
	}

	/**
	 * Node representing a constructor.
	 *
	 * @since 1.1
	 */
	interface ConstructorNode extends Node {

		/**
		 * @return the list of parameter types
		 */
		List<Class<?>> getParameterTypes();
	}

	/**
	 * Node representing the return value of a method or constructor.
	 *
	 * @since 1.1
	 */
	interface ReturnValueNode extends Node {
	}

	/**
	 * Node representing a parameter of a method or constructor.
	 *
	 * @since 1.1
	 */
	interface ParameterNode extends Node {

		/**
		 * @return the parameter index in the method or constructor definition
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
	 * Node representing a bean.
	 *
	 * @since 1.1
	 */
	interface BeanNode extends Node {

		/**
		 * @return the type of the container the node is placed in, if contained in a
		 * container type such as {@code Optional}, {@code List} or {@code Map},
		 * {@code null} otherwise
		 *
		 * @since 2.0
		 */
		Class<?> getContainerClass();

		/**
		 * @return the index of the type argument affected by the violated constraint, if
		 * contained in a generic container type such as {@code Optional}, {@code List} or
		 * {@code Map}.
		 *
		 * @since 2.0
		 */
		Integer getTypeArgumentIndex();
	}

	/**
	 * Node representing a property.
	 *
	 * @since 1.1
	 */
	interface PropertyNode extends Node {

		/**
		 * @return the type of the container the node is placed in, if contained in a
		 * container type such as {@code Optional}, {@code List} or {@code Map},
		 * {@code null} otherwise
		 *
		 * @since 2.0
		 */
		Class<?> getContainerClass();

		/**
		 * @return the index of the type argument affected by the violated constraint, if
		 * contained in a generic container type such as {@code Optional}, {@code List} or
		 * {@code Map}, {@code null} otherwise
		 *
		 * @since 2.0
		 */
		Integer getTypeArgumentIndex();
	}

	/**
	 * Node representing an element in a generic container such as {@code Optional},
	 * {@code List} or {@code Map}.
	 *
	 * @since 2.0
	 */
	interface ContainerElementNode extends Node {

		/**
		 * @return the type of the container the node is placed in
		 */
		Class<?> getContainerClass();

		/**
		 * @return the index of the type argument affected by the violated constraint
		 */
		Integer getTypeArgumentIndex();
	}
}
