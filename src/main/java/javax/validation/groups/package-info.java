/*
* JBoss, Home of Professional Open Source
* Copyright 2009-2013, Red Hat, Inc. and/or its affiliates, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
/**
 * A group defines a subset of constraints. Instead of
 * validating all constraints for a given object graph,
 * only a subset is validated depending on the group targeted.
 * <p/>
 * Each constraint declaration defines the list of groups it belongs to.
 * If no group is explicitly declared, a constraint belongs to the
 * {@link javax.validation.groups.Default} group.
 * <p/>
 * When applying validation, the list of target groups is passed along.
 * If no group is explicitly passed along, the
 * {@code javax.validation.groups.Default} group is used.
 * <p/>
 * During cascading, groups can be converted from one to another by
 * the use of {@link javax.validation.groups.ConvertGroup}
 */
package javax.validation.groups;
