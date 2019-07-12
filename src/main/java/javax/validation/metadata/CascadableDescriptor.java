/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.metadata;

import java.util.Set;

/**
 * Represents a cascadable element.
 *
 * @author Gunnar Morling
 * @since 1.1
 */
public interface CascadableDescriptor {

	/**
	 * Whether this element is marked for cascaded validation or not.
	 *
	 * @return {@code true}, if this element is marked for cascaded validation,
	 *         {@code false} otherwise
	 */
	boolean isCascaded();

	/**
	 * Returns the group conversions configured for this element.
	 *
	 * @return a set containing this element's group conversions; an empty set
	 *         may be returned if no conversions are configured but never
	 *         {@code null}
	 */
	Set<GroupConversionDescriptor> getGroupConversions();
}
