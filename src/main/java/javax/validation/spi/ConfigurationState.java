/*
 * Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.spi;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import javax.validation.ClockProvider;
import javax.validation.Configuration;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.TraversableResolver;
import javax.validation.ValidatorFactory;

/**
 * Contract between a {@link Configuration} and a
 * {@link ValidationProvider} to create a {@link ValidatorFactory}.
 * <p>
 * The configuration artifacts defined in the XML configuration and provided to the
 * {@code Configuration} are merged and passed along via
 * {@code ConfigurationState}.
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 * @author Gunnar Morling
 * @author Guillaume Smet
 */
public interface ConfigurationState {

	/**
	 * Returns {@code true} if {@link Configuration#ignoreXmlConfiguration()} has been called.
	 * <p>
	 * In this case, the {@link ValidatorFactory} must ignore
	 * {@code META-INF/validation.xml}.
	 *
	 * @return {@code true} if {@code META-INF/validation.xml} should be ignored
	 */
	boolean isIgnoreXmlConfiguration();

	/**
	 * Returns the message interpolator of this configuration.
	 * <p>
	 * Message interpolator is defined in the following decreasing priority:
	 * <ul>
	 *     <li>set via the {@link Configuration} programmatic API</li>
	 *     <li>defined in {@code META-INF/validation.xml} provided that
	 *     {@code ignoreXmlConfiguration} is false. In this case the instance
	 *     is created via its no-arg constructor.</li>
	 *     <li>{@code null} if undefined.</li>
	 * </ul>
	 *
	 * @return message interpolator instance or {@code null} if not defined
	 */
	MessageInterpolator getMessageInterpolator();

	/**
	 * Returns a set of configuration streams.
	 * <p>
	 * The streams are defined by:
	 * <ul>
	 *     <li>mapping XML streams passed programmatically in {@link Configuration}</li>
	 *     <li>mapping XML streams located in the resources defined in
	 *     {@code META-INF/validation.xml} (constraint-mapping element)</li>
	 * </ul>
	 * <p>
	 * Streams represented in the XML configuration and opened by the
	 * {@code Configuration} implementation must be closed by the
	 * {@code Configuration} implementation after the {@link ValidatorFactory}
	 * creation (or if an exception occurs). All streams are guaranteed to
	 * adhere to the mark/reset contract (see {@link InputStream#markSupported()}
	 * by the Bean Validation provider.
	 *
	 * @return set of input stream
	 */
	Set<InputStream> getMappingStreams();

	/**
	 * Returns the constraint validator factory of this configuration.
	 * <p>
	 * The {@link ConstraintValidatorFactory} implementation is defined in the following
	 * decreasing priority:
	 * <ul>
	 *     <li>set via the {@link Configuration} programmatic API</li>
	 *     <li>defined in {@code META-INF/validation.xml} provided that
	 *     {@code ignoredXmlConfiguration} is {@code false}. In this case the instance
	 *     is created via its no-arg constructor.</li>
	 *     <li>{@code null} if undefined.</li>
	 * </ul>
	 *
	 * @return factory instance or {@code null} if not defined
	 */
	ConstraintValidatorFactory getConstraintValidatorFactory();

	/**
	 * Returns the traversable resolver for this configuration.
	 * <p>
	 * {@link TraversableResolver} is defined in the following decreasing priority:
	 * <ul>
	 *     <li>set via the {@link Configuration} programmatic API</li>
	 *     <li>defined in {@code META-INF/validation.xml} provided that
	 *     {@code ignoredXmlConfiguration} is {@code false}. In this case the
	 *     instance is created via its no-arg constructor.</li>
	 *     <li>{@code null} if undefined.</li>
	 * </ul>
	 *
	 * @return traversable resolver instance or {@code null} if not defined
	 */
	TraversableResolver getTraversableResolver();

	/**
	 * Returns the parameter name provider for this configuration.
	 * <p>
	 * {@link ParameterNameProvider} is defined in the following decreasing priority:
	 * <ul>
	 *     <li>set via the {@link Configuration} programmatic API</li>
	 *     <li>defined in {@code META-INF/validation.xml} provided that
	 *     {@code ignoreXmlConfiguration} is {@code false}. In this case the instance
	 *     is created via its no-arg constructor.</li>
	 *     <li>{@code null} if undefined.</li>
	 * </ul>
	 *
	 * @return parameter name provider instance or {@code null} if not defined
	 *
	 * @since 1.1
	 */
	ParameterNameProvider getParameterNameProvider();

	/**
	 * Returns the clock provider for this configuration.
	 * <p>
	 * {@link ClockProvider} is defined in the following decreasing priority:
	 * <ul>
	 *     <li>set via the {@link Configuration} programmatic API</li>
	 *     <li>defined in {@code META-INF/validation.xml} provided that
	 *     {@code ignoreXmlConfiguration} is {@code false}. In this case the instance
	 *     is created via its no-arg constructor.</li>
	 *     <li>{@code null} if undefined.</li>
	 * </ul>
	 *
	 * @return clock provider instance or {@code null} if not defined
	 *
	 * @since 2.0
	 */
	ClockProvider getClockProvider();

	/**
	 * Returns a map of non type-safe custom properties.
	 * <p>
	 * Properties defined via:
	 * <ul>
	 *     <li>{@link Configuration#addProperty(String, String)}</li>
	 *     <li>{@code META-INF/validation.xml} provided that
	 *     {@code ignoreXmlConfiguration} is {@code false}.</li>
	 * </ul>
	 * <p>
	 * If a property is defined both programmatically and in XML,
	 * the value defined programmatically has priority.
	 *
	 * @return {@code Map} whose key is the property key and the value
	 *         the property value
	 */
	Map<String, String> getProperties();
}
