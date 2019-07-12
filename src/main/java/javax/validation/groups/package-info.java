/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
/**
 * A group defines a subset of constraints. Instead of
 * validating all constraints for a given object graph,
 * only a subset is validated depending on the group targeted.
 * <p>
 * Each constraint declaration defines the list of groups it belongs to.
 * If no group is explicitly declared, a constraint belongs to the
 * {@link javax.validation.groups.Default} group.
 * <p>
 * When applying validation, the list of target groups is passed along.
 * If no group is explicitly passed along, the
 * {@code javax.validation.groups.Default} group is used.
 * <p>
 * During cascading, groups can be converted from one to another by
 * the use of {@link javax.validation.groups.ConvertGroup}
 */
package javax.validation.groups;
