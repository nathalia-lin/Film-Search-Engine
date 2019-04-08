package project3;

import java.util.*;
// do i have to import iterator ?

//by default collection implements iterable class 
public class LinkedList<E> implements Collection<E>{ 

	private int size = 0;
	private Nodes<E> head = null;
	private Nodes<E> tail = null;
	
	public LinkedList(){
		// default constructor
	}

	 public int indexOf (Object o){
	 	for (int i = 0; i < size; i++){
	 		if (this.get(i).equals(o)){
	 			return i;
	 		}
	 	}
	 	return -1;
	 }

	 public E get (int index) throws IndexOutOfBoundsException{
		if (index < 0 || index >= size){
	 		throw new IndexOutOfBoundsException("Error: index out of bounds");
	 	} 
	 	Nodes<E> curr = head;
	 	for (int i = 0; i < index; i++) {
	 		curr = curr.next;
	 	}
	 	return curr.element;
	 }

	 public String toString(){
		 String str = "[";
		 Nodes<E> n = head;
		 while(n != null) {
			 str += n.element + ", "; // element or hashcode?
			 n = n.next;
		 }
		 str += "]";
		 return str;
	 }

	 public void sort(){
	 	Object [] array = toArray();
	 	System.out.println(array);
	 	Arrays.sort(array);
	 	this.clear();
	 	for (Object o : array ) {
	 		this.add((E)o);
	 	}
	 }

	// COLLECTION INTERFACE METHODS
	public boolean add(E e) throws NullPointerException{
		if(e == null){
			throw new NullPointerException ("Invalid element: cannot add a null");
		}
		Nodes<E> node = new Nodes<E>(e, null);
		if (tail == null && head == null){
			head = node;
			tail = node;
		} else {
			tail.next = node;
			tail = node;
		}
		size++;
		return true;
	}

	public boolean addAll(Collection<? extends E> c) throws UnsupportedOperationException{
		throw new UnsupportedOperationException ("Invalid addAll method is not implemented");
	}

	public void clear(){
		head = null;
		tail = null;
		size = 0;
	}

	public boolean contains (Object o){
		Nodes<E> curr = head;
		for (int i = 0; i < size; i++) {
			if (curr.element == o) {
				return true;
			}
			curr = curr.next;
		}
		return false;
	}

	public boolean containsAll (Collection<?> c){
		if (c == null) {
			return false;
		} 
		for (int i = 0; i < c.size(); i++) {
			if (!(this.contains(c))) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + size;
		result = prime * result + ((tail == null) ? 0 : tail.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkedList other = (LinkedList) obj;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (size != other.size)
			return false;
		if (tail == null) {
			if (other.tail != null)
				return false;
		} else if (!tail.equals(other.tail))
			return false;
		for (int i = 0; i < other.size; i++) {
			if (!(this.containsAll(other))) {
				return false;
			}
		}
		return true;
	}

	public boolean isEmpty(){
		if (head == null && tail == null){
			return true;
		}
		return false;
	}

	 public Iterator<E> iterator(){
		 return new Iter(head);
	 }

	public boolean remove(Object o) throws IllegalStateException{
	 	if (o == null){
	 		throw new IllegalStateException ("Cannot remove a null item");
	 	}
	 	Nodes<E> curr = head;
	 	// one element
	 	if (curr.element.equals(o)){
	 		if (size == 1) {
		 		head = null;
		 		tail = null;
			 	size--;
			 	return true;
			 } else {
			 	head = head.next;
				size--;
			 	return true;
			 }
	 	} else {
		 	for(int i = 0; i < size - 2; i++) {
		 		if (curr.next.element.equals(o)) {
		 			if (curr.next != tail) {
			 			curr.next =  curr.next.next;		 			
		 			} else {
		 				curr.next = null;
		 				tail = curr;
		 			}
		 			size--;
		 			return true;
		 		}
		 		curr = curr.next;
		 	}
	 	}
	 	return false;
	 }

	public boolean removeAll(Collection<?> c) throws UnsupportedOperationException{
		throw new UnsupportedOperationException ("Invalid removeAll method is not implemented");
	}

	public boolean retainAll (Collection<?> c) throws UnsupportedOperationException{
		throw new UnsupportedOperationException ("Invalid retainAll method is not implemented");
	}

	public int size(){
		return size;
	}

	public Object[] toArray() {
		Object[] obj = new Object[size];
		Nodes<E> curr = head;
		E currE = curr.element;
		for (int i = 0; i < size - 1; i++) {
			obj[i] = currE;
			curr = curr.next;
			currE = curr.element;
		}
		return obj;
	}

	public <T> T[] toArray(T[] a) throws NullPointerException{
		if (a == null) {
			throw new NullPointerException ("Invalid element: cannot add a null");
		}
		Object[] obj = new Object[size];
		Nodes<E> curr = head;
		E currE = curr.element;
		for (int i = 0; i < size - 1; i++) {
			obj[i] = currE;
			curr = curr.next;
			currE = curr.element;
		}
		return (T[])(obj);
	}

	private static class Nodes<E> {
		public E element;
		public Nodes<E> next;

		public Nodes(E e, Nodes<E> n){
			this.element = e;
			this.next = n;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Nodes<E> other = (Nodes<E>) obj;
			if (element == null) {
				if (other.element != null)
					return false;
			} else if (!element.equals(other))
				return false;
			return true;
		}
	}

	 private class Iter implements Iterator<E>{
		private int position;
		private Nodes<E> node;
		
		// WORK ON THIS ONE
	 	public Iter(Nodes<E> node){
			this.position = 0;
			this.node = node;
	 	}

		// WORK ON THIS ONE
	 	public boolean hasNext(){
	 		if (position < size) {
	 			return true;
	 		}
	 		return false;
	 	}

	 	public E next(){
	 		if (this.hasNext()){
	 			E currNode = this.node.element;
	 			this.position++;
		 		node = this.node.next;
	 			return currNode;
	 		} 
	 		return null;
	 	}

	 	public void remove() throws UnsupportedOperationException{
	 		throw new UnsupportedOperationException ("Invalid remove method is not implemented");
	 	}
	 }
}