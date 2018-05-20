package flinkOcl.buildEngine.kernelBuilders;

import configuration.ITupleDefinition;
import configuration.json.JsonTupleDefinition;
import flinkOcl.buildEngine.KernelBuilder;
import flinkOcl.buildEngine.KernelBuilderOptions;

public abstract class KernelWithOutputTupleBuilder extends KernelBuilder
{
	
	public KernelWithOutputTupleBuilder(KernelBuilderOptions pKernelBuilderOptions)
	{
		super(pKernelBuilderOptions);
	}
	
	protected ITupleDefinition getOutputTuple()
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
	protected String getOutputVarDeclaration()
	{
		StringBuilder vBuilder = new StringBuilder();
		
		getOutputTupleVariablesAsResult().forEach( x -> vBuilder.append(x)
																.append(";\n"));
		
		return vBuilder.toString();
	}
}
