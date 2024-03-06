package project;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		for (int i=0;i<1;i++) {
			//new GenerateRandom(args[0],9, 2);
			
			Heuristic g = new Heuristic(args[0]);
			String s = g.Minimize();
			String name = args[1];
			//BruteForce bf = new BruteForce(args[0]);
			PrintWriter writer = new PrintWriter("sol1.sol");
			//writer.print(bf.compute()); 
			writer.close();
			
			writer = new PrintWriter(name);
			writer.print(s); 
			writer.close();
			System.out.println("__________");
		}
	}
}
