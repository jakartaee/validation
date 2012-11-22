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
package javax.validation.metadata;

import java.util.List;
import java.util.Set;

/**
 * Provides common functionality of {@link MethodDescriptor} and
 * {@link ConstructorDescriptor}.
 *
 * @author Gunnar Morling
 *
 * @since 1.1
 */
public interface ExecutableDescriptor extends ElementDescriptor {

	/**
	 * Returns the method name in case this descriptor represents a method or
	 * the non-qualified name of the declaring class in case this descriptor
	 * represents a constructor.
	 *
	 * @return The name of the executable represented by this descriptor.
	 */
	String getName();

	/**
	 * <p>
	 * Returns a list with descriptors for this executable's parameters. The
	 * size of this list corresponds to the number of this executable's
	 * parameters.
	 * </p>
	 *
	 * @return A list with descriptors for this executable's parameters. An
	 *         empty list will be returned if this executable has no parameters,
	 *         but never {@code null}.
	 */
	List<ParameterDescriptor> getParameterDescriptors();

	/**
	 * Returns a descriptor for this executable's return value.
	 *
	 * @return A descriptor for this executable's return value or {@code null}
	 *         if this executable has no return value.
	 */
	ReturnValueDescriptor getReturnValueDescriptor();

	/**
	 * Returns {@code true} if the executable parameters are constrained either:
	 * <ul>
	 * <li>because of a constraint on at least one of the parameters</li>
	 * <li>because of a cascade on at least one of the parameters (via
	 * {@code @Valid})</li>
	 * <li>because of at least one cross-parameter constraint</li>
	 * </ul>
	 * Also returns {@code false} if there is no parameter.
	 *
	 * @return {@code true} if the executable parameters are constrained
	 */
	boolean areParametersConstrained();

	/**
	 * Returns {@code true} if the executable return value is constrained
	 * either:
	 * <ul>
	 * <li>because of a constraint on the return value</li>
	 * <li>because validation is cascaded on the return value (via
	 * {@code @Valid})</li>
	 * </ul>
	 * Also returns {@code false} if there is no return value.
	 *
	 * @return {@code true} if the executable return value is constrained
	 */
	boolean isReturnValueConstrained();

	/**
	 * Whether this executable has any cross-parameter constraints.
	 *
	 * @return {@code true} if this executable has at least one cross-parameter
	 *         constraint, {@code false} otherwise.
	 */
	@Override
	boolean hasConstraints();

	/**
	 * Return all constraint descriptors for all cross-parameter constraints of
	 * this executable or an empty {@code Set} if none are present. In
	 * particular, constraints on individual parameters and return value
	 * constraints are not returned.
	 *
	 * @return {@code Set} of cross-parameter constraint descriptors for this
	 *         element
	 */
	@Override
	Set<ConstraintDescriptor<?>> getConstraintDescriptors();

	/**
	 * Find cross-parameter constraints and potentially restricts them to
	 * certain criteria. Neither constraints on individual parameters nor return
	 * value constraints are taken into account.
	 *
	 * @return Constraint finder object.
	 */
	@Override
	ConstraintFinder findConstraints();
}
