package cps;
import java.util.Arrays;

public class Distribution 
{
	private double[] probabilities;
	private static final double distanceThreshold = 0.5;
	
	public Distribution(double[] someProbabilities)
	{		
		probabilities = someProbabilities;
	}
	
	public Distribution()
	{
		probabilities = new double[SemanticTags.values().length];
	}
	
	public double[] getProbabilites()
	{
		return probabilities;
	}
	
	@Override
	public String toString()
	{
		return Arrays.toString(probabilities);
	}
	
	public Distribution merge(Distribution anotherDistribution)
	{
		Distribution newDistribution = new Distribution();
		for(int index = 0; index < SemanticTags.values().length; index++)
		{
			(newDistribution.getProbabilites())[index] = (probabilities[index] + (anotherDistribution.getProbabilites())[index]) / 2.0;
		}
		return newDistribution;
	}
	
	public double relativeEntropy(Distribution anotherDistribution)
	{
		double sum = 0.0;
		for(int index = 0; index < probabilities.length; index++)
		{
			sum += probabilities[index] * Math.log(probabilities[index] / (anotherDistribution.getProbabilites())[index]);
		}
		return sum;
	}
	
	public double calculateDistance(Distribution anotherDistribution)
	{
		return (relativeEntropy(anotherDistribution) + anotherDistribution.relativeEntropy(this)) / 2.0;
	}
	
	public boolean isLSsimilar(Distribution anotherDistribution)
	{
		return calculateDistance(anotherDistribution) <= distanceThreshold;
	}
	
	@Override
	public boolean equals(Object anObject)
	{
		if(this == anObject)
			return true;
		if(anObject == null)
			return false;
		if(!(anObject instanceof Distribution))
			return false;
		
		return Arrays.equals(probabilities, ((Distribution)anObject).getProbabilites());
	}
}
