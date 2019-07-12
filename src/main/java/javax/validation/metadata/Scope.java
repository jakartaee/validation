/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.metadata;

/**
 * Scope looked at when discovering constraints.
 *
 * @author Emmanuel Bernard
 */
public enum Scope {

	/**
	 * Look for constraints declared on the current class element
	 * and ignore inheritance and elements with the same name in
	 * the class hierarchy.
	 */
	LOCAL_ELEMENT,

	/**
	 * Look for constraints declared on all elements of the class hierarchy
	 * with the same name.
	 */
	HIERARCHY
}
