package flinkOcl.buildEngine.kernelBuilders;

import flinkOcl.buildEngine.KernelBuilderOptions;

public class ReduceBuilder extends KernelWithoutOutputTupleBuilder
{
	public ReduceBuilder(KernelBuilderOptions pKernelBuilderOptions)
	{
		super(pKernelBuilderOptions);
	}
	
	@Override
	protected String getKernelSignature()
	{
		return null;
	}
}
