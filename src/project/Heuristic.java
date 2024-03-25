package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;




public class Heuristic {
	Node[] nodes;
	int nfixe;
	int nleaf;
	int maxcount = 0;
	
	public Heuristic(String filename) throws FileNotFoundException {
		// Creates the data structure necessary to apply the greedy algorithm.
		// The graph is represented as a list of nodes.
		
		Scanner sc = new Scanner(new File(filename));
		sc.useLocale(Locale.US);
		String[] header = sc.nextLine().split(" ");
		nleaf = Integer.parseInt(header[3]);
		nfixe = Integer.parseInt(header[2]);
		nodes = new Node[nleaf];
		for (int i=0;i<nleaf;i++) {
			nodes[i] = new Node(i);
		}
		int E = Integer.parseInt(header[4]);
		for (int i=0;i<E;i++) {
			String[] values = sc.nextLine().split(" ");
			int ileaf = Integer.parseInt(values[1])-1-nfixe;
			int inode = Integer.parseInt(values[0])-1;
			nodes[ileaf].add(new Leaf(inode));
		}
		sc.close();
	}
	
	private Pair<Integer,Integer> Compare(Node n1, Node n2) {
		// Compares two nodes. Returns the difference between the number of crossings
		// we would get if n1 was place on the left or on the right of n2.
		
		int u = 0;
		int d = 0;
		for (Leaf l1:n1.leafs) {
			for (Leaf l2:n2.leafs) {
				if (l2.id>l1.id) u++;
				else if (l2.id<l1.id) d++;
			}
		}
		return new Pair<Integer,Integer>(d,u);
	}
	
	private int min(int[] score) {
		// Returns the minimum of a list
		
		int res = score[0];
		for (int i=1;i<score.length;i++) {
			if (score[i]<res) res = score[i];
		}
		return res;
	}
	
	private Pair<ArrayList<Integer>, Integer> Minrec(ArrayList<Integer> lnode, int dep, int scorecum, int deep) {
		// Recursively tries orders for the nodes and keeps the best configuration.
		// The orders are created by comparing the node to be placed with the ones already placed. 
		// When there are several equivalent positions for a node, recursion branches are created.
		
		if (dep ==nodes.length) return new Pair<ArrayList<Integer>, Integer>(lnode, scorecum);
		if (deep>maxcount)return Minlin(lnode, dep+1, scorecum);
		
		int[] score = new int[lnode.size()+1]; // score keeps the number of crossings created at each position
		ArrayList<Integer> sol = lnode;
		
		Node n = nodes[dep];
		for (int j=0;j<lnode.size();j++) {
			Node n2 = nodes[lnode.get(j)-1-nfixe];
			Pair<Integer,Integer> p = Compare(n,n2);
			for (int k=j;k>=0;k--)score[k] += p.a;
			for (int k=j+1;k<score.length;k++)score[k] +=p.b;
		}
		int scoremin = min(score);

		scorecum +=scoremin;
		int mintot = -1;
		
		for (int j=0;j<lnode.size()+1;j++) {
			if (score[j]==scoremin) {
				lnode.add(j,n.id + 1 + nfixe);
				Pair<ArrayList<Integer>, Integer> p = Minrec(lnode, dep+1, scorecum,deep+1);
				if (p.b<mintot || mintot==-1) {
					mintot = p.b;
					sol = (ArrayList<Integer>) p.a.clone();
				}
				lnode.remove(j);

			}
		}

		return new Pair<ArrayList<Integer>, Integer>(sol, mintot);
	}
	
	private Pair<ArrayList<Integer>, Integer> Minlin(ArrayList<Integer> lnode, int dep, int scorecum) {
		// Computes an order by using the compare function, and placing each node to its optimal position in greedy fashion.
		
		if (dep ==nodes.length) return new Pair<ArrayList<Integer>, Integer>(lnode, scorecum);
				
		for (int i=dep;i<nodes.length;i++) {
			int[] score = new int[lnode.size()+1]; // score keeps the number of crossings created at each position
			Node n = nodes[i];
			for (int j=0;j<lnode.size();j++) {
				Node n2 = nodes[lnode.get(j)-1-nfixe];
				Pair<Integer,Integer> p = Compare(n,n2);
				for (int k=j;k>=0;k--)score[k] += p.a;
				for (int k=j+1;k<score.length;k++)score[k] +=p.b;
			}
			int scoremin = min(score);
	
			scorecum +=scoremin;
		
			for (int j=0;j<lnode.size()+1;j++) {
				if (score[j]==scoremin) {
					lnode.add(j,n.id + 1 + nfixe);
					break;
				}
			}
		}

		return new Pair<ArrayList<Integer>, Integer>(lnode, scorecum);
	}
	
	public Tri<String,Integer,Long> Minimize(){
		//Computes the order using the previous functions
		
		long start = System.currentTimeMillis();
		ArrayList<Integer> lnode = new ArrayList<Integer>();
		lnode.add(nodes[0].id + 1 + nfixe);
		Pair<ArrayList<Integer>, Integer> p = Minlin(lnode, 1, 0);
		lnode = p.a;
		int score = p.b;

		
		String s = ""+lnode.get(0);
		for (int i=1;i<lnode.size();i++) {
			s += "\n";
			s += lnode.get(i);
		}
		long finish = System.currentTimeMillis();


		return new Tri<String,Integer,Long>(s,score,finish-start);
	}
	

}
