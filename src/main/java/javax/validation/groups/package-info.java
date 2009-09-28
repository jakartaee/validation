/**
 * A group defines a subset of constraints. Instead of
 * validating all constraints for a given object graph,
 * only a subset is validated depending on the group targeted.
 *
 * Each constraint declaration defines the list of groups it belongs to.
 * If no group is explicitly declared, a constraint belongs to the Default group.
 *
 * When applying validation, the list of target group is passed along.
 * If no group is explicitly passed along, the Default group is used.
 */
package javax.validation.groups;