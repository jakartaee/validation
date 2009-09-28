package javax.validation.metadata;

/**
 * Describes a Java Bean property hosting validation constraints.
 *
 * Constraints placed on the attribute and the getter of a given property
 * are all referenced by this object.
 *
 * @author Emmanuel Bernard
 */
public interface PropertyDescriptor extends ElementDescriptor {
	/**
	 * Is the property marked by the <code>@Valid</code> annotation.
	 * @return <code>true</code> if the annotation is present, <code>false</code> otherwise.
	 */
	boolean isCascaded();

	/**
	 * Name of the property acording to the Java Bean specification.
	 * @return property name.
	 */
	String getPropertyName();
}