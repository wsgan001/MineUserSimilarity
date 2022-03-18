package cps;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class DistributionFrequentPattern 
{
	private LinkedList<Distribution> distributions;
	private double relativeSupport;
	private int absoluteSupport;
	
	public DistributionFrequentPattern()
	{
		distributions = new LinkedList<Distribution>();
		relativeSupport = 0.0;
		absoluteSupport = 0;
	}
	
	public DistributionFrequentPattern(LinkedList<Distribution> someDistributions, double aRelativeSupport, int anAbsoluteSupport)
	{
		distributions = someDistributions;
		relativeSupport = aRelativeSupport;
		absoluteSupport = anAbsoluteSupport;
	}
	
	public boolean startsWith(DistributionFrequentPattern anotherFrequentPattern) {
		if(distributions.size() != anotherFrequentPattern.getDistributions().size() + 1) {
			return false;
		}
		Iterator<Distribution> iterator1 = anotherFrequentPattern.getDistributions().iterator();
		Iterator<Distribution> iterator2 = distributions.iterator();
		while(iterator1.hasNext()) {
			if(!(iterator1.next().equals(iterator2.next()))) {
				return false;
			}
		}
		return true;
	}
	
	public boolean canGeneratePrefixSemanticPattern(SemanticTagPattern aSemanticTagPattern)
	{
		int[] semanticTags = aSemanticTagPattern.getSemanticTags();
		for(int i = 0; i < semanticTags.length; i++)
		{
			if(distributions.get(i).getProbabilites()[semanticTags[i]] == 0.0)
			{
				return false;
			}
		}
		return true;
	}
	
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
	
	public DistributionFrequentPattern merge(DistributionFrequentPattern anotherFrequentPattern)
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
		
		return new DistributionFrequentPattern(newDistributions, relativeSupport + anotherFrequentPattern.getRelativeSupport(), absoluteSupport + anotherFrequentPattern.getAbsoluteSupport());
	}
	
	public boolean isLSsimilar(DistributionFrequentPattern anotherFrequentPattern)
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
	
	public double calculateDistance(DistributionFrequentPattern anotherFrequentPattern)
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
		if(!(anObject instanceof DistributionFrequentPattern))
			return false;
		
		DistributionFrequentPattern anotherFrequentPattern = (DistributionFrequentPattern)anObject;
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
}
