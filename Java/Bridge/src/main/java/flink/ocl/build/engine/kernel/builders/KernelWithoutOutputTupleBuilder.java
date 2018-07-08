package flink.ocl.build.engine.kernel.builders;

import flink.ocl.build.engine.KernelBuilderOptions;
import flink.ocl.build.engine.KernelBuilder;

import java.util.ArrayList;

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
//		getInputTupleVariablesAsResult()
		return new ArrayList<>();
	}
}
