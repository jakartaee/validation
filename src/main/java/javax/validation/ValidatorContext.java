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
 * Represents the context that is used to create {@link Validator}
 * instances.
 *
 * A client may use methods of the {@code ValidatorContext} returned by
 * {@link ValidatorFactory#usingContext()} to customize
 * the context used to create {@code Validator} instances
 * (for instance establish different message interpolators or
 * traversable resolvers).
 *
 * @author Emmanuel Bernard
 * @author Gunnar Morling
 */
public interface ValidatorContext {

	/**
	 * Defines the message interpolator implementation used by the
	 * {@link Validator}.
	 * <p/>
	 * If not set or if {@code null} is passed as a parameter,
	 * the message interpolator of the {@link ValidatorFactory}
	 * is used.
	 *
	 * @param messageInterpolator the {@link MessageInterpolator} used by the {@code Validator}
	 *
	 * @return self following the chaining method pattern
	 */
	ValidatorContext messageInterpolator(MessageInterpolator messageInterpolator);

	/**
	 * Defines the traversable resolver implementation used by the
	 * {@link Validator}.
	 * <p/>
	 * If not set or if {@code null} is passed as a parameter,
	 * the traversable resolver of the {@link ValidatorFactory} is used.
	 *
	 * @param traversableResolver the {@code TraversableResolver} used by the {@code Validator}
	 * @return self following the chaining method pattern
	 */
	ValidatorContext traversableResolver(TraversableResolver traversableResolver);

	/**
	 * Defines the constraint validator factory implementation used by the
	 * {@link Validator}.
	 * If not set or if {@code null} is passed as a parameter,
	 * the constraint validator factory of the {@link ValidatorFactory} is used.
	 *
	 * @param factory the {@link ConstraintValidatorFactory} used by the {@code Validator}
	 * @return self following the chaining method pattern
	 */
	ValidatorContext constraintValidatorFactory(ConstraintValidatorFactory factory);

	/**
	 * Defines the parameter name provider implementation used by the
	 * {@link Validator}. If not set or if {@code null} is passed as a parameter,
	 * the parameter name provider of the {@link ValidatorFactory} is used.
	 *
	 * @param parameterNameProvider parameter name provider implementation.
	 * @return self following the chaining method pattern
	 *
	 * @since 1.1
	 */
	ValidatorContext parameterNameProvider(ParameterNameProvider parameterNameProvider);

	/**
	 * Returns an initialized {@link Validator} instance respecting the defined state.
	 * {@code Validator} instances can be pooled and shared by the implementation.
	 *
	 * @return contextualized {@code Validator}
	 */
	Validator getValidator();
}
