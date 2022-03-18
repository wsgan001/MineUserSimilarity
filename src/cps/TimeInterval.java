package cps;


public class TimeInterval
{
	public TimeInterval() 
	{
		this.startTime = Integer.MAX_VALUE;
		this.endTime = Integer.MIN_VALUE;
	}
	
	public TimeInterval(int startTime, int endTime) 
	{
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public int getStartTime() 
	{
		return startTime;
	}
	
	public void setStartTime(int startTime) 
	{
		this.startTime = startTime;
	}
	
	public int getEndTime() 
	{
		return endTime;
	}
	
	public void setEndTime(int endTime) 
	{
		this.endTime = endTime;
	}
	
	@Override
	public String toString() 
	{
		return "["+ startTime + ", " + endTime +"]";
	}
	
	private int startTime;
	private int endTime;
}
