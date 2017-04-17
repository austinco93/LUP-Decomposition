import java.util.*;
import java.lang.*;
/* LUP.java
 * Author: Austin Corotan
 * 4/16/2017
 */

class LUP {
	double[] P;
	double[][] L;
	double[][] U;
	int[] swapArray;
	static int size;
	static int Lsize;
	static int Usize;

	public void factorMatrix(double[][] A){
		size = A[0].length;
		Lsize = 2*size - 1;
		Usize = size+(size-1)+(size-2);
		this.L = new double[3][Lsize];
		this.U = new double[3][Usize];
		this.P = new double[size];
		int uindex = 0;
		int lindex = 0;
		int swap = 0;
		int currentSwap = 0;

		ArrayList<Double> swapArray = new ArrayList<Double>();

		//initialize L
		for(int i = 0; i < size; i++){
			addEntry(L, i,i, lindex, 1);
			lindex++;
		}

		//initialize P
		for(int i = 0; i < size; i++){
			P[i] = i + 1;
		}

		//calculate L, U, P
		for(int i = 0; i < size-1; i++){
			if(Math.abs(A[1][i]) < Math.abs(A[0][i+1])){
				swap2(A,1,i,0,i+1);
				swap2(A,2,i,1,i+1);
				swap2(A,0,i,2,i+1);
				swap(P, i, i+1);
				swap++;
				A[2][i+1] = 0;
			} else {
				for (int j = 0; j < swapArray.size(); j++) { 
					addEntry(L, currentSwap, swap, lindex, swapArray.get(j));
					lindex++;
					currentSwap++;
				}
				swapArray.clear();
				swap++;
				A[0][i] = 0;
			}

			uindex = addRow(U, i, uindex, A);

			A[0][i+1] = A[0][i+1]/A[1][i];
			swapArray.add(A[0][i+1]);

			A[1][i+1]= A[1][i+1] - A[0][i+1]*A[2][i];
			A[2][i+1]= A[2][i+1] - A[0][i+1]*A[0][i];
		}
		addEntry(U, size-1,size-1, uindex -1, A[1][size-1]);

		for (int j = 0; j < swapArray.size(); j++) {
			addEntry(L, currentSwap, swap, lindex, swapArray.get(j));
			lindex++;
			currentSwap++;
		}
	}

	public static void swap(final double[] arr, final int pos1, final int pos2){
    	final double temp = arr[pos1];
    	arr[pos1] = arr[pos2];
    	arr[pos2] = temp;
	}

	public static void swap2(final double[][] arr, final int pos1X, final int pos1Y, final int pos2X, final int pos2Y){
    	final double temp = arr[pos1X][pos1Y];
    	arr[pos1X][pos1Y] = arr[pos2X][pos2Y];
    	arr[pos2X][pos2Y] = temp;
	}

	public static void addEntry(double[][] M, double i, double j, int index, double val){
		M[0][index] = i;
		M[1][index] = j;
		M[2][index] = val;
	}

	public static int addRow(double[][] U, int i, int uindex, double[][] A){
		addEntry(U, i,i, uindex, A[1][i]);
		uindex++;
		addEntry(U, i,i+1, uindex, A[2][i]);
		uindex++;
		addEntry(U, i,i+2, uindex, A[0][i]);	
		uindex++;

		return uindex;
	}

	public double[] getPerm(){
		return P;
	}

	public	double[][] getL(){
		return L;
	}

	public double[][] getU(){
		return U;
	}

	public int[][] getExpandedPerm(){
		int[][] perm = new int[size][size];
		int index;
		for(int i=0; i<size; i++){
			index = (int)P[i];
			perm[i][index-1] = 1;
		}
		return perm;
	}

	public double[][] getExpandedL(){
		double[][] expL = new double[size][size];
		int indexX;
		int indexY;
		for(int i=0; i<Lsize; i++){
			indexX = (int)(L[1][i]);
			indexY = (int)(L[0][i]);
			expL[indexX][indexY] = L[2][i];
		}
		
		return expL;
	}

	public double[][] getExpandedU(){
		double[][] expU = new double[size][size];
		int indexX;
		int indexY;
		for(int i=0; i<Usize; i++){
			indexX = (int)(U[0][i]);
			indexY = (int)(U[1][i]);
			expU[indexX][indexY] = U[2][i];
		}
		
		return expU;		
	}
}
