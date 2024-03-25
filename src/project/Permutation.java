package project;

public class Permutation {
	int[][] perms;
	int nperm;
	int tot;
	
	public Permutation(int size) {
		// Generates lists of indices that correspond to the permutations of [1,size] and stores them in perms
		
		tot = facto(size);
		perms = new int[tot][size];
		int[] array = new int[size];
		nperm = 0;
		for (int j=0;j<size;j++) {
			array[j] = j;
		}

		generatePermutations(array,0);
	}

    public void generatePermutations(int[] array, int currentIndex) {
        if (currentIndex == array.length - 1) {
            // We have reached the end of the array, print the current permutation
            perms[nperm] = array.clone();
            nperm++;
            return;
        }

        for (int i = currentIndex; i < array.length; i++) {
            // Swap elements at currentIndex and i
            swap(array, currentIndex, i);

            // Recursively generate permutations for the remaining elements
            generatePermutations(array, currentIndex + 1);

            // Backtrack by swapping the elements back
            swap(array, currentIndex, i);
        }
    }
    
    public int[] get(int i) {
    	return perms[i];
    }
    
    private int facto(int i) {
    	if (i==0) {
    		return 1;
    	}
    	return facto(i-1)*i;
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}