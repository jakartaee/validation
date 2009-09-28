package javax.validation;

/**
 * Represent a navigation path from an object to another.
 * Each path element is represented by a Node.
 *
 * The path corresponds to the succession of nodes
 * in the order they are returned by the Iterator
 *
 * @author Emmanuel Bernard
 */
public interface Path extends Iterable<Path.Node> {

	/**
	 * Represents an element of a navigation path
	 */
	interface Node {
		/**
		 * @return Property name the node represents
		 * or null if the leaf node and representing an entity
		 * (in particular the node representing the root object
		 * has its name null)
		 */
		String getName();

		/**
		 * @return True if the node represents an object contained in an Iterable
		 * or in a Map.
		 */
		boolean isInIterable();

		/**
		 * @return The index the node is placed in if contained
		 * in an array or List. Null otherwise.
		 */
		Integer getIndex();

		/**
		 * @return The key the node is placed in if contained
		 * in a Map. Null otherwise.
		 */
		Object getKey();
	}
}
