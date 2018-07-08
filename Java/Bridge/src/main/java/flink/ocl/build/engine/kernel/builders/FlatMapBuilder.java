package flink.ocl.build.engine.kernel.builders;

import flink.ocl.build.engine.KernelBuilderOptions;

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
