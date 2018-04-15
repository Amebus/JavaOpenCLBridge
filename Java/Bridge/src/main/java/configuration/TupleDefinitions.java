package configuration;

import Commons.IBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public class TupleDefinitions
{
	
	private Map<String, TupleDefinition> mTupleDefinitions;
	
	public TupleDefinitions(Map<String, TupleDefinition> pTupleDefinitions)
	{
		mTupleDefinitions = pTupleDefinitions;
	}
	
	public Iterable<String> getTupleNames()
	{
		return mTupleDefinitions.keySet();
	}
	
	public Iterable<TupleDefinition> asIterable()
	{
		return mTupleDefinitions.values();
	}
	
	public TupleDefinition getTupleDefinition(String pTupleName)
	{
		return mTupleDefinitions.get(pTupleName);
	}
	
	public static class TupleDefinitionsBuilder implements IBuilder<TupleDefinitions>
	{
		private Map<String, TupleDefinition> mTupleDefinitionMap;
		
		
		public TupleDefinitionsBuilder()
		{
			this(0);
		}
		
		public TupleDefinitionsBuilder(int pTuplesCount)
		{
			if( pTuplesCount < 1)
			{
				throw new IllegalArgumentException("You must specify at least one tuple definition");
			}
			
			mTupleDefinitionMap = new LinkedHashMap<>(pTuplesCount);
		}
		
		public TupleDefinitionsBuilder addTupleDefinition(TupleDefinition pTupleDefinition)
		{
			mTupleDefinitionMap.put(pTupleDefinition.getName(), pTupleDefinition);
			return this;
		}
		
		@Override
		public TupleDefinitions build()
		{
			return new TupleDefinitions(mTupleDefinitionMap);
		}
	}
}
