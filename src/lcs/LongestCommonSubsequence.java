package lcs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class LongestCommonSubsequence {

//	public int[][] sequenceTable; 
//	private int[] distanceTableA;
//	private int[] distanceTableB;
	//private statisequenceTable void einputSequensequenceTableeAtend(Seq1, Seq2, double threshold) {}

	public static int[][] computeSequenceTable(int inputSequenceA[], int inputSequenceB[]) {
		int m = inputSequenceA.length+1;
		int n = inputSequenceB.length+1;
		int[][] sequenceTable = new int[m][n];
		for (int i = 0;i<m;i++) {
			sequenceTable[i][0] = 0;
		}
		for (int j = 0;j<n;j++) {
			sequenceTable[0][j] = 0;
		}

		for (int i = 1;i<m;i++){
			for (int j= 1;j<n;j++) {
				if (inputSequenceA[i-1] == inputSequenceB[j-1]) {
					sequenceTable[i][j] = sequenceTable[i-1][j-1] + 1;
				}		
				else {
					sequenceTable[i][j] = Math.max(sequenceTable[i][j-1], sequenceTable[i-1][j]);
				}
			}
		}
		return sequenceTable;
	}
	
	public static HashSet<String> backtrackAll(int[][] C, int[] inputA, int[] inputB, int i, int j) {
	    // System.out.println(i+" " + j);
	    if (i == 0 || j == 0) {
	        return new HashSet<String>();
	    } else if (inputA[i - 1] == inputB[j - 1]) {
	        HashSet<String> R = backtrackAll(C, inputA, inputB, i - 1, j - 1);
	        HashSet<String> new_set = new HashSet<String>();

	        for (String Z : R) {
	            new_set.add(Z + inputA[i - 1]);
	        }
	        new_set.add("" + inputA[i - 1]);
	        return new_set;
	    } else {
	        HashSet<String> R = new HashSet<String>();
	        if (C[i][j - 1] >= C[i - 1][j]) {
	            R = backtrackAll(C, inputA, inputB, i, j - 1);
	        }
	        if (C[i - 1][j] >= C[i][j - 1]) {
	            R.addAll(backtrackAll(C, inputA, inputB, i - 1, j));
	        }
	        return R;
	    }
	}
	
	public static List<Integer> backtrack(int[][] C, int[] inputA, int[] inputB, int i, int j) {
	    // System.out.println(i+" " + j);
	    if (i == 0 || j == 0) {
	        return new ArrayList<Integer>();
	    } else if (inputA[i - 1] == inputB[j - 1]) {
	        List<Integer> R = backtrack(C, inputA, inputB, i - 1, j - 1);
	        R.add(inputA[i - 1]);
	        return R;
	    } else {
	        if (C[i][j - 1] > C[i - 1][j]) {
	            return  backtrack(C, inputA, inputB, i, j - 1);
	        }
	        else {
	            return backtrack(C, inputA, inputB, i - 1, j);
	        }
	    }
	}

//
//	public Set<String> backtrackAll(sequenceTable[0..m,0..n], X[1..m], Y[1..n], i, j) {
//		if (i == 0 || j == 0){
//			return new HashSet<String>();
//		}else if( X[i] == Y[j]) {
//			return {Z + X[i] for all Z in backtrackAll(C, X, Y, i-1, j-1)}
//		}else{
//			R := {}
//		if (sequenceTable[i,j-1] >= sequenceTable[i-1,j] ){
//			R := backtrackAll(sequenceTable, X, Y, i, j-1)
//		}
//		if (sequenceTable[i-1,j] >= sequenceTable[i,j-1]) {
//			R := R âˆ?backtrackAll(sequenceTable, X, Y, i-1, j)
//					return R
//		}
//		}

		public void printoutSequenceMatrix( int[][] sequenceTable) {
			for(int i=0;i<sequenceTable.length;i++) {
				System.out.println(Arrays.toString(sequenceTable[i]));
			}
		}
	}