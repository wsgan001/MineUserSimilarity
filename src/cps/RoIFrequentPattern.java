package cps;
import java.util.ArrayList;
import java.util.Arrays;

public class RoIFrequentPattern
{
	public RoIFrequentPattern() { }
	
	public RoIFrequentPattern(RoIFrequentPattern aFrequentPattern)
	{
		relativeSupport = aFrequentPattern.getRelativeSupport();
		absoluteSupport = aFrequentPattern.getAbsoluteSupport();
		RoIIDs = aFrequentPattern.getRoIIDs();
	}
	
	public RoIFrequentPattern(double aRelativeSupport, int anAbsoluteSupport, int[] someRoIIDs)
	{
		relativeSupport = aRelativeSupport;
		absoluteSupport = anAbsoluteSupport;
		RoIIDs = someRoIIDs;		
	}
	
	public ArrayList<TimeInterval> getTransitionTimes()
	{
		return transitionTimes;
	}
	
	public void setTransitionTimes(ArrayList<TimeInterval> someTransitionTimes)
	{
		transitionTimes = someTransitionTimes;
	}
	
	public double getRelativeSupport() 
	{
		return relativeSupport;
	}
	
	public void setRelativeSupport(double aRelativeSupport) 
	{
		relativeSupport = aRelativeSupport;
	}
	
	public int getAbsoluteSupport() 
	{
		return absoluteSupport;
	}
	
	public void setAbsoluteSupport(int anAbsoluteSupport) 
	{
		absoluteSupport = anAbsoluteSupport;
	}
	
	public int[] getRoIIDs() 
	{
		return RoIIDs;
	}
	
	public void setRoIIDs(int[] someRoIIDs) 
	{
		RoIIDs = someRoIIDs;
	}
	
	@Override
	public final boolean equals(Object anotherObject)
	{
		if(this == anotherObject)
			return true;
		if(anotherObject == null)
			return false;
		if(!(anotherObject instanceof RoIFrequentPattern))
			return false;
		
		return Arrays.equals(RoIIDs, ((RoIFrequentPattern)anotherObject).getRoIIDs());
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		for (int RoIID : RoIIDs) 
		{
			sb.append("(");
			sb.append(RoIID);
			sb.append(")\t");
		}
		sb.append(": ");
		sb.append(relativeSupport);
		sb.append("\t\t");
		sb.append(absoluteSupport);
		sb.append("\n");
		for (TimeInterval transitionTime : transitionTimes) 
		{
			sb.append(transitionTime);
		}
		sb.append("\n");
		return sb.toString();
	}
	
	private ArrayList<TimeInterval> transitionTimes = new ArrayList<TimeInterval>();
	private double relativeSupport;
	private int absoluteSupport;
	private int[] RoIIDs;
}
