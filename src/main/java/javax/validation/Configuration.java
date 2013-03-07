/*
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

import javax.validation.spi.ValidationProvider;
import java.io.InputStream;

/**
 * Receives configuration information, selects the appropriate
 * Bean Validation provider and builds the appropriate {@link ValidatorFactory}.
 * <p/>
 * Usage:
 * <pre>
 * Configuration<?> configuration = //provided by one of the Validation bootstrap methods
 *     ValidatorFactory = configuration
 *         .messageInterpolator( new CustomMessageInterpolator() )
 *         .buildValidatorFactory();
 * </pre>
 * <p/>
 * By default, the configuration information is retrieved from
 * {@code META-INF/validation.xml}.
 * It is possible to override the configuration retrieved from the XML file
 * by using one or more of the {@code Configuration} methods.
 * <p/>
 * The {@link ValidationProviderResolver} is specified at configuration time
 * (see {@link ValidationProvider}).
 * If none is explicitly requested, the default {@code ValidationProviderResolver} is used.
 * <p/>
 * The provider is selected in the following way:
 * <ul>
 *     <li>if a specific provider is requested programmatically using
 *     {@link Validation#byProvider(Class)}, find the first provider implementing
 *     the provider class requested and use it</li>
 *     <li>if a specific provider is requested in {@code META-INF/validation.xml}>,
 *     find the first provider implementing the provider class requested and use it</li>
 *     <li>otherwise, use the first provider returned by the {@code ValidationProviderResolver}</li>
 * </ul>
 * <p/>
 * Implementations are not meant to be thread-safe.
 *
 * @author Emmanuel Bernard
 * @author Gunnar Morling
 * @author Hardy Ferentschik
 */
public interface Configuration<T extends Configuration<T>> {

	/**
	 * Ignores data from the {@code META-INF/validation.xml} file if this
	 * method is called.
	 * <p/>
	 * This method is typically useful for containers that parse
	 * {@code META-INF/validation.xml} themselves and pass the information
	 * via the {@code Configuration} methods.
	 *
	 * @return {@code this} following the chaining method pattern.
	 */
	T ignoreXmlConfiguration();

	/**
	 * Defines the message interpolator used. Has priority over the configuration
	 * based message interpolator.
	 * <p/>
	 * If {@code null} is passed, the default message interpolator is used
	 * (defined in XML or the specification default).
	 *
	 * @param interpolator message interpolator implementation
	 * @return {@code this} following the chaining method pattern
	 */
	T messageInterpolator(MessageInterpolator interpolator);

	/**
	 * Defines the traversable resolver used. Has priority over the configuration
	 * based traversable resolver.
	 * <p/>
	 * If {@code null} is passed, the default traversable resolver is used
	 * (defined in XML or the specification default).
	 *
	 * @param resolver traversable resolver implementation
	 * @return {@code this} following the chaining method pattern
	 */
	T traversableResolver(TraversableResolver resolver);

	/**
	 * Defines the constraint validator factory. Has priority over the configuration
	 * based constraint factory.
	 * <p/>
	 * If {@code null} is passed, the default constraint validator factory is used
	 * (defined in XML or the specification default).
	 *
	 * @param constraintValidatorFactory constraint factory implementation
	 * @return {@code this} following the chaining method pattern
	 */
	T constraintValidatorFactory(ConstraintValidatorFactory constraintValidatorFactory);

	/**
	 * Defines the parameter name provider. Has priority over the configuration
	 * based provider.
	 * <p/>
	 * If {@code null} is passed, the default parameter name provider is used
	 * (defined in XML or the specification default).
	 *
	 * @param parameterNameProvider parameter name provider implementation
	 * @return {@code this} following the chaining method pattern.
	 *
	 * @since 1.1
	 */
	T parameterNameProvider(ParameterNameProvider parameterNameProvider);

	/**
	 * Add a stream describing constraint mapping in the Bean Validation XML
	 * format.
	 * <p/>
	 * The stream should be closed by the client API after the
	 * {@link ValidatorFactory} has been built. The Bean Validation provider
	 * must not close the stream.
	 *
	 * @param stream
	 *        XML mapping stream; the given stream should support the
	 *        mark/reset contract (see {@link InputStream#markSupported()});
	 *        if it doesn't, it will be wrapped into a stream supporting the
	 *        mark/reset contract by the Bean Validation provider
	 *
	 * @return {@code this} following the chaining method pattern
	 * @throws IllegalArgumentException if {@code stream} is null
	 */
	T addMapping(InputStream stream);

