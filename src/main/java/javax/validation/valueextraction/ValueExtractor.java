/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.valueextraction;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;

import javax.validation.Path;

/**
 * Defines the logic used to extract the values from a container object of type {@code T}.
 * <p>
 * A value extractor for a generic type such as {@link Optional}, {@link List} or {@link Map}
 * is tied to one specific type parameter of {@code T}. The {@link ExtractedValue} annotation
 * is used to mark that type parameter. A value extractor for a non-generic type such as
 * {@link OptionalInt} needs to declare the type of the wrapped element(s) using
 * {@link ExtractedValue#type()}.
 * <p>
 * The extracted values are passed to the corresponding method of the {@link ValueReceiver}.
 * <p>
 * A typical value extractor implementation for {@code List} may look like this:
 *
 * <pre>
 * public class ListValueExtractor implements
 *         ValueExtractor&lt;List&lt;&#064;ExtractedValue ?&gt;&gt; {
 *
 *     &#064;Override
 *     public void extractValues(List&lt;?&gt; originalValue, ValueReceiver receiver) {
 *         for ( int i = 0; i &lt; originalValue.size(); i++ ) {
 *             receiver.indexedValue( "&lt;list element&gt;", i, originalValue.get( i ) );
 *         }
 *     }
 * }
 * </pre>
 *
 * @param <T> the container type handled by a specific implementation
 *
 * @author Gunnar Morling
 * @author Guillaume Smet
 * @see ExtractedValue
 * @see UnwrapByDefault
 * @since 2.0
 */
public interface ValueExtractor<T> {

	/**
	 * Extracts the values to validate from the original object.
	 *
	 * @param originalValue the original value from which to extract the values, never
	 * {@code null}
	 * @param receiver the corresponding {@code ValueReceiver}
	 */
	void extractValues(T originalValue, ValueReceiver receiver);

	/**
	 * Provides a set of methods receiving value extracted by the {@link ValueExtractor}.
	 * <p>
	 * The value has to be passed to the method corresponding best to the type of the
	 * original value.
	 *
	 * @since 2.0
	 */
	interface ValueReceiver {

		/**
		 * Receives the value extracted from an object.
		 *
		 * @param nodeName the name of the node representing the container element. If not
		 * {@code null}, the name will be used when adding a container element node to the
		 * {@link Path}
		 * @param object the value to validate
		 */
		void value(String nodeName, Object object);

		/**
		 * Receives the value extracted from an iterable object that is not indexed (e.g.
		 * a {@link Iterable}, {@link Set} or a {@link Map}).
		 *
		 * @param nodeName the name of the node representing the container element. If not
		 * {@code null}, the name will be used when adding a container element node to the
		 * {@link Path}
		 * @param object the value to validate
		 */
		void iterableValue(String nodeName, Object object);

		/**
		 * Receives the value extracted from an indexed object (e.g. a {@link List}).
		 *
		 * @param nodeName the name of the node representing the container element. If not
		 * {@code null}, the name will be used when adding a container element node to the
		 * {@link Path}
		 * @param i the index of the value in the original object
		 * @param object the value to validate
		 */
		void indexedValue(String nodeName, int i, Object object);

		/**
		 * Receives the value extracted from a keyed object (e.g. a {@link Map}).
		 *
		 * @param nodeName the name of the node representing the container element. If not
		 * {@code null}, the name will be used when adding a container element node to the
		 * {@link Path}
		 * @param key the key of the value in the original object
		 * @param object the value to validate
		 */
		void keyedValue(String nodeName, Object key, Object object);
	}
}
