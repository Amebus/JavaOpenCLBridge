package flinkOcl.buildEngine.kernelBuilders.utility;

import Commons.utility.StreamUtility;
import configuration.CTType;
import flinkOcl.buildEngine.tupleVariables.VarDefinition;

import java.util.stream.Collectors;

public class StringVarDefinitionLine extends VarDefinitionKernelLine
{
	public StringVarDefinitionLine(Iterable<VarDefinition> pVarDefinitions)
	{
		super(getType(pVarDefinitions), getStringVariableNames(pVarDefinitions));
	}
	
	private static Iterable<String> getStringVariableNames(Iterable<VarDefinition> pVarDefinitions)
	{
		return StreamUtility.streamFrom(pVarDefinitions)
							.filter(x -> x.getCType().isString())
							.map(VarDefinition::getName)
							.collect(Collectors.toList());
	}
	
	private static String getType(Iterable<VarDefinition> pVarDefinitions)
	{
		String vResult = "";
		if(StreamUtility.streamFrom(pVarDefinitions).anyMatch( x -> x.getCType().isString()))
		{
			vResult = CTType.CTypes.STRING;
		}
		return vResult;
	}
}
