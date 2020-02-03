/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
/**
 * Top level package of the Jakarta Bean Validation API.
 *
 * The main runtime artifacts are:
 * <ul>
 *     <li>{@link jakarta.validation.Validation}: the entry point to bootstrap Jakarta Bean Validation</li>
 *     <li>{@link jakarta.validation.ValidatorFactory}: the bootstrapped Jakarta Bean Validation engine</li>
 *     <li>{@link jakarta.validation.Validator}: contract to validate beans and access the metadata</li>
 *     <li>{@link jakarta.validation.ConstraintViolation}: constraint violation report</li>
 * </ul>
 *
 * The main constraint definition artifacts are:
 * <ul>
 *     <li>{@link jakarta.validation.Constraint}: annotation marking an annotation as being a constraint</li>
 *     <li>{@link jakarta.validation.ConstraintValidator}: interface implemented by the piece of code validating
 *     a given constraint</li>
 *     <li>{@link jakarta.validation.ReportAsSingleViolation}: marks a composed constraint as returning a single
 *     constraint violation report</li>
 * </ul>
 *
 * The main constraint declaration artifacts are:
 * <ul>
 *     <li>{@link jakarta.validation.Valid}: annotation marking an association as cascaded during validation</li>
 *     <li>{@link jakarta.validation.GroupSequence}: defines a sequence of groups that should be validated sequentially</li>
 * </ul>
 */
package jakarta.validation;
