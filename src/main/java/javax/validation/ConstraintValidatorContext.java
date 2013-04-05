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
package javax.validation;

/**
 * Provides contextual data and operation when applying a given constraint validator.
 *
 * At least one {@link ConstraintViolation} must be defined (either the default one,
 * of if the default {@code ConstraintViolation} is disabled, a custom one).
 *
 * @author Emmanuel Bernard
 */
public interface ConstraintValidatorContext {

	/**
	 * Disables the default {@link ConstraintViolation} object generation (which
	 * is using the message template declared on the constraint).
	 * <p/>
	 * Useful to set a different violation message or generate a {@code ConstraintViolation}
	 * based on a different property.
	 */
	void disableDefaultConstraintViolation();

	/**
	 * @return the current un-interpolated default message
	 */
	String getDefaultConstraintMessageTemplate();

	/**
	 * Returns a constraint violation builder building a violation report
	 * allowing to optionally associate it to a sub path.
	 * The violation message will be interpolated.
	 * <p/>
	 * To create the {@link ConstraintViolation}, one must call either one of
	 * the {@code addConstraintViolation()} methods available in one of the
	 * interfaces of the fluent API.
	 * If another method is called after {@code addConstraintViolation()} on
	 * {@code ConstraintViolationBuilder} or any of its associated nested interfaces
	 * an {@code IllegalStateException} is raised.
	 * <p/>
	 * If {@link ConstraintValidator#isValid(Object, ConstraintValidatorContext)} returns
	 * {@code false}, a {@code ConstraintViolation} object will be built per constraint
	 * violation report including the default one (unless
	 * {@link #disableDefaultConstraintViolation()} has been called).
	 * <p/>
	 * {@code ConstraintViolation} objects generated from such a call
	 * contain the same contextual information (root bean, path and so on) unless
	 * the path has been overridden.
	 * <p/>
	 * To create a different {@code ConstraintViolation}, a new constraint violation builder
	 * has to be retrieved from {@code ConstraintValidatorContext}
	 *
	 * Here are a few usage examples:
	 * <pre>
	 * //assuming the following domain model
	 * public class User {
	 *     public Map<String,Address> getAddresses() { ... }
	 * }
	 *
	 * public class Address {
	 *     public String getStreet() { ... }
	 *     public Country getCountry() { ... }
	 * }
	 *
	 * public class Country {
	 *     public String getName() { ... }
	 * }
	 *
	 * //From a property-level constraint on User.addresses
	 * //Build a constraint violation on the default path - i.e. the "addresses" property
	 * context.buildConstraintViolationWithTemplate( "this detail is wrong" )
	 *             .addConstraintViolation();
	 *
	 * //From a class level constraint on Address
	 * //Build a constraint violation on the default path + "street"
	 * //i.e. the street property of Address
	 * context.buildConstraintViolationWithTemplate( "this detail is wrong" )
	 *             .addPropertyNode( "street" )
	 *             .addConstraintViolation();
	 *
	 * //From a property-level constraint on  User.addresses
	 * //Build a constraint violation on the default path + the bean stored
	 * //under the "home" key in the map
	 * context.buildConstraintViolationWithTemplate( "Incorrect home address" )
	 *             .addBeanNode()
	 *                 .inIterable().atKey( "home" )
	 *             .addConstraintViolation();
	 *
	 * //From a class level constraint on User
	 * //Build a constraint violation on the default path + addresses["home"].country.name
	 * //i.e. property "country.name" on the object stored under "home" in the map
	 * context.buildConstraintViolationWithTemplate( "this detail is wrong" )
	 *             .addPropertyNode( "addresses" )
	 *             .addPropertyNode( "country" )
	 *                 .inIterable().atKey( "home" )
	 *             .addPropertyNode( "name" )
	 *             .addConstraintViolation();
	 * </pre>
	 * <p/>
	 * Cross-parameter constraints on a method can create a node specific
	 * to a particular parameter if required. Let's explore a few examples:
	 * <p/>
	 * <pre>
	 * //Cross-parameter constraint on method createUser(String password, String passwordRepeat)
	 * //Build a constraint violation on the default path + "passwordRepeat"
	 * context.buildConstraintViolationWithTemplate("Passwords do not match")
	 *             .addParameterNode(1)
	 *             .addConstraintViolation();
	 *
	 * //Cross-parameter constraint on a method
	 * //mergeAddresses(Map<String,Address> addresses, Map<String,Address> otherAddresses)
	 * //Build a constraint violation on the default path + "otherAddresses["home"]
	 * //i.e. the Address bean hosted in the "home" key of the "otherAddresses" map parameter
	 * context.buildConstraintViolationWithTemplate(
	 *         "Map entry home present in both and does not match")
	 *             .addParameterNode(1)
	 *             .addBeanNode()
	 *                 .inIterable().atKey("home")
	 *             .addConstraintViolation();
	 *
	 * //Cross-parameter constraint on a method
	 * //mergeAddresses(Map<String,Address> addresses, Map<String,Address> otherAddresses)
	 * //Build a constraint violation on the default path + "otherAddresses["home"].city
	 * //i.e. on the "city" property of the Address bean hosted in
	 * //the "home" key of the "otherAddresses" map
	 * context.buildConstraintViolationWithTemplate(
	 *         "Map entry home present in both but city does not match")
	 *             .addParameterNode(1)
	 *             .addPropertyNode("city")
	 *                 .inIterable().atKey("home")
	 *             .addConstraintViolation();
	 * </pre>
	 *
	 * @param messageTemplate new un-interpolated constraint message
	 * @return returns a constraint violation builder
	 */
	ConstraintViolationBuilder buildConstraintViolationWithTemplate(String messageTemplate);

