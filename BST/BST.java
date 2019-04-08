package project6;

import java.util.*;

/**
 * Binary Search Tree 
 * BST class extends the Comparable Interface
 * and it also implements the Collection interface. 
 * By default the Collection interface implements from iterable class
 * It contains methods from the Collection interface 
 * Methods that are not implemented throws UnsupportedOpperationException
 * 
 * This class also contains private inner Node class and 
 * Iter class that implements Iterator interface
 * 
 * @author Nathalia Lin
 */
public class BST<E extends Comparable<E>> implements Collection<E> {

	private BSTNode<E> root;
	private int size;

	/**
	 * Constructs a new BST object
	*/
	public BST(){
		root = null;
		size = 0;
	}

	/**
	 * Check if the value passed in exists in the tree
	 * If it does, then return the value
	 * If not, return null
	 * @param an element to search for in this tree
	 * @return reference to the element stored in the tree with the parameter value
	 * @return null if the input is invalid or if the valid cannot be found
	*/
	public E get (E value){
		if (value == null){
			return null;
		} 
		if (root == null){
			return null;
		}
		// check if they have same class
		if (root.data.getClass() != value.getClass()){		
			return null;
		}
		// call helper method
		return getHelper(root, value);
	}

	/**
	 * Helper method for the get method
	 * Recursively searches through tree to get the value input
	 * @param initially the root, but with the recursive call it becomes
	 * a node whose data will be checked to see if it is the same as the value
	 * @param an element to search for in this tree
	 * @return the reference to the element equal to the parameter value
	 * @return null if the value doesn't exist
	*/
	private E getHelper(BSTNode<E> node, E value){
		if (node == null){
			return null;
		}
		// if value is greater than the node data, look left
		else if (value.compareTo(node.data) < 0){
			return getHelper(node.left, value);
		} 
		// if value is less than the node data, look right
		else if (value.compareTo(node.data) > 0){
			return getHelper(node.right, value);
		}
		// if it equal, get data
		return node.data;
	}

	/**
	 * Create an iterator instance of the tree
	 * Loop through the tree and concatenate it to a string
	 * @return a string representation of the tree
	*/
	public String toString(){
		String treeString = "";
		BSTIterator tree = new BSTIterator();

		// iterate through the tree
		boolean empty = true;
		while (tree.hasNext()) {
			empty = false;
			treeString += tree.next() + ",";
		}

		// If it is not empty
		if (!empty) {
			treeString = "[" + treeString.substring(0, treeString.length() - 1) + "]";
		}
		return treeString;
	}

	/**
	 * Method directly taken from project6 scope
	 * Produces tree like string representation of this BST
	 * @return string containing tree-like representation of this BST
	*/
	public String toStringTreeFormat(){
		StringBuilder s = new StringBuilder();
		preOrderPrint(root, 0, s);
		return s.toString();
	}

	/**
	 * Method directly taken from project6 scope
	 * Uses pre-order traversal to produce a tree-like representaiton of this BST
	 * @param tree the root of the current subtree
	 * @param level level (depth) of the current recursive call in the tree to determine
	 * the indentatino of each item
	 * @param output the string that accumulated the string representation of this BST
	*/
	private void preOrderPrint(BSTNode<E> tree, int level, StringBuilder output) {
		if (tree != null) {
		String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
				spaces += " ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append(tree.data);
			preOrderPrint(tree.left, level + 1, output);
			preOrderPrint(tree.right , level + 1, output);
		}
		else { 
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += " ";
					spaces += "|--";
			}
			output.append(spaces);
			output.append("null");
		}
	}

	/**
	 * Returns an iterator over the elements in this collection
	 * The elements should be returned in the order determined by the preorder
	 * traversal of the tree
	 * @return a new preOrder tree iterator
	*/
	public Iterator<E> preorderIterator(){
		return new BSTIterator("pre");
	}

	/**
	 * Returns an iterator over the elements in this collection.
	 * The elements should be returned in the order determined by the postorder
	 * traversal of the tree.
	 * @return a new postOrder tree iterator
	*/
	public Iterator<E> postorderIterator(){
		return new BSTIterator("post");
	}

