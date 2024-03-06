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
	
	private int Compare(Node n1, Node n2) {
		int mult2 = n2.leafs.size();
		int mult = n1.leafs.size();
		if (mult2==0 || mult==0) return 1;
		if (mult2%2==0) {
			int up2 = n2.leafs.get(mult2/2).id;
			int down2 = n2.leafs.get(-1+mult2/2).id;
			if (mult%2==0) {
				int up1 = n1.leafs.get(mult/2).id;
				int down1 = n1.leafs.get(-1+mult/2).id;
				if ((up1>up2 && down1<down2) && n2.leafs.size()>2) {
					LinkedList<Leaf> l2 = (LinkedList<Leaf>) n2.leafs.clone();
					l2.remove(mult2/2);
					l2.remove(-1+mult2/2);
					return Compare(n1, new Node(n2.id, l2));	
				}
				else if ((up1<up2 && down1>down2) && n1.leafs.size()>2) {
					LinkedList<Leaf> l1 = (LinkedList<Leaf>) n1.leafs.clone();
					l1.remove(mult/2);
					l1.remove(-1+mult/2);
					return Compare(new Node(n1.id,l1), n2);	
				}
				else if ((up1==up2 && down1==down2) && n2.leafs.size()>2 && n1.leafs.size()>2) {
					LinkedList<Leaf> l1 = (LinkedList<Leaf>) n1.leafs.clone();
					LinkedList<Leaf> l2 = (LinkedList<Leaf>) n2.leafs.clone();
					l1.remove(mult/2);
					l1.remove(-1+mult/2);
					l2.remove(mult2/2);
					l2.remove(-1+mult2/2);
					return Compare(new Node(n1.id,l1), new Node(n2.id, l2));	
				}
				else if (up1>=up2 && down1>down2 || up1>up2 && down1>=down2) {
					return 1;
				}
				else if (up1<=up2 && down1<down2 || up1<up2 && down1<=down2) return -1;
				else return 0;
			}
			else {
				int m = n1.leafs.get(mult/2).id;
				if (m>=up2) {
					return 1;	
				}
				else if (m<=down2) {
					return -1;	
				}
				else if (n1.leafs.size()>1) {
					LinkedList<Leaf> l1 = (LinkedList<Leaf>) n1.leafs.clone();
					l1.remove(mult/2);
					return Compare(new Node(n1.id,l1), n2);	
				}
	
				else return 0;
			}
		}
		else {
			if (mult%2==0){
				return -Compare(n2,n1);
			}
			else {
				int m = n1.leafs.get(mult/2).id;
				int m2 = n2.leafs.get(mult2/2).id;
				if (m>m2) {
					return 1;	
				}
				else if (m<m2) {
					return -1;	
				}
				else if (n1.leafs.size()>1 && n2.leafs.size()>1) {
					LinkedList<Leaf> l1 = (LinkedList<Leaf>) n1.leafs.clone();
					LinkedList<Leaf> l2 = (LinkedList<Leaf>) n2.leafs.clone();
					l2.remove(mult2/2);
					l1.remove(mult/2);
					return Compare(new Node(n1.id,l1), new Node(n2.id,l2));	
				}
				else return 0;
			}
		}
	}
	
	private Pair<Integer,Integer> Compare2(Node n1, Node n2) {
		int u = 0;
		int d = 0;
		for (Leaf l1:n1.leafs) {
			for (Leaf l2:n2.leafs) {
				if (l2.id>l1.id) u++;
			}
		}
		for (Leaf l1:n1.leafs) {
			for (Leaf l2:n2.leafs) {
				if (l2.id<l1.id) d++;
			}
		}
		return new Pair<Integer,Integer>(d,u);
	}
	
	private int min(int[] score) {
		int res = score[0];
		for (int i=1;i<score.length;i++) {
			if (score[i]<res) res = score[i];
		}
		return res;
	}
	
	private Pair<ArrayList<Integer>, Integer> Minrec(ArrayList<Integer> lnode, int dep, int scorecum, int deep) {
		if (dep ==nodes.length) return new Pair<ArrayList<Integer>, Integer>(lnode, scorecum);
		if (deep>maxcount)return Minlin(lnode, dep+1, scorecum);
		
		int[] score = new int[lnode.size()+1];
		ArrayList<Integer> sol = lnode;
		
		Node n = nodes[dep];
		for (int j=0;j<lnode.size();j++) {
			Node n2 = nodes[lnode.get(j)-1-nfixe];
			Pair<Integer,Integer> p = Compare2(n,n2);
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
		if (dep ==nodes.length) return new Pair<ArrayList<Integer>, Integer>(lnode, scorecum);
				
		for (int i=dep;i<nodes.length;i++) {
			int[] score = new int[lnode.size()+1];
			Node n = nodes[i];
			for (int j=0;j<lnode.size();j++) {
				Node n2 = nodes[lnode.get(j)-1-nfixe];
				Pair<Integer,Integer> p = Compare2(n,n2);
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
	
	public String Minimize() throws FileNotFoundException {
		ArrayList<Integer> lnode = new ArrayList<Integer>();
		lnode.add(nodes[0].id + 1 + nfixe);
		Pair<ArrayList<Integer>, Integer> p = Minrec(lnode, 1, 0,0);
		lnode = p.a;
		int score = p.b;
		System.out.println("Notre solution :" + score);

		
		String s = ""+lnode.get(0);
		for (int i=1;i<lnode.size();i++) {
			s += "\n";
			s += lnode.get(i);
		}

		return s;
	}
	

}
