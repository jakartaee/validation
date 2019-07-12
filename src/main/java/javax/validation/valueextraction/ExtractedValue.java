/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.valueextraction;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.OptionalInt;

/**
 * Marks the type parameter of a generic container type to which a {@link ValueExtractor} is
 * tied or specifies the type of the wrapped element(s) of non-generic container types.
 * <p>
 * Must be given exactly once for a value extractor type.
 *
 * @author Gunnar Morling
 * @author Guillaume Smet
 *
 * @see ValueExtractor
 * @since 2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
@Documented
public @interface ExtractedValue {

	/**
	 * The type of the value extracted by the {@link ValueExtractor}. If not set, the type
	 * will be automatically inferred from the type argument of the parameterized type.
	 * <p>
	 * Used to define value extractors for non-generic wrapper types e.g.
	 * {@link OptionalInt}.
	 * <p>
	 * May not be used when {@code ExtractedValue} is defined on the type parameter of
	 * a generic container type. A {@code ValueExtractorDefinitionException} will be thrown
	 * in this case.
	 *
	 * @return the type of the value extracted by the value extractor
	 */
	Class<?> type() default void.class;
}
