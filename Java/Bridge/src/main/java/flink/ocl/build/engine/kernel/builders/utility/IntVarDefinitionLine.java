package flink.ocl.build.engine.kernel.builders.utility;

import commons.utility.StreamUtility;
import configuration.CTType;
import flink.ocl.build.engine.tuple.variables.VarDefinition;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntVarDefinitionLine extends VarDefinitionKernelLine
{
	public IntVarDefinitionLine(Iterable<VarDefinition> pVarDefinitions)
	{
		super(getType(pVarDefinitions), getIntVariables(pVarDefinitions));
	}
	
	private static String getType(Iterable<VarDefinition> pVarDefinitions)
	{
		String vResult = "";
		if(StreamUtility.streamFrom(pVarDefinitions)
						.anyMatch( x -> x.getCType().isDouble() || x.getCType().isString()))
		{
			vResult = CTType.CTypes.INTEGER;
		}
		return vResult;
	}
	
	private static Iterable<String> getIntVariables(Iterable<VarDefinition> pVarDefinitions)
	{
		
		Stream<VarDefinition> vStream = StreamUtility.streamFrom(pVarDefinitions)
				.filter(x -> x.getCType().isInteger() || x.getCType().isString());
		
		return new ArrayList<>(vStream.map(x ->
								 {
									 VarDefinition vVar = x;
									 if (x.getCType().isString())
									 {
										 vVar = new VarDefinition(new CTType.Builder(CTType.CTypes.INTEGER).build(),
																  x.getIndex())
										 {
											 @Override
											 public String getName()
											 {
												 return getName("_sl");
											 }
	
											 @Override
											 public int getLength()
											 {
												 return getCType().getByteDimension();
											 }
	
											 @Override
											 public boolean isInputVar()
											 {
												 return true;
											 }
										 };
									 }
									 return vVar.getName();
								 }).collect(Collectors.toList()));
	}
	
}
