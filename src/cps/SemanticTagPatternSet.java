package cps;
import java.util.concurrent.CopyOnWriteArraySet;

public class SemanticTagPatternSet 
{
	private CopyOnWriteArraySet<SemanticTagPattern> patterns;
	
	public SemanticTagPatternSet()
	{
		patterns = new CopyOnWriteArraySet<SemanticTagPattern>();
	}
	
	public SemanticTagPatternSet(CopyOnWriteArraySet<SemanticTagPattern> somePatterns)
	{
		patterns = somePatterns;
	}
	
	public CopyOnWriteArraySet<SemanticTagPattern> getPatterns()
	{
		return patterns;
	}
	
	@Override
	public String toString()
	{
		String aString = "";
		for(SemanticTagPattern aPattern : patterns)
		{
			aString += aPattern + "\n";
		}
		return aString;
	}
	
	public double calculateSimilaritySumsOfCommonPatterns()
	{
		double sum = 0.0;
		for(SemanticTagPattern aPattern : patterns)
		{
			CommonSemanticTagPattern aCommonPattern = (CommonSemanticTagPattern)aPattern;
			double relativeSupport = aCommonPattern.getRelativeSupport();
			double relativeSupport2 = aCommonPattern.getRelativeSupport2();
			if(relativeSupport < relativeSupport2)
			{
				sum += relativeSupport / relativeSupport2;
			}
			else
			{
				sum += relativeSupport2 / relativeSupport;
			}
		}
		return sum;
	}
	
	public SemanticTagPatternSet intersect(SemanticTagPatternSet anotherSet)
	{
		CopyOnWriteArraySet<SemanticTagPattern> commonPatterns = new CopyOnWriteArraySet<SemanticTagPattern>();
		for(SemanticTagPattern aPattern : patterns)
		{
			for(SemanticTagPattern anotherPattern : anotherSet.getPatterns())
			{
				if(aPattern.equals(anotherPattern))
				{
					commonPatterns.add(new CommonSemanticTagPattern(aPattern, anotherPattern));
				}
			}
		}
		return new SemanticTagPatternSet(commonPatterns);
	}
	
	public double functionF()
	{
		double sum = 0.0;
		for(SemanticTagPattern aPattern : patterns)
		{
			//sum += Math.pow(aPattern.getSemanticTags().length, 2.0) * aPattern.getRelativeSupport();
			sum += aPattern.getSemanticTags().length * aPattern.getRelativeSupport();
		}
		return sum;
	}
	
	public double functionF(int user)
	{
		double sum = 0.0;
		if(user == 1)
		{
			for(SemanticTagPattern aPattern : patterns)
			{
				//sum += Math.pow(aPattern.getSemanticTags().length, 2.0) * aPattern.getRelativeSupport();
				sum += aPattern.getSemanticTags().length * aPattern.getRelativeSupport();
			}
		}
		else
		{
			for(SemanticTagPattern aPattern : patterns)
			{
				//sum += Math.pow(aPattern.getSemanticTags().length, 2.0) * ((CommonSemanticTagPattern)aPattern).getRelativeSupport2();
				sum += aPattern.getSemanticTags().length * ((CommonSemanticTagPattern)aPattern).getRelativeSupport2();
			}
		}
		return sum;
	}
	
	public double supSim() {
		double sumDif = 0.0;
		double sum = 0.0;
		
		for (SemanticTagPattern aCommonSemanticPattern : patterns) {
			int length = aCommonSemanticPattern.getSemanticTags().length;
			sumDif += Math.abs(aCommonSemanticPattern.getRelativeSupport() - ((CommonSemanticTagPattern) aCommonSemanticPattern).getRelativeSupport2())/* * length * length*/;
			sum += (aCommonSemanticPattern.getRelativeSupport() + ((CommonSemanticTagPattern) aCommonSemanticPattern).getRelativeSupport2())/* * length * length*/;
		}
		
		if (sum == 0.0) {
			return 1.0;
		}
		
		return 1.0 - sumDif / sum;
	}
}
