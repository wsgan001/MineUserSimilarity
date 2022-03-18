package cps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class UserComparisonCPS 
{	
	private static int numberST;
	
	private double calculateSimilarityWithSemantics(File file1, File file2, HashMap<String, Distribution> mapFromRegionToDistribution, boolean ifThresholdOnProb, double thresholdOnProbabilities, /*double numberST, */double threSup) throws Exception
	{
		RoIFrequentPatternSet RoIuser1 = FileOperations.readFile(file1); 
		RoIFrequentPatternSet RoIuser2 = FileOperations.readFile(file2); 
		/*RoIuser1.adjustSupport();
		RoIuser2.adjustSupport();*/
		DistributionFrequentPatternSet distributionUser1 = RoIuser1.convertToDistributionPatternSet(mapFromRegionToDistribution);
		DistributionFrequentPatternSet distributionUser2 = RoIuser2.convertToDistributionPatternSet(mapFromRegionToDistribution);
		
		SemanticTagPatternSet user1ByTagPatterns = distributionUser1.convertToSemanticTagPatternSet(ifThresholdOnProb, thresholdOnProbabilities, threSup);		
		SemanticTagPatternSet user2ByTagPatterns = distributionUser2.convertToSemanticTagPatternSet(ifThresholdOnProb, thresholdOnProbabilities, threSup);		
		SemanticTagPatternSet intersection = user1ByTagPatterns.intersect(user2ByTagPatterns);
		
		double weightOfUser1 = user1ByTagPatterns.functionF();
		double weightOfUser2 = user2ByTagPatterns.functionF();
		double weight1OfIntersection = intersection.functionF(1);
		double weight2OfIntersection = intersection.functionF(2);
		
		double ap = Math.sqrt((weight1OfIntersection/weightOfUser1) * (weight2OfIntersection/weightOfUser2));
		double supSim = intersection.supSim();
		
		return ap * supSim;
	}
	
	private double calculateSimilarity(File file1, File file2) {
		RoIFrequentPatternSet RoIUser1 = FileOperations.readFile(file1); 
		RoIFrequentPatternSet RoIUser2 = FileOperations.readFile(file2); 
		
		/*RoIUser1.adjustSupport();
		RoIUser2.adjustSupport();*/
		RoIFrequentPatternSet intersection = RoIUser1.intersect(RoIUser2);		
		/*intersection.adjustSupport(RoIUser1, RoIUser2);*/
		
		double weight1OfIntersection = intersection.functionF(1);
		double weight2OfIntersection = intersection.functionF(2);
		double weightOfUser1 = RoIUser1.functionF();
		double weightOfUser2 = RoIUser2.functionF();
		
		return Math.sqrt((weight1OfIntersection/weightOfUser1) * (weight2OfIntersection/weightOfUser2)) * intersection.supSim();
	}
	
	public double[][] compareUsers(/*String pathnameOfInputDirectory, */String outputPathnameOfDataset, String pathnameOfDistributionFile, List<String> selectedUsers/*, JTextArea textArea*/, boolean semantics, String dirsPath, boolean ifThresholdOnProb, double thresholdOnProbabilities, double threSup) throws Exception
	{	
		long startTime = System.currentTimeMillis();
		/*File inputDirectory = new File(pathnameOfInputDirectory);*/
		CopyOnWriteArraySet<UserCombination> filesProcessed = new CopyOnWriteArraySet<UserCombination>();		
		File[] allFiles = new File[selectedUsers.size()];
		for(int i = 0; i < selectedUsers.size(); i++) {
			allFiles[i] = new File(outputPathnameOfDataset + "/PatternSets/" + dirsPath + "/" + selectedUsers.get(i) + "MiSTA.output");
		}
		if(semantics) {
			HashMap<String, Distribution> mapFromRegionToDistribution = readDistributionFile(pathnameOfDistributionFile, ifThresholdOnProb, thresholdOnProbabilities);
			for(File aFile : allFiles)
			{
				for(File anotherFile : allFiles)
				{
					/*if(aFile == anotherFile)
						continue;*/
					UserCombination aUserCombination = new UserCombination(aFile, anotherFile);
					if(filesProcessed.contains(aUserCombination))
					{
						continue;
					}
					aUserCombination.Similarity = calculateSimilarityWithSemantics(aFile, anotherFile, mapFromRegionToDistribution, ifThresholdOnProb, thresholdOnProbabilities, /*numberST, */threSup);
					filesProcessed.add(aUserCombination);
				}
			}
		}
		else {			
			for(File aFile : allFiles)
			{
				for(File anotherFile : allFiles)
				{
					/*if(aFile == anotherFile)
						continue;*/
					UserCombination aUserCombination = new UserCombination(aFile, anotherFile);
					if(filesProcessed.contains(aUserCombination))
					{
						continue;
					}
					aUserCombination.Similarity = calculateSimilarity(aFile, anotherFile);
					filesProcessed.add(aUserCombination);
				}
			}
		}
		
		HashSet<String> allUsers = new HashSet<String>();
		for(UserCombination aUserCombination : filesProcessed)
		{
			allUsers.add(aUserCombination.User1);
			allUsers.add(aUserCombination.User2);
		}
		long endTime  = System.currentTimeMillis();
		System.out.println(startTime - endTime);
		
		ArrayList<String> allUsersForSorting = new ArrayList<String>(allUsers);
		Collections.sort(allUsersForSorting);
		
		int numberOfUsers = allUsers.size();
		double[][] similarities = new double[numberOfUsers][numberOfUsers];
		for(UserCombination aUserCombination : filesProcessed)
		{
			int index1 = allUsersForSorting.indexOf(aUserCombination.User1);
			int index2 = allUsersForSorting.indexOf(aUserCombination.User2);
			similarities[index1][index2] = aUserCombination.Similarity;
			similarities[index2][index1] = aUserCombination.Similarity;
		}
		
		StringBuilder aStringBuilder = new StringBuilder(",");
		for(String aUser : allUsersForSorting)
		{
			aStringBuilder.append(aUser);
			aStringBuilder.append(",");
		}
		aStringBuilder.append("\n");
		
		for(int rowIndex = 0; rowIndex < numberOfUsers; rowIndex++)
		{
			aStringBuilder.append(allUsersForSorting.get(rowIndex));
			aStringBuilder.append(",");
			for(int columnIndex = 0; columnIndex < numberOfUsers; columnIndex++)
			{
				aStringBuilder.append(similarities[rowIndex][columnIndex]);
				aStringBuilder.append(",");
			}
			aStringBuilder.append("\n");
		}
		
		StringBuilder sb = new StringBuilder();
		int numberUsers = selectedUsers.size();
		for(int i = 0; i < numberUsers; i++) {
			if(i != numberUsers - 1) {
				sb.append(selectedUsers.get(i) + "_");
			} else {
				sb.append(selectedUsers.get(i));
			}
		}
		File outputDir = new File(outputPathnameOfDataset + "/ComparisonResults/" + dirsPath + "/" + sb.toString());
		if(!outputDir.exists()) {
			outputDir.mkdirs();
		}
		try
		{
			String outputPathname;
			if(semantics == true) {
				if (ifThresholdOnProb) {
					outputPathname = outputDir + "/CPSWithSemanticsThresholdOnProbabilities_" + thresholdOnProbabilities + ".csv";
				} else {
					outputPathname = outputDir + "/CPSWithSemanticsThresholdOnSemanticPatterns_" + thresholdOnProbabilities + ".csv";
				}
			} else {
				outputPathname = outputDir + "/CPSWithoutSemantics.csv";
			}
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputPathname));		
			bufferedWriter.write(aStringBuilder.toString());
			bufferedWriter.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		return similarities;
	}
	
	private static HashMap<String, Distribution> readDistributionFile(String pathnameOfDistributionFile, boolean ifThresholdOnProb, double thresholdOnProbabilities)
	{
		HashMap<String, Distribution> mapFromRegionToDistribution = new HashMap<String, Distribution>();
		try
		{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(pathnameOfDistributionFile));
			String aLine = null;
			boolean firstLine = true;
			numberST = 0;
			while((aLine = bufferedReader.readLine()) != null)
			{
				String[] fields = aLine.split("\t");
				if (firstLine == true) {
					numberST = fields.length - 1;
					firstLine = false;
				}
				double[] probabilities = new double[numberST];
				for(int i = 0; i < numberST; i++)
				{
					probabilities[i] = Double.parseDouble(fields[i + 1]);		
					if (ifThresholdOnProb) {
						if(probabilities[i] < thresholdOnProbabilities/*1.0/numberST*/) {
							probabilities[i] = 0.0;
						}
					}
				}
				mapFromRegionToDistribution.put(fields[0].trim(), new Distribution(probabilities)); 
			}
			bufferedReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return mapFromRegionToDistribution;
	}
}
