package lcs;

public class UserCombination 
{	
	public UserCombination(User userU, User userV) 
	{
		User1 = userU.ID;
		User2 = userV.ID;					
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
