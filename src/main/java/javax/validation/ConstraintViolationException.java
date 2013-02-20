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

import java.util.HashSet;
import java.util.Set;

/**
 * Reports the result of constraint violations.
 *
 * @author Emmanuel Bernard
 * @author Gunnar Morling
 */
public class ConstraintViolationException extends ValidationException {
	private final Set<ConstraintViolation<?>> constraintViolations;

	/**
	 * Creates a constraint violation report.
	 *
	 * @param message error message
	 * @param constraintViolations {@code Set} of {@link ConstraintViolation}
	 */
	public ConstraintViolationException(String message,
										Set<? extends ConstraintViolation<?>> constraintViolations) {
		super( message );

		if ( constraintViolations == null ) {
			this.constraintViolations = null;
		}
		else {
			this.constraintViolations = new HashSet<ConstraintViolation<?>>( constraintViolations );
		}
	}

	/**
	 * Creates a constraint violation report.
	 *
	 * @param constraintViolations {@code Set} of {@link ConstraintViolation}
	 */
	public ConstraintViolationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
		this( null, constraintViolations );
	}

	/**
	 * Set of constraint violations reported during a validation.
	 *
	 * @return {@code Set} of {@link ConstraintViolation}
	 */
	public Set<ConstraintViolation<?>> getConstraintViolations() {
		return constraintViolations;
	}
}
