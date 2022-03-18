package cps;
import java.util.HashSet;

public class Bijection 
{
	/*private HashMap<FrequentPattern, FrequentPattern> mappings;*/
	private HashSet<PatternPair> commonPatternSet;
	
	/*public double calculateDistance()
	{
		double sum = 0.0;
		for(Map.Entry<FrequentPattern, FrequentPattern> anEntry : mappings.entrySet())
		{
			sum += anEntry.getKey().calculateDistance(anEntry.getValue());
		}
		return sum;
	}*/	
	
	//The default constructor provided by the compiler will execute "commonPatternSet = null".
	public Bijection()
	{
		commonPatternSet = new HashSet<PatternPair>();
	}
	
	public HashSet<PatternPair> getCommonPatternSet()
	{
		return commonPatternSet;
	}
	
	@Override
	public String toString()
	{
		String aString = "";
		for(PatternPair aPatternPair : commonPatternSet)
		{
			aString += aPatternPair.toString();
		}
		return aString;
	}
	
	public double functionF(int user)
	{
		double sum = 0.0;
		if(user == 1)
		{
			for(PatternPair aPatternPair : commonPatternSet)
			{			
				sum += Math.pow(aPatternPair.getPattern1().getDistributions().size(), 2.0) * aPatternPair.getPattern1().getRelativeSupport();
			}
		}
		if(user == 2)
		{
			for(PatternPair aPatternPair : commonPatternSet)
			{			
				sum += Math.pow(aPatternPair.getPattern2().getDistributions().size(), 2.0) * aPatternPair.getPattern2().getRelativeSupport();
			}
		}
		return sum;
	}
	
	/*public static LinkedList<Bijection> findAllBijectionsWithSomeLength(LinkedList<FrequentPattern> patternSet1, LinkedList<FrequentPattern> patternSet2)
	{
		LinkedList<FrequentPattern> patternSetWithSmallerSize = (patternSet1.size() > patternSet2.size()) ? patternSet2 : patternSet1;
		LinkedList<FrequentPattern> patternSetWithLargerSize = (patternSet1.size() > patternSet2.size()) ? patternSet1 : patternSet2;
		int numberOfMappingsInEachBijection = (patternSet1.size() < patternSet2.size()) ? patternSet1.size() : patternSet2.size();
		
		LinkedList<LinkedList<FrequentPattern>> subsets = new LinkedList<LinkedList<FrequentPattern>>();
		LinkedList<FrequentPattern> stack = new LinkedList<FrequentPattern>();
		findAllSubsetsOfSomeSize(patternSetWithLargerSize, numberOfMappingsInEachBijection, 0, stack, subsets);
		
		LinkedList<Bijection> allBijectionsWithSomeLength = new LinkedList<Bijection>();
		LinkedList<LinkedList<FrequentPattern>> allPermutationsOfASubset = null;
		for(LinkedList<FrequentPattern> aSubset : subsets)
		{
			allPermutationsOfASubset = new LinkedList<LinkedList<FrequentPattern>>();
			LinkedList<FrequentPattern> beginningList = new LinkedList<FrequentPattern>();
			findAllPermutationsOfASubset(beginningList, aSubset, allPermutationsOfASubset);
			
			for(LinkedList<FrequentPattern> aPermutation : allPermutationsOfASubset)
			{
				Bijection aBijection = new Bijection();
				Iterator<FrequentPattern> iterator1 = aPermutation.iterator();
				Iterator<FrequentPattern> iterator2 = patternSetWithSmallerSize.iterator();
				while(iterator1.hasNext())
				{
					if(patternSet1.size() > patternSet2.size())
					{
						aBijection.getMappings().put(iterator1.next(), iterator2.next());
					}
					else
					{
						aBijection.getMappings().put(iterator2.next(), iterator1.next());
					}					
				}
				allBijectionsWithSomeLength.add(aBijection);
			}
		}
		return allBijectionsWithSomeLength;
	}
	
	private static void findAllPermutationsOfASubset(LinkedList<FrequentPattern> beginningList, LinkedList<FrequentPattern> endingList, LinkedList<LinkedList<FrequentPattern>> allPermutationsOfASubset)
	{
		if(endingList.size() <= 1)
		{
			LinkedList<FrequentPattern> copyOfBeginningList = (LinkedList<FrequentPattern>)beginningList.clone();
			copyOfBeginningList.addAll(endingList);
			allPermutationsOfASubset.add(copyOfBeginningList);
			return;
		}
		for(int i = 0; i < endingList.size(); i++)
		{
			LinkedList<FrequentPattern> newList = (LinkedList<FrequentPattern>)endingList.clone();
			newList.remove(i);
			LinkedList<FrequentPattern> copyOfBeginningList = (LinkedList<FrequentPattern>)beginningList.clone();
			copyOfBeginningList.add(endingList.get(i));
			findAllPermutationsOfASubset(copyOfBeginningList, newList, allPermutationsOfASubset);
		}
	}
	
	private static void findAllSubsetsOfSomeSize(LinkedList<FrequentPattern> patternSetWithLargerSize, int left, int index, LinkedList<FrequentPattern> stack, LinkedList<LinkedList<FrequentPattern>> subsets)
	{		
		if(left == 0)
		{
			LinkedList<FrequentPattern> copy = (LinkedList<FrequentPattern>)stack.clone();
			subsets.add(copy);
			return;
		}
		for(int i = index; i < patternSetWithLargerSize.size(); i++)
		{
			stack.addFirst(patternSetWithLargerSize.get(i));
			findAllSubsetsOfSomeSize(patternSetWithLargerSize, left - 1, i + 1, stack, subsets);
			stack.removeFirst();
		}
	}*/
}
