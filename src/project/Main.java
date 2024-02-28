package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new Graph(args[0]);
		File f = new File(args[0]);
		String s = g.Minimize();
		PrintWriter writer = new PrintWriter(f.getName().replaceFirst("[.][^.]+$", "") + ".sol");
		writer.print(s); 
		writer.close();
	}
}
