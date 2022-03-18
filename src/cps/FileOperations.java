package cps;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class FileOperations 
{
	public static RoIFrequentPatternSet readFile(File aFile)
	{
		RoIFrequentPatternSet aFrequentPatternSet = new RoIFrequentPatternSet();
		try
		{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(aFile));
			String aLine = null;
			while((aLine = bufferedReader.readLine()) != null)
			{
				if(aLine.contains(":"))
					aFrequentPatternSet.add(readRoISequence(aLine));
			}
			bufferedReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return aFrequentPatternSet;
	}
	
	private static RoIFrequentPattern readRoISequence(String aLine) throws NumberFormatException
	{
		String[] twoParts = aLine.split(":");
		
		ArrayList<Integer> listOfRoIIDs = new ArrayList<Integer>();
		String[] RoIIDsWithParentheses = twoParts[0].split("\t");
		for(String anRoIID : RoIIDsWithParentheses)
		{
			if(!(anRoIID.trim().isEmpty()))
			{
				int RoIID = Integer.parseInt(anRoIID.substring(1, anRoIID.length() - 1));
				listOfRoIIDs.add(RoIID);
			}
		}
		
		String[] supports = twoParts[1].split("\t");
		double relativeSupport =  Double.parseDouble(supports[0].trim());
		//int absoluteSupport =  Integer.parseInt(supports[1].trim().substring(5, supports[1].length()-1));
		int absoluteSupport = 0;
		
		int[] RoIIDs = convertArrayOfObjectToInt(listOfRoIIDs.toArray()); //This conversion can also be accomplished by the function "public <T> T[] toArray(T[] a)". 
		RoIFrequentPattern aFrequentPattern = new RoIFrequentPattern(relativeSupport, absoluteSupport, RoIIDs);
		
		return aFrequentPattern;
	}
	
	private static int[] convertArrayOfObjectToInt(Object[] anObjectArray)
	{
		int[] anIntArray = new int[anObjectArray.length];
		for(int i = 0; i < anObjectArray.length; i++)
		{
			anIntArray[i] = ((Integer)anObjectArray[i]).intValue();
		}
		return anIntArray;
	}
}
