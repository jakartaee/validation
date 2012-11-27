/*
* JBoss, Home of Professional Open Source
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

import java.util.HashSet;
import java.util.Set;
import javax.validation.metadata.ConstraintDescriptor;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Tests for the creation of {@link ConstraintViolationException}.
 * @author Gunnar Morling
 */
public class ConstraintViolationExceptionTest {

	@Test
	public void testCreationOfConstraintViolationException() {

		Set<ConstraintViolation<ConstraintViolationExceptionTest>> constraintViolations = new HashSet<ConstraintViolation<ConstraintViolationExceptionTest>>();
		constraintViolations.add( new DummyConstraintViolation<ConstraintViolationExceptionTest>() );

		ConstraintViolationException constraintViolationException = new ConstraintViolationException(
				constraintViolations
		);

		assertEquals( constraintViolationException.getConstraintViolations(), constraintViolations );
	}

	@Test
	public void testCreationOfConstraintViolationExceptionWithMessage() {

		Set<ConstraintViolation<ConstraintViolationExceptionTest>> constraintViolations = new HashSet<ConstraintViolation<ConstraintViolationExceptionTest>>();
		constraintViolations.add( new DummyConstraintViolation<ConstraintViolationExceptionTest>() );

		String message = "constraint violations occurred";
		ConstraintViolationException constraintViolationException = new ConstraintViolationException(
				message,
				constraintViolations
		);

		assertEquals( constraintViolationException.getConstraintViolations(), constraintViolations );
		assertEquals( constraintViolationException.getMessage(), message );
	}

	@Test
	public void testCreationOfConstraintViolationExceptionWithoutViolations() {

		ConstraintViolationException constraintViolationException = new ConstraintViolationException( null, null );

		assertNull( constraintViolationException.getConstraintViolations() );
		assertNull( constraintViolationException.getMessage() );
	}

	/**
	 * @author Gunnar Morling
	 */
	private static class DummyConstraintViolation<T> implements ConstraintViolation<T> {

		@Override
		public String getMessage() {
			return null;
		}

		@Override
		public String getMessageTemplate() {
			return null;
		}

		@Override
		public T getRootBean() {
			return null;
		}

		@Override
		public Class<T> getRootBeanClass() {
			return null;
		}

		@Override
		public Object getLeafBean() {
			return null;
		}

		@Override
		public Path getPropertyPath() {
			return null;
		}

		@Override
		public Object getInvalidValue() {
			return null;
		}

		@Override
		public ConstraintDescriptor<?> getConstraintDescriptor() {
			return null;
		}
	}
}
