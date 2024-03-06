package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class Graph {
	private Tri tri;
	private int[] multiplicities;
	private float[] mediane;
	private float[] moy;
	private int[] count;
	private int nfixe;
	private int[] up;
	private int[] down;
	
	public Graph(String filename) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(filename));
		sc.useLocale(Locale.US);
		String[] header = sc.nextLine().split(" ");
		nfixe = Integer.parseInt(header[2]);
		tri = new Tri();
		mediane = new float[Integer.parseInt(header[3])];
		multiplicities = new int[Integer.parseInt(header[3])];
		up = new int[Integer.parseInt(header[3])];
		down = new int[Integer.parseInt(header[3])];
		moy = new float[Integer.parseInt(header[3])];
		count = new int[Integer.parseInt(header[3])];
		int E = Integer.parseInt(header[4]);
		for (int i=0;i<E;i++) {
			String[] values = sc.nextLine().split(" ");
			int ileaf = Integer.parseInt(values[1])-1-nfixe;
			int inode = Integer.parseInt(values[0])-1;
			multiplicities[ileaf] ++;
			moy[ileaf] += inode;
		}
		sc.close(); 
		
		sc = new Scanner(new File(filename));
		sc.nextLine();
		for (int i=0;i<E;i++) {
			String[] values = sc.nextLine().split(" ");
			int ileaf = Integer.parseInt(values[1])-1-nfixe;
			int inode = Integer.parseInt(values[0])-1;
			if (multiplicities[ileaf]%2==0) {
				count[ileaf] ++;
				if (count[ileaf] == multiplicities[ileaf]/2) {
					mediane[ileaf] = inode;
					down[ileaf] = inode;
				}
				if (count[ileaf] == 1 + multiplicities[ileaf]/2) {
					mediane[ileaf] = (mediane[ileaf]+inode)/2;
					up[ileaf] = inode;
				}
			}
		}
		sc.close();
		
		sc = new Scanner(new File(filename));
		sc.nextLine();
		for (int i=0;i<E;i++) {
			String[] values = sc.nextLine().split(" ");
			int ileaf = Integer.parseInt(values[1])-1-nfixe;
			int inode = Integer.parseInt(values[0])-1;
			if (multiplicities[ileaf]%2==1) {
				count[ileaf] ++;
				if (count[ileaf] == 1+multiplicities[ileaf]/2) {
					mediane[ileaf] = inode;
					Boolean found = false;
					for (int j=0;j<nfixe;j++) {;
						if (inode<up[j] && inode>down[j]) {
							found = true;
						}
					}
					if (found==false) {
					}
				}
			}
		}
	}
	
	public String Minimize() throws FileNotFoundException {
		int[] res = new int[mediane.length];
		int[] isused = new int[mediane.length];
		int idx = 0;
		SortedSet<Float> keySet = new TreeSet<>(tri.ln.keySet());
		for (float k:keySet) {
			ArrayList<Leaf> ll = tri.ln.get(k);
			for (Leaf l:ll) {
				if (isused[l.id]==0) {
					res[idx] = l.id + 1 + nfixe;
					idx++;
					isused[l.id] = 1;
				}
			}
			k++;
		}
		for (int i=0;i<mediane.length;i++) {
			if (isused[i]==0) {
				res[idx] = i + 1 + nfixe;
				idx++;
			}
		}
		String s = ""+res[0];
		for (int i=1;i<mediane.length;i++) {
			s+= "\n";
			s += res[i] ;
		}
		return s;
	}
	

}