	/**
	 * Returns an instance of the specified type allowing access to
	 * provider-specific APIs. If the Bean Validation provider
	 * implementation does not support the specified class,
	 * {@link ValidationException} is thrown.
	 *
	 * @param type the class of the object to be returned
	 * @return an instance of the specified class
	 * @throws ValidationException if the provider does not support the call
	 *
	 * @since 1.1
	 */
	<T> T unwrap(Class<T> type);

	/**
	 * {@link ConstraintViolation} builder allowing to optionally associate
	 * the violation report to a sub path.
	 * <p/>
	 * To create the {@code ConstraintViolation}, one must call either one of
	 * the {@code addConstraintViolation()} methods available in one of the
	 * interfaces of the fluent API.
	 * <p/>
	 * If another method is called after {@code addConstraintViolation()} on
	 * {@code ConstraintViolationBuilder} or any of its associated objects
	 * an {@code IllegalStateException} is raised.
	 */
	interface ConstraintViolationBuilder {

		/**
		 * Adds a node to the path the {@link ConstraintViolation} will be associated to.
		 * <p/>
		 * {@code name} describes a single property. In particular,
		 * dot (.) is not allowed.
		 *
		 * @param name property name
		 * @return a builder representing node {@code name}
		 * @deprecated since 1.1 - replaced by {@link #addPropertyNode(String)},
		 *             {@link #addBeanNode()} and {@link #addParameterNode(int)}
		 */
		NodeBuilderDefinedContext addNode(String name);

		/**
		 * Adds a property node to the path the {@link ConstraintViolation}
		 * will be associated to.
		 * <p/>
		 * {@code name} describes a single property. In particular,
		 * dot (.) is not allowed.
		 *
		 * @param name property name
		 * @return a builder representing node {@code name}
		 * @throws IllegalArgumentException if the name is null
		 *
		 * @since 1.1
		 */
		NodeBuilderCustomizableContext addPropertyNode(String name);

		/**
		 * Adds a bean node (class-level) to the path the {@link ConstraintViolation}
		 * will be associated to.
		 * Note that bean nodes are always leaf nodes.
		 *
		 * @return a builder representing the bean node
		 *
		 * @since 1.1
		 */
		LeafNodeBuilderCustomizableContext addBeanNode();

		/**
		 * Adds a method parameter node to the path the {@link ConstraintViolation}
		 * will be associated to.
		 * The parameter index must be valid (i.e. within the boundaries of the method
		 * parameter indexes). May only be called from within cross-parameter validators.
		 *
		 * @param index the parameter index
		 * @return a builder representing the index-th parameter node
		 * @throws IllegalArgumentException if the index is not valid
		 *
		 * @since 1.1
		 */
		NodeBuilderDefinedContext addParameterNode(int index);

		/**
		 * Adds the new {@link ConstraintViolation} to be generated if the
		 * constraint validator marks the value as invalid.
		 * <p/>
		 * Methods of this {@code ConstraintViolationBuilder} instance and its nested
		 * objects throw {@code IllegalStateException} from now on.
		 *
		 * @return the {@code ConstraintValidatorContext} instance the
		 *         {@code ConstraintViolationBuilder} comes from
		 */
		ConstraintValidatorContext addConstraintViolation();

		/**
		 * Represents a node whose context is known
		 * (i.e. index, key and isInIterable)
		 * and that is a leaf node (i.e. no subnode can be added).
		 *
		 * @since 1.1
		 */
		interface LeafNodeBuilderDefinedContext {

