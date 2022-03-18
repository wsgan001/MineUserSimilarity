package lcs;

public class TimeRange {

	private int startTime;
	private int endTime;
	
	public TimeRange() {
		this.startTime =  Integer.MAX_VALUE;
		this.endTime = Integer.MIN_VALUE;
	}
	
	public TimeRange(int startTime, int endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	@Override
	public String toString() {
		return "["+ startTime + "," + endTime +"]";
	}
}
