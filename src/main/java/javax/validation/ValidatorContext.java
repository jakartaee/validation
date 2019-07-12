/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

import javax.validation.valueextraction.ValueExtractor;
import javax.validation.valueextraction.ValueExtractorDeclarationException;

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
 * @author Guillaume Smet
 */
public interface ValidatorContext {

	/**
	 * Defines the message interpolator implementation used by the
	 * {@link Validator}.
	 * <p>
	 * If not set or if {@code null} is passed as a parameter,
	 * the message interpolator of the {@link ValidatorFactory}
	 * is used.
	 *
	 * @param messageInterpolator the {@link MessageInterpolator} used by the
	 * {@code Validator}
	 *
	 * @return self following the chaining method pattern
	 */
	ValidatorContext messageInterpolator(MessageInterpolator messageInterpolator);

	/**
	 * Defines the traversable resolver implementation used by the
	 * {@link Validator}.
	 * <p>
	 * If not set or if {@code null} is passed as a parameter,
	 * the traversable resolver of the {@link ValidatorFactory} is used.
	 *
	 * @param traversableResolver the {@code TraversableResolver} used by the
	 * {@code Validator}
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
	 * Defines the {@link ClockProvider} implementation used by the {@link Validator}.
	 * If not set or if {@code null} is passed as a parameter,
	 * the clock provider of the {@link ValidatorFactory} is used.
	 *
	 * @param clockProvider {@code ClockProvider} implementation
	 * @return self following the chaining method pattern
	 *
	 * @since 2.0
	 */
	ValidatorContext clockProvider(ClockProvider clockProvider);

	/**
	 * Adds a value extractor to be used by the {@link Validator}. Has
	 * priority over any extractor for the same type and type parameter
	 * detected through the service loader, given in the XML configuration or
	 * configured for the validator factory.
	 *
	 * @param extractor value extractor implementation
	 * @return self following the chaining method pattern
	 * @throws ValueExtractorDeclarationException if more than one extractor for
	 *         the same type and type parameter is added
	 * @since 2.0
	 */
	ValidatorContext addValueExtractor(ValueExtractor<?> extractor);

	/**
	 * Returns an initialized {@link Validator} instance respecting the defined state.
	 * {@code Validator} instances can be pooled and shared by the implementation.
	 *
	 * @return contextualized {@code Validator}
	 */
	Validator getValidator();
}
