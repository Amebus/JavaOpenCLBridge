package flinkOcl.buildEngine.kernelBuilders;

import configuration.TupleDefinition;
import flinkOcl.buildEngine.KernelBuilder;
import flinkOcl.buildEngine.KernelBuilderOptions;
import flinkOcl.buildEngine.OclKernel;

public abstract class KernelWithoutOutputTupleBuilder extends KernelBuilder
{
	public KernelWithoutOutputTupleBuilder(KernelBuilderOptions pKernelBuilderOptions)
	{
		super(pKernelBuilderOptions);
	}
	
	@Override
	protected String getOutputVarDeclaration()
	{
		StringBuilder vBuilder = new StringBuilder();
		
		getOutputSectionLines().forEach( x -> vBuilder.append(x)
													  .append(";\n"));
		
		return vBuilder.toString();
	}
	
	@Override
	protected String getOutputSection()
	{
		StringBuilder vBuilder = new StringBuilder();
		
		getOutputSectionLines().forEach( x -> vBuilder.append(x)
													  .append(";\n"));
		
		return vBuilder.toString();
	}
	
	protected Iterable<String> getOutputSectionLines()
	{
		return getInputTupleVariablesAsResult();
	}
}
