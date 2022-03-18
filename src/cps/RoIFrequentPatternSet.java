package cps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class RoIFrequentPatternSet 
{
	public RoIFrequentPatternSet()
	{
		frequentPatterns = new CopyOnWriteArraySet<RoIFrequentPattern>();
	}
	
	public RoIFrequentPatternSet(CopyOnWriteArraySet<RoIFrequentPattern> someFrequentPatterns)
	{
		frequentPatterns = someFrequentPatterns;
	}
	
	public boolean add(RoIFrequentPattern aFrequentPattern)
	{
		return frequentPatterns.add(aFrequentPattern);
	}
	
	public CopyOnWriteArraySet<RoIFrequentPattern> getFrequentPatterns()
	{
		return frequentPatterns;
	}
	
	public DistributionFrequentPatternSet convertToDistributionPatternSet(HashMap<String, Distribution> mapFromRegionToDistribution) throws Exception {
		LinkedList<DistributionFrequentPattern> distributionFrequentPatterns = new LinkedList<DistributionFrequentPattern>();
		for(RoIFrequentPattern anRoIPattern : frequentPatterns) {
			LinkedList<Distribution> distributions = new LinkedList<Distribution>();
			for(int anRoI : anRoIPattern.getRoIIDs()) {
				Distribution distributionOfTheRoI = mapFromRegionToDistribution.get(Integer.toString(anRoI));
				if(distributionOfTheRoI == null) {
					throw new Exception();
				}
				distributions.add(distributionOfTheRoI);
			}
			DistributionFrequentPattern aDistributionPattern = new DistributionFrequentPattern(distributions, anRoIPattern.getRelativeSupport(), anRoIPattern.getAbsoluteSupport());
			distributionFrequentPatterns.add(aDistributionPattern);
		}
		return new DistributionFrequentPatternSet(distributionFrequentPatterns);
	}
	
	public RoIFrequentPatternSet intersect(RoIFrequentPatternSet anotherFrequentPatternSet)
	{
		RoIFrequentPatternSet intersection = new RoIFrequentPatternSet();		
		for(RoIFrequentPattern aFrequentPattern : frequentPatterns)
		{
			for(RoIFrequentPattern anotherFrequentPattern : anotherFrequentPatternSet.getFrequentPatterns())
			{
				if(aFrequentPattern.equals(anotherFrequentPattern))
				{
					CommonRoIFrequentPattern aCommonFrequentPattern = new CommonRoIFrequentPattern(aFrequentPattern, anotherFrequentPattern);
					intersection.add(aCommonFrequentPattern);
				}
						
			}
		}
		return intersection;
	}
	
	public double functionF()
	{
		double sumOfSupports = 0.0;
		for(RoIFrequentPattern aFrequentPattern : frequentPatterns)
			//sumOfSupports += Math.pow(aFrequentPattern.getRoIIDs().length, 2.0) * aFrequentPattern.getRelativeSupport();
			sumOfSupports += aFrequentPattern.getRoIIDs().length * aFrequentPattern.getRelativeSupport();
			//sumOfSupports += aFrequentPattern.getRelativeSupport();
		return sumOfSupports;
	}
	
	public double functionF(int user)
	{
		double sumOfSupports = 0.0;
		if(user == 1)
		{
			for(RoIFrequentPattern aFrequentPattern : frequentPatterns)
				//sumOfSupports += Math.pow(aFrequentPattern.getRoIIDs().length, 2.0) * aFrequentPattern.getRelativeSupport();
				sumOfSupports += aFrequentPattern.getRoIIDs().length * aFrequentPattern.getRelativeSupport();
				//sumOfSupports += aFrequentPattern.getRelativeSupport();
			return sumOfSupports;
		}
		else
		{
			for(RoIFrequentPattern aFrequentPattern : frequentPatterns)
				//sumOfSupports += Math.pow(aFrequentPattern.getRoIIDs().length, 2.0) * ((CommonRoIFrequentPattern)aFrequentPattern).getRelativeSupport2();
				sumOfSupports += aFrequentPattern.getRoIIDs().length * ((CommonRoIFrequentPattern)aFrequentPattern).getRelativeSupport2();
				//sumOfSupports += ((CommonFrequentPattern)aFrequentPattern).getRelativeSupport2();
			return sumOfSupports;
		}
	}
	
	public void adjustSupport()
	{
		TreeMap<Integer, HashSet<RoIFrequentPattern>> patternMapByLength = new TreeMap<Integer, HashSet<RoIFrequentPattern>>();
		for(RoIFrequentPattern aFrequentPattern : frequentPatterns)
		{
			int length = aFrequentPattern.getRoIIDs().length;
			if(patternMapByLength.containsKey(length))
			{
				patternMapByLength.get(length).add(aFrequentPattern);
			}
			else
			{
				HashSet<RoIFrequentPattern> newSetByLength = new HashSet<RoIFrequentPattern>();
				newSetByLength.add(aFrequentPattern);
				patternMapByLength.put(length, newSetByLength);
			}
		}
				
		Iterator<Integer> keyIterator = patternMapByLength.keySet().iterator();	
		Integer nextKey;
		while(!((nextKey = keyIterator.next()).equals(patternMapByLength.lastKey())))
		{
			for(RoIFrequentPattern aFrequentPatternWithLengthNextKey : patternMapByLength.get(nextKey))
			{
				double maximumSupport = 0;
				for(RoIFrequentPattern aFrequentPatternWithLengthNextKeyPlus1 : patternMapByLength.get(nextKey.intValue() + 1))
				{
					/*String RoIIDsOfPatternLengthNextKeyPlus1 = Arrays.toString(aFrequentPatternWithLengthNextKeyPlus1.getRoIIDs());
					String RoIIDsOfPatternLengthNextKey = Arrays.toString(aFrequentPatternWithLengthNextKey.getRoIIDs());*/
					int[] RoIIDsOfPatternLengthNextKeyPlus1 = aFrequentPatternWithLengthNextKeyPlus1.getRoIIDs();
					int[] RoIIDsOfPatternLengthNextKey = aFrequentPatternWithLengthNextKey.getRoIIDs();
					if(subArray(RoIIDsOfPatternLengthNextKey, RoIIDsOfPatternLengthNextKeyPlus1)/*RoIIDsOfPatternLengthNextKeyPlus1startsWith(RoIIDsOfPatternLengthNextKey.substring(0, RoIIDsOfPatternLengthNextKey.length() - 1))*/)
					{
						double supportOfPatternWithLengthNextKeyPlus1 = aFrequentPatternWithLengthNextKeyPlus1.getRelativeSupport();
						if(supportOfPatternWithLengthNextKeyPlus1 > maximumSupport)
						{
							maximumSupport = supportOfPatternWithLengthNextKeyPlus1;
						}
					}
				}
				aFrequentPatternWithLengthNextKey.setRelativeSupport(aFrequentPatternWithLengthNextKey.getRelativeSupport() - maximumSupport);
			}
		}
	}
	
	private boolean subArray(int[] array1, int[] array2) {
		int i = 0;
		int k = 0;
		for (k = 0; k < array1.length; k++) {
			for (int j = i; j < array2.length; j++) {
				if ((array2[j] != array1[k]) && (j == (array2.length - 1))) {
					return false;
				} else if (array2[j] == array1[k]) {
					i = j + 1;
					break;
				}
			}
			if (i == array2.length && k != array1.length - 1) {
				return false;
			}
		}		
		return true;		
	}
	
	public void adjustSupport(RoIFrequentPatternSet user1, RoIFrequentPatternSet user2)
	{
		if(frequentPatterns.size() != 0)
		{
			TreeMap<Integer, HashSet<RoIFrequentPattern>> patternMapByLength = new TreeMap<Integer, HashSet<RoIFrequentPattern>>();
			for(RoIFrequentPattern aFrequentPattern : frequentPatterns)
			{
				int length = aFrequentPattern.getRoIIDs().length;
				if(patternMapByLength.containsKey(length))
				{
					patternMapByLength.get(length).add(aFrequentPattern);
				}
				else
				{
					HashSet<RoIFrequentPattern> newSetByLength = new HashSet<RoIFrequentPattern>();
					newSetByLength.add(aFrequentPattern);
					patternMapByLength.put(length, newSetByLength);
				}
			}			
						
			for(RoIFrequentPattern aCommonFrequentPattern : frequentPatterns)
			{
				for(RoIFrequentPattern aFrequentPattern : user1.getFrequentPatterns())
				{
					if(aCommonFrequentPattern.equals(aFrequentPattern))
					{
						aCommonFrequentPattern.setRelativeSupport(aFrequentPattern.getRelativeSupport());
					}
				}
			}

			for(RoIFrequentPattern aCommonFrequentPattern : frequentPatterns)
			{
				for(RoIFrequentPattern aFrequentPattern : user2.getFrequentPatterns())
				{
					if(aCommonFrequentPattern.equals(aFrequentPattern))
					{
						((CommonRoIFrequentPattern)aCommonFrequentPattern).setRelativeSupport2(aFrequentPattern.getRelativeSupport());
					}
				}
			}			
		}
	}
	
	public double supSim() {
		double sumDif = 0.0;
		double sum = 0.0;
		
		for (RoIFrequentPattern aCommonFrequentPattern : frequentPatterns) {
			int length = aCommonFrequentPattern.getRoIIDs().length;
			sumDif += Math.abs(aCommonFrequentPattern.getRelativeSupport() - ((CommonRoIFrequentPattern) aCommonFrequentPattern).getRelativeSupport2())/* * length * length*/;
			sum += (aCommonFrequentPattern.getRelativeSupport() + ((CommonRoIFrequentPattern) aCommonFrequentPattern).getRelativeSupport2())/* * length * length*/;
		}
		
		if (sum == 0.0) {
			return 1.0;
		}
		
		return 1.0 - sumDif / sum;
	}
	
	private CopyOnWriteArraySet<RoIFrequentPattern> frequentPatterns;
}