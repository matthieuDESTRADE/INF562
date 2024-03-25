package project;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		// Computation of solutions
		
		for (int i=44;i<=44;i++) {
			Heuristic h = new Heuristic("data/heuristic-public/"+i+".gr");
			String s = h.Minimize().a;
			PrintWriter writer = new PrintWriter("sol-heuristic-public/"+i+".sol");
			writer.print(s); 
			writer.close();
			System.out.println(i);
		}

		
		
		/*
		// Precision tests :
		
		int n = 50;
		
		long timeBF = 0;
		long timeRand = 0;
		long timeMed = 0;
		long timeMea = 0;
		long timeGreed = 0;
		
		float erRand = 0;
		float erMed = 0;
		float erMea = 0;
		float erGreed = 0;


		for (int i=0;i<n;i++) {
			new GenerateRandom(args[0],10, 3);
			
			BruteForce bf = new BruteForce(args[0]);
			MedianHeuristic mh = new MedianHeuristic(args[0]);
			Heuristic h = new Heuristic(args[0]);

			Tri<String,Integer,Long> bfsol = bf.Minimize();
			//Tri<String,Integer,Long> bfsol = new Tri<String,Integer,Long>("",1000,(long)0);
			timeBF += bfsol.c;
			
			Tri<String,Integer,Long> mhsolNoSort = mh.Minimize(true,false);
			timeRand += mhsolNoSort.c;
			erRand += (mhsolNoSort.b+1)/((float)bfsol.b+1) -1;
			
			Tri<String,Integer,Long> mhsol = mh.Minimize(false,false);
			timeMed += mhsol.c;
			erMed += (mhsol.b+1)/((float)bfsol.b+1) -1;
			
			Tri<String,Integer,Long> mhmeansol = mh.Minimize(false,true);
			timeMea += mhmeansol.c;
			erMea += (mhmeansol.b+1)/((float)bfsol.b+1) -1;
			
			Tri<String,Integer,Long> hsol = h.Minimize();
			timeGreed += hsol.c;
			erGreed += (hsol.b+1)/((float)bfsol.b+1) -1;
			
		}


		System.out.println("Time BF : " + timeBF/(float)n);
		System.out.println("______________");
		System.out.println("Time random : " + timeRand/(float)n);
		System.out.println("Error random : " + erRand/(float)n);
		System.out.println("______________");
		System.out.println("Time median : " + timeMed/(float)n);
		System.out.println("Error median : " + erMed/(float)n);
		System.out.println("______________");
		System.out.println("Time mean : " + timeMea/(float)n);
		System.out.println("Error mean : " + erMea/(float)n);
		System.out.println("______________");
		System.out.println("Time greedy : " + timeGreed/(float)n);
		System.out.println("Error greedy : " + erGreed/(float)n);
		System.out.println("______________");
		*/
		
		/*
		// Tests on real graphs
		
		MedianHeuristic mh = new MedianHeuristic(args[0]);
		Heuristic h = new Heuristic(args[0]);

		Tri<String,Integer,Long> mhsolNoSort = mh.Minimize(true,false);
		System.out.println("Solution without sorting : " + mhsolNoSort.b);
		System.out.println("Time without sorting : 0.0");
		System.out.println("______________");

		
		Tri<String,Integer,Long> mhsol = mh.Minimize(false,false);
		System.out.println("Solution median : " + mhsol.b);
		System.out.println("Time median : " + mhsol.c);
		System.out.println("______________");
		
		Tri<String,Integer,Long> mhmeansol = mh.Minimize(false,true);
		System.out.println("Solution mean : " + mhmeansol.b);
		System.out.println("Time mean : " + mhmeansol.c);
		System.out.println("______________");
		
		Tri<String,Integer,Long> hsol = h.Minimize();
		System.out.println("Solution greedy : " + hsol.b);
		System.out.println("Time greedy : " + hsol.c);
		*/

	}
}
