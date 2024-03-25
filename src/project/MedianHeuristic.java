package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

public class MedianHeuristic {
	Node[] nodes;
	int nleaf;
	
	public MedianHeuristic(String filename) throws FileNotFoundException {
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
	
	public Tri<String,Integer,Long> Minimize(Boolean noSort, Boolean Mean){
		// Sorts the nodes according to the median of their leaves
		// Returns the found order and the number of crossings
		
		long start = System.currentTimeMillis();
		ArrayList<Double> mediane = new ArrayList<Double>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		for (int i=0;i<nodes.length;i++) indices.add(i);
		
		if (!noSort) {
			for (int i=0;i<nodes.length;i++) { // Here we suppose that the inputs are given in ascending order in the input file
				int mult = nodes[i].leafs.size();
				if (mult==0) mediane.add(0.);
				else {
					if (!Mean) {		
						if (mult%2==1) mediane.add((double)nodes[i].leafs.get(mult/2).id);
						else mediane.add((nodes[i].leafs.get(mult/2).id + nodes[i].leafs.get(mult/2 - 1).id)/2.);
					}
					else {
						double mean = 0;
						for (Leaf l:nodes[i].leafs) {
							mean += l.id;
						}
						mediane.add(mean/(double)mult);
					}
				}
			}
			
			Collections.sort(indices,Comparator.comparingDouble(mediane::get));
		}
		
		String s = ""+(indices.get(0)+1+nleaf);

		for (int i=1;i<indices.size();i++) {
			s+= "\n";
			s += ""+(indices.get(i)+1+nleaf);
		}
		long finish = System.currentTimeMillis();
		
		
		int crossings=0; // Here we compute the number of crossings, we do not take the related time into account.
		/*
		for (int i=0;i<indices.size();i++) {
			int idnode = indices.get(i);
			for (int j = i+1;j<indices.size();j++) {
				int idnode2 = indices.get(j);
				for (Leaf leaf:nodes[idnode].leafs) {
					for (Leaf leaf2:nodes[idnode2].leafs) {
						if (leaf2.id<leaf.id) crossings++;
					}
				}
			 }
		}
		*/
		return new Tri<String,Integer,Long>(s,crossings,finish-start);
	}

}