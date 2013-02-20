/*
* JBoss, Home of Professional Open Source
* Copyright 2009-2013, Red Hat, Inc. and/or its affiliates, and individual contributors
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

import java.util.Locale;
import javax.validation.metadata.ConstraintDescriptor;

/**
 * Interpolates a given constraint violation message.
 * <p/>
 * Implementations should be as tolerant as possible on syntax errors.
 * Implementations must be thread-safe.
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public interface MessageInterpolator {

	/**
	 * Interpolates the message template based on the constraint validation context.
	 * <p/>
	 * The locale is defaulted according to the {@code MessageInterpolator}
	 * implementation. See the implementation documentation for more detail.
	 *
	 * @param messageTemplate the message to interpolate
	 * @param context contextual information related to the interpolation
	 *
	 * @return interpolated error message
	 */
	String interpolate(String messageTemplate, Context context);

	/**
	 * Interpolates the message template based on the constraint validation context.
	 * The {@code Locale} used is provided as a parameter.
	 *
	 * @param messageTemplate the message to interpolate
	 * @param context contextual information related to the interpolation
	 * @param locale the locale targeted for the message
	 *
	 * @return interpolated error message
	 */
	String interpolate(String messageTemplate, Context context,  Locale locale);

	/**
	 * Information related to the interpolation context.
	 */
	interface Context {
		/**
		 * @return {@link ConstraintDescriptor} corresponding to the constraint being validated
		 */
		ConstraintDescriptor<?> getConstraintDescriptor();

		/**
		 * @return value being validated
		 */
		Object getValidatedValue();

		/**
		 * Returns an instance of the specified type allowing access to
		 * provider-specific APIs. If the Bean Validation provider
		 * implementation does not support the specified class,
		 * {@link ValidationException} is thrown.
		 *
		 * @param type the class of the object to be returned
		 * @return an instance of the specified class
		 * @throws ValidationException if the provider does not support the call
		 *
		 * @since 1.1
		 */
		<T> T unwrap(Class<T> type);
	}
}
