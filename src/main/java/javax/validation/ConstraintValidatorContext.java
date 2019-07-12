/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

import java.time.Clock;

/**
 * Provides contextual data and operation when applying a given constraint validator.
 *
 * At least one {@link ConstraintViolation} must be defined (either the default one,
 * of if the default {@code ConstraintViolation} is disabled, a custom one).
 *
 * @author Emmanuel Bernard
 * @author Guillaume Smet
 */
public interface ConstraintValidatorContext {

	/**
	 * Disables the default {@link ConstraintViolation} object generation (which
	 * is using the message template declared on the constraint).
	 * <p>
	 * Useful to set a different violation message or generate a {@code ConstraintViolation}
	 * based on a different property.
	 */
	void disableDefaultConstraintViolation();

	/**
	 * @return the current un-interpolated default message
	 */
	String getDefaultConstraintMessageTemplate();

	/**
	 * Returns the provider for obtaining the current time in the form of a {@link Clock},
	 * e.g. when validating the {@code Future} and {@code Past} constraints.
	 *
	 * @return the provider for obtaining the current time, never {@code null}. If no
	 * specific provider has been configured during bootstrap, a default implementation using
	 * the current system time and the current default time zone as returned by
	 * {@link Clock#systemDefaultZone()} will be returned.
	 *
	 * @since 2.0
	 */
	ClockProvider getClockProvider();

	/**
	 * Returns a constraint violation builder building a violation report
	 * allowing to optionally associate it to a sub path.
	 * The violation message will be interpolated.
	 * <p>
	 * To create the {@link ConstraintViolation}, one must call either one of
	 * the {@code addConstraintViolation()} methods available in one of the
	 * interfaces of the fluent API.
	 * If another method is called after {@code addConstraintViolation()} on
	 * {@code ConstraintViolationBuilder} or any of its associated nested interfaces
	 * an {@code IllegalStateException} is raised.
	 * <p>
	 * If {@link ConstraintValidator#isValid(Object, ConstraintValidatorContext)} returns
	 * {@code false}, a {@code ConstraintViolation} object will be built per constraint
	 * violation report including the default one (unless
	 * {@link #disableDefaultConstraintViolation()} has been called).
	 * <p>
	 * {@code ConstraintViolation} objects generated from such a call
	 * contain the same contextual information (root bean, path and so on) unless
	 * the path has been overridden.
	 * <p>
	 * To create a different {@code ConstraintViolation}, a new constraint violation builder
	 * has to be retrieved from {@code ConstraintValidatorContext}
	 *
	 * Here are a few usage examples:
	 * <pre>
	 * //assuming the following domain model
	 * public class User {
	 *     public Map&lt;String,Address&gt; getAddresses() { ... }
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
	 *                 .inContainer( Map.class, 1 )
	 *                 .inIterable().atKey( "home" )
	 *             .addConstraintViolation();
	 *
	 * //From a class level constraint on User
	 * //Build a constraint violation on the default path + addresses["home"].country.name
	 * //i.e. property "country.name" on the object stored under "home" in the map
	 * context.buildConstraintViolationWithTemplate( "this detail is wrong" )
	 *             .addPropertyNode( "addresses" )
	 *             .addPropertyNode( "country" )
	 *                 .inContainer( Map.class, 1 )
	 *                 .inIterable().atKey( "home" )
	 *             .addPropertyNode( "name" )
	 *             .addConstraintViolation();
	 *
	 * //From a class level constraint on User
	 * //Build a constraint violation on the default path + addresses["home"].&lt;map key&gt;
	 * //i.e. a container element constraint violation for the map key
	 * context.buildConstraintViolationWithTemplate( "the map key is invalid" )
	 *             .addPropertyNode( "addresses" )
	 *             .addContainerElementNode( "&lt;map key&gt;", Map.class, 0 )
	 *                 .inIterable().atKey( "invalid" )
	 *             .addConstraintViolation();
	 * </pre>
	 * <p>
	 * Cross-parameter constraints on a method can create a node specific
	 * to a particular parameter if required. Let's explore a few examples:
	 * <pre>
	 * //Cross-parameter constraint on method
	 * //createUser(String password, String passwordRepeat)
	 * //Build a constraint violation on the default path + "passwordRepeat"
	 * context.buildConstraintViolationWithTemplate("Passwords do not match")
	 *             .addParameterNode(1)
	 *             .addConstraintViolation();
	 *
	 * //Cross-parameter constraint on a method
	 * //mergeAddresses(Map&lt;String,Address&gt; addresses,
	 * //        Map&lt;String,Address&gt; otherAddresses)
	 * //Build a constraint violation on the default path + "otherAddresses["home"]
	 * //i.e. the Address bean hosted in the "home" key of the "otherAddresses" map parameter
	 * context.buildConstraintViolationWithTemplate(
	 *         "Map entry home present in both and does not match")
	 *             .addParameterNode(1)
	 *             .addBeanNode()
	 *                 .inContainer( Map.class, 1 )
	 *                 .inIterable().atKey("home")
	 *             .addConstraintViolation();
	 *
	 * //Cross-parameter constraint on a method
	 * //mergeAddresses(Map&lt;String,Address&gt; addresses,
	 * //        Map&lt;String,Address&gt; otherAddresses)
	 * //Build a constraint violation on the default path + "otherAddresses["home"].city
	 * //i.e. on the "city" property of the Address bean hosted in
	 * //the "home" key of the "otherAddresses" map
	 * context.buildConstraintViolationWithTemplate(
	 *         "Map entry home present in both but city does not match")
	 *             .addParameterNode(1)
	 *             .addPropertyNode("city")
	 *                 .inContainer( Map.class, 1 )
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
	 * provider-specific APIs. If the Jakarta Bean Validation provider
	 * implementation does not support the specified class,
	 * {@link ValidationException} is thrown.
	 *
	 * @param type the class of the object to be returned
	 * @param <T> the type of the object to be returned
	 * @return an instance of the specified class
	 * @throws ValidationException if the provider does not support the call
	 *
	 * @since 1.1
	 */
	<T> T unwrap(Class<T> type);

