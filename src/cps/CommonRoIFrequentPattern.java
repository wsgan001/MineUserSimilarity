package cps;

import java.util.ArrayList;

public class CommonRoIFrequentPattern extends RoIFrequentPattern{
	public CommonRoIFrequentPattern() 
	{ 
		super();
	}
	
	public CommonRoIFrequentPattern(RoIFrequentPattern aFrequentPattern, RoIFrequentPattern anotherFrequentPattern)
	{
		super(aFrequentPattern);
		relativeSupport2 = anotherFrequentPattern.getRelativeSupport();
		absoluteSupport2 = anotherFrequentPattern.getAbsoluteSupport();
	}
	
	public CommonRoIFrequentPattern(double aRelativeSupport, double anotherRelativeSupport, int anAbsoluteSupport, int anotherAbsoluteSupport, int[] someRoIIDs)
	{
		super(aRelativeSupport, anAbsoluteSupport, someRoIIDs);
		relativeSupport2 = anotherRelativeSupport;
		absoluteSupport2 = anotherAbsoluteSupport;				
	}
	
	public ArrayList<TimeInterval> getTransitionTimes2()
	{
		return transitionTimes2;
	}
	
	public void setTransitionTimes2(ArrayList<TimeInterval> someTransitionTimes)
	{
		transitionTimes2 = someTransitionTimes;
	}
	
	public double getRelativeSupport2() 
	{
		return relativeSupport2;
	}
	
	public void setRelativeSupport2(double aRelativeSupport) 
	{
		relativeSupport2 = aRelativeSupport;
	}
	
	public int getAbsoluteSupport2() 
	{
		return absoluteSupport2;
	}
	
	public void setAbsoluteSupport2(int anAbsoluteSupport) 
	{
		absoluteSupport2 = anAbsoluteSupport;
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		for(int RoIID : getRoIIDs()) 
		{
			sb.append("(");
			sb.append(RoIID);
			sb.append(")\t");
		}
		sb.append(": ");
		sb.append(getRelativeSupport());
		sb.append("\t\t");
		sb.append(relativeSupport2);
		sb.append("\t\t");
		sb.append(getAbsoluteSupport());
		sb.append("\t\t");
		sb.append(absoluteSupport2);
		sb.append("\n");
		for(TimeInterval transitionTime : getTransitionTimes()) 
		{
			sb.append(transitionTime);
		}
		sb.append("\n");
		for(TimeInterval transitionTime : transitionTimes2) 
		{
			sb.append(transitionTime);
		}
		sb.append("\n");
		return sb.toString();
	}
	
	private ArrayList<TimeInterval> transitionTimes2 = new ArrayList<TimeInterval>();
	private double relativeSupport2;
	private int absoluteSupport2;
}