	/**
	 * Adds a provider specific property. This property is equivalent to
	 * XML configuration properties.
	 * If the underlying provider does not know how to handle the property,
	 * it must silently ignore it.
	 * <p/>
	 * Note: Using this non type-safe method is generally not recommended.
	 * <p/>
	 * It is more appropriate to use, if available, the type-safe equivalent provided
	 * by a specific provider via its {@link Configuration} subclass.
	 * <pre>
	 * ValidatorFactory factory = Validation.byProvider(ACMEProvider.class)
	 *     .configure()
	 *         .providerSpecificProperty(ACMEState.FAST)
	 *     .buildValidatorFactory();
	 * </pre>
	 * This method is typically used by containers parsing {@code META-INF/validation.xml}
	 * themselves and injecting the state to the {@code Configuration} object.
	 * <p/>
	 * If a property with a given name is defined both via this method and in the
	 * XML configuration, the value set programmatically has priority.
	 * <p/>
	 * If {@code null} is passed as a value, the value defined in XML is used. If no value
	 * is defined in XML, the property is considered unset.
	 *
	 * @param name property name
	 * @param value property value
	 * @return {@code this} following the chaining method pattern
	 * @throws IllegalArgumentException if {@code name} is null
	 */
	T addProperty(String name, String value);

	/**
	 * Returns an implementation of the {@link MessageInterpolator} interface
	 * following the default {@code MessageInterpolator} defined in the
	 * specification:
	 * <ul>
	 *     <li>use the {@code ValidationMessages} resource bundle to load keys</li>
	 *     <li>use {@code Locale.getDefault()}</li>
	 * </ul>
	 *
	 * @return default {@code MessageInterpolator} implementation compliant with the
	 *         specification
	 */
	MessageInterpolator getDefaultMessageInterpolator();

	/**
	 * Returns an implementation of the {@link TraversableResolver} interface
	 * following the default {@code TraversableResolver} defined in the
	 * specification:
	 * <ul>
	 *     <li>if Java Persistence is available in the runtime environment,
	 *     a property is considered reachable if Java Persistence considers
	 *     the property as loaded</li>
	 *     <li>if Java Persistence is not available in the runtime environment,
	 *     all properties are considered reachable</li>
	 *     <li>all properties are considered cascadable.</li>
	 * </ul>
	 *
	 * @return default {@code TraversableResolver} implementation compliant with the
	 *         specification
	 */
	TraversableResolver getDefaultTraversableResolver();

	/**
	 * Returns an implementation of the {@link ConstraintValidatorFactory} interface
	 * following the default {@code ConstraintValidatorFactory} defined in the
	 * specification:
	 * <ul>
	 *     <li>uses the public no-arg constructor of the {@link ConstraintValidator}</li>
	 * </ul>
	 *
	 * @return default {@code ConstraintValidatorFactory} implementation compliant with the
	 *         specification
	 */
	ConstraintValidatorFactory getDefaultConstraintValidatorFactory();

	/**
	 * Returns an implementation of the {@link ParameterNameProvider}
	 * interface following the default {@code ParameterNameProvider}
	 * defined in the specification:
	 * <ul>
	 *     <li>returns names in the form {@code arg&lt;PARAMETER_INDEX&gt;}
	 *     where {@code PARAMETER_INDEX} starts at 0 for the first parameter,
	 *     e.g. {@code arg0}, {@code arg1} etc.</li>
	 * </ul>
	 *
	 * @return default {@code ParameterNameProvider} implementation compliant with
	 *         the specification
	 *
	 * @since 1.1
	 */
	ParameterNameProvider getDefaultParameterNameProvider();

	/**
	 * Returns configuration information stored in the {@code META-INF/validation.xml} file.
	 * <p/>
	 * <b>Note</b>:<br/>
	 * Implementations are encouraged to lazily build this object to delay parsing.
	 *
	 * @return returns an instance of {@link BootstrapConfiguration}; this method never
	 *         returns {@code null}; if there is no {@code META-INF/validation.xml} the
	 *         different getters of the returned instance will return {@code null}
	 *         respectively an empty set or map
	 *
	 * @since 1.1
	 */
	BootstrapConfiguration getBootstrapConfiguration();

	/**
	 * Build a {@link ValidatorFactory} implementation.
	 *
	 * @return the {@code ValidatorFactory}
	 * @throws ValidationException if the {@code ValidatorFactory} cannot be built
	 */
	ValidatorFactory buildValidatorFactory();
}
