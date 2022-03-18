package lcs;

import java.util.ArrayList;

/**
 * Contains the data for a single sequence.
 *
 */
public class SequenceData {
	private int[] sequence;
	private double relativeSupport;
	private int absoluteSupport;

	private ArrayList<TimeRange> timeRanges = new ArrayList<TimeRange>();
	
	public SequenceData() {
		super();
	}
	
	public SequenceData(int[] sequence, double relativeSupport,
			int absoluteSupport) {
		super();
		this.sequence = sequence;
		this.relativeSupport = relativeSupport;
		this.absoluteSupport = absoluteSupport;
	}
	
	public int[] getSequence() {
		return sequence;
	}
	public void setSequence(int[] sequence) {
		this.sequence = sequence;
	}
	public double getRelativeSupport() {
		return relativeSupport;
	}
	public void setRelativeSupport(double relativeSupport) {
		this.relativeSupport = relativeSupport;
	}
	public int getAbsoluteSupport() {
		return absoluteSupport;
	}
	public void setAbsoluteSupport(int absoluteSupport) {
		this.absoluteSupport = absoluteSupport;
	}
	
	

	public ArrayList<TimeRange> getTimeRange() {
		return timeRanges;
	}

	public void setTimeRange(ArrayList<TimeRange> timeRange) {
		this.timeRanges = timeRange;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int val : sequence) {
			sb.append("(");
			sb.append(val);
			sb.append(")\t");
		}
		sb.append(": ");
		sb.append(relativeSupport);
		sb.append("\t\t");
		sb.append(absoluteSupport);
		sb.append("\n");
		for (TimeRange tr : timeRanges) {
			sb.append(tr);
		}
		sb.append("\n");
		return sb.toString();
	}
	
}