// -------------------------------------------------------------------------------------------

	// TREESET

	/**
	 * Search through the tree for an element equal 
	 * or the smallest greater element than the given element
	 * @param an element to make comparisons for in this tree
	 * @return the least element in this set greater than or equal to the given element
	 * @return null if the input is invalid or there is no such element.
	*/
	public E ceiling (E e){
		if (e == null){
			return null;
		}
		if (!isEmpty()){
			if (root.data.getClass() != e.getClass()){		
				return null;
			}
		}

		BSTNode<E> node = ceilingHelper(root, e);

		if (node == null){
			return null;
		}
		return node.data;
	}

	/**
	 * Helper method for the ceiling method
	 * Recursively searches through tree 
	 * @param initially the root, but with the recursive call it becomes
	 * a node whose data will be checked to see if it is the same as the value
	 * @param an element to make comparisons for in this tree
	 * @return the least element in this set greater than or equal to the given element
	 * @return null if the input is invalid or there is no such element. 
	*/
	private BSTNode<E> ceilingHelper (BSTNode<E> node, E e){
		if (node == null){
			return null;
		}
		// if the element equal to parameter
		if (node.data.compareTo(e) == 0){
			return node;
		} 
		// if the element is greater than the node data
		// look at the right of the tree
		else if (node.data.compareTo(e) < 0){
			return ceilingHelper(node.right, e);
		} 
		// if the element is less than the node data
		// save it to the temp variable and
		// look at the left of the tree
		else {
			BSTNode<E> temp = ceilingHelper(node.left, e);
			if (temp != null){
				return temp;
			} else {
				return node;
			}
		}
	}

	/**
	 * Call the preOrder iterator
	 * and create a shallow copy of this tree instance
	 * @return a shallow copy of this tree instance.
	*/
	public Object clone(){
		BST<E> newRoot = new BST<E>();
		Iterator<E> copy = preorderIterator();
		while (copy.hasNext()){
			newRoot.add(copy.next());
		}
		return newRoot;
	}

	/**
	 * Looks all the way to the left for the smallest element
	 * @return the first(lowest) element in the tree
	*/
	public E first(){
		if (root == null)
			return null;
		return firstHelper(root).data;
	}

	/**
	 * Recursive helper for first method
	 * Looks all the way to the left for the smallest element
	 * @param a node value 
	 * @return calls itself or return the node with the smallest element
	*/
	private BSTNode<E> firstHelper(BSTNode<E> node){
		if (node.left == null)
			return node;
		return firstHelper(node.left);
	}

	/**
	 * Search through the tree for the greatest element in this set less than 
	 * or equal to the given element
	 * @param an element to make comparisons for in this tree
	 * @return the greatest element in this set less than 
	 * or equal to the given element
	 * @return null if the input is invalid or there is no such element.
	*/
	public E floor (E e){
		if (e == null){
			return null;
		}
		if (!isEmpty()){
			if (root.data.getClass() != e.getClass()){		
				return null;
			}
		}
		BSTNode<E> node = floorHelper(root, e);

		if (node == null){
			return null;
		}
		return node.data;
	}

	/**
	 * Helper method for the floor method
	 * Recursively searches through tree 
	 * @param initially the root, but with the recursive call it becomes
	 * a node whose data will be checked to see if it is the same as the value
	 * @param an element to make comparisons for in this tree
	 * @return the greatest element in this set less than or equal to the given element
	 * @return null if the input is invalid or there is no such element. 
	*/
	private BSTNode<E> floorHelper (BSTNode<E> node, E e){
		if (node == null){
			return null;
		}
		// if the element equal to parameter
		if (node.data.compareTo(e) == 0){
			return node;
		} 
		// if the element is smalleter than the node data
		// look at the left of the tree
		else if (node.data.compareTo(e) > 0){
			return floorHelper(node.left, e);
		} 
		// if the element is greater than the node data
		// save it to the temp variable and
		// look at the right of the tree
		else {
			BSTNode<E> temp = floorHelper(node.right, e);
			if (temp != null){
				return temp;
			} else {
				return node;
			}
		}
	}

	/**
	 * Similar to ceiling, but doesnt't return equal element
	 * Only least element strictly greater than the givne element
	 * @param an element to make comparisons for in this tree
	 * @return the least element in this set strictly greater than the given element
	 * @return null if the input is invalid or there is no such element.
	*/
	public E higher (E e){
		if (e == null){
			return null;
		}
		if (!isEmpty()){
			if (root.data.getClass() != e.getClass()){		
				return null;
			}
		}
		BSTNode<E> node = higherHelper(root, e);
		if (node == null){
			return null;
		}
		return node.data;
	}

	/**
	 * Helper method for the higher method
	 * Recursively searches through tree 
	 * @param initially the root, but with the recursive call it becomes
	 * a node whose data will be checked to see if it is the same as the value
	 * @param an element to make comparisons for in this tree
	 * @return the least element in this set greater than
	 * @return null if the input is invalid or there is no such element. 
	*/
	private BSTNode<E> higherHelper(BSTNode<E> node, E e){
		if (node == null){
			return null;
		}
		// if element is greater or equal to the node
		// look to the right (greater) of the tree
		if (node.data.compareTo(e) <= 0){
			return higherHelper(node.right, e);
		} 
		// if the element is less than the node data
		// save it to the temp variable and
		// look at the left of the tree
		else {
			BSTNode<E> temp = higherHelper(node.left, e);
			if (temp != null){
				return temp;
			} else {
				return node;
			}
		}
	}

	/**
	 * Looks all the way to the right for the largest element
	 * @return the last(greatest) element in the tree
	*/
	public E last(){
		if (root == null)
			return null;
		return lastHelper(root).data;
	}

	/**
	 * Recursive helper for last method
	 * Looks all the way to the right for the largest element
	 * @param a node value 
	 * @return calls itself or return the node with the greatest element
	*/
	private BSTNode<E> lastHelper(BSTNode<E> node){
		if (node.right == null)
			return node;
		return lastHelper(node.right);
	}

	/**
	 * Similar to floor, but don't return equal element
	 * Only greatest element strictly less than the given element
	 * @param an element to make comparisons for in this tree
	 * @return the greatest element in this set strictly less than the given element
	 * @return null if the input is invalid or there is no such element.
	*/
	public E lower (E e){
		if (e == null){
			return null;
		}
		if (!isEmpty()){
			if (root.data.getClass() != e.getClass()){		
				return null;
			}
		}
		BSTNode<E> node = lowerHelper(root, e);
		if (node == null){
			return null;
		}
		return node.data;
	} 

	/**
	 * Helper method for the lower method
	 * Recursively searches through tree 
	 * @param initially the root, but with the recursive call it becomes
	 * a node whose data will be checked to see if it is the same as the value
	 * @param an element to make comparisons for in this tree
	 * @return the greatest element in this set strictly less than the given element
	 * @return null if the input is invalid or there is no such element. 
	*/
	private BSTNode<E> lowerHelper(BSTNode<E> node, E e){
		if (node == null){
			return null;
		}
		// if element is less or equal to the node
		// look to the left (smaller) of the tree
		if (node.data.compareTo(e) >= 0){
			return lowerHelper(node.left, e);
		} 
		// if the element is greater than the node data
		// save it to the temp variable and
		// look at the right of the tree
		else {
			BSTNode<E> temp = lowerHelper(node.right, e);
			if (temp != null){
				return temp;
			} else {
				return node;
			}
		}
	}

