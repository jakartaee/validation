/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.metadata;

import javax.validation.groups.ConvertGroup;

/**
 * A group conversion rule to be applied during cascaded validation. Two group
 * conversion descriptors are considered equal if they have the same
 * {@code from} and {@code to} group respectively.
 *
 * @author Gunnar Morling
 * @see ConvertGroup
 * @since 1.1
 */
public interface GroupConversionDescriptor {

	/**
	 * Returns the source group of this conversion rule.
	 *
	 * @return the source group of this conversion rule
	 */
	Class<?> getFrom();

	/**
	 * Returns the target group of this conversion rule.
	 *
	 * @return the target group of this conversion rule
	 */
	Class<?> getTo();
}
