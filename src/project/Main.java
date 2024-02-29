package project;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new Graph(args[0]);
		String s = g.Minimize();
		String name = args[1];
		PrintWriter writer = new PrintWriter(name);
		writer.print(s); 
		writer.close();
	}
}
