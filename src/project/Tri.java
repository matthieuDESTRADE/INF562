package project;

import java.util.ArrayList;
import java.util.HashMap;

public class Tri {
	public HashMap<Float, ArrayList<Leaf>> ln;
	public HashMap<Float, Boolean> lb;
	
	public Tri() {
		this.ln = new HashMap<Float, ArrayList<Leaf>>();
	}
	public void add(Leaf l,float m, Boolean rec) {
		if (!ln.containsKey(m)) {
			this.ln.put(m, new ArrayList<Leaf>());
		}
		if (rec) {
			this.lb.put(m, rec);
		}
		ln.get(m).add(l);		
	}
}
