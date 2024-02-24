package project;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new Graph("data/tiny_test_set/ladder_4_4_shuffled.gr");
		int[] res = g.Minimize();
	}
}