// -------------------------------------------------------------------------------------------

	/**
	 * Method taken and modified from class notes
	 * Add new node to the tree
	 * @param the element you want to add
	 * @return true when you add the new element to tree
	 * @return false if data is invalid or a duplicate
	*/
	public boolean add(E data){
		// if it is a duplicate
		if (contains(data)){
			return false;
		}		
		// if data is not an instance of E
		if (data == null){
			return false;
		}
		if (!isEmpty()){
			if (root.data.getClass() != data.getClass()){		
				return false;
			}
		}
		
		BSTNode<E> node = addHelper(root, data);
		// if empty tree, add new node
		if (root == null){
			root = node;
		}
		if (node == null){
			return false;
		}

		size++;
		return true;
	}

	/**
	 * Method taken and modified from class notes
	 * Add helper method
	 * @param initially the root, but with the recursive call it becomes
	 * a node whose data will be checked to see if it is the same as the value
	 * @param the element you want to add
	 * @return true when you add the new element to tree
	 * @return false if data is invalid or a duplicate
	*/
	private BSTNode<E> addHelper(BSTNode<E> node, E data){
		if (node == null){
			BSTNode<E> newNode = new BSTNode<E>(data);
			return newNode;
		} 
		// if a duplicate
		if (data.compareTo(node.data) == 0){
			return null;
		}
		// if data is less than node data, go left
		else if (data.compareTo(node.data) < 0){
			node.left = addHelper(node.left, data);
		} 
		// if data is greater than node data, go right
		else if (data.compareTo(node.data) > 0){
			node.right = addHelper(node.right, data);
		}
		return node;
	}

	/**
	 * no implementation
	 * @throws UnsupportedOperationException is addAll is called
	 */
	public boolean addAll(Collection<? extends E> c) throws UnsupportedOperationException{
		throw new UnsupportedOperationException ("Invalid addAll method is not implemented");
	}

	/**
	 * Removes all of the elements from this collection
	*/
	public void clear(){
		root = null;
		size = 0;
	}

	/**
	 * Method taken from class notes
	 * Checks if this collection contains the specified element
 	 * @param the element you want to search for
	 * @return true if this collection contains the specified element 
	*/
	public boolean contains (Object o){
		// if data is not an instance of E
		if (o == null){
			return false;
		}
		if (!isEmpty()){
			if (root.data.getClass() != o.getClass()){		
				return false;
			}
		}
		return containsHelper(root, o);
	}

	/**
	 * Method taken from class notes
	 * Checks if this collection contains the specified element
	 * @param initially the root, but with the recursive call it becomes
	 * a node whose data will be checked to see if it is the same as the value
	 * @param the element you want to search for
	 * @return true if this collection contains the specified element, else false
	*/
	private boolean containsHelper (BSTNode<E> node, Object o){
		if (node == null){
			return false;
		}
		if (node.data.compareTo((E) o) == 0){
			return true;
		}
		else if (node.data.compareTo((E) o) < 0){
			return containsHelper(node.right, o);
		}
		else if (node.data.compareTo((E) o) > 0){
			return containsHelper(node.left, o);  
		}
		return false;
	}

	/**
	 * Checks if this collection contains all of the elements in the specified collection
	 * @param a collection
	 * @return true if this collection contains all of the elements in the specified collection.
	*/
	public boolean containsAll (Collection<?> c){
		for (Object i : c){
			if (!this.contains(i)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Compares the specified object with this collection for equality
	 * @param an object that will be compared with this
	 * @return true if the specified object and this object is the same, else false
	*/
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		BST<E> other = (BST<E>) obj;
		// check root
		if (root == null){
			if (other.root != null){
				return false;
			}
		} 
		// check size
		if (size != other.size()){
			return false;
		} 
		// check content
		for (int i = 0; i < other.size; i++){
			if (!(this.containsAll(other))){
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if this collection contains no elements
	 * @return true if this collection contains no elements
	*/
	public boolean isEmpty(){
		return (root == null);
	}

	/**
	 * The elements should be returned in the order determined by the inorder traversal of the tree
	 * @return an iterator over the elements in this collection. 
	*/
	public Iterator<E> iterator(){
		Iterator<E> iter = new BSTIterator();
		return iter;
	}

	/**
	 * Method taken from class notes
	 * remove the data item from a tree if it is there
	 * otherwise tree remains unchanged
	 * @param the element you want to remove
	 * @return true if the element was successfully removed
	*/
	public boolean remove(Object data) throws IllegalStateException{
	 	// can't remove a null data
	 	if (data == null){
	 		return false;
	 	}
	 	// remove first instance of the data in the tree
	 	BSTNode<E> node = removeHelper(root, (E) data);
	 	// if data does not exist
	 	if (node == null){
	 		return false;
	 	}
	 	size--;
	 	return true;
	}

	/**
	 * Method taken from class notes
	 * Find the rightmost node in a left subtree of a given node n 
	 * @param a node 
	 * @return the rightmost node in a left subtree
	*/
	private E getPredecessor(BSTNode<E> node){
		if (node.left == null){
			return null;
		} else {
			BSTNode<E> current = node.left; 
			while (current.right != null){
				current = current.right;
			}
			return current.data;
		}
	}

	/**
	 * Method taken from class notes
	 * Locates the node and then removes it and returns the reference to the new
	 * @param initially the root, but with the recursive call it becomes
	 * a node whose data will be checked to see if it is the same as the value
	 * @param the data element that is going to be removed
	 * @return 
	*/
	private BSTNode<E> removeHelper(BSTNode<E> node, E data){
		if (node == null){
			return null;
		} 
		else if (data.compareTo(node.data) < 0) {
			node.left = removeHelper(node.left, data);
		}
		else if (data.compareTo(node.data) > 0) {
			node.right = removeHelper(node.right, data);
		} 
		else {
			node = actualRemove(node);
		}
		return node;
	}

	/**
	 * Method taken from class notes
	 * Method that actually does the removal 
	 * @param a node
	 * @return the node removed
	*/
	private BSTNode<E> actualRemove(BSTNode<E> node){
		if (node.left == null){
			return node.right;
		}
	 	if (node.right == null) {
	 		return node.left;
	 	}
	 	E data = getPredecessor(node);
	 	node.data = data;
	 	node.left = removeHelper(node.left, data);
	 	return node;
	}

	/**
	 * no implementation
	 * @throws UnsupportedOperationException if removeAll is called
	 */
	public boolean removeAll(Collection<?> c) throws UnsupportedOperationException{
		throw new UnsupportedOperationException ("Invalid removeAll method is not implemented");
	}

	/**
	 * no implementation
	 * @throws UnsupportedOperationException if retainAll is called
	 */
	public boolean retainAll (Collection<?> c) throws UnsupportedOperationException{
		throw new UnsupportedOperationException ("Invalid retainAll method is not implemented");
	}

	/**
	 * Returns the number of elements in this collection
	 * @return the number of elements in this collection
	 */
	public int size(){
		return size;
	}

	/**
	 * Creates an array containing all of the elements in this collection
	 * @return an array containing all of the elements in this collection
	 */
	public Object[] toArray() {
		Object[] obj = new Object[size];
		Iterator<E> iter = this.iterator();

		int i = 0;
		while (iter.hasNext()){
			obj[i] = iter.next();
			i++;
		}

		return obj;
	}

	/**
	 * Got help from stackOverflow
	 * Returns an array containing all of the elements in this collection
	 * the runtime type of the returned array is that of the specified array
	 * @return an array containing all of the elements in this collection
	 */
	public <T> T[] toArray(T[] a) throws NullPointerException{
		if (a == null) {
			throw new NullPointerException ("Invalid element: cannot add a null");
		}

		Object[] obj = this.toArray() ;
		
		if (a.length < size) {
		   return ( (T[]) Arrays.copyOf(obj, size, a.getClass())) ;
		}
		
		System.arraycopy(obj, 0, a, 0, size);
		
		if (a.length > size) {
		   a[size] = null ;
		}
		
		return (T[])(obj);
	}

// -------------------------------------------------------------------------------------------

	/**
	 * This class represents a Node which consists of an element and its node
	 * Nodes class that will be used in BST class
	 *
	 * @author NathaliaLin
	 */
	private static class BSTNode<E extends Comparable<E>> {
		private E data;
		private BSTNode<E> left;
		private BSTNode<E> right;

		/**
		 * Constructs a new Node object with specified data 
		 * @param e element of type E
		 */
		public BSTNode (E data){
			this.data = data;
		}

		/**
		 * Constructs a new Node object with specified data
		 * @param e element of type E
		 * @param left node that will be pointing to
		 * @param right node that will be pointing to
		 */
		public BSTNode(E data, BSTNode<E> left, BSTNode<E> right) {
			this.data = data;
			this.left = left;
			this.right = right; 
		}
	}
	
// -------------------------------------------------------------------------------------------

	/**
	 * This class represents an Iter which consists of a node and its position
	 * implements from Iterator interface
	 *
	 * @author Nathalia Lin
	 */
	private class BSTIterator implements Iterator<E>{
	 	ArrayList<BSTNode> tree = new ArrayList<BSTNode>();
	 	int position = 0; 
		
		/**
		 * Construct a new BSTIterator object
		 * Default: inorder bst iterator
		 */
	 	public BSTIterator(){
	 		inOrder(root);
	 	}

	 	/**
		 * Construct a new BSTIterator object
		 * Traversal depends on pre or post parameter input
		 * @param a string that defines if the iterator will be preOrder or postOrder
		 */
	 	public BSTIterator(String order){
	 		if (order.equals("pre")) {
	 			preOrder(root);
	 		} 
	 		if (order.equals("post")) {
	 			postOrder(root);
	 		}
	 	}

	 	/**
	 	 * Check to see if its not the end of the list
	 	 * @return true if its not the end of the list
	 	 * @return false if it is the end of the list
	 	 */
	 	public boolean hasNext(){
	 		if (position < tree.size()){
	 			return true;
	 		}
	 		return false;
	 	}
 	
 		/**
	 	 * Get the next element of the list
	 	 * @return the element of the next node
	 	 * @return null if it is the end of the list
	 	 */
	 	public E next(){
	 		BSTNode<E> node = tree.get(position);
 			position++;
 			return node.data;
 		}

 		/**
	 	 * Create an inOrder traversal tree recursively
	 	 * Goes to the left, visits the node, and then goes to the right
	 	 * @param initially the root, but then a node to check the left and right
	 	 */
	 	protected void inOrder(BSTNode<E> root){
	 		if (root != null){
	 			inOrder(root.left);
	 			tree.add(root);
	 			inOrder(root.right);
	 		}
	 	}

	 	/**
	 	 * Create an preOrder traversal tree recursively
	 	 * Visits the node, goes to the left, and then goes to the right
	 	 * @param initially the root, but then a node to check the left and right
	 	 */
	 	protected void preOrder(BSTNode<E> root){
			if (root != null){
	 			tree.add(root);
	 			preOrder(root.left);
	 			preOrder(root.right);
	 		}
	 	}

	 	/**
	 	 * Create an postOrder traversal tree recursively
	 	 * Goes to the left, goes to the right, and then visits the node
	 	 * @param initially the root, but then a node to check the left and right
	 	 */
	 	protected void postOrder(BSTNode<E> root){
			if (root != null){
	 			postOrder(root.left);
	 			postOrder(root.right);
	 			tree.add(root);
	 		}
	 	}

	 	/**
		 * no implementation
		 * @throws UnsupportedOperationException
		 */
	 	public void remove() throws UnsupportedOperationException{
	 		throw new UnsupportedOperationException ("Invalid remove method is not implemented");
	 	}
	}
}