	/**
	 * {@link ConstraintViolation} builder allowing to optionally associate
	 * the violation report to a sub path.
	 * <p>
	 * To create the {@code ConstraintViolation}, one must call either one of
	 * the {@code addConstraintViolation()} methods available in one of the
	 * interfaces of the fluent API.
	 * <p>
	 * If another method is called after {@code addConstraintViolation()} on
	 * {@code ConstraintViolationBuilder} or any of its associated objects
	 * an {@code IllegalStateException} is raised.
	 */
	interface ConstraintViolationBuilder {

		/**
		 * Adds a node to the path the {@link ConstraintViolation} will be associated to.
		 * <p>
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
		 * <p>
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
		 * Adds a container element node to the path the {@link ConstraintViolation}
		 * will be associated to.
		 *
		 * @param name the node name
		 * @param containerType the type of the container
		 * @param typeArgumentIndex the index of the type argument
		 * @return a builder representing the container element node
		 * @throws IllegalArgumentException if the index is not valid
		 *
		 * @since 2.0
		 */
		ContainerElementNodeBuilderCustomizableContext addContainerElementNode(String name,
				Class<?> containerType, Integer typeArgumentIndex);

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
		 * <p>
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
			 * <p>
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
			 * Marks the node as being in an iterable, e.g. array, {@code Iterable} or a
			 * {@code Map}.
			 *
			 * @return a builder representing iterable details
			 */
			LeafNodeContextBuilder inIterable();

			/**
			 * Marks the node as being in a container such as a {@code List}, {@code Map} or
			 * {@code Optional}.
			 *
			 * @param containerClass the type of the container
			 * @param typeArgumentIndex type index of the concerned type argument
			 * @return a builder representing the current node
			 * @throws IllegalArgumentException if the index is not valid
			 *
			 * @since 2.0
			 */
			LeafNodeBuilderCustomizableContext inContainer(Class<?> containerClass,
														   Integer typeArgumentIndex);

