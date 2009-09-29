// $Id$
/*
* JBoss, Home of Professional Open Source
* Copyright 2008, Red Hat, Inc. and/or its affiliates, and individual contributors
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
package javax.validation.bootstrap;

import javax.validation.ValidationProviderResolver;
import javax.validation.Configuration;

/**
 * Defines the state used to bootstrap Bean Validation and
 * creates a provider agnostic Configuration.
 *
 * @author Emmanuel Bernard
 */
public interface GenericBootstrap {
	/**
	 * Defines the provider resolution strategy.
	 * This resolver returns the list of providers evaluated
	 * to build the Configuration
	 * <p/>
	 * If no resolver is defined, the default ValidationProviderResolver
	 * implementation is used.
	 *
	 * @return <code>this</code> following the chaining method pattern
	 */
	GenericBootstrap providerResolver(ValidationProviderResolver resolver);

	/**
	 * Returns a generic Configuration implementation.
	 * At this stage the provider used to build the ValidatorFactory is not defined.
	 * <p/>
	 * The Configuration implementation is provided by the first provider returned
	 * by the ValidationProviderResolver strategy.
	 *
	 * @return a Configuration implementation compliant with the bootstrap state
	 * @throws javax.validation.ValidationException if the Configuration object cannot be built
	 *                        this is generally due to an issue with the ValidationProviderResolver
	 */
	Configuration<?> configure();
}
