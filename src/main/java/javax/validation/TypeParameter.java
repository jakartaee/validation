/*
 * Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

import java.io.Serializable;
import java.lang.reflect.TypeVariable;

/**
 * Serializable structure representing a type parameter in a generic declaration.
 * <p>
 * This is a serializable and very simplified replacement for {@link TypeVariable}.
 * <p>
 * For now, it only supports class level generic declaration.
 *
 * @author Guillaume Smet
 * @since 2.0
 */
public interface TypeParameter extends Serializable {

	/**
	 * Returns the name of the type parameter.
	 *
	 * @return the name of the type parameter
	 */
	public String getName();

	/**
	 * Returns the index of the type parameter in the array of type parameters of the generic declaration.
	 *
	 * @return the index of the type parameter
	 */
	public int getIndex();

	/**
	 * Returns the generic declaration defining this type parameter.
	 *
	 * @return the generic declaration
	 */
	public GenericDeclaration getGenericDeclaration();

	/**
	 * A common interface for the generic declaration deemed to be serializable.
	 *
	 * @author Guillaume Smet
	 * @since 2.0
	 */
	public interface GenericDeclaration extends Serializable {

		/**
		 * Returns the type parameters.
		 *
		 * @return the type parameters
		 */
		TypeParameter[] getTypeParameters();

		/**
		 * Casts the generic declaration to one of its implementation.
		 *
		 * @param implementation the desired implementation
		 * @param <T> the desired implementation
		 * @return the same generic declaration cast to the specified implementation
		 * @throws ValidationException if the generic declaration cannot be cast to the desired type
		 */
		<T extends GenericDeclaration> T as(Class<T> implementation);
	}
}
