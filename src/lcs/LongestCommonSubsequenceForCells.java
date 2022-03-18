package lcs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LongestCommonSubsequenceForCells {

	//public Cell[][] sequenceTable; 
	//private statisequenceTable void einputSequensequenceTableeAtend(Seq1, Seq2, double threshold) {}

	public static Cell[][] computeTable(int[] inputSequenceA, int[] inputSequenceB) {
		int aIndex = inputSequenceA.length+1;
		int bIndex = inputSequenceB.length+1;

		// Init the table
		Cell[][] sequenceTable = new Cell[aIndex][bIndex];	
		for (int i = 0;i<aIndex;i++) {
			for (int j = 0;j<bIndex;j++) {
				sequenceTable[i][j] = new Cell();
				sequenceTable[0][j].seqLength = 0;
			}
			sequenceTable[i][0].seqLength = 0;
		}


		for (int i = 1;i<aIndex;i++){
			for (int j= 1;j<bIndex;j++) {
				//				String A =inputSequenceA.substring(i-1,i);
				//				String B = inputSequenceB.substring(j-1,j);
				int A =inputSequenceA[i-1];
				int B = inputSequenceB[j-1];
				if ( A==B ) {
					sequenceTable[i][j].seqLength = sequenceTable[i-1][j-1].seqLength + 1;
				}		
				else { 
					sequenceTable[i][j].seqLength = Math.max(sequenceTable[i][j-1].seqLength, sequenceTable[i-1][j].seqLength);
				}
			}
		}

		return sequenceTable;
	}
    
	//compute the matrix for locaiton semantics
	public static Cell[][] computeTableLS(int[] inputSequenceA, int[] inputSequenceB, double distance,  HashMap<String, double[]> distributionOfRegions) throws Exception {
		int aIndex = inputSequenceA.length+1;
		int bIndex = inputSequenceB.length+1;

		// Init the table
		Cell[][] sequenceTable = new Cell[aIndex][bIndex];	
		for (int i = 0;i<aIndex;i++) {
			for (int j = 0;j<bIndex;j++) {
				sequenceTable[i][j] = new Cell();
				sequenceTable[0][j].seqLength = 0;
			}
			sequenceTable[i][0].seqLength = 0;
		}


		for (int i = 1;i<aIndex;i++){
			for (int j= 1;j<bIndex;j++) {
				//				String A =inputSequenceA.substring(i-1,i);
				//				String B = inputSequenceB.substring(j-1,j);
				int A =inputSequenceA[i-1];
				int B = inputSequenceB[j-1];
				
				double distanceBetweenAB = VectorDistance(A,B, distributionOfRegions);
				if ( distanceBetweenAB<=distance ) {
					sequenceTable[i][j].seqLength = sequenceTable[i-1][j-1].seqLength + 1;
				}		
				else { 
					sequenceTable[i][j].seqLength = Math.max(sequenceTable[i][j-1].seqLength, sequenceTable[i-1][j].seqLength);
				}
			}
		}

		return sequenceTable;
	}
	
	public static Cell[][] computeTableLSYing(int[] inputSequenceA, int[] inputSequenceB, HashMap<String, double[]> distributionOfRegions) {
		int aIndex = inputSequenceA.length+1;
		int bIndex = inputSequenceB.length+1;

		// Init the table
		Cell[][] sequenceTable = new Cell[aIndex][bIndex];	
		for (int i = 0;i<aIndex;i++) {
			for (int j = 0;j<bIndex;j++) {
				sequenceTable[i][j] = new Cell();
				sequenceTable[0][j].seqLength = 0;
			}
			sequenceTable[i][0].seqLength = 0;
		}


		for (int i = 1;i<aIndex;i++){
			for (int j= 1;j<bIndex;j++) {
				//				String A =inputSequenceA.substring(i-1,i);
				//				String B = inputSequenceB.substring(j-1,j);
				int A =inputSequenceA[i-1];
				int B = inputSequenceB[j-1];
				
				int count = intersectionOfRoITags(A, B, distributionOfRegions);
				int temp = sequenceTable[i-1][j-1].seqLength + count;
				if ( (temp > sequenceTable[i][j-1].seqLength) && (temp > sequenceTable[i-1][j].seqLength) ) {
					sequenceTable[i][j].seqLength = temp;					
					sequenceTable[i][j].ratioA = sequenceTable[i-1][j-1].ratioA + count / countTags(A, distributionOfRegions);
					sequenceTable[i][j].ratioB = sequenceTable[i-1][j-1].ratioB + count / countTags(B, distributionOfRegions);
				}		
				else { 
					if(sequenceTable[i][j-1].seqLength > sequenceTable[i-1][j].seqLength) {
						sequenceTable[i][j].seqLength = sequenceTable[i][j-1].seqLength;
						sequenceTable[i][j].ratioA = sequenceTable[i][j-1].ratioA;
						sequenceTable[i][j].ratioB = sequenceTable[i][j-1].ratioB;
					} else {
						sequenceTable[i][j].seqLength = sequenceTable[i-1][j].seqLength;
						sequenceTable[i][j].ratioA = sequenceTable[i-1][j].ratioA;
						sequenceTable[i][j].ratioB = sequenceTable[i-1][j].ratioB;
					}
				}
			}
		}

		return sequenceTable;
	}
	
	public static double countTags(int A, HashMap<String, double[]> distributionOfRegions) {
		double[] distributionA = distributionOfRegions.get(Integer.toString(A));
		
		double count = 0;
		for(double prob : distributionA) {
			if(prob != 0.0) {
				count++;
			}
		}
		return count;
	}
	
	public static int intersectionOfRoITags(int A, int B,  HashMap<String, double[]> distributionOfRegions) {
		double[] distributionA = distributionOfRegions.get(Integer.toString(A));
		double[] distributionB = distributionOfRegions.get(Integer.toString(B));
		
		int count = 0;
		for(int i = 0; i < distributionA.length; i++) {
			if((distributionA[i] != 0.0) && (distributionB[i] != 0.0)) {
				count++;
			}
		}
		
		return count;
	}

	public static double VectorDistance(int A, int B,  HashMap<String, double[]> distributionOfRegions) throws Exception{
		double[] distributionA = distributionOfRegions.get(Integer.toString(A));
		double[] distributionB = distributionOfRegions.get(Integer.toString(B));
		
		if(distributionA == null || distributionB == null) {
			throw new Exception();
		}
		double distanceAB = RelativeEntropy(distributionA, distributionB);
		double distanceBA = RelativeEntropy(distributionB, distributionA);
		
		return (distanceAB + distanceBA)/2;
	}
	
	public static double RelativeEntropy(double[] distA, double[] distB){
		double entropy = 0;
		for (int i=0; i<distA.length; i++)
			entropy += distA[i] * (Math.log(distA[i]/distB[i])/Math.log(2));
		return entropy;
	}
	public static ArrayList<ArrayList<Cell>> backtrackAll(Cell[][] C, int[] inputSequenceA, int[] inputSequenceB, int i, int j, TimeRange[] timeRangeA, TimeRange[] timeRangeB) {
		// end of recursion, hit top or left 
		if (i == 0 || j == 0) {
			return new ArrayList<ArrayList<Cell>>();
		} else if (inputSequenceA[i - 1]== inputSequenceB[j - 1]) {
			// found matching element, backtrack all other elements
			ArrayList<ArrayList<Cell>> R = backtrackAll(C, inputSequenceA, inputSequenceB, i - 1, j - 1, timeRangeA, timeRangeB);

			ArrayList<ArrayList<Cell>> new_set = new ArrayList<ArrayList<Cell>>();

			// while rebuilding sequence append current matching element to all found sequences
			for (ArrayList<Cell> Z : R) {
				// sum up all timeranges since last found element for input sequence A
				Cell lastCell = Z.get(Z.size()-1);//new Cell(Z.get(Z.size()-1));
				if (lastCell.timeRangeForUserA== null) {
					lastCell.timeRangeForUserA = new TimeRange(0,0);
				}
				if (lastCell.timeRangeForUserB== null) {
					lastCell.timeRangeForUserB = new TimeRange(0,0);
				}
				int inputStartSum = 0;
				int inputEndSum = 0;

				for(int inputAIndex = lastCell.inputAIndex; inputAIndex<i-1;inputAIndex++) {
					if (timeRangeA[inputAIndex] == null) {
						inputStartSum += 0;
						inputEndSum += 0;
					}else {
						inputStartSum += timeRangeA[inputAIndex].getStartTime();
						inputEndSum += timeRangeA[inputAIndex].getEndTime();
					}
				}
				lastCell.timeRangeForUserA.setStartTime(inputStartSum);
				lastCell.timeRangeForUserA.setEndTime(inputEndSum);

				// sum up all timeranges since last found element for input sequence B
				inputStartSum = 0;
				inputEndSum = 0;
				for(int inputBIndex = lastCell.inputBIndex; inputBIndex<j-1;inputBIndex++) {
					if (timeRangeB[inputBIndex] == null) {
						inputStartSum += 0;
						inputEndSum += 0;
					}else{
						inputStartSum += timeRangeB[inputBIndex].getStartTime();
						inputEndSum += timeRangeB[inputBIndex].getEndTime();
					}
					
				}
				lastCell.timeRangeForUserB.setStartTime(inputStartSum);
				lastCell.timeRangeForUserB.setEndTime(inputEndSum);
				TimeRange ZTimeRangeA = null;
				TimeRange ZTimeRangeB = null;

				if ( ((i-1)<=timeRangeA.length-1) && ((j-1)<=timeRangeB.length-1) ) {

					ZTimeRangeA = timeRangeA[i-1];
					ZTimeRangeB = timeRangeB[j-1];

				}
				Z.add(new Cell(inputSequenceA[i - 1], i-1,j-1,ZTimeRangeA, ZTimeRangeB));//TODO: check for correctness

				new_set.add(Z);
			}
			// start a new sequence with the matching element as head. special case of above loop
			if (R.isEmpty()) {
				ArrayList<Cell> subSequence =  new ArrayList<Cell>();
				subSequence.add(new Cell(inputSequenceA[i - 1], i-1, j-1, timeRangeA[i-1], timeRangeB[j-1]));

				new_set.add(subSequence);
			}

			return new_set;
		} else {
			ArrayList<ArrayList<Cell>> R = new ArrayList<ArrayList<Cell>>();
			if (C[i][j - 1].seqLength >= C[i - 1][j].seqLength) {
				R = backtrackAll(C, inputSequenceA, inputSequenceB, i, j - 1, timeRangeA, timeRangeB);
			}
			if (C[i - 1][j].seqLength >= C[i][j - 1].seqLength) {
				R.addAll(backtrackAll(C, inputSequenceA, inputSequenceB, i - 1, j, timeRangeA, timeRangeB));
			}
			return R;
		}
	}

	public static void printoutSequenceMatrix(Cell[][] sequenceTable, int[] inputSequenceA, int[] inputSequenceB) {
		System.out.println("     "+Arrays.toString(inputSequenceB));
		System.out.println("  "+Arrays.toString(sequenceTable[0]));
		for(int i=1;i<sequenceTable.length;i++) {
			System.out.print(inputSequenceA[i-1]+" ");
			System.out.println(Arrays.toString(sequenceTable[i]));
		}
	}

	public static ArrayList<ArrayList<Cell>> fillInTimeRanges(ArrayList<ArrayList<Cell>> lcSequences, TimeRange[] timeRangeA, TimeRange[] timeRangeB) {
		ArrayList<ArrayList<Cell>> timeAnnotatedLCSequences = new ArrayList<ArrayList<Cell>>(lcSequences);

		for (ArrayList<Cell> LcSequence : timeAnnotatedLCSequences) {
			for (int index = 1; index<=LcSequence.size()-2; index++) {
				//LcSequence.get(index).;
			}
		}

		return timeAnnotatedLCSequences;
	}

	public static int calcRangeOverlap(int rangeAStart, int rangeAEnd, int rangeBStart, int rangeBEnd){
		assert (rangeAStart <= rangeAEnd && rangeBStart <=rangeBEnd);
		// make sure that range A starts before B
		if (rangeAStart > rangeBStart) {
			// swap starts
			int temp = rangeAStart;
			rangeAStart =  rangeBStart;
			rangeBStart = temp;

			// swap ends
			temp = rangeAEnd;
			rangeAEnd =  rangeBEnd;
			rangeBEnd = temp;
		}
		System.out.println("A: "+rangeAStart+"-"+rangeAEnd);
		System.out.println("B: "+rangeBStart+"-"+rangeBEnd);

		// After swap range A starts before range B or equal
		if (rangeBEnd <= rangeAEnd) {
			// range B is contained in A
			return rangeBEnd-rangeBStart;
		} else {
			if (rangeAEnd < rangeBStart) {
				// strictly disjoint
				return 0;
			} else{
				// overlap between endA and startB
				return rangeAEnd-rangeBStart;
			}
		}
	}

	public static double calcRangeOverlapFactor(int rangeAStart, int rangeAEnd, int rangeBStart, int rangeBEnd){
		assert (rangeAStart <= rangeAEnd && rangeBStart <=rangeBEnd);
		// make sure that range A starts before B
		if (rangeAStart > rangeBStart) {
			// swap starts
			int temp = rangeAStart;
			rangeAStart =  rangeBStart;
			rangeBStart = temp;

			// swap ends
			temp = rangeAEnd;
			rangeAEnd =  rangeBEnd;
			rangeBEnd = temp;
		}
		//		System.out.println("A: "+rangeAStart+"-"+rangeAEnd);
		//		System.out.println("B: "+rangeBStart+"-"+rangeBEnd);

		// After swap range A starts before range B or equal
		if (rangeBEnd <= rangeAEnd) {
			// Range A has zero length [x,x]
			if ((rangeAEnd-rangeAStart) == 0) {
				return 0;
			}
			// range B is contained in A
			return (double)(rangeBEnd-rangeBStart)/(double)(rangeAEnd-rangeAStart);
		} else {
			if (rangeAEnd < rangeBStart) {
				// strictly disjoint
				return 0;
			} else{
				// overlap between endA and startB
				return (double)(rangeAEnd-rangeBStart)/(rangeBEnd-rangeAStart);
			}
		}
	}


	public static Set<ArrayList<Cell>> purgeNonMaximumLengthPaths(List<ArrayList<Cell>> subSequences) {
		Set<ArrayList<Cell>> maximumLengthSequences = new HashSet<ArrayList<Cell>>(subSequences);

		int maxLength = 0;

		for (ArrayList<Cell> sequence : subSequences) {
			if (sequence.size()> maxLength) {
				maxLength = sequence.size();
			}else if (sequence.size()< maxLength) {
				maximumLengthSequences.remove(sequence);
			}
		}

		return maximumLengthSequences;
	}


//	public static double calculateAverageOverlapFactorForSingleLCS(ArrayList<Cell> longestCommonSequence, int[] inputASequence, int[] inputBSequence, TimeRange[] TimeRangesForA, TimeRange[] TimeRangesForB) {
	public static double calculateAverageOverlapFactorForSingleLCS(ArrayList<Cell> longestCommonSequence, int[] inputASequence, int[] inputBSequence) {

		double numberOfLinksForLCS = longestCommonSequence.size()-1;
		if (numberOfLinksForLCS==0){
			return 0;
		}
		double overlapfactorSum = 0;
		for (Cell cell : longestCommonSequence) {
//			TimeRange TimeRangeA = TimeRangesForA[cell.inputAIndex];
//			TimeRange TimeRangeB = TimeRangesForB[cell.inputBIndex];
			TimeRange TimeRangeA = cell.timeRangeForUserA;
			TimeRange TimeRangeB = cell.timeRangeForUserB;

			// If either time range is null, the overlap is zero and not added
			if (TimeRangeA != null && TimeRangeB != null ) {
				overlapfactorSum += calcRangeOverlapFactor(TimeRangeA.getStartTime(), TimeRangeA.getEndTime(), TimeRangeB.getStartTime(), TimeRangeB.getEndTime());
			}
		}

		return overlapfactorSum /numberOfLinksForLCS;
	}

//	public static double calculateAverageOverLapFactorForLCSList(Set<ArrayList<Cell>> longestCommonSequences, int[] inputASequence, int[] inputBSequence, TimeRange[] TimeRangesForA, TimeRange[] TimeRangesForB) {
	public static double calculateAverageOverLapFactorForLCSList(Set<ArrayList<Cell>> longestCommonSequences, int[] inputASequence, int[] inputBSequence) {
		double overlapfactorSum = 0;
		for (ArrayList<Cell> lcSequence : longestCommonSequences) {	
			//lcSequence with a SINGLE ELEMENT has no time overlap but it has an OVERLAP FACTOR of 1.
			//if both inpt sequences are identical AND neither one has a time range (all timeRange elements are null)
			TimeRange[] TimeRangesForA = extractTimeRangesForUserAFromCells(lcSequence);
			TimeRange[] TimeRangesForB = extractTimeRangesForUserBFromCells(lcSequence);
			if (lcSequence.size()==1 || (Arrays.equals(inputASequence, inputBSequence) && isTimeRangeEmpty(TimeRangesForA) && isTimeRangeEmpty(TimeRangesForB)) ) {
				
				overlapfactorSum+=1d;
				
			}else {
//				overlapfactorSum += calculateAverageOverlapFactorForSingleLCS(lcSequence, inputASequence, inputBSequence, TimeRangesForA, TimeRangesForB);
				overlapfactorSum += calculateAverageOverlapFactorForSingleLCS(lcSequence, inputASequence, inputBSequence);
			}
		}

		return overlapfactorSum / (double) longestCommonSequences.size();
	}

//	public static double calculatePatternSimularity(Set<ArrayList<Cell>> longestCommonSequences, int[] inputASequence, int[] inputBSequence, TimeRange[] TimeRangesForA, TimeRange[] TimeRangesForB) {
	public static double calculatePatternSimularity(Set<ArrayList<Cell>> longestCommonSequences, int[] inputASequence, int[] inputBSequence){
		if (longestCommonSequences.isEmpty() || inputASequence.length ==0 || inputBSequence.length == 0) {
			return 0;
		}
		double lengthOfLCS =  longestCommonSequences.iterator().next().size();

		double firstOperand = (2d * lengthOfLCS ) / (inputASequence.length + inputBSequence.length);

		double secondOperand =  calculateAverageOverLapFactorForLCSList(longestCommonSequences, inputASequence, inputBSequence);

		return firstOperand * secondOperand;
	}

	public static double calculatePatternSimularityWithoutTimeOverlap(double lengthOfLCS, int[] inputASequence, int[] inputBSequence) {
		if ( inputASequence.length ==0 || inputBSequence.length == 0) {
			return 0d;
		}

		return (2d * lengthOfLCS ) / (inputASequence.length + inputBSequence.length);
	}
	
	public static double calculatePatternSimularityWithoutTimeOverlapYing(int[] inputASequence, int[] inputBSequence, HashMap<String, double[]> distributionOfRegions) {
		if ( inputASequence.length ==0 || inputBSequence.length == 0) {
			return 0d;
		}
		
		Cell[][] table = computeTableLSYing(inputASequence, inputBSequence, distributionOfRegions);
		return ((table[inputASequence.length][inputBSequence.length].ratioA / inputASequence.length) + (table[inputASequence.length][inputBSequence.length].ratioB / inputBSequence.length)) / 2.0;		
	}

	public static Set<ArrayList<Cell>> calculateLongestCommonSequences (int[] inputASequence, int[] inputBSequence, TimeRange[] inputATransitionTimes, TimeRange[] inputBTransitionTimes){
		// table with the lcs lengths stored in cells, ready for backtracking
		Cell[][] table = computeTable(inputASequence, inputBSequence);
//		for (int index = 0; index <inputATransitionTimes.length;index++) {TODO: Check if necessary since it might make detection of no timeranges more complicated
//			if (inputATransitionTimes[index] == null) {
//				inputATransitionTimes[index] = new TimeRange(0,0);
//			}
//		}
//
//		for (int index = 0; index <inputBTransitionTimes.length;index++) {
//			if (inputBTransitionTimes[index] == null) {
//				inputBTransitionTimes[index] = new TimeRange(0,0);
//			}
//		}
		ArrayList<ArrayList<Cell>> lCSequences = backtrackAll(table, inputASequence, inputBSequence, inputASequence.length, inputBSequence.length, inputATransitionTimes, inputBTransitionTimes);
		return purgeNonMaximumLengthPaths(lCSequences);
	}

	public static int calculateLCSlength(int[] inputASequence, int[] inputBSequence){
		// table with the lcs lengths stored in cells, ready for backtracking
		Cell[][] table = computeTable(inputASequence, inputBSequence);
		if (table!=null && table[0] != null) {
			return table[inputASequence.length][inputBSequence.length].seqLength;
		}
		return 0;
	}
	
	
	//the compuatation of LCS length with Location semantics
	
	public static int calculateLCSlengthLS(int[] inputASequence, int[] inputBSequence,double distance,  HashMap<String, double[]> distributionOfRegions) throws Exception{
		// table with the lcs lengths stored in cells, ready for backtracking
		Cell[][] table = computeTableLS(inputASequence, inputBSequence, distance, distributionOfRegions);
		if (table!=null && table[0] != null) {
			return table[inputASequence.length][inputBSequence.length].seqLength;
		}
		return 0;
	}

	public static double calculateWeightForInputSequencePair(double supportForInputSequenceA, double supportForInputSequenceB) {
		return (supportForInputSequenceA +supportForInputSequenceB ) /2d;
	}

	/**
	 * Version 1, averages the all similarity scores (taking the timeoverlap into account) between two sequences. 
	 * For every sequence of userU find the sequence with highest similarity score and average all found similarityScores.
	 * @param userU
	 * @param userV
	 * @return the similarity between the 2 users.
	 */
	public static double calculateSimularityBetweenTwoUsersVersion1(User userU, User userV) {
		double weightSum = 0;
		double weightedSum = 0;
		for (int indexUserU =0; indexUserU< userU.inputSequences.size();indexUserU++) {
			for (int indexUserV =0; indexUserV< userV.inputSequences.size();indexUserV++) {
				Set<ArrayList<Cell>> longestCommonSequences = calculateLongestCommonSequences(userU.inputSequences.get(indexUserU), 
							userV.inputSequences.get(indexUserV), userU.inputSequenceTransitionTimes.get(indexUserU), 
							userV.inputSequenceTransitionTimes.get(indexUserV));
				double weigth = calculateWeightForInputSequencePair(userU.supportValuesForInputSequences.get(indexUserU), 
						userV.supportValuesForInputSequences.get(indexUserV));
				weightedSum += weigth*
						calculatePatternSimularity(longestCommonSequences, userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV));
				weightSum +=weigth;

			}
		}

		return weightedSum / weightSum;
	}

	/**
	 * Version 2, averages the all similarity scores (disregarding the timeoverlap ) between two sequences. 
	 * For every sequence of userU find the sequence with highest similarity score and average all found similarityScores.
	 * @param userU
	 * @param userV
	 * @return the similarity between the 2 users.
	 */
	public static double calculateSimularityBetweenTwoUsersVersion2(User userU, User userV) {
		double weightSum = 0;
		double weightedSum = 0;
		for (int indexUserU =0; indexUserU< userU.inputSequences.size();indexUserU++) {
			for (int indexUserV =0; indexUserV< userV.inputSequences.size();indexUserV++) {
				double lengthOfLCS = calculateLCSlength(userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV));
				double weigth = calculateWeightForInputSequencePair(userU.supportValuesForInputSequences.get(indexUserU), userV.supportValuesForInputSequences.get(indexUserV));
				weightedSum += weigth *
						calculatePatternSimularityWithoutTimeOverlap(lengthOfLCS, userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV));
				weightSum +=weigth;

			}
		}

		return weightedSum / weightSum;
	}
	
	public static double calculateSimularityBetweenTwoUsersVersionYing(User userU, User userV, HashMap<String, double[]> distributionOfRegions) {
		double weightSum = 0;
		double weightedSum = 0;
		for (int indexUserU =0; indexUserU< userU.inputSequences.size();indexUserU++) {
			for (int indexUserV =0; indexUserV< userV.inputSequences.size();indexUserV++) {
				//double lengthOfLCS = calculateLCSlength(userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV));
				double weigth = calculateWeightForInputSequencePair(userU.supportValuesForInputSequences.get(indexUserU), userV.supportValuesForInputSequences.get(indexUserV));
				weightedSum += weigth *
						calculatePatternSimularityWithoutTimeOverlapYing(userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV), distributionOfRegions);
				weightSum +=weigth;

			}
		}

		return weightedSum / weightSum;
	}

	/**
	 * Version 3, only averages the maximum similarity scores (taking the timeoverlap into account) between two sequences. 
	 * For every sequence of userU find the sequence with highest similarity score and average all found similarityScores.
	 * @param userU
	 * @param userV
	 * @return the similarity between the 2 users.
	 */
	public static double calculateSimularityBetweenTwoUsersVersion3(User userU, User userV) {
		double similarityUserUAndUserV = LongestCommonSubsequenceForCells.calculateSimularityBetweenTwoUsersHelperVersion3(userU, userV);
		double similarityUserVAndUserU = LongestCommonSubsequenceForCells.calculateSimularityBetweenTwoUsersHelperVersion3(userV, userU);

		return (similarityUserUAndUserV + similarityUserVAndUserU)/2d;
	}
	
	private static double calculateSimularityBetweenTwoUsersHelperVersion3(User userU, User userV) {		
		double weightSum = 0;
		double weightedSum = 0;
		for (int indexUserU =0; indexUserU< userU.inputSequences.size();indexUserU++) {
			// 0th iteration of the following loop
			double weight = calculateWeightForInputSequencePair(userU.supportValuesForInputSequences.get(indexUserU), userV.supportValuesForInputSequences.get(0));	
			Set<ArrayList<Cell>> longestCommonSequences = calculateLongestCommonSequences(userU.inputSequences.get(indexUserU), userV.inputSequences.get(0), userU.inputSequenceTransitionTimes.get(indexUserU), userV.inputSequenceTransitionTimes.get(0));			
//			double userSimilarity = calculatePatternSimularity(longestCommonSequences, userU.inputSequences.get(indexUserU), userV.inputSequences.get(0),userU.inputSequenceTransitionTimes.get(indexUserU), userV.inputSequenceTransitionTimes.get(0));
			double userSimilarity = calculatePatternSimularity(longestCommonSequences, userU.inputSequences.get(indexUserU), userV.inputSequences.get(0));// longestCommonSequences., userV.inputSequenceTransitionTimes.get(0));

			double maxSimilarityValue = userSimilarity;
			double maxSimilarityWeight = weight;//(userSimilarity>0) ? weight : 0.0d;//TODO: check if correct

			for (int indexUserV =1; indexUserV< userV.inputSequences.size();indexUserV++) {
				weight = calculateWeightForInputSequencePair(userU.supportValuesForInputSequences.get(indexUserU), userV.supportValuesForInputSequences.get(indexUserV));
				longestCommonSequences = calculateLongestCommonSequences(userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV), userU.inputSequenceTransitionTimes.get(indexUserU), userV.inputSequenceTransitionTimes.get(indexUserV));
//				userSimilarity = calculatePatternSimularity(longestCommonSequences, userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV),userU.inputSequenceTransitionTimes.get(indexUserU), userV.inputSequenceTransitionTimes.get(indexUserV));
				userSimilarity = calculatePatternSimularity(longestCommonSequences, userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV));

				if ( (userSimilarity > maxSimilarityValue) ) {
					maxSimilarityValue = userSimilarity;
					maxSimilarityWeight = weight;
				}
			}
			weightedSum += maxSimilarityValue * maxSimilarityWeight;
			weightSum +=maxSimilarityWeight;// divide by the sum of all weights
		}
		if (weightSum==0.0d || weightedSum==0.0d) {
			return 0;
		}
		return weightedSum / weightSum;
	}

	/**
	 * Version 4, only averages the maximum similarity scores (calculate disregarding the timeoverlap) between two sequences. 
	 * For every sequence of userU find the sequence with highest similarity score and average all found similarityScores.
	 * @param userU
	 * @param userV
	 * @return the similarity between the 2 users.
	 */
	public static double calculateSimularityBetweenTwoUsersVersion4(User userU, User userV) {
		double similarityUserUAndUserV = LongestCommonSubsequenceForCells.calculateSimularityBetweenTwoUsersHelperVersion4(userU, userV);
		double similarityUserVAndUserU = LongestCommonSubsequenceForCells.calculateSimularityBetweenTwoUsersHelperVersion4(userV, userU);

		return (similarityUserUAndUserV + similarityUserVAndUserU)/2;
	}
	
	private static double calculateSimularityBetweenTwoUsersHelperVersion4(User userU, User userV) {
		double weightSum = 0;
		double weightedSum = 0;
		double weight = 0;
		for (int indexUserU =0; indexUserU< userU.inputSequences.size();indexUserU++) {
			// 0th iteration of the following loop
						
			double lengthOfLCS = 0;
			double userSimilarity = 0;

			double maxSimilarity = 0;

			for (int indexUserV =0; indexUserV< userV.inputSequences.size();indexUserV++) {
				
				lengthOfLCS = calculateLCSlength(userU.inputSequences.get(indexUserU), 
						userV.inputSequences.get(indexUserV));
				userSimilarity = calculatePatternSimularityWithoutTimeOverlap(lengthOfLCS, userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV));

				if ( (userSimilarity > maxSimilarity) ) {
					maxSimilarity = userSimilarity;
					weight = calculateWeightForInputSequencePair(userU.supportValuesForInputSequences.get(indexUserU), 
							userV.supportValuesForInputSequences.get(indexUserV));	
				}
			}
			weightedSum += maxSimilarity * weight;
			weightSum += weight;
		}
		if (weightSum==0.0d || weightedSum==0.0d) {
			return 0;
		}
		return weightedSum / weightSum;
	}

	/*private static double calculateSimularityBetweenTwoUsersHelperVersion4(User userU, User userV) {		
		double weightSum = 0;
		double weightedSum = 0;
		for (int indexUserU =0; indexUserU< userU.inputSequences.size();indexUserU++) {
			// 0th iteration of the following loop
			double weight = calculateWeightForInputSequencePair(userU.supportValuesForInputSequences.get(indexUserU), userV.supportValuesForInputSequences.get(0));	
			double lengthOfLCS = calculateLCSlength(userU.inputSequences.get(indexUserU), userV.inputSequences.get(0));
			double userSimilarity = calculatePatternSimularityWithoutTimeOverlap(lengthOfLCS, userU.inputSequences.get(indexUserU), userV.inputSequences.get(0));


			double maxSimilarityValue = userSimilarity;
			double maxSimilarityWeight = weight;//(userSimilarity>0) ? weight : 0.0d;//TODO: check if correct

			for (int indexUserV =1; indexUserV< userV.inputSequences.size();indexUserV++) {
				//weight = calculateWeightForInputSequencePair(userU.supportValuesForInputSequences.get(indexUserU), userV.supportValuesForInputSequences.get(indexUserV));
				lengthOfLCS = calculateLCSlength(userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV));
				userSimilarity = calculatePatternSimularityWithoutTimeOverlap(lengthOfLCS, userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV));

				if ( (userSimilarity > maxSimilarityValue) ) {
					maxSimilarityValue = userSimilarity;
					maxSimilarityWeight = calculateWeightForInputSequencePair(userU.supportValuesForInputSequences.get(indexUserU), userV.supportValuesForInputSequences.get(indexUserV));;
				}
			}
			weightedSum += maxSimilarityValue * maxSimilarityWeight;
			weightSum +=maxSimilarityWeight;
		}
		if (weightSum==0.0d || weightedSum==0.0d) {
			return 0;
		}
		return weightedSum / weightSum;
	}*/
	
	/* 
	 * time overlapping is not considered
	 * support values are not considered during comparsion between patterns.
	 */
	public static double calculateSimularityBetweenTwoUsersVersion5(User userU, User userV) {
		double simUV = LongestCommonSubsequenceForCells.similarity2users(userU, userV);
		double simVU = LongestCommonSubsequenceForCells.similarity2users(userV, userU);

		return (simUV + simVU)/2;
	}

	private static double similarity2users(User userU, User userV) {
		double weightSum = 0;
		double weightedSum = 0;
		double weight = 0;
		double maxSimilarity = 0;
		for (int indexUserU =0; indexUserU< userU.inputSequences.size();indexUserU++) {
			// 0th iteration of the following loop
			int lengthOfLCS = 0;
			double userSimilarity;
			maxSimilarity = 0;
			for (int indexUserV = 0; indexUserV< userV.inputSequences.size();indexUserV++) {
				lengthOfLCS = calculateLCSlength(userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV));
				userSimilarity = calculatePatternSimularityWithoutTimeOverlap(lengthOfLCS, userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV));

				if ( (userSimilarity >= maxSimilarity) ) {
					maxSimilarity = userSimilarity;
					weight =calculateWeightForInputSequencePair(userU.supportValuesForInputSequences.get(indexUserU), 
							userV.supportValuesForInputSequences.get(indexUserV));
				}
			}
			weightedSum += maxSimilarity * weight;
			weightSum += weight;
		}
		if (weightSum==0.0d || weightedSum==0.0d) {
			return 0;
		}
		return weightedSum / weightSum;
	}
	
	public static double calculateSimularityBetweenTwoUsersVersion5Ying(User userU, User userV, HashMap<String, double[]> distributionOfRegions) {
		double simUV = LongestCommonSubsequenceForCells.similarity2usersYing(userU, userV, distributionOfRegions);
		double simVU = LongestCommonSubsequenceForCells.similarity2usersYing(userV, userU, distributionOfRegions);

		return (simUV + simVU)/2;
	}

	private static double similarity2usersYing(User userU, User userV, HashMap<String, double[]> distributionOfRegions) {
		double weightSum = 0;
		double weightedSum = 0;
		double weight = 0;
		double maxSimilarity = 0;
		for (int indexUserU =0; indexUserU< userU.inputSequences.size();indexUserU++) {
			// 0th iteration of the following loop
			int lengthOfLCS = 0;
			double userSimilarity;
			maxSimilarity = 0;
			for (int indexUserV = 0; indexUserV< userV.inputSequences.size();indexUserV++) {
				//lengthOfLCS = calculateLCSlength(userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV));
				userSimilarity = calculatePatternSimularityWithoutTimeOverlapYing(userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV), distributionOfRegions);

				if ( (userSimilarity >= maxSimilarity) ) {
					maxSimilarity = userSimilarity;
					weight =calculateWeightForInputSequencePair(userU.supportValuesForInputSequences.get(indexUserU), 
							userV.supportValuesForInputSequences.get(indexUserV));
				}
			}
			weightedSum += maxSimilarity * weight;
			weightSum += weight;
		}
		if (weightSum==0.0d || weightedSum==0.0d) {
			return 0;
		}
		return weightedSum / weightSum;
	}
	

	
	/* 
	 * time overlapping is not considered
	 * location semantics is considered
	 */
	

	
	public static double userSimilarityWithLocationSemantics(User userU, User userV, double distance, HashMap<String, double[]> distributionOfRegions) throws Exception {
		
		double simUV = LongestCommonSubsequenceForCells.similarity2usersLS(userU, userV, distance, distributionOfRegions);
		double simVU = LongestCommonSubsequenceForCells.similarity2usersLS(userV, userU, distance, distributionOfRegions);

		return (simUV + simVU)/2;
	}

	private static double similarity2usersLS(User userU, User userV, double distance, HashMap<String, double[]> distributionOfRegions) throws Exception {
		double weightSum = 0;
		double weightedSum = 0;
		double weight = 0;
		
		for (int indexUserU =0; indexUserU< userU.inputSequences.size();indexUserU++) {
			
			double lengthOfLCS = 0; 
			double userSimilarity = 0;

			double maxSimilarity = 0;

			for (int indexUserV = 0; indexUserV< userV.inputSequences.size();indexUserV++) {
				lengthOfLCS = calculateLCSlengthLS(userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV), distance, distributionOfRegions);
				userSimilarity = calculatePatternSimularityWithoutTimeOverlap(lengthOfLCS, userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV));

				if ( (userSimilarity > maxSimilarity) ) {
					maxSimilarity = userSimilarity;
					weight =calculateWeightForInputSequencePair(userU.supportValuesForInputSequences.get(indexUserU), 
							userV.supportValuesForInputSequences.get(indexUserV));
				}
			}
			weightedSum += maxSimilarity * weight;
			weightSum += weight;
		}
		if (weightSum==0.0d || weightedSum==0.0d) {
			return 0;
		}
		return weightedSum / weightSum;
	}
	
	
