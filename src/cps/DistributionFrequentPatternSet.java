package cps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class DistributionFrequentPatternSet 
{
	private LinkedList<DistributionFrequentPattern> frequentPatterns;
	
	public DistributionFrequentPatternSet()
	{
		frequentPatterns = new LinkedList<DistributionFrequentPattern>();
	}
	
	public DistributionFrequentPatternSet(LinkedList<DistributionFrequentPattern> someFrequentPatterns)
	{
		frequentPatterns = someFrequentPatterns;
	}
	
	public LinkedList<DistributionFrequentPattern> getFrequentPatterns()
	{
		return frequentPatterns;
	}
	
	public SemanticTagPatternSet convertToSemanticTagPatternSet(boolean ifThresholdOnProb, double thresholdOnProbabilities, /*double numberST, */double threSup)
	{		
		if (ifThresholdOnProb) {
			TreeMap<Integer, CopyOnWriteArraySet<DistributionFrequentPattern>> map = new TreeMap<Integer, CopyOnWriteArraySet<DistributionFrequentPattern>>();
			for(DistributionFrequentPattern aFrequentPattern : frequentPatterns)
			{
				int patternLength = aFrequentPattern.getDistributions().size();
				if(map.containsKey(patternLength))
				{
					map.get(patternLength).add(aFrequentPattern);
				}
				else
				{
					 CopyOnWriteArraySet<DistributionFrequentPattern> newSet = new CopyOnWriteArraySet<DistributionFrequentPattern>();
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
			
			for(Map.Entry<Integer, CopyOnWriteArraySet<DistributionFrequentPattern>> anEntry : map.entrySet())
			{
				int key = anEntry.getKey();
				CopyOnWriteArraySet<DistributionFrequentPattern> value = anEntry.getValue();
				if(key == 1)
				{					
					for(DistributionFrequentPattern aFrequentPattern : value)
					{
						int distributionCount = 0;
						LinkedList<Integer> tags = new LinkedList<Integer>();
						
						convertOnePatternToSemanticTagPatterns(semanticTagPatternSetsByLength.get(0), aFrequentPattern, distributionCount, tags);
					}					
				}
				else
				{
					CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfLengthShorterBy1 = semanticTagPatternSetsByLength.get(key - 2);
					for(SemanticTagPattern aSemanticPatternOfLengthShorterBy1 : semanticTagPatternSetOfLengthShorterBy1)
					{					
						for(DistributionFrequentPattern aPatternOfLengthKey : value)
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
				}
			}
			
			CopyOnWriteArraySet<SemanticTagPattern> tagPatterns = new CopyOnWriteArraySet<SemanticTagPattern>();
			for(CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfSomeLength : semanticTagPatternSetsByLength)
			{
				tagPatterns.addAll(semanticTagPatternSetOfSomeLength);
			}
			
			return new SemanticTagPatternSet(tagPatterns);
						
			/*CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatterns = new CopyOnWriteArraySet<SemanticTagPattern>();
			
			for(DistributionFrequentPattern aFrequentPattern : frequentPatterns)
			{
				int distributionCount = 0;
				LinkedList<Integer> tags = new LinkedList<Integer>();
				convertOnePatternToSemanticTagPatterns(semanticTagPatterns, aFrequentPattern, distributionCount, tags);
			}
			
			return new SemanticTagPatternSet(semanticTagPatterns);*/
		} else {
			TreeMap<Integer, CopyOnWriteArraySet<DistributionFrequentPattern>> map = new TreeMap<Integer, CopyOnWriteArraySet<DistributionFrequentPattern>>();
			for(DistributionFrequentPattern aFrequentPattern : frequentPatterns)
			{
				int patternLength = aFrequentPattern.getDistributions().size();
				if(map.containsKey(patternLength))
				{
					map.get(patternLength).add(aFrequentPattern);
				}
				else
				{
					 CopyOnWriteArraySet<DistributionFrequentPattern> newSet = new CopyOnWriteArraySet<DistributionFrequentPattern>();
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
			
			CopyOnWriteArraySet<SemanticTagPattern> tagPatterns = new CopyOnWriteArraySet<SemanticTagPattern>();
			for(Map.Entry<Integer, CopyOnWriteArraySet<DistributionFrequentPattern>> anEntry : map.entrySet())
			{				
				int key = anEntry.getKey();
				CopyOnWriteArraySet<DistributionFrequentPattern> value = anEntry.getValue();
				for(DistributionFrequentPattern aFrequentPattern : value)
				{
					int distributionCount = 0;
					LinkedList<Integer> tags = new LinkedList<Integer>();
					//CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfLength1 = semanticTagPatternSetsByLength.get(0);
					convertOnePatternToSemanticTagPatterns(semanticTagPatternSetsByLength.get(key - 1), aFrequentPattern, distributionCount, tags);
				}
				
				//double threshold = value.size() * threSup/ (Math.pow(numberST, key));
				double threshold = value.size() * threSup * (Math.pow(thresholdOnProbabilities, key));
				CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfLengthKey = semanticTagPatternSetsByLength.get(key - 1);
				for(SemanticTagPattern aTagPatternOfLengthKey : semanticTagPatternSetOfLengthKey)
				{
					if(aTagPatternOfLengthKey.getRelativeSupport() >= threshold)
					{
						//semanticTagPatternSetOfLengthKey.remove(aTagPatternOfLength1);
						tagPatterns.add(aTagPatternOfLengthKey);
					}
				}
				
	//////////////////////////////////////////////////////////////////////////////////////////////			
				
				/*int key = anEntry.getKey();
				CopyOnWriteArraySet<DistributionFrequentPattern> value = anEntry.getValue();
				if(key == 1)
				{
					//CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetsOfLength1 = new CopyOnWriteArraySet<SemanticTagPattern>();
					for(DistributionFrequentPattern aFrequentPattern : value)
					{
						int distributionCount = 0;
						LinkedList<Integer> tags = new LinkedList<Integer>();
						//CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfLength1 = semanticTagPatternSetsByLength.get(0);
						convertOnePatternToSemanticTagPatterns(semanticTagPatternSetsByLength.get(0), aFrequentPattern, distributionCount, tags);
					}
					
					double threshold = value.size() / 10.0 * numberST;
					CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfLength1 = semanticTagPatternSetsByLength.get(0);
					CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfLength1AfterDeletion = new CopyOnWriteArraySet<SemanticTagPattern>();
					for(SemanticTagPattern aTagPatternOfLength1 : semanticTagPatternSetOfLength1)
					{						
						if(aTagPatternOfLength1.getRelativeSupport() >= threshold)
						{
							semanticTagPatternSetOfLength1AfterDeletion.add(aTagPatternOfLength1);
						}
					}
					semanticTagPatternSetsByLength.set(0, semanticTagPatternSetOfLength1AfterDeletion);
				}
				else
				{
					CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfLengthShorterBy1 = semanticTagPatternSetsByLength.get(key - 2);
					for(SemanticTagPattern aSemanticPatternOfLengthShorterBy1 : semanticTagPatternSetOfLengthShorterBy1)
					{					
						for(DistributionFrequentPattern aPatternOfLengthKey : value)
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
					
					double threshold = value.size()/(10.0*Math.pow(numberST, key));
					CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfLengthKey = semanticTagPatternSetsByLength.get(key - 1);
					CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfLengthKeyAfterDeletion = new CopyOnWriteArraySet<SemanticTagPattern>();
					for(SemanticTagPattern aTagPatternOfLength1 : semanticTagPatternSetOfLengthKey)
					{						
						if(aTagPatternOfLength1.getRelativeSupport() >= threshold)
						{
							semanticTagPatternSetOfLengthKeyAfterDeletion.add(aTagPatternOfLength1);
						}
					}
					semanticTagPatternSetsByLength.set(key - 1, semanticTagPatternSetOfLengthKeyAfterDeletion);
				}*/
			}
			
			
			/*for(CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatternSetOfSomeLength : semanticTagPatternSetsByLength)
			{
				tagPatterns.addAll(semanticTagPatternSetOfSomeLength);
			}*/			 
			
			return new SemanticTagPatternSet(tagPatterns);
		}		
	}
	
	private void convertOnePatternToSemanticTagPatterns(CopyOnWriteArraySet<SemanticTagPattern> semanticTagPatterns, DistributionFrequentPattern aFrequentPattern, int distributionCount, LinkedList<Integer> tags)
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
			/*tags.removeLast();*/
			
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
				/*System.out.println(newSemanticTagPattern);*/
				if(!(semanticTagPatterns.add(newSemanticTagPattern)))
				{
					/*System.out.println("executed");*/
					for(SemanticTagPattern aSemanticTagPattern : semanticTagPatterns)
					{
						if(aSemanticTagPattern.equals(newSemanticTagPattern))
						{
							aSemanticTagPattern.setRelativeSupport(aSemanticTagPattern.getRelativeSupport() + newSemanticTagPattern.getRelativeSupport());
							break;
						}
					}
				}				
				
				/*distributionCount--;*/
				tags.removeLast();
			}
		}
		
		distributionCount--;
		if(tags.size() > 0)
		{
			tags.removeLast();
		}		
	}
	
	@Override
	public String toString()
	{
		String aString = "";
		for(DistributionFrequentPattern aFrequentPattern : frequentPatterns)
		{
			aString += aFrequentPattern.toString();
		}
		return aString;
	}
	
	public double functionF()
	{
		double sumOfSupports = 0.0;
		for(DistributionFrequentPattern aFrequentPattern : frequentPatterns)
			sumOfSupports += Math.pow(aFrequentPattern.getDistributions().size(), 2.0) * aFrequentPattern.getRelativeSupport();
			
		return sumOfSupports;
	}
	
	public void mergeLSsimilarPatterns()
	{	
		LinkedList<DistributionFrequentPattern> patternsAfterMerging = new LinkedList<DistributionFrequentPattern>();
		
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
			SimpleWeightedGraph<DistributionFrequentPattern, DefaultWeightedEdge> patternsGraph/*WithLengthIndex*/ = new SimpleWeightedGraph<DistributionFrequentPattern, DefaultWeightedEdge>(DefaultWeightedEdge.class);
			/*LinkedList<FrequentPattern> allPatternsWithLengthKey = anEntry.getValue();*/
			for(DistributionFrequentPattern aVertex : frequentPatterns/*set*//*allPatternsWithLengthKey*/)
			{
				patternsGraph/*WithLengthIndex*/.addVertex(aVertex);
			}
			
			for(DistributionFrequentPattern aVertex : frequentPatterns/*set*//*allPatternsWithLengthKey*/)
			{
				for(DistributionFrequentPattern anotherVertex : frequentPatterns/*set*//*allPatternsWithLengthKey*/)
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
				BronKerboschCliqueFinder<DistributionFrequentPattern, DefaultWeightedEdge> aCliqueFinder = new BronKerboschCliqueFinder<DistributionFrequentPattern, DefaultWeightedEdge>(patternsGraph/*WithLengthIndex*/);
				Collection<Set<DistributionFrequentPattern>> allMaximumCliques = aCliqueFinder.getBiggestMaximalCliques();
				Set<DistributionFrequentPattern> optimalMaximumClique = null;
				double minimumTwiceSumOfEdgeWeights = Double.MAX_VALUE;

				if(allMaximumCliques.size() > 1)
				{				
					for(Set<DistributionFrequentPattern> aMaximumClique : allMaximumCliques)
					{
						double twiceSumOfEdgeWeights = 0.0;
						for(DistributionFrequentPattern vertex1 : aMaximumClique)
						{
							for(DistributionFrequentPattern vertex2 : aMaximumClique)
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

				DistributionFrequentPattern newPattern = null; 
				boolean firstTime = true;				
				for(DistributionFrequentPattern aPattern : optimalMaximumClique)
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
	
	public Bijection findOptimalBijection(DistributionFrequentPatternSet anotherFrequentPatternSet)
	{
		Bijection optimalBijection = new Bijection();
		
		/*TreeMap<Integer, LinkedList<FrequentPattern>> map1 = buildMapByLength();
		TreeMap<Integer, LinkedList<FrequentPattern>> map2 = anotherFrequentPatternSet.buildMapByLength();		
		
		for(int index = 1; index <= ((map1.size() < map2.size()) ? map1.size() : map2.size()); index++)
		{*/
			LinkedList<PatternPair> allLSsimilarPatternPairs/*WithLengthIndex */= new LinkedList<PatternPair>();
			for(DistributionFrequentPattern pattern1 : frequentPatterns)
			{
				for(DistributionFrequentPattern pattern2 : anotherFrequentPatternSet.getFrequentPatterns())
				{
					if(pattern1.isLSsimilar(pattern2))
					{
						allLSsimilarPatternPairs/*WithLengthIndex*/.add(new PatternPair(pattern1, pattern2));
					}						
				}
			}
			
			Collections.sort(allLSsimilarPatternPairs/*WithLengthIndex*/);
			/*Bijection optimalBijectionWithLengthIndex = new Bijection();*/
			HashSet<DistributionFrequentPattern> allPatternsFound = new HashSet<DistributionFrequentPattern>();
			for(PatternPair aPatternPair : allLSsimilarPatternPairs/*WithLengthIndex*/)
			{
				if(!(allPatternsFound.contains(aPatternPair.getPattern1()) || allPatternsFound.contains(aPatternPair.getPattern2())))
				{
					optimalBijection/*WithLengthIndex*/.getCommonPatternSet().add(aPatternPair);
					allPatternsFound.add(aPatternPair.getPattern1());
					allPatternsFound.add(aPatternPair.getPattern2());
				}
			}	
			
			/*optimalBijection.getCommonPatternSet().addAll(optimalBijectionWithLengthIndex.getCommonPatternSet());*/
		/*}*/
		
		/*for(int index = 1; index <= ((map1.size() < map2.size()) ? map1.size() : map2.size()); index++)
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
		}*/
		
		return optimalBijection;
	}
}
