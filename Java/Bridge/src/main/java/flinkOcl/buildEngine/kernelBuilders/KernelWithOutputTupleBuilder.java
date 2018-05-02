package flinkOcl.buildEngine.kernelBuilders;

import Commons.IBuilder;
import configuration.OclContextOptions;
import configuration.OclKernelOptions;
import configuration.TupleDefinition;
import configuration.TupleDefinitions;
import flinkOcl.IUserFunction;
import flinkOcl.buildEngine.KernelBuilder;
import flinkOcl.buildEngine.KernelBuilderOptions;
import flinkOcl.buildEngine.OclKernel;

public abstract class KernelWithOutputTupleBuilder extends KernelBuilder
{
	
	public KernelWithOutputTupleBuilder(KernelBuilderOptions pKernelBuilderOptions)
	{
		super(pKernelBuilderOptions);
	}
	
	protected TupleDefinition getOutputTuple()
	{
		return getTupleDefinitions().getTupleDefinition(getUserFunction().getOutputTupleName());
	}
	
	protected Iterable<String> getOutputTupleVariablesAsInput()
	{
		return getTupleVariables(getOutputTuple(), "t");
	}
	
	protected Iterable<String> getOutputTupleVariablesAsResult()
	{
		return getTupleVariables(getOutputTuple(), "r");
	}
	
	@Override
	protected String getOutputSection()
	{
		StringBuilder vBuilder = new StringBuilder();
		
		getOutputTupleVariablesAsResult().forEach( x -> vBuilder.append(x)
																.append(";\n"));
		
		return vBuilder.toString();
	}
}
