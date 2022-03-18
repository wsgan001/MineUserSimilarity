package hausdorff;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
/*import java.util.LinkedHashSet;*/
import java.util.LinkedList;
/*import java.util.Map;*/
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
/*import java.util.TreeMap;*/

import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class FrequentPatternSet 
{
	private LinkedList<FrequentPattern> frequentPatterns;
	/*private static final double distanceThreshold = 0.5;*/
	
	public FrequentPatternSet()
	{
		frequentPatterns = new LinkedList<FrequentPattern>();
	}
	
	public FrequentPatternSet(LinkedList<FrequentPattern> someFrequentPatterns)
	{
		frequentPatterns = someFrequentPatterns;
	}
	
	public LinkedList<FrequentPattern> getFrequentPatterns()
	{
		return frequentPatterns;
	}	

	public double calculateSimilarity(FrequentPatternSet anotherSet, boolean adjustLen, boolean hellinger)
	{
		TreeMap<Integer, CopyOnWriteArraySet<FrequentPattern>> map1 = getMapByLength();
		TreeMap<Integer, CopyOnWriteArraySet<FrequentPattern>> map2 = anotherSet.getMapByLength();
		
		Iterator<Map.Entry<Integer, CopyOnWriteArraySet<FrequentPattern>>> iterator1 = map1.entrySet().iterator();
		Iterator<Map.Entry<Integer, CopyOnWriteArraySet<FrequentPattern>>> iterator2 = map2.entrySet().iterator();
		double[] distances = null;
		try
		{			
			if(map1.size() < map2.size())
			{
				distances = new double[map2.size()];
				while(iterator1.hasNext())
				{
					Map.Entry<Integer, CopyOnWriteArraySet<FrequentPattern>> entry1 = iterator1.next();
					Map.Entry<Integer, CopyOnWriteArraySet<FrequentPattern>> entry2 = iterator2.next();
					distances[entry1.getKey() - 1] = calculateHausdorffDistance(entry1.getValue(), entry2.getValue(), adjustLen, hellinger);
				}
				for(int i = map1.size(); i < map2.size(); i++)
				{
					distances[i] = 1.0; 
				}
			}
			else
			{
				distances = new double[map1.size()];
				while(iterator2.hasNext())
				{
					Map.Entry<Integer, CopyOnWriteArraySet<FrequentPattern>> entry1 = iterator1.next();
					Map.Entry<Integer, CopyOnWriteArraySet<FrequentPattern>> entry2 = iterator2.next();
					distances[entry1.getKey() - 1] = calculateHausdorffDistance(entry1.getValue(), entry2.getValue(), adjustLen, hellinger);
				}
				for(int i = map2.size(); i < map1.size(); i++)
				{
					distances[i] = 1.0; 
				}
			}
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
		
		double[] similarities = new double[distances.length];
		for(int i = 0; i < distances.length; i++)
		{
			similarities[i] = 1.0 - /*Math.pow(*/distances[i]/*, 2.0)*/;
		}
		
		double sumOfWeightedSimilarities = 0.0;
		int sumOfWeights = 0;
		for(int i = 0; i < similarities.length; i++)
		{
			sumOfWeightedSimilarities += similarities[i] * Math.pow(i + 1, 2.0);
			sumOfWeights +=  Math.pow(i + 1, 2.0);
		}
		double similarity = sumOfWeightedSimilarities / sumOfWeights;
		return similarity;
	}
	
	public double calculateHausdorffDistance(CopyOnWriteArraySet<FrequentPattern> set1, CopyOnWriteArraySet<FrequentPattern> set2, boolean adjustLen, boolean hellinger)
	{
		HashMap<PatternPair, Double> mapFromPatternPairToDistance = calculateDistancesBetweenAnyPairOfPatterns(set1, set2, adjustLen, hellinger);
		CopyOnWriteArraySet<Double> minDistancesOfSet1 = new CopyOnWriteArraySet<Double>();
		CopyOnWriteArraySet<Double> minDistancesOfSet2 = new CopyOnWriteArraySet<Double>();
		
		for(FrequentPattern pattern1 : set1)
		{
			double minDistance = Double.MAX_VALUE;
			/*FrequentPattern patternWithMinimumDistance = null;*/
			for(FrequentPattern pattern2 : set2)
			{
				/*PatternPair apair = new PatternPair(pattern1, pattern2);
				System.out.println(apair.equals(new PatternPair(pattern1, pattern2)));*/
				/*HashMap<PatternPair, Double> map = calculateDistancesBetweenAnyPairOfPatterns(set1, set2);;
				map.put(new PatternPair(pattern1, pattern2), 1.0);
				System.out.println(map.containsKey(new PatternPair(pattern1, pattern2)));*/
				/*assert mapFromPatternPairToDistance.containsKey(new PatternPair(pattern1, pattern2));*/
				double distance = mapFromPatternPairToDistance.get(new PatternPair(pattern1, pattern2));
				if(distance < minDistance)
				{
					minDistance = distance;
					/*patternWithMinimumDistance = pattern2;*/
				}
			}
			minDistancesOfSet1.add(minDistance);
		}
		
		for(FrequentPattern pattern2 : set2)
		{
			double minDistance = Double.MAX_VALUE;
			/*FrequentPattern patternWithMinimumDistance = null;*/
			for(FrequentPattern pattern1 : set1)
			{
				double distance = mapFromPatternPairToDistance.get(new PatternPair(pattern1, pattern2));
				if(distance < minDistance)
				{
					minDistance = distance;
					/*patternWithMinimumDistance = pattern1;*/
				}
			}
			minDistancesOfSet2.add(minDistance);
		}
		
		double maxDistanceSet1 = -1.0;
		double maxDistanceSet2 = -1.0;		
		for(Double aDistance : minDistancesOfSet1)
		{
			/*double distance = mapFromPatternPairToDistance.get(aPatternPair);*/
			if(aDistance > maxDistanceSet1)
			{
				maxDistanceSet1 = aDistance;
			}
		}
				
		for(Double aDistance : minDistancesOfSet2)
		{
			/*double distance = mapFromPatternPairToDistance.get(aPatternPair);*/
			if(aDistance > maxDistanceSet2)
			{
				maxDistanceSet2 = aDistance;
			}
		}
		
		return (maxDistanceSet1 + maxDistanceSet2) / 2.0;
		
		/*double sumSet1 = 0.0;
		double sumSet2 = 0.0;
		for(Double aDistance : minDistancesOfSet1) {
			sumSet1 += aDistance;
		}
		for(Double aDistance : minDistancesOfSet2) {
			sumSet2 += aDistance;
		}
		
		return (sumSet1 / minDistancesOfSet1.size() + sumSet2 / minDistancesOfSet2.size()) / 2.0;*/
	}
	
	public HashMap<PatternPair, Double> calculateDistancesBetweenAnyPairOfPatterns(CopyOnWriteArraySet<FrequentPattern> set1, CopyOnWriteArraySet<FrequentPattern> set2, boolean adjustLen, boolean hellinger)
	{
		HashMap<PatternPair, Double> map = new HashMap<PatternPair, Double>();
		for(FrequentPattern pattern1 : set1)
		{
			for(FrequentPattern pattern2 : set2)
			{
				double distance = 0.0;
				if(adjustLen == true && hellinger == true) {
					distance = pattern1.calculateHellingerDistance2(pattern2) * (1 - pattern1.getRelativeSupport() * pattern2.getRelativeSupport());
				}
				if(adjustLen == true && hellinger == false) {
					distance = pattern1.calculateTotalVariationDistance2(pattern2) * (1 - pattern1.getRelativeSupport() * pattern2.getRelativeSupport());
				}
				if(adjustLen == false && hellinger == true) {
					distance = pattern1.calculateHellingerDistance(pattern2) * (1 - pattern1.getRelativeSupport() * pattern2.getRelativeSupport());
				}
				if(adjustLen == false && hellinger == false) {
					distance = pattern1.calculateTotalVariationDistance(pattern2) * (1 - pattern1.getRelativeSupport() * pattern2.getRelativeSupport());
				}
				map.put(new PatternPair(pattern1, pattern2), distance);				
			}
		}
		return map;
	}
	
	public TreeMap<Integer, CopyOnWriteArraySet<FrequentPattern>> getMapByLength()
	{
		TreeMap<Integer, CopyOnWriteArraySet<FrequentPattern>> map = new TreeMap<Integer, CopyOnWriteArraySet<FrequentPattern>>();
		for(FrequentPattern aFrequentPattern : frequentPatterns)
		{
			int patternLength = aFrequentPattern.getDistributions().size();
			if(map.containsKey(patternLength))
			{
				map.get(patternLength).add(aFrequentPattern);
			}
			else
			{
				 CopyOnWriteArraySet<FrequentPattern> newSet = new CopyOnWriteArraySet<FrequentPattern>();
				 newSet.add(aFrequentPattern);
				 map.put(patternLength, newSet);
			}
		}
		
		return map;
	}
	
	/*public SemanticTagPatternSet convertToSemanticTagPatternSet()
	{			
		TreeMap<Integer, CopyOnWriteArraySet<FrequentPattern>> map = new TreeMap<Integer, CopyOnWriteArraySet<FrequentPattern>>();
		for(FrequentPattern aFrequentPattern : frequentPatterns)
		{
			int patternLength = aFrequentPattern.getDistributions().size();
			if(map.containsKey(patternLength))
			{
				map.get(patternLength).add(aFrequentPattern);
			}
			else
			{
				 CopyOnWriteArraySet<FrequentPattern> newSet = new CopyOnWriteArraySet<FrequentPattern>();
				 newSet.add(aFrequentPattern);
				 map.put(patternLength, newSet);
			}
		}
		
		ArrayList<CopyOnWriteArraySet<SemanticTagPattern>> semanticTagPatternSetsByLength = new ArrayList<CopyOnWriteArraySet<SemanticTagPattern>>();
		for(int i = 0; i < map.size(); i++)
		{
			CopyOnWriteArraySet<SemanticTagPattern> aSet = new CopyOnWriteArraySet<SemanticTagPattern>();
			semanticTagPatternSetsByLength.add(aSet);
		}		
		
		for(Map.Entry<Integer, CopyOnWriteArraySet<FrequentPattern>> anEntry : map.entrySet())
		{
			int key = anEntry.getKey();
			CopyOnWriteArraySet<FrequentPattern> value = anEntry.getValue();
			if(key == 1)
			{
				//CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetsOfLength1 = new CopyOnWriteArraySet<SemanticTagPattern>();
				for(FrequentPattern aFrequentPattern : value)
				{
					int distributionCount = 0;
					LinkedList<Integer> tags = new LinkedList<Integer>();
					//CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfLength1 = semanticTagPatternSetsByLength.get(0);
					convertOnePatternToSemanticTagPatterns(semanticTagPatternSetsByLength.get(0), aFrequentPattern, distributionCount, tags);
				}
				
				CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfLength1 = semanticTagPatternSetsByLength.get(0);
				for(SemanticTagPattern aTagPatternOfLength1 : semanticTagPatternSetOfLength1)
				{
					if(aTagPatternOfLength1.getRelativeSupport() < 0.1)
					{
						semanticTagPatternSetOfLength1.remove(aTagPatternOfLength1);
					}
				}
			}
			else
			{
				CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfLengthShorterBy1 = semanticTagPatternSetsByLength.get(key - 2);
				for(SemanticTagPattern aSemanticPatternOfLengthShorterBy1 : semanticTagPatternSetOfLengthShorterBy1)
				{					
					for(FrequentPattern aPatternOfLengthKey : value)
					{
						if(aPatternOfLengthKey.canGeneratePrefixSemanticPattern(aSemanticPatternOfLengthShorterBy1))
						{
							double[] probabilitiesOfLastDistribution = aPatternOfLengthKey.getDistributions().getLast().getProbabilites();
							for(int i = 0; i < probabilitiesOfLastDistribution.length; i++)
							{
								if(probabilitiesOfLastDistribution[i] != 0.0)
								{									
									int[] semanticTags = Arrays.copyOf(aSemanticPatternOfLengthShorterBy1.getSemanticTags(), key);
									semanticTags[key - 1] = i;
									double product = 1.0;
									for(int i2 = 0; i2 < semanticTags.length; i2++)
									{
										product *= (aPatternOfLengthKey.getDistributions().get(i2).getProbabilites())[semanticTags[i2]];
									}									
									
									SemanticTagPattern newSemanticTagPattern = new SemanticTagPattern(semanticTags, product * aPatternOfLengthKey.getRelativeSupport());
									
									CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfLengthKey = semanticTagPatternSetsByLength.get(key - 1);
									if(!(semanticTagPatternSetOfLengthKey.add(newSemanticTagPattern)))
									{										
										for(SemanticTagPattern aSemanticTagPattern : semanticTagPatternSetOfLengthKey)
										{
											if(aSemanticTagPattern.equals(newSemanticTagPattern))
											{
												aSemanticTagPattern.setRelativeSupport(aSemanticTagPattern.getRelativeSupport() + newSemanticTagPattern.getRelativeSupport());
												break;
											}
										}
									}
								}
							}
						}
					}
				}
				
				CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfLengthKey = semanticTagPatternSetsByLength.get(key - 1);
				for(SemanticTagPattern aTagPatternOfLength1 : semanticTagPatternSetOfLengthKey)
				{
					if(aTagPatternOfLength1.getRelativeSupport() < 0.1)
					{
						semanticTagPatternSetOfLengthKey.remove(aTagPatternOfLength1);
					}
				}
			}
		}
		
		CopyOnWriteArraySet<SemanticTagPattern> tagPatterns = new CopyOnWriteArraySet<SemanticTagPattern>();
		for(CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfSomeLength : semanticTagPatternSetsByLength)
		{
			tagPatterns.addAll(semanticTagPatternSetOfSomeLength);
		}
		
		return new SemanticTagPatternSet(tagPatterns);
		
		CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatterns = new CopyOnWriteArraySet<SemanticTagPattern>();
		
		for(FrequentPattern aFrequentPattern : frequentPatterns)
		{
			int distributionCount = 0;
			LinkedList<Integer> tags = new LinkedList<Integer>();
			convertOnePatternToSemanticTagPatterns(semanticTagPatterns, aFrequentPattern, distributionCount, tags);
		}
		for(SemanticTagPattern aSemanticTagPattern : semanticTagPatterns)
		{
			if(aSemanticTagPattern.getRelativeSupport() < 0.1)
			{
				semanticTagPatterns.remove(aSemanticTagPattern);
			}
		}
		return new SemanticTagPatternSet(semanticTagPatterns);
	}
	
	private void convertOnePatternToSemanticTagPatterns(CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatterns, FrequentPattern aFrequentPattern, int distributionCount, LinkedList<Integer> tags)
	{
		if(distributionCount == aFrequentPattern.getDistributions().size())
		{
			return;
		}
		
		double[] probabilites = aFrequentPattern.getDistributions().get(distributionCount).getProbabilites();
		for(int index = 0; index < probabilites.length; index++)
		{	
			if(probabilites[index] == 0.0)
			{
				continue;
			}			
			
			tags.add(index);
			convertOnePatternToSemanticTagPatterns(semanticTagPatterns, aFrequentPattern, ++distributionCount, tags);	
			
			distributionCount--;
			tags.removeLast();
			
			if(distributionCount == (aFrequentPattern.getDistributions().size() - 1)) //tags.size() == aFrequentPattern.getDistributions().size()
			{
				//Integer[] semanticTagsIntegers = new Integer;
				Integer[] semanticTagsIntegers = tags.toArray(new Integer[]{1});
				int[] semanticTags = new int[semanticTagsIntegers.length];
				for(int i = 0; i < semanticTagsIntegers.length; i++)
				{
					semanticTags[i] = semanticTagsIntegers[i].intValue();
				}
				double product = 1.0;
				for(int i = 0; i < semanticTags.length; i++)
				{
					product *= (aFrequentPattern.getDistributions().get(i).getProbabilites())[semanticTags[i]];
				}				
				
				SemanticTagPattern newSemanticTagPattern = new SemanticTagPattern(semanticTags, product * aFrequentPattern.getRelativeSupport());
				System.out.println(newSemanticTagPattern);
				if(!(semanticTagPatterns.add(newSemanticTagPattern)))
				{
					System.out.println("executed");
					for(SemanticTagPattern aSemanticTagPattern : semanticTagPatterns)
					{
						if(aSemanticTagPattern.equals(newSemanticTagPattern))
						{
							aSemanticTagPattern.setRelativeSupport(aSemanticTagPattern.getRelativeSupport() + newSemanticTagPattern.getRelativeSupport());
							break;
						}
					}
				}				
				
				distributionCount--;
				tags.removeLast();
			}
		}
		
		distributionCount--;
		if(tags.size() > 0)
		{
			tags.removeLast();
		}		
	}*/
	
	@Override
	public String toString()
	{
		String aString = "";
		for(FrequentPattern aFrequentPattern : frequentPatterns)
		{
			aString += aFrequentPattern.toString();
		}
		return aString;
	}
	
	public double functionF()
	{
		double sumOfSupports = 0.0;
		for(FrequentPattern aFrequentPattern : frequentPatterns)
			sumOfSupports += Math.pow(aFrequentPattern.getDistributions().size(), 2.0) * aFrequentPattern.getRelativeSupport();
			
		return sumOfSupports;
	}
	
	/*private TreeMap<Integer, LinkedList<FrequentPattern>> buildMapByLength()
	{
		TreeMap<Integer, LinkedList<FrequentPattern>> mapByLength = new TreeMap<Integer, LinkedList<FrequentPattern>>();
		for(FrequentPattern aFrequentPattern : frequentPatterns)
		{
			int length = aFrequentPattern.getDistributions().size();
			if(mapByLength.containsKey(length))
			{
				mapByLength.get(length).add(aFrequentPattern);
			}
			else
			{
				LinkedList<FrequentPattern> newSetWithLength = new LinkedList<FrequentPattern>();
				newSetWithLength.add(aFrequentPattern);
				mapByLength.put(length, newSetWithLength);
			}
		}
		
		return mapByLength;
	}*/
	
	/*private TreeMap<Integer, LinkedList<FrequentPattern>> buildMapByLength(LinkedHashSet<FrequentPattern> set)
	{
		TreeMap<Integer, LinkedList<FrequentPattern>> mapByLength = new TreeMap<Integer, LinkedList<FrequentPattern>>();
		for(FrequentPattern aFrequentPattern : set)
		{
			int length = aFrequentPattern.getDistributions().size();
			if(mapByLength.containsKey(length))
			{
				mapByLength.get(length).add(aFrequentPattern);
			}
			else
			{
				LinkedList<FrequentPattern> newSetWithLength = new LinkedList<FrequentPattern>();
				newSetWithLength.add(aFrequentPattern);
				mapByLength.put(length, newSetWithLength);
			}
		}
		
		return mapByLength;
	}*/
	
	public void mergeLSsimilarPatterns()
	{	
		LinkedList<FrequentPattern> patternsAfterMerging = new LinkedList<FrequentPattern>();
		
		/*LinkedHashSet<FrequentPattern> set = new LinkedHashSet<FrequentPattern>();
		for(FrequentPattern aFrequentPattern : frequentPatterns)
		{
			if(!(set.add(aFrequentPattern)))
			{
				for(FrequentPattern aPatternInSet : set)
				{
					if(aPatternInSet.equals(aFrequentPattern))
					{
						aPatternInSet.setRelativeSupport(aPatternInSet.getRelativeSupport() + aFrequentPattern.getRelativeSupport());
						break;
					}
				}
			}
		}*/
		
		/*TreeMap<Integer, LinkedList<FrequentPattern>> mapByLength = buildMapByLength(set);*/		
		
		/*for(Map.Entry<Integer, LinkedList<FrequentPattern>> anEntry : mapByLength.entrySet())
		{*/
			SimpleWeightedGraph<FrequentPattern, DefaultWeightedEdge> patternsGraph/*WithLengthIndex*/ = new SimpleWeightedGraph<FrequentPattern, DefaultWeightedEdge>(DefaultWeightedEdge.class);
			/*LinkedList<FrequentPattern> allPatternsWithLengthKey = anEntry.getValue();*/
			for(FrequentPattern aVertex : frequentPatterns/*set*//*allPatternsWithLengthKey*/)
			{
				patternsGraph/*WithLengthIndex*/.addVertex(aVertex);
			}
			
			for(FrequentPattern aVertex : frequentPatterns/*set*//*allPatternsWithLengthKey*/)
			{
				for(FrequentPattern anotherVertex : frequentPatterns/*set*//*allPatternsWithLengthKey*/)
				{
					if(aVertex == anotherVertex)
						continue;
					if(aVertex.isLSsimilar(anotherVertex))
					{
						DefaultWeightedEdge anEdge = patternsGraph/*WithLengthIndex*/.addEdge(aVertex, anotherVertex);
						if(anEdge != null)
						{
							patternsGraph/*WithLengthIndex*/.setEdgeWeight(anEdge, aVertex.calculateDistance(anotherVertex));
						}
					}
				}
			}			
			
			while(true)
			{
				BronKerboschCliqueFinder<FrequentPattern, DefaultWeightedEdge> aCliqueFinder = new BronKerboschCliqueFinder<FrequentPattern, DefaultWeightedEdge>(patternsGraph/*WithLengthIndex*/);
				Collection<Set<FrequentPattern>> allMaximumCliques = aCliqueFinder.getBiggestMaximalCliques();
				Set<FrequentPattern> optimalMaximumClique = null;
				double minimumTwiceSumOfEdgeWeights = Double.MAX_VALUE;

				if(allMaximumCliques.size() > 1)
				{				
					for(Set<FrequentPattern> aMaximumClique : allMaximumCliques)
					{
						double twiceSumOfEdgeWeights = 0.0;
						for(FrequentPattern vertex1 : aMaximumClique)
						{
							for(FrequentPattern vertex2 : aMaximumClique)
							{
								if(vertex1 == vertex2)
									continue;
								twiceSumOfEdgeWeights += patternsGraph/*WithLengthIndex*/.getEdgeWeight(patternsGraph/*WithLengthIndex*/.getEdge(vertex1, vertex2));
							}
						}
						if(twiceSumOfEdgeWeights < minimumTwiceSumOfEdgeWeights)
						{
							optimalMaximumClique = aMaximumClique;
							minimumTwiceSumOfEdgeWeights = twiceSumOfEdgeWeights;							
						}
					}
				}
				else
				{
					optimalMaximumClique = allMaximumCliques.iterator().next();
				}			

				FrequentPattern newPattern = null; 
				boolean firstTime = true;				
				for(FrequentPattern aPattern : optimalMaximumClique)
				{
					if(firstTime == true)
					{
						newPattern = aPattern;
						firstTime = false;
					}
					else
					{
						newPattern = newPattern.merge(aPattern);
					}
				}

				patternsAfterMerging.add(newPattern);				
				
				patternsGraph/*WithLengthIndex*/.removeAllVertices(optimalMaximumClique);	
				
				if(patternsGraph/*WithLengthIndex*/.vertexSet().size() == 0)
				{
					break;
				}
			}
		/*}*/
		
		frequentPatterns = patternsAfterMerging;
		/*TreeMap<Integer, LinkedList<FrequentPattern>> map = buildMapByLength();
		for(Map.Entry<Integer, LinkedList<FrequentPattern>> anEntry : map.entrySet())
		{
			LinkedList<FrequentPattern> allPatternsWithLengthKey = anEntry.getValue();
			boolean unDone = true;
			while(unDone)
			{
				unDone = false;
				for(FrequentPattern pattern1 : allPatternsWithLengthKey)
				{
					boolean mergingHappened = false;
					for(FrequentPattern pattern2 : allPatternsWithLengthKey)
					{
						if(pattern1 == pattern2)
							continue;
						if(pattern1.calculateDistance(pattern2) < distanceThreshold)
						{
							allPatternsWithLengthKey.remove(pattern1);
							allPatternsWithLengthKey.remove(pattern2);
							allPatternsWithLengthKey.add(pattern1.merge(pattern2));
							unDone = true;
							mergingHappened = true;
							break;
						}
					}
					if(mergingHappened == true)
					{
						break;
					}
				}
			}
		}*/
	}
	
	/*public Bijection findOptimalBijection(FrequentPatternSet anotherFrequentPatternSet)
	{
		Bijection optimalBijection = new Bijection();
		
		TreeMap<Integer, LinkedList<FrequentPattern>> map1 = buildMapByLength();
		TreeMap<Integer, LinkedList<FrequentPattern>> map2 = anotherFrequentPatternSet.buildMapByLength();		
		
		for(int index = 1; index <= ((map1.size() < map2.size()) ? map1.size() : map2.size()); index++)
		{
			LinkedList<PatternPair> allLSsimilarPatternPairsWithLengthIndex = new LinkedList<PatternPair>();
			for(FrequentPattern pattern1 : frequentPatterns)
			{
				for(FrequentPattern pattern2 : anotherFrequentPatternSet.getFrequentPatterns())
				{
					if(pattern1.isLSsimilar(pattern2))
					{
						allLSsimilarPatternPairsWithLengthIndex.add(new PatternPair(pattern1, pattern2));
					}						
				}
			}
			
			Collections.sort(allLSsimilarPatternPairsWithLengthIndex);
			Bijection optimalBijectionWithLengthIndex = new Bijection();
			HashSet<FrequentPattern> allPatternsFound = new HashSet<FrequentPattern>();
			for(PatternPair aPatternPair : allLSsimilarPatternPairsWithLengthIndex)
			{
				if(!(allPatternsFound.contains(aPatternPair.getPattern1()) || allPatternsFound.contains(aPatternPair.getPattern2())))
				{
					optimalBijectionWithLengthIndex.getCommonPatternSet().add(aPatternPair);
					allPatternsFound.add(aPatternPair.getPattern1());
					allPatternsFound.add(aPatternPair.getPattern2());
				}
			}	
			
			optimalBijection.getCommonPatternSet().addAll(optimalBijectionWithLengthIndex.getCommonPatternSet());
		}
		
		for(int index = 1; index <= ((map1.size() < map2.size()) ? map1.size() : map2.size()); index++)
		{
			LinkedList<Bijection> allBijectionsWithLengthIndex = Bijection.findAllBijectionsWithSomeLength(map1.get(index), map2.get(index));
			Bijection optimalBijectionWithLengthIndex = null;
			double distanceOfOptimalBijectionWithLengthIndex = Double.MAX_VALUE;
			for(Bijection aBijectionWithLengthIndex : allBijectionsWithLengthIndex)
			{
				double distanceOfABijectionWithLengthIndex = aBijectionWithLengthIndex.calculateDistance();
				if(distanceOfABijectionWithLengthIndex < distanceOfOptimalBijectionWithLengthIndex)
				{
					optimalBijectionWithLengthIndex = aBijectionWithLengthIndex;
					//distanceOfOptimalBijectionWithLengthIndex = distanceOfABijectionWithLengthIndex;
				}
			}
			
			optimalBijection.getMappings().putAll(optimalBijectionWithLengthIndex.getMappings());
		}
		
		return optimalBijection;
	}*/
}
