package lcs;

import java.util.ArrayList;

public class User {
	public String ID;
	// all the sequences of a user, each sequence is stored as int array
	public ArrayList<int[]> inputSequences= new ArrayList<int[]>();
	// all the timeintervals for all sequences, each array stored a time interval for one sequence from one element to the next.  
	public ArrayList<TimeRange[]> inputSequenceTransitionTimes= new ArrayList<TimeRange[]>();
	// all the support values of a sequence
	public ArrayList<Double> supportValuesForInputSequences= new ArrayList<Double>();
}