			/**
			 * Adds the new {@link ConstraintViolation} to be generated if the
			 * constraint validator mark the value as invalid.
			 * <p>
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
		 * in an iterable, e.g. array, {@code Iterable} or {@code Map}.
		 * <p>
		 * If the iterable is an indexed collection or a map,
		 * the index or the key should be set.
		 * <p>
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
			 * <p>
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
			 * <p>
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
			 * <p>
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
			 * Adds a container element node to the path the {@link ConstraintViolation}
			 * will be associated to.
			 *
			 * @param name the node name
			 * @param containerType the type of the container
			 * @param typeArgumentIndex the index of the type argument
			 * @return a builder representing the container element node
			 * @throws IllegalArgumentException if the index is not valid
			 *
			 * @since 2.0
			 */
			ContainerElementNodeBuilderCustomizableContext addContainerElementNode(
					String name, Class<?> containerType, Integer typeArgumentIndex);

			/**
			 * Adds the new {@link ConstraintViolation} to be generated if the
			 * constraint validator marks the value as invalid.
			 * <p>
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
			 * Marks the node as being in an iterable, e.g. array, {@code Iterable} or a
			 * {@code Map}.
			 *
			 * @return a builder representing iterable details
			 */
			NodeContextBuilder inIterable();

			/**
			 * Marks the node as being in a container such as a {@code List}, {@code Map} or
			 * {@code Optional}.
			 *
			 * @param containerClass the type of the container
			 * @param typeArgumentIndex type index of the concerned type argument
			 * @return a builder representing the current node
			 * @throws IllegalArgumentException if the index is not valid
			 *
			 * @since 2.0
			 */
			NodeBuilderCustomizableContext inContainer(Class<?> containerClass,
													   Integer typeArgumentIndex);

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
			 * Adds a container element node to the path the {@link ConstraintViolation}
			 * will be associated to.
			 *
			 * @param name the node name
			 * @param containerType the type of the container
			 * @param typeArgumentIndex the index of the type argument
			 * @return a builder representing the container element node
			 * @throws IllegalArgumentException if the index is not valid
			 *
			 * @since 2.0
			 */
			ContainerElementNodeBuilderCustomizableContext addContainerElementNode(
					String name, Class<?> containerType, Integer typeArgumentIndex);

			/**
			 * Adds the new {@link ConstraintViolation} to be generated if the
			 * constraint validator mark the value as invalid.
			 * <p>
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
		 * in an iterable, e.g. array, {@code Iterable} or {@code Map}.
		 * <p>
		 * If the iterable is an indexed collection or a map,
		 * the index or the key should be set.
		 * <p>
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
			 * <p>
			 * Note that bean nodes are always leaf nodes.
			 *
			 * @return a builder representing the bean node
			 *
			 * @since 1.1
			 */
			LeafNodeBuilderCustomizableContext addBeanNode();

			/**
			 * Adds a container element node to the path the {@link ConstraintViolation}
			 * will be associated to.
			 *
			 * @param name the node name
			 * @param containerType the type of the container
			 * @param typeArgumentIndex the index of the type argument
			 * @return a builder representing the container element node
			 * @throws IllegalArgumentException if the index is not valid
			 *
			 * @since 2.0
			 */
			ContainerElementNodeBuilderCustomizableContext addContainerElementNode(
					String name, Class<?> containerType, Integer typeArgumentIndex);

			/**
			 * Adds the new {@link ConstraintViolation} to be generated if the
			 * constraint validator mark the value as invalid.
			 * <p>
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
		 * Represents a container element node whose context is known
		 * (i.e. index, key and isInIterable)
		 * and that is not necessarily a leaf node (i.e. subnodes can
		 * be added).
		 *
		 * @since 2.0
		 */
		interface ContainerElementNodeBuilderDefinedContext {

			/**
			 * Adds a property node to the path the {@link ConstraintViolation}
			 * will be associated to.
			 * <p>
			 * {@code name} describes a single property. In particular,
			 * dot (.) is not allowed.
			 *
			 * @param name property name
			 * @return a builder representing node {@code name}
			 * @throws IllegalArgumentException if the name is null
			 */
			NodeBuilderCustomizableContext addPropertyNode(String name);

