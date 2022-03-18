package cps;

public class CommonSemanticTagPattern extends SemanticTagPattern
{
	private double relativeSupport2;
	
	public CommonSemanticTagPattern(int[] someSemanticTags, double aRelativeSupport, double anotherRelativeSupport)
	{
		super(someSemanticTags, aRelativeSupport);
		relativeSupport2 = anotherRelativeSupport;
	}
	
	public CommonSemanticTagPattern(SemanticTagPattern pattern1, SemanticTagPattern pattern2)
	{
		super(pattern1.getSemanticTags(), pattern1.getRelativeSupport());
		relativeSupport2 = pattern2.getRelativeSupport();
	}
	
	public double getRelativeSupport2()
	{
		return relativeSupport2;
	}
}
