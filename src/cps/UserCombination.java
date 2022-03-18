package cps;
import java.io.File;

public class UserCombination 
{
	/*public UserCombination(String fileName) 
	{
		String[] fields = fileName.split("-");		
		User1 = fields[0];
		User2 = fields[1];
		FileNameUser1 = User1 + "-" + User2 + "-" + User1 + "MiSTA.output";
		FileNameUser2 = User1 + "-" + User2 + "-" + User2 + "MiSTA.output";
	}*/
	
	public UserCombination(File fileOfUser1, File fileOfUser2) 
	{
		User1 = fileOfUser1.getName().substring(0, 3);
		User2 = fileOfUser2.getName().substring(0, 3);					
		possiblePermutation1 = User1 + User2;
		possiblePermutation2 = User2 + User1;
	}
	
	@Override
	public boolean equals(Object anotherObject)
	{
		if(this == anotherObject)
			return true;
		if(anotherObject == null)
			return false;
		if(!(anotherObject instanceof UserCombination))
			return false;
		
		UserCombination anotherUserCombination = (UserCombination)anotherObject;
		return ((possiblePermutation1.equals(anotherUserCombination.possiblePermutation1)) || (possiblePermutation1.equals(anotherUserCombination.possiblePermutation2)) || (possiblePermutation2.equals(anotherUserCombination.possiblePermutation1)) || (possiblePermutation2.equals(anotherUserCombination.possiblePermutation2)));
	}
	
	/*@Override
	public String toString() 
	{
		return FileNameUser1 + " " + FileNameUser2;
	}*/
	
	public String User1;
	public String User2;
	public String possiblePermutation1;
	public String possiblePermutation2;
	public double Similarity;
}
