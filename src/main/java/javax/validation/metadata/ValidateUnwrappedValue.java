/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.metadata;

import javax.validation.valueextraction.UnwrapByDefault;
import javax.validation.valueextraction.ValueExtractor;

/**
 * The unwrapping behavior that can be applied to a specific constraint.
 *
 * @author Guillaume Smet
 * @since 2.0
 */
public enum ValidateUnwrappedValue {

	/**
	 * No specific unwrapping behavior has been defined for this constraint and the default
	 * behavior applies: if there is exactly one maximally-specific type-compliant
	 * {@link ValueExtractor} and this extractor is marked with {@link UnwrapByDefault}, this
	 * extractor is applied and the constraint is applied to the value(s) wrapped by the
	 * annotated container. Otherwise, no value extractor is applied.
	 */
	DEFAULT,

	/**
	 * The value is unwrapped before validation, i.e. the constraint is applied to the
	 * value(s) wrapped by the annotated container.
	 */
	UNWRAP,

	/**
	 * The value is not unwrapped before validation, i.e. the constraint is applied to the
	 * annotated element.
	 */
	SKIP;
}