			/**
			 * Adds the new {@link ConstraintViolation} to be generated if the
			 * constraint validator marks the value as invalid.
			 * <p/>
			 * Methods of the {@code ConstraintViolationBuilder} instance this object
			 * comes from and the constraint violation builder nested
			 * objects throw {@code IllegalStateException} after this call.
			 *
			 * @return {@code ConstraintValidatorContext} instance the
			 *         {@code ConstraintViolationBuilder} comes from
			 */
			ConstraintValidatorContext addConstraintViolation();
		}

		/**
		 * Represents a node whose context is
		 * configurable (i.e. index, key and isInIterable)
		 * and that is a leaf node (i.e. no subnode can be added).
		 *
		 * @since 1.1
		 */
		interface LeafNodeBuilderCustomizableContext {

			/**
			 * Marks the node as being in an {@code Iterable} or a {@code Map}.
			 * 
			 * @return a builder representing iterable details
			 */
			LeafNodeContextBuilder inIterable();

			/**
			 * Adds the new {@link ConstraintViolation} to be generated if the
			 * constraint validator mark the value as invalid.
			 * <p/>
			 * Methods of the {@code ConstraintViolationBuilder} instance this object
			 * comes from and the constraint violation builder nested
			 * objects throw {@code IllegalStateException} after this call.
			 *
			 * @return {@code ConstraintValidatorContext} instance the
			 *         {@code ConstraintViolationBuilder} comes from
			 */
			ConstraintValidatorContext addConstraintViolation();
		}

		/**
		 * Represents refinement choices for a node which is
		 * in an {@code Iterator} or {@code Map}.
		 * <p/>
		 * If the iterator is an indexed collection or a map,
		 * the index or the key should be set.
		 * <p/>
		 * The node is a leaf node (i.e. no subnode can be added).
		 *
		 * @since 1.1
		 */
		interface LeafNodeContextBuilder {

			/**
			 * Defines the key the object is into the {@code Map}.
			 *
			 * @param key map key
			 * @return a builder representing the current node
			 */
			LeafNodeBuilderDefinedContext atKey(Object key);

			/**
			 * Defines the index the object is into the {@code List} or array
			 *
			 * @param index index
			 * @return a builder representing the current node
			 */
			LeafNodeBuilderDefinedContext atIndex(Integer index);

			/**
			 * Adds the new {@link ConstraintViolation} to be generated if the
			 * constraint validator mark the value as invalid.
			 * <p/>
			 * Methods of the {@code ConstraintViolationBuilder} instance this object
			 * comes from and the constraint violation builder nested
			 * objects throw {@code IllegalStateException} after this call.
			 *
			 * @return {@code ConstraintValidatorContext} instance the
			 *           {@code ConstraintViolationBuilder} comes from
			 */
			ConstraintValidatorContext addConstraintViolation();
		}

		/**
		 * Represents a node whose context is known
		 * (i.e. index, key and isInIterable)
		 * and that is not necessarily a leaf node (i.e. subnodes can
		 * be added).
		 */
		interface NodeBuilderDefinedContext {

			/**
			 * Adds a node to the path the {@link ConstraintViolation} will be associated to.
			 * <p/>
			 * {@code name} describes a single property. In particular,
			 * dot (.) is not allowed.
			 *
			 * @param name property name
			 * @return a builder representing node {@code name}
			 * @deprecated since 1.1 - replaced by {@link #addPropertyNode(String)}
			 *             and {@link #addBeanNode()}
			 */
			NodeBuilderCustomizableContext addNode(String name);

			/**
			 * Adds a property node to the path the {@link ConstraintViolation}
			 * will be associated to.
			 * <p/>
			 * {@code name} describes a single property. In particular,
			 * dot (.) is not allowed.
			 *
			 * @param name property name
			 * @return a builder representing node {@code name}
			 * @throws IllegalArgumentException if the name is null
			 *
			 * @since 1.1
			 */
			NodeBuilderCustomizableContext addPropertyNode(String name);

			/**
			 * Adds a bean node (class-level) to the path the {@link ConstraintViolation}
			 * will be associated to.
			 * Note that bean nodes are always leaf nodes.
			 *
			 * @return a builder representing the bean node
			 *
			 * @since 1.1
			 */
			LeafNodeBuilderCustomizableContext addBeanNode();

			/**
			 * Adds the new {@link ConstraintViolation} to be generated if the
			 * constraint validator marks the value as invalid.
			 * <p/>
			 * Methods of the {@code ConstraintViolationBuilder} instance this object
			 * comes from and the constraint violation builder nested
			 * objects throw {@code IllegalStateException} after this call.
			 *
			 * @return {@code ConstraintValidatorContext} instance the
			 *           {@code ConstraintViolationBuilder} comes from
			 */
			ConstraintValidatorContext addConstraintViolation();
		}

