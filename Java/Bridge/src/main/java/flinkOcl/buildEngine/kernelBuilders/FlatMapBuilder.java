package flinkOcl.buildEngine.kernelBuilders;

import flinkOcl.buildEngine.KernelBuilderOptions;

public class FlatMapBuilder extends KernelWithOutputTupleBuilder
{
	public FlatMapBuilder(KernelBuilderOptions pKernelBuilderOptions)
	{
		super(pKernelBuilderOptions);
	}
	
	@Override
	protected String getOutputSection()
	{
		return null;
	}
	
}
