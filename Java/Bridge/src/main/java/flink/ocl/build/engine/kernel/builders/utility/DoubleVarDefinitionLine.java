package flink.ocl.build.engine.kernel.builders.utility;

import commons.utility.StreamUtility;
import configuration.CTType;
import flink.ocl.build.engine.tuple.variables.VarDefinition;

import java.util.stream.Collectors;

public class DoubleVarDefinitionLine extends VarDefinitionKernelLine
{
	public DoubleVarDefinitionLine(Iterable<VarDefinition> pVarDefinitions)
	{
		super(getType(pVarDefinitions), getDoubleVariableNames(pVarDefinitions));
	}
	
	private static Iterable<String> getDoubleVariableNames(Iterable<VarDefinition> pVarDefinitions)
	{
		return StreamUtility.streamFrom(pVarDefinitions)
							.filter(x -> x.getCType().isDouble())
							.map(VarDefinition::getName)
							.collect(Collectors.toList());
				
	}
	
	private static String getType(Iterable<VarDefinition> pVarDefinitions)
	{
		String vResult = "";
		if(StreamUtility.streamFrom(pVarDefinitions).anyMatch( x -> x.getCType().isDouble()))
		{
			vResult = CTType.CTypes.DOUBLE;
		}
		return vResult;
	}
}
