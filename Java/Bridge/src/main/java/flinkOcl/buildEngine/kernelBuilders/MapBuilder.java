package flinkOcl.buildEngine.kernelBuilders;

import configuration.OclContextOptions;
import configuration.OclKernelOptions;
import configuration.TupleDefinitions;
import flinkOcl.IUserFunction;
import flinkOcl.buildEngine.KernelBuilderOptions;
import flinkOcl.buildEngine.OclKernel;

public class MapBuilder extends KernelWithOutputTupleBuilder
{
	
	public MapBuilder(KernelBuilderOptions pKernelBuilderOptions)
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
		return null;
	}
}
