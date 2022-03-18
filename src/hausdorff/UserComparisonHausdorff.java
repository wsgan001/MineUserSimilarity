package hausdorff;
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
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class UserComparisonHausdorff 
{	
	/*public static void main(String[] args)
	{				
		String pathnameOfDistributionFile = "C:\\Documents and Settings\\Ruipeng Lu\\My Documents\\workspace\\data\\RoIAndDistribution\\DistributionsOfRegions8.txt";
		HashMap<String, Distribution> mapFromRegionToDistribution = readDistributionFile(pathnameOfDistributionFile);
		
		String pathnameOfInputDirectory = "C:\\Documents and Settings\\Ruipeng Lu\\My Documents\\workspace\\data\\testpatterns";
		String pathnameOfOutputDirectory = "C:\\Documents and Settings\\Ruipeng Lu\\My Documents\\workspace\\data\\testresult";
		compareUsers(pathnameOfInputDirectory, pathnameOfOutputDirectory, mapFromRegionToDistribution);		
	}*/
	
	private static double calculateSimilarity(File file1, File file2, HashMap<String, Distribution> mapFromRegionToDistribution, boolean adjustLen, boolean hellinger)
	{
		//System.out.println(file1.getName().substring(0, 3) + " " + file2.getName().substring(0, 3));
		FrequentPatternSet user1 = readPatternSetFile(file1, mapFromRegionToDistribution);
		FrequentPatternSet user2 = readPatternSetFile(file2, mapFromRegionToDistribution);
		CopyOnWriteArraySet<FrequentPattern> set1 = new CopyOnWriteArraySet<FrequentPattern>(user1.getFrequentPatterns());
		CopyOnWriteArraySet<FrequentPattern> set2 = new CopyOnWriteArraySet<FrequentPattern>(user2.getFrequentPatterns());
		
		if(adjustLen) {
			return 1.0 - /*Math.pow(*/user1.calculateHausdorffDistance(set1, set2, adjustLen, hellinger)/*, 2.0)*/;
		} else {
			return user1.calculateSimilarity(user2, adjustLen, hellinger);
		}
		/*SemanticTagPatternSet user1ByTagPatterns = user1.convertToSemanticTagPatternSet();		
		SemanticTagPatternSet user2ByTagPatterns = user2.convertToSemanticTagPatternSet();		
		SemanticTagPatternSet intersection = user1ByTagPatterns.intersect(user2ByTagPatterns);
		
		try
		{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter("C:\\k\\data.txt"));
			aWriter.write("user1-------------------------------\n");
			aWriter.write(user1ByTagPatterns.toString());
			aWriter.write("user2-------------------------------\n");
			aWriter.write(user2ByTagPatterns.toString());
			aWriter.write("intersection-------------------------------\n");
			aWriter.write(intersection.toString());
			aWriter.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}		
		
		double weightOfUser1 = user1ByTagPatterns.functionF();
		double weightOfUser2 = user2ByTagPatterns.functionF();
		double weight1OfIntersection = intersection.functionF(1);
		double weight2OfIntersection = intersection.functionF(2);
		
		System.out.println(weightOfUser1 + " " + weightOfUser2 + " " + weight1OfIntersection + " " + weight2OfIntersection);
		user1.mergeLSsimilarPatterns();
		user2.mergeLSsimilarPatterns();
		
		Bijection optimalBijection = user1.findOptimalBijection(user2);
		System.out.println(optimalBijection);
		double weightOfUser1 = user1.functionF();
		double weightOfUser2 = user2.functionF();
		double weight1OfIntersection = optimalBijection.functionF(1);
		double weight2OfIntersection = optimalBijection.functionF(2);
		
		return Math.sqrt((weight1OfIntersection/weightOfUser1) * (weight2OfIntersection/weightOfUser2));*/
	}
	
	public static double[][] compareUsers(/*String pathnameOfInputDirectory, */String outputPathnameOfDataset, String pathnameOfDistributionFile, List<String> selectedUsers, boolean adjustLen, boolean hellinger, String dirsPath)
	{
		HashMap<String, Distribution> mapFromRegionToDistribution = readDistributionFile(pathnameOfDistributionFile);
		
		CopyOnWriteArraySet<UserCombination> filesProcessed = new CopyOnWriteArraySet<UserCombination>();
		
		File[] allFiles = new File[selectedUsers.size()];
		for(int i = 0; i < selectedUsers.size(); i++) {
			allFiles[i] = new File(outputPathnameOfDataset + "/PatternSets/" + dirsPath + "/" + selectedUsers.get(i) + "MiSTA.output");
		}
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
				aUserCombination.Similarity = calculateSimilarity(aFile, anotherFile, mapFromRegionToDistribution, adjustLen, hellinger);
				filesProcessed.add(aUserCombination);
			}
		}
		
		HashSet<String> allUsers = new HashSet<String>();
		for(UserCombination aUserCombination : filesProcessed)
		{
			allUsers.add(aUserCombination.User1);
			allUsers.add(aUserCombination.User2);
		}
		
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
			String filename = null;
			if(adjustLen == true && hellinger == true) {
				filename = outputDir + "/HausdorffAdjustLenHellinger.csv";
			}
			if(adjustLen == true && hellinger == false) {
				filename = outputDir + "/HausdorffAdjustLenTV.csv";
			}
			if(adjustLen == false && hellinger == true) {
				filename = outputDir + "/HausdorffClassifyByLenHellinger.csv";
			}
			if(adjustLen == false && hellinger == false) {
				filename = outputDir + "/HausdorffClassifyByLenTV.csv";
			}
			
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));		
			bufferedWriter.write(aStringBuilder.toString());
			bufferedWriter.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return similarities;
	}
	
	private static FrequentPatternSet readPatternSetFile(File aFile, HashMap<String, Distribution> mapFromRegionToDistribution)
	{
		LinkedList<FrequentPattern> frequentPatterns = new LinkedList<FrequentPattern>();
		try
		{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(aFile));
			String aLine = null;
			while((aLine = bufferedReader.readLine()) != null)
			{
				if(aLine.contains(":"))
					frequentPatterns.add(readRoISequence(aLine, mapFromRegionToDistribution));
			}
			bufferedReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new FrequentPatternSet(frequentPatterns);
	}
	
	private static FrequentPattern readRoISequence(String aLine, HashMap<String, Distribution> mapFromRegionToDistribution) throws Exception
	{
		String[] twoParts = aLine.split(":");
		String[] RoIIDsWithParentheses = twoParts[0].split("\t");
		LinkedList<Distribution> distributions = new LinkedList<Distribution>();
		for(String anRoIID : RoIIDsWithParentheses)
		{
			if(!(anRoIID.trim().isEmpty()))
			{
				Distribution distributionOfTheRoI = mapFromRegionToDistribution.get(anRoIID/*.trim()*/.substring(1, anRoIID.length() - 1));
				distributions.add(distributionOfTheRoI);
			}
		}
		
		String[] supports = twoParts[1].split("\t");
		double relativeSupport =  Double.parseDouble(supports[0].trim());		
		int absoluteSupport = 0;
		
		return new FrequentPattern(distributions, relativeSupport, absoluteSupport);
	}
	
	private static HashMap<String, Distribution> readDistributionFile(String pathnameOfDistributionFile)
	{
		HashMap<String, Distribution> mapFromRegionToDistribution = new HashMap<String, Distribution>();
		try
		{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(pathnameOfDistributionFile));
			String aLine = null;
			while((aLine = bufferedReader.readLine()) != null)
			{
				String[] fields = aLine.split("\t");
				double[] probabilities = new double[10];
				for(int i = 0; i < 9; i++)
				{
					probabilities[i] = Double.parseDouble(fields[i + 1]);					
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
