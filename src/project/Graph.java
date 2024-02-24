package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class Graph {
	private Node[] nodes;
	private int[] last;
	
	public Graph(String filename) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(filename));
		sc.useLocale(Locale.US);
		String[] header = sc.nextLine().split(" ");
		nodes = new Node[Integer.parseInt(header[2])];
		int nfixe = Integer.parseInt(header[2]);
		for (int i=0;i<nfixe;i++){
			nodes[i] = new Node(i);
		}
		last = new int[Integer.parseInt(header[3])];
		int E = Integer.parseInt(header[4]);
		for (int i=0;i<E;i++) {
			String[] values = sc.nextLine().split(" ");
			int ileaf = Integer.parseInt(values[1])-1-nfixe;
			last[ileaf] = Integer.parseInt(values[0]);
		}
		sc.close();
		
		sc = new Scanner(new File(filename));
		sc.nextLine();
		for (int i=0;i<E;i++) {
			String[] values = sc.nextLine().split(" ");
			int inode = Integer.parseInt(values[0])-1;
			int ileaf = Integer.parseInt(values[1])-1-nfixe;
			nodes[inode].add(new Leaf(ileaf), last[ileaf]);
		}
		sc.close();
	}
	
	public int[] Minimize() throws FileNotFoundException {
		int[] res = new int[last.length];
		int[] isused = new int[last.length];
		int idx = 0;
		for (int i=0;i<nodes.length;i++) {
			Node n = nodes[i];
			SortedSet<Float> keySet = new TreeSet<>(n.ln.keySet());
			for (float k:keySet) {
				ArrayList<Leaf> ll = n.ln.get(k);
				for (Leaf l:ll) {
					System.out.println(i+1);
					System.out.println(k);
					System.out.println(l.id+ 1 + nodes.length);
					System.out.println("--------------------");
					if (isused[l.id]==0) {
						res[idx] = l.id + 1 + nodes.length;
						idx++;
						isused[l.id] = 1;
					}
				}
				k++;
			}
		}
		for (int i=0;i<last.length;i++) {
			if (isused[i]==0) {
				res[idx] = i + 1 + nodes.length;
				idx++;
			}
		}
		String s = ""+res[0];
		for (int i=1;i<last.length;i++) {
			s+= "\n";
			s += res[i] ;
		}

		PrintWriter writer = new PrintWriter("BestConfig.txt");
		writer.print(s); 
		writer.close();
		return res;
	}
	

}
