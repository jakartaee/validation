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
package javax.validation;

/**
 * Provide contextual data and operation when applying a given constraint validator.
 *
 * At least one error must be defined (either the default one, of if the default error
 * is disabled, a custom one).
 *
 * @author Emmanuel Bernard
 */
public interface ConstraintValidatorContext {
	/**
	 * Disable the default error message and default ConstraintViolation object generation.
	 * Useful to set a different error message or generate a ConstraintViolation based on
	 * a different property
	 */
	void disableDefaultError();

	/**
	 * @return the current uninterpolated default message
	 */
	String getDefaultErrorMessageTemplate();

	/**
	 * Return an error builder building an error allowing to optionally associate
	 * the error to a sub path.
	 * The error message will be interpolated.
	 * <p/>
	 * To create the error, one must call either one of
	 * the #addError method available in one of the
	 * interfaces of the fluent API.
	 * If another method is called after #addError() on
	 * ErrorBuilder or any of its associated nested interfaces
	 * an IllegalStateException is raised.
	 * <p/>
	 * If <code>isValid<code> returns <code>false</code>, a <code>ConstraintViolation</code>
	 * object will be built per error including the default one unless
	 * {@link #disableDefaultError()} has been called.
	 * <p/>
	 * <code>ConstraintViolation</code> objects generated from such a call
	 * contain the same contextual information (root bean, path and so on) unless
	 * the path has been overriden.
	 * <p/>
	 * To create a different error, a new error builder has to be retrieved from
	 * ConstraintValidatorContext
	 *
	 * Here are a few usage examples:
	 * <pre>
	 * {@code
	 * // create new error with the default path the constraint is located on
	 * context.buildErrorWithMessageTemplate( "way too long" )
	 *             .addError();
	 *
	 * // create new error in the "street" subnode of the default path the constraint
	 * // is located on
	 * context.buildErrorWithMessageTemplate( "way too long" )
	 *              .addSubNode( "street" )
	 *              .addError();
	 *
	 * //create new error in the "addresses["home"].city.name subnode of the default
	 * // path the constraint is located on
	 * context.buildErrorWithMessageTemplate( "this detail is wrong" )
	 *              .addSubNode( "addresses" )
	 *              .addSubNode( "country" )
	 *                  .inIterable().atKey( "home" )
	 *              .addSubNode( "name" )
	 *              .addError();
	 * }
	 * </pre>
	 *
	 * @param messageTemplate new uninterpolated error message.
	 * @return Returns an error builder
	 */
	ErrorBuilder buildErrorWithMessageTemplate(String messageTemplate);

	/**
	 * Error builder allowing to optionally associate
	 * the error to a sub path.
	 *
	 * To create the error, one must call either one of
	 * the #addError method available in one of the
	 * interfaces of the fluent API.
	 * If another method is called after #addError() on
	 * ErrorBuilder or any of its associated objects
	 * an IllegalStateException is raised.
	 * 
	 */
	interface ErrorBuilder {
		/**
		 * Add a subNode to the path the error will be associated to.
		 *
		 * <code>name</code> describes a single property. In particular,
		 * dot (.) are not allowed.
		 *
		 * @param name property name
		 * @return a builder representing node <code>name</code>
		 */
		NodeBuilderDefinedContext addSubNode(String name);

		/**
		 * Add the new error report to be generated if the
		 * constraint validator mark the value as invalid.
		 * Methods of this ErrorBuilder instance and its nested
		 * objects returns IllegalStateException from now on.
		 *
		 * @return ConstraintValidatorContext instance the ErrorBuilder comes from 
		 */
		ConstraintValidatorContext addError();

		/**
		 * Represent a subnode whose context is known
		 * (ie index, key and isInIterable)
		 */
		interface NodeBuilderDefinedContext {

			/**
			 * Add a subNode to the path the error will be associated to.
			 *
			 * <code>name</code> describes a single property. In particular,
	         * dot (.) are not allowed.
			 *
			 * @param name property <code>name</code>
			 * @return a builder representing this node
			 */
			NodeBuilderCustomizableContext addSubNode(String name);

			/**
			 * Add the new error report to be generated if the
			 * constraint validator mark the value as invalid.
			 * Methods of the ErrorBuilder instance this object comes
			 * from and the error builder nested
			 * objects returns IllegalStateException after this call.
			 *
			 * @return ConstraintValidatorContext instance the ErrorBuilder comes from
			 */
			ConstraintValidatorContext addError();
		}

		/**
		 * Represent a subnode whose context is
		 * configurable (ie index, key and isInIterable)
		 */
		interface NodeBuilderCustomizableContext {

			/**
			 * Mark the node as being in an Iterable or a Map
			 * 
			 * @return a builder representing iterable details
			 */
			NodeContextBuilder inIterable();

			/**
			 * Add a subNode to the path the error will be associated to.
			 *
			 * <code>name</code> describes a single property. In particular,
	         * dot (.) are not allowed.
			 *
			 * @param name property <code>name</code>
			 * @return a builder representing this node
			 */
			NodeBuilderCustomizableContext addSubNode(String name);

			/**
			 * Add the new error report to be generated if the
			 * constraint validator mark the value as invalid.
			 * Methods of the ErrorBuilder instance this object comes
			 * from and the error builder nested
			 * objects returns IllegalStateException after this call.
			 *
			 * @return ConstraintValidatorContext instance the ErrorBuilder comes from
			 */
			ConstraintValidatorContext addError();
		}

		/**
		 * Represent refinement choices for a node which is
		 * in an Iterator or Map.
		 * If the iterator is an indexed collection or a map,
		 * the index or the key should be set.
		 */
		interface NodeContextBuilder {
			
			/**
			 * Define the key the object is into the Map
			 *
			 * @param key map key
			 * @return a builder representing the current node
			 */
			NodeBuilderDefinedContext atKey(Object key);

			/**
			 * Define the index the object is into the List or array
			 *
			 * @param index index
			 * @return a builder representing the current node
			 */
			NodeBuilderDefinedContext atIndex(Integer index);

			/**
			 * Add a subNode to the path the error will be associated to.
			 *
			 * <code>name</code> describes a single property. In particular,
	         * dot (.) are not allowed.
			 *
			 * @param name property <code>name</code>
			 * @return a builder representing this node
			 */
			NodeBuilderCustomizableContext addSubNode(String name);

			/**
			 * Add the new error report to be generated if the
			 * constraint validator mark the value as invalid.
			 * Methods of the ErrorBuilder instance this object comes
			 * from and the error builder nested
			 * objects returns IllegalStateException after this call.
			 *
			 * @return ConstraintValidatorContext instance the ErrorBuilder comes from
			 */
			ConstraintValidatorContext addError();
		}
	}
}
