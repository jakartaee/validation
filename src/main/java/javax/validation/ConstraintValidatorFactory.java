/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

/**
 * Instantiates a {@link ConstraintValidator} instance based off its class.
 * The {@code ConstraintValidatorFactory} is <b>not</b> responsible
 * for calling {@link ConstraintValidator#initialize(java.lang.annotation.Annotation)}.
 *
 * @author Dhanji R. Prasanna
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public interface ConstraintValidatorFactory {

	/**
	 * @param key The class of the constraint validator to instantiate
	 * @param <T> The type of the constraint validator to instantiate
	 *
	 * @return A new constraint validator instance of the specified class
	 */
	<T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key);

	/**
	 * Signals {@code ConstraintValidatorFactory} that the instance is no longer
	 * being used by the Jakarta Bean Validation provider.
	 *
	 * @param instance validator being released
	 *
	 * @since 1.1
	 */
	void releaseInstance(ConstraintValidator<?, ?> instance);
}
