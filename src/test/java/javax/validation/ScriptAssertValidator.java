package javax.validation;

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
