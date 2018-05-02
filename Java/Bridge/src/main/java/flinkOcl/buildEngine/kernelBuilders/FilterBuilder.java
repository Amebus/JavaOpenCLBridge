package flinkOcl.buildEngine.kernelBuilders;

import flinkOcl.buildEngine.KernelBuilderOptions;

public class FilterBuilder extends KernelWithoutOutputTupleBuilder
{
	public FilterBuilder(KernelBuilderOptions pKernelBuilderOptions)
	{
		super(pKernelBuilderOptions);
	}
	
	@Override
	protected String getKernelSignature()
	{
		return null;
	}
	
	@Override
	protected String getOutputVarDeclaration()
	{
		return "int _r0 = 0;";
	}
	
	@Override
	protected String getOutputSection()
	{
		return RESULT + "[" + G_ID + "] = _r0";
	}
}
