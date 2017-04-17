import java.util.*;
import java.io.*;
/* CSCI 511 Assignment 1
 * Author: Austin Corotan
 * Date: April 16, 2017
 * Description: This program is designed to calculate the LUP decomposition of a
 * tri-diagonal matrix in linear time.
 */

public class LUPDriver{
	public static void main(String[] args) {
		try {
			double[][] A;
			if(args.length > 0) {
            	File inputFile = new File(args[0]);
            	A = processInput(inputFile);
            	
            	System.out.println("Compact A:");
           		printDoubleMatrix(A);

           		double[][] aa = expandInput(A);
				System.out.println("Expanded A:");
           		printDoubleMatrix(aa);
        
				LUP lup = new LUP();
				lup.factorMatrix(A);

				int[][] p = lup.getExpandedPerm();
				double[][] L = lup.getExpandedL();
				double[][] U = lup.getExpandedU();

				System.out.println("Perm:");
				printIntMatrix(p);

				System.out.println("\nLower:");
				printDoubleMatrix(L);

				System.out.println("\nUpper:");
				printDoubleMatrix(U);

				System.out.println("\nPA");
				double[][] pp = intToDouble(p);
				double[][] pa = matrixMultiply(pp, aa);
				printDoubleMatrix(pa);

				System.out.println("\nLU");
				double[][] lu = matrixMultiply(L, U);
				printDoubleMatrix(lu);

				if(matrixEquality(pa,lu) == true){
					System.out.println("\nPA = LU!");
				}else{
					System.out.println("\nPA != LU.");
				}
				
			}
		} catch (Exception e) {
        	e.printStackTrace();
    	}
	}

	static double[][] processInput(File inputFile) throws IOException{
		Scanner input = new Scanner (inputFile);
		int colNum = 0;
		while(input.hasNextLine()){
			colNum++;
			input.nextLine();
		}

		double A[][] = new double[3][colNum];
		String line;
		colNum = 0;
		input = new Scanner (inputFile);

		while(input.hasNextLine()){
			line = input.nextLine();
    		String[] vals = line.trim().split("\\s+");
    		for (int i = 0 ; i < 3 ; i++){
         		A[i][colNum] = Double.parseDouble(vals[i]);
         	}
         	colNum++;
		}	

    	return A;
	}

	static double[][] processFullInput(File inputFile) throws IOException{
		Scanner input = new Scanner (inputFile);
		int colNum = 0;
		while(input.hasNextLine()){
			colNum++;
			input.nextLine();
		}

		double A[][] = new double[colNum][colNum];
		String line;
		input = new Scanner (inputFile);

		for(int i = 0; i < colNum; i++){
			line = input.nextLine();
			String[] numbers = line.split(" ");
			for (int j = 0 ; j < colNum ; j++){
         		A[i][j] = Double.parseDouble(numbers[j]);
         	}

		}

    	return A;
	}

	static void printIntMatrix(int[][] matrix){
		for (int i = 0; i < matrix.length; i++) {
    		for (int j = 0; j < matrix[i].length; j++) {
        		System.out.printf("%4d",matrix[i][j]);
    		}
    		System.out.println();
    	}
	}

	static void printDoubleMatrix(double[][] matrix){
		for (int i = 0; i < matrix.length; i++) {
    		for (int j = 0; j < matrix[i].length; j++) {
        		System.out.printf("%10.4f",matrix[i][j]);
    		}
    		System.out.println();
    	}
    	System.out.println();
	}

	static void printFullDoubleMatrix(double[][] matrix){
		for (int i = 0; i < matrix.length; i++) {
    		for (int j = 0; j < matrix.length; j++) {
        		System.out.print(matrix[i][j] + " ");
    		}
    		System.out.println();
    	}
    	System.out.println();
	}

	static double[][] intToDouble(int[][] p){
		int size = p[0].length;
		double[][] c = new double[size][size];
		for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
            	c[i][j] = (double)p[i][j];
            }
        }
        return c;
	}

	static double[][] expandInput(double[][] A){
		int size = A[0].length;
		double[][] c = new double[size][size];
		for(int j = 0; j < size - 1; j++){
				c[j][j] = A[1][j];
				c[j+1][j] = A[0][j+1];
				c[j][j+1] = A[2][j];
		}
		c[A[0].length-1][A[0].length-1] = A[1][A[0].length-1];
		return c;
	}

	static double[][] matrixMultiply(double[][] a, double[][] b){
		int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
        double[][] c = new double[m1][n2];
        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n2; j++)
                for (int k = 0; k < n1; k++)
                    c[i][j] += a[i][k] * b[k][j];

        return c;

	}

	static boolean matrixEquality(double[][] a, double[][] b){
		int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2 || n2 != m1) throw new RuntimeException("Illegal matrix dimensions.");
        boolean c = true;
        for (int i = 0; i < m1; i++){
            for (int j = 0; j < n1; j++){
                if(Math.abs(a[i][j] - b[i][j]) > 1e-4){
                	c = false;
                }
            }
        }
                    
        return c;
	}

}