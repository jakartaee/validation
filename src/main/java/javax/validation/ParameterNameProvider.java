/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual contributors
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
package javax.validation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Provides names for method and constructor parameters.
 * <p/>
 * Used by the Bean Validation runtime when creating constraint violation
 * objects for violated method constraints.
 * <p/>
 * Implementations must be thread-safe.
 *
 * @author Gunnar Morling
 * @since 1.1
 */
public interface ParameterNameProvider {

	/**
	 * Returns the names of the parameters of the given constructor.
	 *
	 * @param constructor the constructor for which the parameter names shall be
	 *        retrieved; never {@code null}
	 * @return a list containing the names of the parameters of the given
	 *         constructor; may be empty but never {@code null}
	 */
	List<String> getParameterNames(Constructor<?> constructor);

	/**
	 * Returns the names of the parameters of the given method.
	 *
	 * @param method the method for which the parameter names shall be retrieved;
	 *        never {@code null}
	 * @return a list containing the names of the parameters of the given method;
	 *         may be empty but never {@code null}
	 */
	List<String> getParameterNames(Method method);
}
