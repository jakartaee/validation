/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.metadata;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Provides the common functionality of {@link MethodDescriptor} and
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
	 * @return the name of the executable represented by this descriptor
	 */
	String getName();

	/**
	 * Returns a list of descriptors representing this executable's
	 * parameters, in the order of their declaration, including synthetic
	 * parameters.
	 *
	 * @return a list of descriptors representing this executable's
	 *         parameters; an empty list will be returned if this executable has
	 *         no parameters, but never {@code null}
	 */
	List<ParameterDescriptor> getParameterDescriptors();

	/**
	 * Returns a descriptor containing the cross-parameter constraints
	 * of this executable.
	 *
	 * @return a descriptor containing the cross-parameter constraints of
	 *         this executable
	 */
	CrossParameterDescriptor getCrossParameterDescriptor();

	/**
	 * Returns a descriptor for this executable's return value.
	 * <p>
	 * An executable without return value will return a descriptor
	 * representing {@code void}. This descriptor will have no constraint
	 * associated.
	 *
	 * @return a descriptor for this executable's return value
	 */
	ReturnValueDescriptor getReturnValueDescriptor();

	/**
	 * Returns {@code true} if the executable parameters are constrained either:
	 * <ul>
	 *     <li>because of a constraint on at least one of the parameters</li>
	 *     <li>because of a cascade on at least one of the parameters (via
	 *     {@link Valid})</li>
	 *     <li>because of at least one cross-parameter constraint</li>
	 * </ul>
	 * <p>
	 * Also returns {@code false} if there is no parameter.
	 *
	 * @return {@code true} if the executable parameters are constrained
	 */
	boolean hasConstrainedParameters();

	/**
	 * Returns {@code true} if the executable return value is constrained
	 * either:
	 * <ul>
	 *     <li>because of a constraint on the return value</li>
	 *     <li>because validation is cascaded on the return value (via
	 *     {@link Valid})</li>
	 * </ul>
	 * <p>
	 * Also returns {@code false} if there is no return value.
	 *
	 * @return {@code true} if the executable return value is constrained
	 */
	boolean hasConstrainedReturnValue();

	/**
	 * Returns {@code false}.
	 * <p>
	 * An executable per se does not host constraints, use
	 * {@link #getParameterDescriptors()}, {@link #getCrossParameterDescriptor()}
	 * and {@link #getReturnValueDescriptor()} to discover constraints.
	 *
	 * @return {@code false}
	 */
	@Override
	boolean hasConstraints();

	/**
	 * Returns an empty {@code Set}.
	 * <p>
	 * An executable per se does not host constraints, use
	 * {@link #getParameterDescriptors()}, {@link #getCrossParameterDescriptor()}
	 * and {@link #getReturnValueDescriptor()} to discover constraints.
	 *
	 * @return an empty {@code Set}
	 */
	@Override
	Set<ConstraintDescriptor<?>> getConstraintDescriptors();

	/**
	 * Returns a finder that will always return an empty {@code Set}.
	 * <p>
	 * An executable per se does not host constraints, use
	 * {@link #getParameterDescriptors()}, {@link #getCrossParameterDescriptor()}
	 * and {@link #getReturnValueDescriptor()} to discover constraints.
	 *
	 * @return {@code ConstraintFinder} object
	 */
	@Override
	ConstraintFinder findConstraints();
}
