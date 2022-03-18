package cps;

public class CommonDistributionFrequentPattern 
{
	private DistributionFrequentPattern pattern1;
	private DistributionFrequentPattern pattern2;
	
	public CommonDistributionFrequentPattern(DistributionFrequentPattern aPattern, DistributionFrequentPattern anotherPattern)
	{
		pattern1 = aPattern;
		pattern2 = anotherPattern;
	}	
	
	public DistributionFrequentPattern getPattern1()
	{
		return pattern1;
	}
	
	public DistributionFrequentPattern getPattern2()
	{
		return pattern2;
	}
}
