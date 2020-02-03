/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.metadata.ConstraintDescriptor;

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

		constraintViolationException = new ConstraintViolationException( null );

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
		public Object[] getExecutableParameters() {
			return new Object[0];
		}

		@Override
		public Object getExecutableReturnValue() {
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

		@Override
		public <U> U unwrap(Class<U> type) {
			return null;
		}
	}
}
