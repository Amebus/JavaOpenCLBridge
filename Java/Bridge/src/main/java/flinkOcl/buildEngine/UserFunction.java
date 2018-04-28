package flinkOcl.buildEngine;

import utils.ObjectHelper;

public abstract class UserFunction implements IUserFunction
{
	@Override
	public boolean isMap()
	{
		return getType().equals(MAP);
	}
	
	@Override
	public boolean isFlatMap()
	{
		return getType().equals(FLAT_MAP);
	}
	
	@Override
	public boolean isFilter()
	{
		return getType().equals(FILTER);
	}
	
	@Override
	public boolean isReduce()
	{
		return getType().equals(REDUCE);
	}
	
	protected void checkType()
	{
		if(isOfUnknownType())
			throw new IllegalArgumentException("The function \"" + getName() + "\" is of type \"" + getType() + "\" which is unknown.");
	}
	
	protected void checkOutputTuple()
	{
		if(doesNotHaveOutputTupleSpecified() && shouldHaveOutputTuple())
		{
			throw new IllegalArgumentException("The function \"" + getName() + "\" is a " + getType() + " and therefore should return a tuple.\n" +
											   "Change the function type or specify an output tuple.");
		}
	}
	
	protected boolean doesNotHaveOutputTupleSpecified()
	{
		return ObjectHelper.isNullOrWhiteSpace(getOutputTupleName());
	}
	
	protected boolean shouldHaveOutputTuple()
	{
		return isMap() || isFlatMap();
	}
}
