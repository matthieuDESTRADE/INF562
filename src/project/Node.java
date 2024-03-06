package project;

import java.util.LinkedList;

public class Node {
	public LinkedList<Leaf> leafs;
	public int id;
	
	public Node(int _id) {
		this.id = _id;
		leafs = new LinkedList<Leaf>();
	}
	public Node(int _id, LinkedList<Leaf> _leafs) {
		this.id = _id;
		leafs = _leafs;
	}
	
	public void add(Leaf l) {
		leafs.add(l);
	}
}
