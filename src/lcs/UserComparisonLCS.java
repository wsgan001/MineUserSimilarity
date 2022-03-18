package lcs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class UserComparisonLCS {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public double[][] compareUsers(/*String pathnameOfInputDirectory, */String outputPathnameOfDataset, String pathnameOfDistributionFile, List<String> selectedUsers/*, JTextArea textArea*/, boolean time, boolean semantics, double threshold, String dirsPath, boolean improved) throws Exception {		
		String outputFileName = null;
		CopyOnWriteArraySet<UserCombination> filesProcessed = new CopyOnWriteArraySet<UserCombination>();
		CopyOnWriteArraySet<UserCombination> userCombinations = new CopyOnWriteArraySet<UserCombination>();
		
		File[] files = new File[selectedUsers.size()];
		for(int i = 0; i < selectedUsers.size(); i++) {
			files[i] = new File(outputPathnameOfDataset + "/PatternSets/" + dirsPath + "/" + selectedUsers.get(i) + "MiSTA.output");
		}
		ArrayList<User> users = new ArrayList<User>();
		for(File aFile : files) {				
			List<String> lines = CommonFileOperations.getTextFileAsLines(aFile);
			List<SequenceData> sequences = extractSequences(lines);
			sequences = purgeSequences(sequences);
			users.add(fillUserDataFromSequenceDataList(sequences, aFile.getName()));
		}

		if(improved == true) {
			if (time == false && semantics == false) {
				/*textArea.append("Comparing users using the \"LCS improved\" method...\n");*/
				for (User userU : users) {
					for (User userV : users) {
						/*if(userU == userV)
							continue;*/
						UserCombination aUserCombination = new UserCombination(userU, userV);
						if (filesProcessed.contains(aUserCombination)) {
							continue;
						}
						filesProcessed.add(aUserCombination);
						userCombinations.add(aUserCombination);

						// *************** Determine similarity between User A & B *************************
						aUserCombination.Similarity = LongestCommonSubsequenceForCells.calculateSimularityBetweenTwoUsersVersion5(userU, userV);				
					}
				}
				outputFileName = "LCSImprovedWithoutSemanticsOrTime.csv";
			}

			if (time == true && semantics == false) {
				/*textArea.append("Comparing users using the \"LCS improved with time\" method...\n");*/
				for (User userU : users) {
					for (User userV : users) {
						UserCombination aUserCombination = new UserCombination(userU, userV);
						if (filesProcessed.contains(aUserCombination)) {
							continue;
						}
						filesProcessed.add(aUserCombination);
						userCombinations.add(aUserCombination);

						// *************** Determine similarity between User A & B *************************
						aUserCombination.Similarity = LongestCommonSubsequenceForCells.calculateSimularityBetweenTwoUsersVersion3(userU, userV);				
					}
				}
				outputFileName = "LCSImprovedWithoutSemanticsWithTime.csv";
			}

			if (time == false && semantics == true) {
				//textArea.append("Comparing users using the \"CPS\" method...\n");				
				HashMap<String, double[]> distributionOfRegions = readDistributionOfRegions(pathnameOfDistributionFile);
				for (User userU : users) {
					for (User userV : users) {
						/*if (userU == userV) {
							break;
						}*/
						UserCombination aUserCombination = new UserCombination(userU, userV);
						if (filesProcessed.contains(aUserCombination)) {
							continue;
						}
						filesProcessed.add(aUserCombination);
						userCombinations.add(aUserCombination);

						// *************** Determine similarity between User A & B *************************
						aUserCombination.Similarity = LongestCommonSubsequenceForCells.userSimilarityWithLocationSemantics(userU, userV, threshold, distributionOfRegions);				
					}
				}
				outputFileName = "LCSImprovedWithSemanticsWithoutTime_" + threshold + ".csv";
			}
		} else {
			if (time == false && semantics == false) {
				/*textArea.append("Comparing users using the \"LCS improved\" method...\n");*/
				//pathnameOfDistributionFile = "C:\\Documents and Settings\\Ruipeng Lu\\My Documents\\workspace\\data\\OutputYonsei\\Dist\\001_002_003_004_007_009_081_082_121_122-30_115_200.txt\\N.txt";
				//HashMap<String, double[]> distributionOfRegions = readDistributionOfRegions2(pathnameOfDistributionFile);
				for (User userU : users) {
					for (User userV : users) {
						/*if(userU == userV)
							continue;*/
						UserCombination aUserCombination = new UserCombination(userU, userV);
						if (filesProcessed.contains(aUserCombination)) {
							continue;
						}
						filesProcessed.add(aUserCombination);
						userCombinations.add(aUserCombination);

						// *************** Determine similarity between User A & B *************************
						aUserCombination.Similarity = LongestCommonSubsequenceForCells.calculateSimularityBetweenTwoUsersVersion2(userU, userV);			
						//aUserCombination.Similarity = LongestCommonSubsequenceForCells.calculateSimularityBetweenTwoUsersVersion5Ying(userU, userV, distributionOfRegions);
					}
				}
				outputFileName = "LCSWithoutSemanticsOrTime.csv";
			}
			
			if (time == true && semantics == false) {
				/*textArea.append("Comparing users using the \"LCS improved with time\" method...\n");*/
				//pathnameOfDistributionFile = "C:\\Documents and Settings\\Ruipeng Lu\\My Documents\\workspace\\data\\OutputYonsei\\Dist\\001_002_003_004_007_009_081_082_121_122-30_115_200.txt\\N.txt";
				//HashMap<String, double[]> distributionOfRegions = readDistributionOfRegions2(pathnameOfDistributionFile);
				for (User userU : users) {
					for (User userV : users) {
						UserCombination aUserCombination = new UserCombination(userU, userV);
						if (filesProcessed.contains(aUserCombination)) {
							continue;
						}
						filesProcessed.add(aUserCombination);
						userCombinations.add(aUserCombination);

						// *************** Determine similarity between User A & B *************************
						aUserCombination.Similarity = LongestCommonSubsequenceForCells.calculateSimularityBetweenTwoUsersVersion1(userU, userV);				
						//aUserCombination.Similarity = LongestCommonSubsequenceForCells.calculateSimularityBetweenTwoUsersVersionYing(userU, userV, distributionOfRegions);
					}
				}
				outputFileName = "LCSWithoutSemanticsWithTime.csv";
			}
			
			if (time == false && semantics == true) {
				/*textArea.append("Comparing users using the \"LCS improved\" method...\n");*/
				//pathnameOfDistributionFile = "C:\\Documents and Settings\\Ruipeng Lu\\My Documents\\workspace\\data\\OutputYonsei\\Dist\\001_002_003_004_007_009_081_082_121_122-30_115_200.txt\\N.txt";
				HashMap<String, double[]> distributionOfRegions = readDistributionOfRegions2(pathnameOfDistributionFile, threshold);
				for (User userU : users) {
					for (User userV : users) {
						/*if(userU == userV)
							continue;*/
						UserCombination aUserCombination = new UserCombination(userU, userV);
						if (filesProcessed.contains(aUserCombination)) {
							continue;
						}
						filesProcessed.add(aUserCombination);
						userCombinations.add(aUserCombination);

						// *************** Determine similarity between User A & B *************************
						//aUserCombination.Similarity = LongestCommonSubsequenceForCells.calculateSimularityBetweenTwoUsersVersion2(userU, userV);			
						aUserCombination.Similarity = LongestCommonSubsequenceForCells.calculateSimularityBetweenTwoUsersVersion5Ying(userU, userV, distributionOfRegions);
					}
				}
				outputFileName = "LCSWithSemanticsWithoutTime_" + threshold + ".csv";
			}
		}
		
		//System.out.println(processedFiles);
		StringBuilder sb = new StringBuilder(",");
		Set<String> printedUsers = new HashSet<String>();
		
		for (UserCombination uc : userCombinations) {
			printedUsers.add(uc.User1);
			printedUsers.add(uc.User2);
		}
		
		List<String> sortedUsers = new ArrayList<String>(printedUsers);
		Collections.sort(sortedUsers);
		
		for (String user : sortedUsers) {
			sb.append(user);
			sb.append(",");
		} 		
		
		int numberOfDifferentUsers = printedUsers.size();// number of different users
		double similarityMatrix[][] = new double[numberOfDifferentUsers][numberOfDifferentUsers];

		// create matrix for easy printing
		for(int x=0;x<numberOfDifferentUsers;x++) {
			for(int y=0;y<numberOfDifferentUsers;y++) {
				similarityMatrix[x][y] =0d;
			}
		}
		
		for (UserCombination uc : userCombinations) {
			int xIndex = sortedUsers.indexOf(uc.User1);
			int yIndex = sortedUsers.indexOf(uc.User2);
						
			similarityMatrix[xIndex][yIndex] = uc.Similarity;
			similarityMatrix[yIndex][xIndex] = uc.Similarity;
		}
		// print matrix
		int yIndex = 0;
		for(int x=0;x<numberOfDifferentUsers;x++) {
			sb.append("\n");
			sb.append( sortedUsers.get(yIndex++));
			sb.append(",");
			for(int y=0;y<numberOfDifferentUsers;y++) {
				sb.append(similarityMatrix[x][y]);
				sb.append(",");
			}
		}
		
		StringBuilder sb2 = new StringBuilder();
		int numberUsers = selectedUsers.size();
		for(int i = 0; i < numberUsers; i++) {
			if(i != numberUsers - 1) {
				sb2.append(selectedUsers.get(i) + "_");
			} else {
				sb2.append(selectedUsers.get(i));
			}
		}
		CommonFileOperations.writeToFile(/*"\\\\?\\C:/"*/outputPathnameOfDataset + "/ComparisonResults/" + dirsPath + "/" + sb2.toString(), outputFileName, sb.toString());
		return similarityMatrix;
	}
	
	private HashMap<String, double[]> readDistributionOfRegions(String file){
		HashMap<String, double[]> distributionOfRegions =  new HashMap<String, double[]>(); 
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(file)));
			String strLine = null;
			while ((strLine = br.readLine()) != null)   
			{
				String[] f = strLine.split("\t");
				double[] dis = new double[f.length-1];
				for(int i = 1; i< f.length; i++)
					dis[i-1]= Double.parseDouble(f[i]);
				distributionOfRegions.put(f[0], dis);
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return distributionOfRegions;
	}
	
	private HashMap<String, double[]> readDistributionOfRegions2(String file, double threshold){
		HashMap<String, double[]> distributionOfRegions =  new HashMap<String, double[]>(); 
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(file)));
			String strLine = null;
			while ((strLine = br.readLine()) != null)   
			{
				String[] f = strLine.split("\t");
				double[] dis = new double[f.length-1];
				for(int i = 1; i< f.length; i++) {
					dis[i-1]= Double.parseDouble(f[i]);
					//double prob = Double.parseDouble(f[i]);
					if (dis[i-1] <= threshold) {
						dis[i-1] = 0.0;						
					}
				}
				distributionOfRegions.put(f[0], dis);
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return distributionOfRegions;
	}

	private List<SequenceData> extractSequences(List<String> lines) {
		List<SequenceData> sequences = new ArrayList<SequenceData>();
		SequenceData sequence =  null;
		for (String line : lines) {
			if (line.contains(":")) {
				// line contains sequence data
				sequence =  parseSequenceData(line);

				sequences.add(sequence);
			}else if (line.trim().startsWith("[")) {
				// line contains timerange data
				// append to the last saved sequence data object 
				if (sequence != null) {
					// a valid sequence has been stored before (this is the normal case, a time range before sequence data is abnormal)

					int indexOfOpeningBracket = line.indexOf("[");
					int timeRangeIndex  = 0;
					//int currentParenthesIndex = indexOfOpeningBracket;

					while ( indexOfOpeningBracket > -1 ) {
						int indexOfClosingBracket = line.indexOf("]", indexOfOpeningBracket);
						if (indexOfClosingBracket ==-1) {
							System.err.println(line.substring(indexOfOpeningBracket)+ " no closing bracket was found");
							return null;
						}
						String[] timeRangeElements = line.substring(indexOfOpeningBracket, indexOfClosingBracket).split(",");

						int startTime =  Integer.parseInt(timeRangeElements[0].substring(1).trim());
						int endTime =  Integer.parseInt(timeRangeElements[1].trim());
						// element does not exist yet
						if (sequence.getTimeRange().size()<=timeRangeIndex) {
							sequence.getTimeRange().add(new TimeRange(startTime,endTime));
						}

						if (sequence.getTimeRange().get(timeRangeIndex).getStartTime() > startTime) {
							sequence.getTimeRange().get(timeRangeIndex).setStartTime(startTime);
						}

						if (sequence.getTimeRange().get(timeRangeIndex).getEndTime() < endTime){
							sequence.getTimeRange().get(timeRangeIndex).setEndTime(endTime);
						}
						indexOfOpeningBracket = line.indexOf("[", indexOfClosingBracket);
						timeRangeIndex++;
					}
				}
			}else {
				System.err.println(line+" is malformed");
				System.exit(1);
			}
		}
		return sequences;
	}

	private SequenceData parseSequenceData(String lineWithSequenceData) {


		int indexOfFirstColon =  lineWithSequenceData.indexOf(":");
		String stringWithSequencElementsInParentheses = lineWithSequenceData.substring(0, indexOfFirstColon);// from index 0 to char at indexOfColon not included. [0,indexOfColon-1]

		// Extract the sequence
		List<Integer> sequenceElements  = new ArrayList<Integer>();


		String [] sequenceElementStrings = stringWithSequencElementsInParentheses.split("\t");
		for (String elementID : sequenceElementStrings) {
			if (!elementID.trim().isEmpty()) {
				int id = Integer.parseInt( elementID.substring(1, elementID.length()-1) );
				sequenceElements.add(id);
			}
		}		

		// Extract  both support values
		String StringWithSupporValues = lineWithSequenceData.substring(indexOfFirstColon+1);
		String supports[] = StringWithSupporValues.split("\t\t");
		double relativeSupport =  Double.parseDouble(supports[0].trim());
		int absoluteSupport =  Integer.parseInt(supports[1].substring(5, supports[1].length()-1));// excluding the '[' and ']'

		// converts list into int array
		int sequenceIDs[] = new int[ sequenceElements.size()];
		int i = 0;
		for (Integer e : sequenceElements)  {
			sequenceIDs[i++] = e.intValue();
		}

		SequenceData sequence = new SequenceData(sequenceIDs, relativeSupport, absoluteSupport);

		return sequence;
	}

	private List<SequenceData> purgeSequences(List<SequenceData> sequences) {
		Collections.sort(sequences, new Comparator<SequenceData>() {
			@Override
			public int compare(SequenceData o1, SequenceData o2) {
				return  new Integer(o1.getSequence().length).compareTo(o2.getSequence().length);
			}
		});

		int maxSequenceLength = sequences.get(sequences.size()-1).getSequence().length;
		ArrayList<SequenceData> toBeDeletedSequences =  new ArrayList<SequenceData>();

		for ( int index=0; index< sequences.size();index++) {
			for(int innerIndex =index+1;innerIndex<sequences.size();innerIndex++) {

				if ( (sequences.get(index).getSequence().length == sequences.get(innerIndex).getSequence().length) ) {
					continue;// only need to compare to longer sequences 
				} else if (sequences.get(index).getSequence().length == maxSequenceLength){
					break;// done, once the first element (index) of maximum sequence length is reach no more comparisons have to be done.
				}


				int[][] lcsTable = LongestCommonSubsequence.computeSequenceTable(sequences.get(index).getSequence(),sequences.get(innerIndex).getSequence());
				List<Integer> lcs = LongestCommonSubsequence.backtrack(	lcsTable, 
						sequences.get(index).getSequence(),
						sequences.get(innerIndex).getSequence(), 
						sequences.get(index).getSequence().length,
						sequences.get(innerIndex).getSequence().length);
				int[] arLCS = new int[lcs.size()];
				int i = 0;
				for (Integer e : lcs)  {
					arLCS[i++] = e.intValue();
				}
				if (Arrays.equals(arLCS, sequences.get(index).getSequence())) {// should be deleted and move on
					toBeDeletedSequences.add(sequences.get(index));
					break; //move on to next index since 
				}

			}
		}		
		sequences.removeAll(toBeDeletedSequences);
		return sequences;
	}

	/*private void outputSequences(String outpathFolderPath, List<SequenceData> sequences, String fileName ) {
		StringBuilder sb = new StringBuilder("n sequences with 'tabs': relative support 'double tab' absolute support\n");
		for (SequenceData sequenceData : sequences) {
			sb.append(sequenceData.toString());
			sb.append("\n");
		}
		CommonFileOperations.writeToFile(outpathFolderPath, fileName, sb.toString());	
	}*/

	private User fillUserDataFromSequenceDataList(List<SequenceData> sequences, String fileName) {
		User user = new User();
		user.ID = fileName.substring(0, 3);
		ArrayList<int[]> inputSequences= new ArrayList<int[]>();
		ArrayList<TimeRange[]> inputSequenceTransitionTimes= new ArrayList<TimeRange[]>();
		ArrayList<Double> supportValuesForInputSequences= new ArrayList<Double>();

		for (SequenceData data : sequences) {
			inputSequences.add(data.getSequence());
			TimeRange[] timeRanges = new TimeRange[data.getSequence().length];
			timeRanges = data.getTimeRange().toArray(timeRanges);
			inputSequenceTransitionTimes.add(timeRanges);
			supportValuesForInputSequences.add(data.getRelativeSupport());
		}

		user.inputSequences = inputSequences;
		user.inputSequenceTransitionTimes = inputSequenceTransitionTimes;
		user.supportValuesForInputSequences = supportValuesForInputSequences;

		return user;
	}
}

