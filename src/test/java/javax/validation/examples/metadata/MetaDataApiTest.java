/*
 * Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.examples.metadata;

import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.examples.metadata.Book.SecondLevelCheck;
import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.ConstructorDescriptor;
import javax.validation.metadata.ContainerElementTypeDescriptor;
import javax.validation.metadata.CrossParameterDescriptor;
import javax.validation.metadata.GroupConversionDescriptor;
import javax.validation.metadata.MethodDescriptor;
import javax.validation.metadata.MethodType;
import javax.validation.metadata.ParameterDescriptor;
import javax.validation.metadata.PropertyDescriptor;
import javax.validation.metadata.ReturnValueDescriptor;

/**
 * Example used in 6.12 (metadata example).
 *
 * @author Emmanuel Bernard
 * @author Gunnar Morling
 */
public class MetaDataApiTest {

	public void testMeta() {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

		//tag::include[]
		BeanDescriptor bookDescriptor = validator.getConstraintsForClass(Book.class);

		assert ! bookDescriptor.hasConstraints();

		assert bookDescriptor.isBeanConstrained();
		assert bookDescriptor.getConstrainedMethods( MethodType.NON_GETTER ).size() > 0;

		assert bookDescriptor.getConstraintDescriptors().size() == 0; //no bean-level constraint

		//more specifically "author" and "title"
		assert bookDescriptor.getConstrainedProperties().size() == 2;

		//not a property
		assert bookDescriptor.getConstraintsForProperty( "doesNotExist" ) == null;

		//property with no constraint
		assert bookDescriptor.getConstraintsForProperty( "description" ) == null;

		PropertyDescriptor propertyDescriptor = bookDescriptor.getConstraintsForProperty( "title" );
		assert propertyDescriptor.getConstraintDescriptors().size() == 2;
		assert "title".equals( propertyDescriptor.getPropertyName() );

		//assuming the implementation returns the @NotEmpty constraint first
		ConstraintDescriptor<?> constraintDescriptor = propertyDescriptor.getConstraintDescriptors()
				.iterator().next();
		assert constraintDescriptor.getAnnotation().annotationType().equals( NotEmpty.class );
		assert constraintDescriptor.getGroups().size() == 2; //FirstLevelCheck and Default
		assert constraintDescriptor.getComposingConstraints().size() == 2;
		assert constraintDescriptor.isReportAsSingleViolation() == true;

		//@NotEmpty cannot be null
		boolean notNullPresence = false;
		for ( ConstraintDescriptor<?> composingDescriptor : constraintDescriptor.getComposingConstraints() ) {
			if ( composingDescriptor.getAnnotation().annotationType().equals( NotNull.class ) ) {
				notNullPresence = true;
			}
		}
		assert notNullPresence;

		//assuming the implementation returns the Size constraint second
		constraintDescriptor = propertyDescriptor.getConstraintDescriptors().iterator().next();
		assert constraintDescriptor.getAnnotation().annotationType().equals( Size.class );
		assert constraintDescriptor.getAttributes().get( "max" ) == Integer.valueOf( 30 );
		assert constraintDescriptor.getGroups().size() == 1;

		propertyDescriptor = bookDescriptor.getConstraintsForProperty( "author" );
		assert propertyDescriptor.getConstraintDescriptors().size() == 1;
		assert propertyDescriptor.isCascaded();

		propertyDescriptor = bookDescriptor.getConstraintsForProperty( "keywordsPerChapter" );

		// @Valid on the map key
		ContainerElementTypeDescriptor mapKeyElementDescriptor = propertyDescriptor.getConstraintsForContainerElementType( 0 );
		assert mapKeyElementDescriptor.isCascaded() == true;

		// @Size on the map value
		ContainerElementTypeDescriptor mapValueElementDescriptor = propertyDescriptor.getConstraintsForContainerElementType( 1 );
		assert mapValueElementDescriptor.getConstraintDescriptors().size() == 1;
		assert mapValueElementDescriptor.getConstraintDescriptors().iterator().next().getAnnotation().annotationType() == Size.class;

		// @NotBlank on the nested list elements
		ContainerElementTypeDescriptor listElementDescriptor = mapValueElementDescriptor.getConstraintsForContainerElementType( 0 );
		assert listElementDescriptor.getConstraintDescriptors().size() == 1;
		assert listElementDescriptor.getConstraintDescriptors().iterator().next().getAnnotation().annotationType() == NotBlank.class;

		// no further nested container element constraints
		assert listElementDescriptor.getConstrainedContainerElementTypes().isEmpty();

		//getTitle() and addChapter()
		assert bookDescriptor.getConstrainedMethods(MethodType.GETTER, MethodType.NON_GETTER).size() == 2;

		//the constructor accepting title, description and author
		assert bookDescriptor.getConstrainedConstructors().size() == 1;

		ConstructorDescriptor constructorDescriptor = bookDescriptor.getConstraintsForConstructor(
				String.class, String.class, Author.class
				);
		assert constructorDescriptor.getName().equals( "Book" );
		assert constructorDescriptor.getElementClass() == Book.class;
		assert constructorDescriptor.hasConstrainedParameters() == true;

		//return value is marked for cascaded validation
		assert constructorDescriptor.hasConstrainedReturnValue() == true;

		//constraints are retrieved via the sub-descriptors for parameters etc.
		assert constructorDescriptor.hasConstraints() == false;

		//one descriptor for each parameter
		assert constructorDescriptor.getParameterDescriptors().size() == 3;

		//"description" parameter
		ParameterDescriptor parameterDescriptor = constructorDescriptor.getParameterDescriptors().get( 1 );

		//Assuming the default parameter name provider
		assert parameterDescriptor.getName().equals( "arg1" );
		assert parameterDescriptor.getElementClass() == String.class;
		assert parameterDescriptor.getIndex() == 1;
		assert parameterDescriptor.hasConstraints() == true;

		Set<ConstraintDescriptor<?>> parameterConstraints = parameterDescriptor.getConstraintDescriptors();
		assert parameterConstraints.iterator().next().getAnnotation().annotationType() == Size.class;

		//"author" parameter
		parameterDescriptor = constructorDescriptor.getParameterDescriptors().get( 2 );
		assert parameterDescriptor.hasConstraints() == false;
		assert parameterDescriptor.isCascaded() == true;

		//group conversion on "author" parameter
		GroupConversionDescriptor groupConversion =
				parameterDescriptor.getGroupConversions().iterator().next();
		assert groupConversion.getFrom() == Default.class;
		assert groupConversion.getTo() == SecondLevelCheck.class;

		//constructor return value
		ReturnValueDescriptor returnValueDescriptor = constructorDescriptor.getReturnValueDescriptor();
		assert returnValueDescriptor.hasConstraints() == false;
		assert returnValueDescriptor.isCascaded() == true;

		//a getter is also a method which is constrained on its return value
		MethodDescriptor methodDescriptor = bookDescriptor.getConstraintsForMethod( "getTitle" );
		assert methodDescriptor.getName().equals( "getTitle" );
		assert methodDescriptor.getElementClass() == String.class;
		assert methodDescriptor.hasConstrainedParameters() == false;
		assert methodDescriptor.hasConstrainedReturnValue() == true;
		assert methodDescriptor.hasConstraints() == false;

		returnValueDescriptor = methodDescriptor.getReturnValueDescriptor();
		assert returnValueDescriptor.getElementClass() == String.class;
		assert returnValueDescriptor.getConstraintDescriptors().size() == 2;
		assert returnValueDescriptor.isCascaded() == false;

		//void method which has a cross-parameter constraint
		methodDescriptor = bookDescriptor.getConstraintsForMethod(
				"addChapter", String.class, int.class, int.class
				);
		assert methodDescriptor.getElementClass() == void.class;
		assert methodDescriptor.hasConstrainedParameters() == true;
		assert methodDescriptor.hasConstrainedReturnValue() == false;

		//cross-parameter constraints accessible via separate descriptor
		assert methodDescriptor.hasConstraints() == false;

		assert methodDescriptor.getReturnValueDescriptor().getElementClass() == void.class;

		//cross-parameter descriptor
		CrossParameterDescriptor crossParameterDescriptor = methodDescriptor.getCrossParameterDescriptor();
		assert crossParameterDescriptor.getElementClass() == Object[].class;
		assert crossParameterDescriptor.hasConstraints() == true;

		ConstraintDescriptor<?> crossParameterConstraint =
				crossParameterDescriptor.getConstraintDescriptors().iterator().next();
		assert crossParameterConstraint.getAnnotation().annotationType() == ValidInterval.class;
		//end::include[]
	}
}
