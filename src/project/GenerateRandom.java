package project;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class GenerateRandom {
	
	public GenerateRandom(String path, int size,int prob) throws FileNotFoundException {
		String s= "";
		Random random = new Random();
		
		int sum = 0;
		
		for (int i=0;i<size;i++) {
			for (int j=0;j<size;j++) {
				if (random.nextInt(prob)==0) {
					s += (i+1) + " " + (j+1+size) + "\n";
					sum++;
				}
			}
		}
		s = "p ocr " + size + " " + size + " " + sum + "\n" + s;
		PrintWriter writer = new PrintWriter(path);
		writer.print(s); 
		writer.close();
	}
}
