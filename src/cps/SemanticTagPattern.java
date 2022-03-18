package cps;
import java.util.Arrays;

public class SemanticTagPattern 
{
	private int[] semanticTags;
	private double relativeSupport;		
	
	public SemanticTagPattern(int[] someSemanticTags, double aRelativeSupport)
	{
		semanticTags = someSemanticTags;
		relativeSupport = aRelativeSupport;
	}
	
	public double getRelativeSupport()
	{
		return relativeSupport;
	}
	
	public int[] getSemanticTags()
	{
		return semanticTags;
	}
	
	public void setRelativeSupport(double aRelativeSupport)
	{
		relativeSupport = aRelativeSupport;
	}
	
	@Override
	public String toString()
	{
		return Arrays.toString(semanticTags) + ": " + relativeSupport;
	}
	
	@Override
	public boolean equals(Object anObject)
	{
		if(this == anObject)
			return true;
		if(anObject == null)
			return false;
		if(!(anObject instanceof SemanticTagPattern))
			return false;		
		
		return Arrays.equals(semanticTags, ((SemanticTagPattern)anObject).semanticTags);
	}
}
