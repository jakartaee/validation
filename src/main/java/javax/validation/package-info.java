/*
* JBoss, Home of Professional Open Source
* Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual contributors
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
 * Top level package of the Bean Validation API.
 *
 * The main runtime artifacts are:
 * <ul>
 *     <li>{@link javax.validation.Validation}: the entry point to bootstrap Bean Validation</li>
 *     <li>{@link javax.validation.ValidatorFactory}: the bootstrapped Bean Validation engine</li>
 *     <li>{@link javax.validation.Validator}: contract to validate beans and access the metadata</li>
 *     <li>{@link javax.validation.ConstraintViolation}: constraint violation report</li>
 * </ul>
 *
 * The main constraint definition artifacts are:
 * <ul>
 *     <li>{@link javax.validation.Constraint}: annotation marking an annotation as being a constraint</li>
 *     <li>{@link javax.validation.ConstraintValidator}: interface implemented by the piece of code validating
 *     a given constraint</li>
 *     <li>{@link javax.validation.ReportAsSingleViolation}: marks a composed constraint as returning a single
 *     constraint violation report</li>
 * </ul>
 *
 * The main constraint declaration artifacts are:
 * <ul>
 *     <li>{@link javax.validation.Valid}: annotation marking an association as cascaded during validation</li>
 *     <li>{@link javax.validation.GroupSequence}: defines a sequence of groups that should be validated sequentially</li>
 * </ul>
 */
package javax.validation;
