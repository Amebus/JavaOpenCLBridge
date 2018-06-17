package flinkOcl.buildEngine.kernelBuilders;

import configuration.ITupleDefinition;
import configuration.json.JsonTupleDefinition;
import flinkOcl.buildEngine.KernelBuilder;
import flinkOcl.buildEngine.KernelBuilderOptions;
import flinkOcl.buildEngine.tupleVariables.OutputVarDefinition;

import java.util.ArrayList;

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
//		return getTupleVariables(getOutputTuple(),
//								 (r, t, i) -> r.add(new OutputVarDefinition(t, i)));
		return new ArrayList<>();
	}
	
	protected Iterable<String> getOutputTupleVariablesAsResult()
	{
//		return getTupleVariables(getInputTuple(),
//								 (r, t, i) -> r.add(new OutputVarDefinition(t, i)));
		return new ArrayList<>();
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