		/**
		 * Represents a node whose context is
		 * configurable (i.e. index, key and isInIterable)
		 * and that is not necessarily a leaf node (i.e. subnodes can
		 * be added).
		 */
		interface NodeBuilderCustomizableContext {

			/**
			 * Marks the node as being in an {@code Iterable} or a {@code Map}.
			 *
			 * @return a builder representing iterable details
			 */
			NodeContextBuilder inIterable();

			/**
			 * Adds a node to the path the {@link ConstraintViolation} will be associated to.
			 *
			 * {@code name} describes a single property. In particular,
			 * dot (.) is not allowed.
			 *
			 * @param name property name
			 * @return a builder representing node {@code name}
			 * @deprecated since 1.1 - replaced by {@link #addPropertyNode(String)}
			 *             and {@link #addBeanNode()}
			 */
			NodeBuilderCustomizableContext addNode(String name);

			/**
			 * Adds a property node to the path the {@link ConstraintViolation}
			 * will be associated to.
			 *
			 * {@code name} describes a single property. In particular,
			 * dot (.) is not allowed.
			 *
			 * @param name property name
			 * @return a builder representing node {@code name}
			 * @throws IllegalArgumentException if the name is null
			 *
			 * @since 1.1
			 */
			NodeBuilderCustomizableContext addPropertyNode(String name);

			/**
			 * Adds a bean node (class-level) to the path the {@link ConstraintViolation}
			 * will be associated to.
			 * Note that bean nodes are always leaf nodes.
			 *
			 * @return a builder representing the bean node
			 *
			 * @since 1.1
			 */
			LeafNodeBuilderCustomizableContext addBeanNode();

			/**
			 * Adds the new {@link ConstraintViolation} to be generated if the
			 * constraint validator mark the value as invalid.
			 * <p/>
			 * Methods of the {@code ConstraintViolationBuilder} instance this object
			 * comes from and the constraint violation builder nested
			 * objects throw {@code IllegalStateException} after this call.
			 *
			 * @return {@code ConstraintValidatorContext} instance the
			 *           {@code ConstraintViolationBuilder} comes from
			 */
			ConstraintValidatorContext addConstraintViolation();
		}

		/**
		 * Represents refinement choices for a node which is
		 * in an {@code Iterator} or {@code Map}.
		 * <p/>
		 * If the iterator is an indexed collection or a map,
		 * the index or the key should be set.
		 * <p/>
		 * The node is not necessarily a leaf node (i.e. subnodes can
 		 * be added).
		 */
		interface NodeContextBuilder {
			
			/**
			 * Defines the key the object is into the {@code Map}.
			 *
			 * @param key map key
			 * @return a builder representing the current node
			 */
			NodeBuilderDefinedContext atKey(Object key);

			/**
			 * Defines the index the object is into the {@code List} or array.
			 *
			 * @param index index
			 * @return a builder representing the current node
			 */
			NodeBuilderDefinedContext atIndex(Integer index);

			/**
			 * Adds a node to the path the {@code ConstraintViolation} will be associated to.
			 *
			 * {@code name} describes a single property. In particular,
			 * dot (.) is not allowed.
			 *
			 * @param name property name
			 * @return a builder representing node {@code name}
			 * @deprecated since 1.1 - replaced by {@link #addPropertyNode(String)}
			 *             and {@link #addBeanNode()}
			 */
			NodeBuilderCustomizableContext addNode(String name);

			/**
			 * Adds a property node to the path the {@link ConstraintViolation}
			 * will be associated to.
			 *
			 * {@code name} describes a single property. In particular,
			 * dot (.) is not allowed.
			 *
			 * @param name property name
			 * @return a builder representing node {@code name}
			 * @throws IllegalArgumentException if the name is null
			 *
			 * @since 1.1
			 */
			NodeBuilderCustomizableContext addPropertyNode(String name);

			/**
			 * Adds a bean node (class-level) to the path the {@link ConstraintViolation}
			 * will be associated to.
			 * <p/>
			 * Note that bean nodes are always leaf nodes.
			 *
			 * @return a builder representing the bean node
			 *
			 * @since 1.1
			 */
			LeafNodeBuilderCustomizableContext addBeanNode();

			/**
			 * Adds the new {@link ConstraintViolation} to be generated if the
			 * constraint validator mark the value as invalid.
			 * <p/>
			 * Methods of the {@code ConstraintViolationBuilder} instance this object
			 * comes from and the constraint violation builder nested
			 * objects throw {@code IllegalStateException} after this call.
			 *
			 * @return {@code ConstraintValidatorContext} instance the
			 *         {@code ConstraintViolationBuilder} comes from
			 */
			ConstraintValidatorContext addConstraintViolation();
		}
	}
}
