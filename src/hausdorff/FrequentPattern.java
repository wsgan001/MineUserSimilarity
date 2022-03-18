package hausdorff;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class FrequentPattern 
{
	private LinkedList<Distribution> distributions;
	private double relativeSupport;
	private int absoluteSupport;
	
	public FrequentPattern()
	{
		distributions = new LinkedList<Distribution>();
		relativeSupport = 0.0;
		absoluteSupport = 0;
	}
	
	public FrequentPattern(LinkedList<Distribution> someDistributions, double aRelativeSupport, int anAbsoluteSupport)
	{
		distributions = someDistributions;
		relativeSupport = aRelativeSupport;
		absoluteSupport = anAbsoluteSupport;
	}
	
	public double calculateHellingerDistance(FrequentPattern anotherFrequentPattern)
	{
		if(distributions.size() != anotherFrequentPattern.getDistributions().size())
		{
			System.out.println("Error: cannot calculate the distance between two patterns of different lengths");
			System.exit(0);
		}
		
		double sumOfDistances = 0.0;
		Iterator<Distribution> iterator1 = distributions.iterator();
		Iterator<Distribution> iterator2 = anotherFrequentPattern.getDistributions().iterator();
		try
		{
			while(iterator1.hasNext())
			{
				Distribution distribution1 = iterator1.next();
				Distribution distribution2 = iterator2.next();
				sumOfDistances += distribution1.calculateHellingerDistance(distribution2); 
			}
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
		
		return sumOfDistances / distributions.size();
	}
	
	public double calculateHellingerDistance2(FrequentPattern anotherFrequentPattern) {
		double sumOfDistances = 0.0;
		if(distributions.size() < anotherFrequentPattern.getDistributions().size()) {
			Iterator<Distribution> iterator1 = distributions.iterator();
			Iterator<Distribution> iterator2 = anotherFrequentPattern.getDistributions().iterator();
			try {
				while(iterator1.hasNext()) {
					Distribution distribution1 = iterator1.next();
					Distribution distribution2 = iterator2.next();
					sumOfDistances += distribution1.calculateHellingerDistance(distribution2); 
				}
				while(iterator2.hasNext()) {
					iterator2.next();
					sumOfDistances += 1.0;
				}
			} catch(NoSuchElementException e) {
				e.printStackTrace();
			}
			return sumOfDistances / anotherFrequentPattern.getDistributions().size();
		}
		else {
			Iterator<Distribution> iterator1 = distributions.iterator();
			Iterator<Distribution> iterator2 = anotherFrequentPattern.getDistributions().iterator();
			try {
				while(iterator2.hasNext()) {
					Distribution distribution1 = iterator1.next();
					Distribution distribution2 = iterator2.next();
					sumOfDistances += distribution1.calculateHellingerDistance(distribution2); 
				}
				while(iterator1.hasNext()) {
					iterator1.next();
					sumOfDistances += 1.0;
				}
			} catch(NoSuchElementException e) {
				e.printStackTrace();
			}
			return sumOfDistances / distributions.size();
		}		
	}
	
	public double calculateTotalVariationDistance(FrequentPattern anotherFrequentPattern) {
		if(distributions.size() != anotherFrequentPattern.getDistributions().size())
		{
			System.out.println("Error: cannot calculate the distance between two patterns of different lengths");
			System.exit(0);
		}
		
		double sumOfDistances = 0.0;
		Iterator<Distribution> iterator1 = distributions.iterator();
		Iterator<Distribution> iterator2 = anotherFrequentPattern.getDistributions().iterator();
		try
		{
			while(iterator1.hasNext())
			{
				Distribution distribution1 = iterator1.next();
				Distribution distribution2 = iterator2.next();
				sumOfDistances += distribution1.calculateTotalVariationDistance(distribution2); 
			}
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
		
		return sumOfDistances / distributions.size();
	}
	
	public double calculateTotalVariationDistance2(FrequentPattern anotherFrequentPattern) {
		double sumOfDistances = 0.0;
		if(distributions.size() < anotherFrequentPattern.getDistributions().size()) {
			Iterator<Distribution> iterator1 = distributions.iterator();
			Iterator<Distribution> iterator2 = anotherFrequentPattern.getDistributions().iterator();
			try {
				while(iterator1.hasNext()) {
					Distribution distribution1 = iterator1.next();
					Distribution distribution2 = iterator2.next();
					sumOfDistances += distribution1.calculateTotalVariationDistance(distribution2); 
				}
				while(iterator2.hasNext()) {
					iterator2.next();
					sumOfDistances += 1.0;
				}
			} catch(NoSuchElementException e) {
				e.printStackTrace();
			}
			return sumOfDistances / anotherFrequentPattern.getDistributions().size();
		}
		else {
			Iterator<Distribution> iterator1 = distributions.iterator();
			Iterator<Distribution> iterator2 = anotherFrequentPattern.getDistributions().iterator();
			try {
				while(iterator2.hasNext()) {
					Distribution distribution1 = iterator1.next();
					Distribution distribution2 = iterator2.next();
					sumOfDistances += distribution1.calculateTotalVariationDistance(distribution2); 
				}
				while(iterator1.hasNext()) {
					iterator1.next();
					sumOfDistances += 1.0;
				}
			} catch(NoSuchElementException e) {
				e.printStackTrace();
			}
			return sumOfDistances / distributions.size();
		}
		
	}
	
	/*public boolean canGeneratePrefixSemanticPattern(SemanticTagPattern aSemanticTagPattern)
	{
		int[] semanticTags = aSemanticTagPattern.getSemanticTags();
		for(int i = 0; i < semanticTags.length; i++)
		{
			if(distributions.get(i).getProbabilites()[semanticTags[i]] == 0)
			{
				return false;
			}
		}
		return true;
	}*/
	
	@Override
	public String toString()
	{
		String aString = "";
		for(Distribution aDistribution : distributions)
		{
			aString += aDistribution.toString() + " ";
		}
		aString += ": " + relativeSupport;
		aString += " " + absoluteSupport + "\n";
		return aString;
	}
	
	public LinkedList<Distribution> getDistributions()
	{
		return distributions;
	}
	
	public double getRelativeSupport()
	{
		return relativeSupport;
	}
	
	public int getAbsoluteSupport()
	{
		return absoluteSupport;
	}
	
	public void setRelativeSupport(double aRelativeSupport)
	{
		relativeSupport = aRelativeSupport;
	}
	
	public FrequentPattern merge(FrequentPattern anotherFrequentPattern)
	{
		if(distributions.size() != anotherFrequentPattern.getDistributions().size())
		{
			System.out.println("The two patterns which are to be merged are not of the same length.");
			System.exit(0);
		}
		
		LinkedList<Distribution> newDistributions = new LinkedList<Distribution>();
		Iterator<Distribution> iterator1 = distributions.iterator();
		Iterator<Distribution> iterator2 = anotherFrequentPattern.getDistributions().iterator();
		while(iterator1.hasNext())
		{
			newDistributions.add(iterator1.next().merge(iterator2.next()));
		}
		
		return new FrequentPattern(newDistributions, relativeSupport + anotherFrequentPattern.getRelativeSupport(), absoluteSupport + anotherFrequentPattern.getAbsoluteSupport());
	}
	
	public boolean isLSsimilar(FrequentPattern anotherFrequentPattern)
	{
		if(distributions.size() != anotherFrequentPattern.getDistributions().size())
		{
			return false;
		}
		
		Iterator<Distribution> iterator1 = distributions.iterator();
		Iterator<Distribution> iterator2 = anotherFrequentPattern.getDistributions().iterator();
		try
		{
			while(iterator1.hasNext())
			{
				Distribution distribution1 = iterator1.next();
				Distribution distribution2 = iterator2.next();
				if(!(distribution1.isLSsimilar(distribution2)))
					return false;
			}
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	public double calculateDistance(FrequentPattern anotherFrequentPattern)
	{
		if(distributions.size() != anotherFrequentPattern.getDistributions().size())
		{
			System.out.println("The two patterns are not of the same length. Cannot calculate their distance.");
			System.exit(0);
		}
		double sum = 0.0;
		Iterator<Distribution> iterator1 = distributions.iterator();
		Iterator<Distribution> iterator2 = anotherFrequentPattern.getDistributions().iterator();
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
		return sum / distributions.size();
	}
	
	@Override
	public boolean equals(Object anObject)
	{
		if(this == anObject)
			return true;
		if(anObject == null)
			return false;
		if(!(anObject instanceof FrequentPattern))
			return false;
		
		FrequentPattern anotherFrequentPattern = (FrequentPattern)anObject;
		if(distributions.size() != anotherFrequentPattern.getDistributions().size())
			return false;
		Iterator<Distribution> iterator1 = distributions.iterator();
		Iterator<Distribution> iterator2 = anotherFrequentPattern.getDistributions().iterator();
		try
		{
			while(iterator1.hasNext())
			{
				Distribution distribution1 = iterator1.next();
				Distribution distribution2 = iterator2.next();
				if(!(distribution1.equals(distribution2)))
					return false;
			}
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public int hashCode()
	{
		int hashcode = 0;
		for(Distribution aDistribution : distributions)
		{
			hashcode += aDistribution.hashCode();
		}
		return hashcode;
	}
}
