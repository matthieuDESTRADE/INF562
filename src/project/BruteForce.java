package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

public class BruteForce {
	Node[] nodes;
	int nleaf;
	
	
	public BruteForce(String filename) throws FileNotFoundException {
		// Creates the data structure necessary to apply the brute force algorithm.
		// The graph is represented as a list of nodes.
		
		Scanner sc = new Scanner(new File(filename));
		sc.useLocale(Locale.US);
		String[] header = sc.nextLine().split(" ");
		int nfixe = Integer.parseInt(header[3]);
		nleaf = Integer.parseInt(header[2]);
		nodes = new Node[nfixe];
		for (int i=0;i<nfixe;i++) {
			nodes[i] = new Node(i);
		}
		int E = Integer.parseInt(header[4]);
		for (int i=0;i<E;i++) {
			String[] values = sc.nextLine().split(" ");
			int inode = Integer.parseInt(values[1])-1-nleaf;
			int ileaf = Integer.parseInt(values[0])-1;
			nodes[inode].add(new Leaf(ileaf));
		}
		sc.close();
	}
	
	public Tri<String,Integer,Long> Minimize() {
		// Computes the order minimizing the number of crossings in the graph given as input.
		// Returns the number of crossings and the order as a string.
		
		long start = System.currentTimeMillis();

		int res = -1;
		int[] sol = new int[nodes.length];
		Permutation p = new Permutation(nodes.length);

		for (int k=0;k<p.tot;k++) {
			int crossings = 0;
			for (int i = 0;i<p.get(k).length;i++) {
				int idnode = p.get(k)[i];
				LinkedList<Leaf> lfs = nodes[idnode].leafs;
				for (int j = i+1;j<p.get(k).length;j++) {
					int idnode2 = p.get(k)[j];
					LinkedList<Leaf> lfs2 = nodes[idnode2].leafs;
					for (Leaf leaf:lfs) {
						int idleaf = leaf.id;
						for (Leaf leaf2:lfs2) {
							int idleaf2 = leaf2.id;
							if (idleaf2<idleaf) crossings++;
						}
					}
				 }
			}
			if (res==-1) {
				res = crossings;
				sol = p.get(k).clone();
			}
			if (res>crossings) {
				res = crossings;
				sol = p.get(k).clone();
			}
		}
		String s = ""+(sol[0]+nleaf+1);
		for (int i=1;i<nodes.length;i++) {
			s += "\n";
			s += sol[i] + nleaf+1 ;
		}
		
		long finish = System.currentTimeMillis();

		return new Tri<String,Integer,Long>(s,res,finish-start);
	}
}
