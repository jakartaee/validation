/*
 * Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.examples;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

/**
 * @author Emmanuel Bernard <emmanuel@hibernate.org>
 */
@SupportedValidationTarget( { ValidationTarget.ANNOTATED_ELEMENT, ValidationTarget.PARAMETERS } )
public class ScriptAssertValidator implements ConstraintValidator<NotNull,Object[]> {
	@Override
	public void initialize(NotNull constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object[] value, ConstraintValidatorContext context) {
		return false;
	}
}
