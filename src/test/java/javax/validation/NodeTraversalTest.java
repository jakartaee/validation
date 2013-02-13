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
