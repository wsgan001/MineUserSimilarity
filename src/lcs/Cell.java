package lcs;

public class Cell {

	public String label ="_";
	public int seqLength =-1;
	public int regionID =-1; 
	public int inputAIndex = -1;
	public int inputBIndex = -1;
	public TimeRange timeRangeForUserA = null;
	public TimeRange timeRangeForUserB = null;
	public double ratioA = 0.0;
	public double ratioB = 0.0;

	public Cell(Cell otherCell) {
		this.label = otherCell.label;
		this.seqLength = otherCell.seqLength;
		this.regionID = otherCell.regionID;
		this.inputAIndex = otherCell.inputAIndex;
		this.inputBIndex = otherCell.inputBIndex;

		if (otherCell.timeRangeForUserA == null) {
			this.timeRangeForUserA = new TimeRange(0,0);
		}else {
			this.timeRangeForUserA = new TimeRange(otherCell.timeRangeForUserA.getStartTime(), otherCell.timeRangeForUserA.getEndTime());
		}
		if (otherCell.timeRangeForUserB == null) {
			this.timeRangeForUserB = new TimeRange(0,0);
		}else {
			this.timeRangeForUserB = new TimeRange(otherCell.timeRangeForUserB.getStartTime(), otherCell.timeRangeForUserB.getEndTime());
		}
	}

	public Cell(int c, int inputAIndex, int inputBIndex) {
		//this.label =  Character.toString(c);
		this.regionID = c;
		this.inputAIndex = inputAIndex;
		this.inputBIndex = inputBIndex;
	}

	public Cell(int c, int inputAIndex, int inputBIndex,TimeRange timeRangeForUserA, TimeRange timeRangeForUserB) {
		//this.label =  Character.toString(c);
		this.regionID = c;
		this.inputAIndex = inputAIndex;
		this.inputBIndex = inputBIndex;
		this.timeRangeForUserA = timeRangeForUserA;
		this.timeRangeForUserB = timeRangeForUserB;
	}


	public Cell() {

	}


	@Override
	public String toString() {
		return ""+regionID;//seqLength;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + regionID;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (regionID != other.regionID)
			return false;
		return true;
	}
}
