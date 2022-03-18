package cps;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class PatternPair implements Comparable<PatternPair>
{
	private DistributionFrequentPattern pattern1;
	private DistributionFrequentPattern pattern2;
	
	public PatternPair()
	{
		pattern1 = new DistributionFrequentPattern();
		pattern2 = new DistributionFrequentPattern();
	}
	
	public PatternPair(DistributionFrequentPattern aPattern, DistributionFrequentPattern anotherPattern)
	{
		pattern1 = aPattern;
		pattern2 = anotherPattern;
	}
	
	@Override
	public String toString()
	{
		String aString = "---------PatternPair beginning----------\n";
		aString += pattern1.toString() + pattern2.toString();
		aString += "---------PatternPair end----------\n";
		return aString;
	}
	
	public DistributionFrequentPattern getPattern1()
	{
		return pattern1;
	}
	
	public DistributionFrequentPattern getPattern2()
	{
		return pattern2;
	}
	
	public void setPattern1(DistributionFrequentPattern aPattern)
	{
		pattern1 = aPattern;
	}
	
	public void setPattern2(DistributionFrequentPattern aPattern)
	{
		pattern2 = aPattern;
	}
	
	@Override
	public boolean equals(Object anObject)
	{
		if(this == anObject)
			return true;
		if(anObject == null)
			return false;
		if(!(anObject instanceof PatternPair))
			return false;
		
		PatternPair anotherPatternPair = (PatternPair)anObject;
		return pattern1.equals(anotherPatternPair.getPattern1()) && pattern2.equals(anotherPatternPair.getPattern2());
	}	
	
	public int compareTo(PatternPair anotherPatternPair)
	{
		double distanceOfThisPatternPair = calculateDistance();
		double distanceOfAnotherPatternPair = anotherPatternPair.calculateDistance();
		if(distanceOfThisPatternPair < distanceOfAnotherPatternPair)
		{
			return -1;
		}
		if(distanceOfThisPatternPair == distanceOfAnotherPatternPair)
		{
			return 0;
		}
		return 1;
	}
	
	public double calculateDistance()
	{
		LinkedList<Distribution> distributionsOfPattern1 = pattern1.getDistributions();
		LinkedList<Distribution> distributionsOfPattern2 = pattern2.getDistributions();
		if(distributionsOfPattern1.size() != distributionsOfPattern2.size())
		{
			System.out.println("The two patterns are not of the same length. Cannot calculate their distance.");
			System.exit(0);
		}
		double sum = 0.0;
		Iterator<Distribution> iterator1 = distributionsOfPattern1.iterator();
		Iterator<Distribution> iterator2 = distributionsOfPattern2.iterator();
		try
		{
			while(iterator1.hasNext())
			{
				Distribution distribution1 = iterator1.next();
				Distribution distribution2 = iterator2.next();
				sum += distribution1.calculateDistance(distribution2);
			}
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
		return sum / distributionsOfPattern1.size();
	}
}