/*public static double userSimilarityWithLocationSemanticsAndTime(User userU, User userV, double distance, HashMap<String, double[]> distributionOfRegions) throws Exception {
		
		double simUV = LongestCommonSubsequenceForCells.similarity2usersLS(userU, userV, distance, distributionOfRegions);
		double simVU = LongestCommonSubsequenceForCells.similarity2usersLS(userV, userU, distance, distributionOfRegions);

		return (simUV + simVU)/2;
	}

	private static double similarity2usersLSAndTime(User userU, User userV, double distance, HashMap<String, double[]> distributionOfRegions) throws Exception {
		double weightSum = 0;
		double weightedSum = 0;
		double weight = 0;
		
		for (int indexUserU =0; indexUserU< userU.inputSequences.size();indexUserU++) {			
			double lengthOfLCS = 0; 
			double userSimilarity = 0;
			double maxSimilarity = 0;

			for (int indexUserV = 0; indexUserV< userV.inputSequences.size();indexUserV++) {
				lengthOfLCS = calculateLCSlengthLS(userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV), distance, distributionOfRegions);
				Set<ArrayList<Cell>> longestCommonSequences = calculateLongestCommonSequences(userU.inputSequences.get(indexUserU), userV.inputSequences.get(0), userU.inputSequenceTransitionTimes.get(indexUserU), userV.inputSequenceTransitionTimes.get(0));
				userSimilarity = calculatePatternSimularity(longestCommonSequences, userU.inputSequences.get(indexUserU), userV.inputSequences.get(indexUserV));

				if ( (userSimilarity > maxSimilarity) ) {
					maxSimilarity = userSimilarity;
					weight =calculateWeightForInputSequencePair(userU.supportValuesForInputSequences.get(indexUserU), 
							userV.supportValuesForInputSequences.get(indexUserV));
				}
			}
			weightedSum += maxSimilarity * weight;
			weightSum += weight;
		}
		if (weightSum==0.0d || weightedSum==0.0d) {
			return 0;
		}
		return weightedSum / weightSum;
	}*/
	
	
	
	// If a timeRange has only null or (0,0) elements then it is considered empty
	private static boolean isTimeRangeEmpty(TimeRange[] timeRange) {
		for (TimeRange tr : timeRange) {
			if (tr!= null && tr.getStartTime()!=0 && tr.getEndTime()!=0) {
				return false;
			}
		}
		return true;
	}
	
	private static TimeRange[] extractTimeRangesForUserAFromCells(ArrayList<Cell> cells) {
		TimeRange[] timeRanges = new TimeRange[cells.size()];
		int index = 0;
		for (Cell cell : cells) {
			timeRanges[index++] = cell.timeRangeForUserB;
		}
		
		return timeRanges;
	}
	
	private static TimeRange[] extractTimeRangesForUserBFromCells(ArrayList<Cell> cells) {
		TimeRange[] timeRanges = new TimeRange[cells.size()];
		int index = 0;
		for (Cell cell : cells) {
			timeRanges[index++] = cell.timeRangeForUserB;
		}
		
		return timeRanges;
	}
}