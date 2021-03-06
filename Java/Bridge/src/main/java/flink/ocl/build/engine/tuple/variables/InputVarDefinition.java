package flink.ocl.build.engine.tuple.variables;

import configuration.CTType;

public class InputVarDefinition extends VarDefinition
{
	
	public InputVarDefinition(CTType pType, int pIndex)
	{
		super(pType, pIndex);
	}
	
	@Override
	public String getName()
	{
		return getName("_t");
	}
	
	@Override
	public int getLength()
	{
		return getCType().getByteDimension();
	}
	
	@Override
	public boolean isInputVar()
	{
		return true;
	}
}
