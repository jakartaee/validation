/*
* JBoss, Home of Professional Open Source
* Copyright 2011, Red Hat, Inc. and/or its affiliates, and individual contributors
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

import java.util.Collections;
import java.util.Set;

/**
 * Exception class to be thrown by integrators of the BV method validation feature.
 *
 * @author Gunnar Morling
 */
public class MethodConstraintViolationException extends ValidationException {

	private static final long serialVersionUID = 5694703022614920634L;

	private final Set<MethodConstraintViolation<?>> constraintViolations;

	/**
	 * Creates a new {@link MethodConstraintViolationException}.
	 *
	 * @param constraintViolations A set of constraint violations for which this exception shall be created.
	 */
	public MethodConstraintViolationException(
			Set<? extends MethodConstraintViolation<?>> constraintViolations) {

		this( null, constraintViolations );
	}

	/**
	 * Creates a new {@link MethodConstraintViolationException}.
	 *
	 * @param message The message for the exception to be created.
	 * @param constraintViolations A set of constraint violations for which this exception shall be created.
	 */
	public MethodConstraintViolationException(String message,
											  Set<? extends MethodConstraintViolation<?>> constraintViolations) {

		super( message );
		this.constraintViolations = constraintViolations == null ? Collections.<MethodConstraintViolation<?>>emptySet() : Collections
				.unmodifiableSet( constraintViolations );
	}

	/**
	 * Returns the set of constraint violations reported during a validation.
	 *
	 * @return An unmodifiable set of {@link MethodConstraintViolation}s occurred during a method level validation call.
	 */
	public Set<MethodConstraintViolation<?>> getConstraintViolations() {
		return constraintViolations;
	}

}
