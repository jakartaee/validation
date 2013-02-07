/*
* Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual contributors
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

/**
 * Represents the user specified default configuration in
 * <i>META-INF/validation.xml</i>. Note that modifications to the returned
 * objects don't have any effect; Instead use the methods provided on
 * {@link Configuration} in order to apply modifications to the configuration.
 *
 * @author Emmanuel Bernard <emmanuel@hibernate.org>
 * @author Gunnar Morling
 * @author Hardy Ferentschik
 * @since 1.1
 */
public interface BootstrapConfiguration {
	/**
	 * Class name of the {@code ValidationProvider} implementation
	 * or {@code null} if none is specified.
	 *
	 * @return validation provider class name
	 */
	String getDefaultProviderClassName();

	/**
	 * Class name of the {@code ConstraintValidatorFactory} implementation
	 * or {@code null} if none is specified.
	 *
	 * @return constraint validator factory class name
	 */
	String getConstraintValidatorFactoryClassName();

	/**
	 * Class name of the {@code MessageInterpolator} implementation
	 * or {@code null} if none is specified.
	 *
	 * @return message interpolator class name or {@code null}
	 */
	String getMessageInterpolatorClassName();

	/**
	 * Class name of the {@code TraversableResolver} implementation
	 * or {@code null} if none is specified.
	 *
	 * @return traversable resolver class name or {@code null}
	 */
	String getTraversableResolverClassName();

	/**
	 * Class name of the {@code ParameterNameProvider} implementation
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
	 * Return the set of executable types that should be considered
	 * unless explicitly overridden via {@code javax.validation.ValidateExecutable}
	 *
	 * Return a set containing {@code CONSTRUCTORS} and {@code NON_GETTER_METHODS}
	 * if unspecified in the configuration.
	 *
	 * @return set of validated executable types
	 */
	Set<ExecutableType> getValidatedExecutableTypes();

	/**
	 * Returns properties as a map of string based key/value pairs.
	 * The map is empty if no property has been specified.
	 *
	 * @return the properties map
	 */
	Map<String, String> getProperties();
}
