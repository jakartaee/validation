/*
* JBoss, Home of Professional Open Source
* Copyright 2009-2012, Red Hat, Inc. and/or its affiliates, and individual contributors
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
 * Factory returning initialized {@code Validator} instances.
 * <p/>
 * Implementations are thread-safe and instances are typically cached and reused.
 *
 * @author Emmanuel Bernard
 * @author Gunnar Morling
 * @author Hardy Ferentschik
 */
public interface ValidatorFactory {

	/**
	 * Returns an initialized {@link Validator} instance using the
	 * factory defaults for message interpolator, traversable resolver
	 * and constraint validator factory.
	 * <p/>
	 * Validator instances can be pooled and shared by the implementation.
	 *
	 * @return an initialized {@code Validator} instance
	 */
	Validator getValidator();

	/**
	 * Defines a new validator context and returns a {@code Validator}
	 * compliant this new context.
	 *
	 * @return a {@link ValidatorContext} instance
	 */
	ValidatorContext usingContext();

	/**
	 * Returns the {@link MessageInterpolator} instance configured at
	 * initialization time for the {@code ValidatorFactory}.
	 * This is the instance used by {@link #getValidator()}.
	 *
	 * @return {@code MessageInterpolator} instance
	 */
	MessageInterpolator getMessageInterpolator();

	/**
	 * Returns the {@link TraversableResolver} instance configured
	 * at initialization time for the {@code ValidatorFactory}.
	 * This is the instance used by {@link #getValidator()}.
	 *
	 * @return {@code TraversableResolver} instance
	 */
	TraversableResolver getTraversableResolver();

	/**
	 * Returns the {@link ConstraintValidatorFactory} instance
	 * configured at initialization time for the
	 * {@code ValidatorFactory}.
	 * This is the instance used by {@link #getValidator()}.
	 *
	 * @return {@code ConstraintValidatorFactory} instance
	 */
	ConstraintValidatorFactory getConstraintValidatorFactory();

	/**
	 * Returns the {@link ParameterNameProvider} instance configured at
	 * initialization time for the {@code ValidatorFactory}.
	 * This is the instance used by #getValidator().
	 *
	 * @return {@code ParameterNameProvider} instance
	 *
	 * @since 1.1
	 */
	ParameterNameProvider getParameterNameProvider();

	/**
	 * Returns an instance of the specified type allowing access to
	 * provider-specific APIs. If the Bean Validation provider
	 * implementation does not support the specified class, a
	 * {@code ValidationException} is thrown.
	 *
	 * @param type the class of the object to be returned
	 * @return an instance of the specified class
	 * @throws ValidationException if the provider does not
	 *         support the call.
	 */
	public <T> T unwrap(Class<T> type);

	/**
	 * Closes the {@code ValidatorFactory} instance.
	 *
	 * After the {@code ValidatorFactory} instance is closed, calling the following
	 * methods is not allowed:
	 * <ul>
	 *     <li>methods of this {@code ValidatorFactory} instance</li>
	 *     <li>methods of {@link Validator} instances created by this {@code ValidatorFactory}</li>
	 * </ul>
	 *
	 * @since 1.1
	 */
	public void close();
}