			/**
			 * Adds a bean node (class-level) to the path the {@link ConstraintViolation}
			 * will be associated to.
			 * Note that bean nodes are always leaf nodes.
			 *
			 * @return a builder representing the bean node
			 */
			LeafNodeBuilderCustomizableContext addBeanNode();

			/**
			 * Adds a container element node to the path the {@link ConstraintViolation}
			 * will be associated to.
			 *
			 * @param name the node name
			 * @param containerType the type of the container
			 * @param typeArgumentIndex the index of the type argument
			 * @return a builder representing the container element node
			 * @throws IllegalArgumentException if the index is not valid
			 */
			ContainerElementNodeBuilderCustomizableContext addContainerElementNode(
					String name, Class<?> containerType, Integer typeArgumentIndex);

			/**
			 * Adds the new {@link ConstraintViolation} to be generated if the
			 * constraint validator marks the value as invalid.
			 * <p>
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
		 * Represents a container element node whose context is
		 * configurable (i.e. index, key and isInIterable)
		 * and that is not necessarily a leaf node (i.e. subnodes can
		 * be added).
		 *
		 * @since 2.0
		 */
		interface ContainerElementNodeBuilderCustomizableContext {

			/**
			 * Marks the node as being in an iterable, e.g. array, {@code Iterable} or a
			 * {@code Map}.
			 *
			 * @return a builder representing iterable details
			 */
			ContainerElementNodeContextBuilder inIterable();

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
			 */
			NodeBuilderCustomizableContext addPropertyNode(String name);

			/**
			 * Adds a bean node (class-level) to the path the {@link ConstraintViolation}
			 * will be associated to.
			 * <p>
			 * Note that bean nodes are always leaf nodes.
			 *
			 * @return a builder representing the bean node
			 */
			LeafNodeBuilderCustomizableContext addBeanNode();

			/**
			 * Adds a container element node to the path the {@link ConstraintViolation}
			 * will be associated to.
			 *
			 * @param name the node name
			 * @param containerType the type of the container
			 * @param typeArgumentIndex the index of the type argument
			 * @return a builder representing the container element node
			 * @throws IllegalArgumentException if the index is not valid
			 */
			ContainerElementNodeBuilderCustomizableContext addContainerElementNode(
					String name, Class<?> containerType, Integer typeArgumentIndex);

			/**
			 * Adds the new {@link ConstraintViolation} to be generated if the
			 * constraint validator mark the value as invalid.
			 * <p>
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
		 * Represents refinement choices for a container element node.
		 * <p>
		 * If the container is an indexed collection or a map,
		 * the index or the key should be set.
		 * <p>
		 * The node is not necessarily a leaf node (i.e. subnodes can
		 * be added).
		 *
		 * @since 2.0
		 */
		interface ContainerElementNodeContextBuilder {

			/**
			 * Defines the key the object is into the {@code Map}.
			 *
			 * @param key map key
			 * @return a builder representing the current node
			 */
			ContainerElementNodeBuilderDefinedContext atKey(Object key);

			/**
			 * Defines the index the object is into the {@code List} or array.
			 *
			 * @param index index
			 * @return a builder representing the current node
			 */
			ContainerElementNodeBuilderDefinedContext atIndex(Integer index);

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
			 */
			NodeBuilderCustomizableContext addPropertyNode(String name);

			/**
			 * Adds a bean node (class-level) to the path the {@link ConstraintViolation}
			 * will be associated to.
			 * <p>
			 * Note that bean nodes are always leaf nodes.
			 *
			 * @return a builder representing the bean node
			 */
			LeafNodeBuilderCustomizableContext addBeanNode();

			/**
			 * Adds a container element node to the path the {@link ConstraintViolation}
			 * will be associated to.
			 *
			 * @param name the node name
			 * @param containerType the type of the container
			 * @param typeArgumentIndex the index of the type argument
			 * @return a builder representing the container element node
			 * @throws IllegalArgumentException if the index is not valid
			 */
			ContainerElementNodeBuilderCustomizableContext addContainerElementNode(
					String name, Class<?> containerType, Integer typeArgumentIndex);

			/**
			 * Adds the new {@link ConstraintViolation} to be generated if the
			 * constraint validator mark the value as invalid.
			 * <p>
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
