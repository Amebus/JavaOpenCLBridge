package flinkOcl.buildEngine.kernelBuilders;

import flinkOcl.buildEngine.KernelBuilderOptions;

public class MapBuilder extends KernelWithOutputTupleBuilder
{
	
	public MapBuilder(KernelBuilderOptions pKernelBuilderOptions)
	{
		super(pKernelBuilderOptions);
	}
	
	@Override
	protected String getOutputSection()
	{
		return null;
	}
	
}
