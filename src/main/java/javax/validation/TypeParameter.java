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
public class TypeParameter implements Serializable {

	private final String name;

	private final int index;

	private GenericDeclaration genericDeclaration;

	public static TypeParameter of(TypeVariable<?> typeVariable) {
		java.lang.reflect.GenericDeclaration originalGenericDeclaration = (java.lang.reflect.GenericDeclaration) typeVariable.getGenericDeclaration();
		if ( !( originalGenericDeclaration instanceof Class ) ) {
			throw new ValidationException( "Only class level type variables are supported." );
		}

		GenericDeclaration genericDeclaration = new ClassGenericDeclaration( (Class<?>) originalGenericDeclaration );
		TypeParameter typeParameter = null;

		for ( TypeParameter currentTypeParameter : genericDeclaration.getTypeParameters() ) {
			currentTypeParameter.genericDeclaration = genericDeclaration;
			if ( typeVariable.getName().equals( currentTypeParameter.getName() ) ) {
				typeParameter = currentTypeParameter;
			}
		}

		return typeParameter;
	}

	private TypeParameter(String name, int index) {
		this.name = name;
		this.index = index;
	}

	/**
	 * Returns the name of the type parameter.
	 *
	 * @return the name of the type parameter
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the index of the type parameter in the array of type parameters of the generic declaration.
	 *
	 * @return the index of the type parameter
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Returns the generic declaration defining this type parameter.
	 *
	 * @return the generic declaration
	 */
	public GenericDeclaration getGenericDeclaration() {
		return genericDeclaration;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( name );
		sb.append( " from " );
		sb.append( genericDeclaration );
		return sb.toString();
	}

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

	/**
	 * A class based generic declaration.
	 *
	 * @author Guillaume Smet
	 * @since 2.0
	 */
	public static class ClassGenericDeclaration implements GenericDeclaration {

		private final String className;

		private final TypeParameter[] typeParameters;

		private ClassGenericDeclaration(Class<?> clazz) {
			this.className = clazz.getName();
			this.typeParameters = new TypeParameter[clazz.getTypeParameters().length];
			for ( int i = 0; i < clazz.getTypeParameters().length; i++ ) {
				this.typeParameters[i] = new TypeParameter( clazz.getTypeParameters()[i].getName(), i );
			}
		}

		/**
		 * Returns the fully qualified class name of the class.
		 *
		 * @return the fully qualified class name
		 */
		public String getClassName() {
			return className;
		}

		@Override
		public TypeParameter[] getTypeParameters() {
			return typeParameters;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T extends GenericDeclaration> T as(Class<T> implementation) {
			if ( !implementation.isAssignableFrom( getClass() ) ) {
				throw new ValidationException( "Cannot cast " + getClass().getName() + " to " + implementation.getName() + "." );
			}
			return (T) this;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append( className );
			sb.append( "<" );
			for ( int i = 0; i < typeParameters.length; i++ ) {
				if ( i > 0 ) {
					sb.append( ", " );
				}
				sb.append( typeParameters[i].getName() );
			}
			sb.append( ">" );
			return sb.toString();
		}
	}
}
