package project;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	public HashMap<Float, ArrayList<Leaf>> ln;
	public int id;
	
	public Node(int _id) {
		this.ln = new HashMap<Float, ArrayList<Leaf>>();
		this.id = _id;
	}
	public void add(Leaf l,float m) {
		if (!ln.containsKey(m)) {
			this.ln.put(m, new ArrayList<Leaf>());
		}
		ln.get(m).add(l);		
	}
}
