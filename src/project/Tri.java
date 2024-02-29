package project;

import java.util.ArrayList;
import java.util.HashMap;

public class Tri {
	public HashMap<Float, ArrayList<Leaf>> ln;
	
	public Tri() {
		this.ln = new HashMap<Float, ArrayList<Leaf>>();
	}
	public void add(Leaf l,float m) {
		if (!ln.containsKey(m)) {
			this.ln.put(m, new ArrayList<Leaf>());
		}
		ln.get(m).add(l);		
	}
}
