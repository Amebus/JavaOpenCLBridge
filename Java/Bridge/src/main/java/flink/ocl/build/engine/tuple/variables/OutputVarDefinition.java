package flink.ocl.build.engine.tuple.variables;

import configuration.CTType;

public class OutputVarDefinition extends VarDefinition
{
	public OutputVarDefinition(CTType pType, int pIndex)
	{
		super(pType, pIndex);
	}
	
	@Override
	public String getName()
	{
		return getName("_r");
	}
	
	@Override
	public int getLength()
	{
		return getCType().getMaxByteDimension();
	}
	
	@Override
	public boolean isInputVar()
	{
		return false;
	}
}
