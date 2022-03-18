package hausdorff;
import java.util.Arrays;

public class Distribution 
{
	private double[] probabilities;
	private static final double distanceThreshold = 0.5;
	
	public Distribution(double[] someProbabilities)
	{
		/*double sum = 0.0;
		for(double aProbability : someProbabilities)
		{
			sum += aProbability;
		}
		if(sum != 1.0)
		{
			System.out.println("The input is not a valid distribution.");
			System.out.println(Arrays.toString(someProbabilities));
			System.exit(0);
		}*/
		
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
	
	public boolean isLSsimilar(Distribution anotherDistribution)
	{
		return calculateDistance(anotherDistribution) <= distanceThreshold;
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
	
	public double calculateHellingerDistance(Distribution anotherDistribution)
	{
		double BhattacharyyaCoefficient = 0.0;
		for(int i = 0; i < probabilities.length; i++)
		{
			BhattacharyyaCoefficient += Math.sqrt(probabilities[i] * anotherDistribution.getProbabilites()[i]);
		}		
		if(((BhattacharyyaCoefficient - 1.0) <= Math.pow(10, -10)) && ((BhattacharyyaCoefficient - 1.0) >= -Math.pow(10, -10)))
		{
			return 0.0;
		}
		return Math.sqrt(1.0 - BhattacharyyaCoefficient);		 
	}
	
	public double calculateTotalVariationDistance(Distribution anotherDistribution) {
		double sum = 0.0;
		for(int i = 0; i < probabilities.length; i++) {
			sum += Math.abs(probabilities[i] - anotherDistribution.getProbabilites()[i]);
		}
		return sum / 2.0;
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
	
	@Override
	public int hashCode()
	{
		double hashcode = 0.0;
		for(int i = 0; i < probabilities.length; i++)
		{
			hashcode += probabilities[i] * (31^i); 
		}
		return (int)hashcode;
	}
}
