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
package javax.validation.metadata;

/**
 * Represents the type of a method: getter or non getter.
 *
 * @author Emmanuel Bernard <emmanuel@hibernate.org>
 * @since 1.1
 */
public enum MethodType {

	/**
	 * A method following the getter pattern. A getter according to the
	 * JavaBeans specification is a method whose:
	 * <ul>
	 *     <li>name starts with get, has a return type but no parameter</li>
	 *     <li>name starts with is, has a return type and is returning {@code boolean}.</li>
	 * </ul>
	 */
	GETTER,

	/**
	 * A method that does not follow the getter pattern. A getter according to the
	 * JavaBeans specification is a method whose:
	 * <ul>
	 *     <li>name starts with get, has a return type but no parameter</li>
	 *     <li>name starts with is, has a return type and is returning {@code boolean}.</li>
	 * </ul>
	 */
	NON_GETTER
}
