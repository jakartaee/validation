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

/**
 * Describes a validated constructor.
 *
 * @author Gunnar Morling
 * @author Emmanuel Bernard
 * @since 1.1
 */
public interface ConstructorDescriptor extends ElementDescriptor {

	/**
	 * <p>
	 * Returns a list with descriptors for this constructor parameters. The size
	 * of this list corresponds to the number of this constructor parameters.
	 * </p>
	 *
	 * @return A list with descriptors for this constructor's parameters. An
	 *         empty list will be returned if this constructor has no
	 *         parameters, but never {@code null}.
	 */
	List<ParameterDescriptor> getParameterDescriptors();

	/**
	 * Returns a descriptor for this constructor's return value.
	 *
	 * @return A descriptor for this constructor's return value.
	 */
	ReturnValueDescriptor getReturnValueDescriptor();

	/**
	 * Returns {@code true} if the constructor parameters are constrained either:
	 * <ul>
	 * <li>because of a constraint on at least one of the parameters</li>
	 * <li>because of a cascade on at least one of the parameters (via {@code @Valid})</li>
	 * <li>because of at least one cross-parameter constraint</li>
	 * </ul>
	 * Also returns {@code false} if there is no parameter.
	 *
	 * @return true if the constructor parameters are constrained
	 */
	boolean areParametersConstrained();

	/**
	 * Returns {@code true} if the constructor return value is constrained either:
	 * <ul>
	 * <li>because of a constraint on the return value</li>
	 * <li>because validation is cascaded on the return value (via {@code @Valid})</li>
	 * </ul>
	 * Also returns {@code false} if there is no return value.
	 *
	 * @return true if the constructor return value is constrained
	 */
	boolean isReturnValueConstrained();
}
