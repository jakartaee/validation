/*
* JBoss, Home of Professional Open Source
* Copyright 2012-2013, Red Hat, Inc. and/or its affiliates, and individual contributors
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

import java.util.Iterator;

import javax.validation.Path.*;

/**
 * This test does not have assertion but makes sure the APIs compile
 * properly and can be used as expected
 *
 * @author Emmanuel Bernard <emmanuel@hibernate.org>
 */
public class NodeTraversalTest {
	public void testTraversePath() throws Exception {
		//Iterating over Path
		Path path = null;
		Iterator<Node> nodeIterator = path.iterator();
		String methodName = null;
		Class<?>[] params = null;
		boolean isCtr = false;
		int arg;
		while(nodeIterator.hasNext()) {
			Node node = nodeIterator.next();
			switch ( node.getKind() ) {
			case METHOD:
				MethodNode methodNode = node.as(MethodNode.class);
				methodName = methodNode.getName();
				isCtr = false;
				params = methodNode.getParameterTypes().toArray( new Class<?>[methodNode.getParameterTypes().size()] );
				break;
			case CONSTRUCTOR:
				ConstructorNode constructorNode = node.as(ConstructorNode.class);
				methodName = constructorNode.getName();
				isCtr = true;
				params = constructorNode.getParameterTypes().toArray( new Class<?>[constructorNode.getParameterTypes().size()] );
				break;
			case PARAMETER:
				arg = node.as(ParameterNode.class).getParameterIndex();
				break;
			case RETURN_VALUE:
				arg = -1; //convention in my special code
				break;
			case BEAN:
				break;
			case PROPERTY:
				// else deal with normal nodes for navigation
				//say stored in a String[] and the leaf info for constraint in a dedicated structure
			}
		}
		//use methodName, params, isCtr and arg to put an icon on the sidebar
	}
}
