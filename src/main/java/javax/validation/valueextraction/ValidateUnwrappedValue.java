package javax.validation.valueextraction;

/**
 * The unwrapping behavior that can be applied to a specific constraint.
 *
 * @author Guillaume Smet
 * @since 2.0
 */
public enum ValidateUnwrappedValue {

	/**
	 * Respects the default behavior of the {@link ValueExtractor}.
	 */
	DEFAULT,

	/**
	 * The value is unwrapped before validation.
	 */
	YES,

	/**
	 * The value is not unwrapped before validation.
	 */
	NO;

}
