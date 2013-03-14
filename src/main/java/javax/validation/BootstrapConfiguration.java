/*
* Copyright 2012-2013, Red Hat, Inc. and/or its affiliates, and individual contributors
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

import java.util.Map;
import java.util.Set;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import javax.validation.spi.ValidationProvider;

/**
 * Represents the user specified default configuration in
 * {@code META-INF/validation.xml}.
 * <p/>
 * Note that modifications to the returned objects do not have any effect.
 * Instead use the methods provided on {@link Configuration} in order to
 * apply modifications to the configuration.
 *
 * @author Emmanuel Bernard
 * @author Gunnar Morling
 * @author Hardy Ferentschik
 * @since 1.1
 */
public interface BootstrapConfiguration {

	/**
	 * Class name of the {@link ValidationProvider} implementation
	 * or {@code null} if none is specified.
	 *
	 * @return validation provider class name
	 */
	String getDefaultProviderClassName();

	/**
	 * Class name of the {@link ConstraintValidatorFactory} implementation
	 * or {@code null} if none is specified.
	 *
	 * @return constraint validator factory class name
	 */
	String getConstraintValidatorFactoryClassName();

	/**
	 * Class name of the {@link MessageInterpolator} implementation
	 * or {@code null} if none is specified.
	 *
	 * @return message interpolator class name or {@code null}
	 */
	String getMessageInterpolatorClassName();

	/**
	 * Class name of the {@link TraversableResolver} implementation
	 * or {@code null} if none is specified.
	 *
	 * @return traversable resolver class name or {@code null}
	 */
	String getTraversableResolverClassName();

	/**
	 * Class name of the {@link ParameterNameProvider} implementation
	 * or {@code null} if none is specified.
	 *
	 * @return parameter name provider class name or {@code null}
	 */
	String getParameterNameProviderClassName();

	/**
	 * Returns a set of resource paths pointing to XML constraint mapping files.
	 * The set is empty if none are specified.
	 *
	 * @return set of constraint mapping resource paths
	 */
	Set<String> getConstraintMappingResourcePaths();

	/**
	 * Returns true if the validation execution is explicitly marked as enabled
	 * or if it is left undefined.
	 *
	 * @return whether validation execution is globally enabled
	 */
	boolean isExecutableValidationEnabled();

	/**
	 * Returns the set of executable types that should be considered
	 * unless explicitly overridden via {@link ValidateOnExecution}
	 * <p/>
	 * Returns a set containing {@link ExecutableType#CONSTRUCTORS} and
	 * {@link ExecutableType#NON_GETTER_METHODS} if unspecified in the configuration.
	 *
	 * @return set of validated executable types
	 */
	Set<ExecutableType> getDefaultValidatedExecutableTypes();

	/**
	 * Returns properties as a map of string based key/value pairs.
	 * The map is empty if no property has been specified.
	 *
	 * @return the properties map
	 */
	Map<String, String> getProperties();
}
