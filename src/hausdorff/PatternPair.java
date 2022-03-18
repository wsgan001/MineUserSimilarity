package hausdorff;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class PatternPair implements Comparable<PatternPair>
{
	private FrequentPattern pattern1;
	private FrequentPattern pattern2;
	/*private double distance;*/
	
	public PatternPair()
	{
		pattern1 = new FrequentPattern();
		pattern2 = new FrequentPattern();
		/*distance = -1.0;*/
	}
	
	public PatternPair(FrequentPattern aPattern, FrequentPattern anotherPattern)
	{
		pattern1 = aPattern;
		pattern2 = anotherPattern;
		/*distance = aPattern.calculateHellingerDistance(anotherPattern) * (1 - pattern1.getRelativeSupport() * pattern2.getRelativeSupport());*/
	}
	
	@Override
	public String toString()
	{
		String aString = "---------PatternPair beginning----------\n";
		aString += pattern1.toString() + pattern2.toString()/* + " " + distance*/;
		aString += "---------PatternPair end----------\n";
		return aString;
	}
	
	@Override
	public int hashCode()
	{
		return pattern1.hashCode() + pattern2.hashCode();
	}
	
	public FrequentPattern getPattern1()
	{
		return pattern1;
	}
	
	public FrequentPattern getPattern2()
	{
		return pattern2;
	}
	
	/*public double getDistance()
	{
		return distance;
	}*/
	
	public void setPattern1(FrequentPattern aPattern)
	{
		pattern1 = aPattern;
	}
	
	public void setPattern2(FrequentPattern aPattern)
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
		return (pattern1.equals(anotherPatternPair.getPattern1()) && pattern2.equals(anotherPatternPair.getPattern2()))/* || (pattern1.equals(anotherPatternPair.getPattern2()) && pattern2.equals(anotherPatternPair.getPattern1()))*/;
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