package flink.ocl.build.engine;

import configuration.TType;
import configuration.json.JsonTupleDefinition;

import java.util.Iterator;

public class TupleCodeGenerator
{
	
	private final String INT_DES_MACRO = "INT_DES";
	private final String DOUBLE_DES_MACRO = "DOUBLE_DES";
	private final String STRING_DES_MACRO = "STRING_DES";
	
	private final String _A = "int _gId = get_global_id(0);\n";
	private final String A = "$type _T$Index;\n";
	private final String B = "_T$Index = $Macro($OclIndex);\n";
	private final String C = "int _i = _gId;\n";
	
	private final String KERNEL_SIGNATURE_A = "__kernel void " +
											  "$name(" +
											  	"__global signed char* _data, " +
											  	"__global signed char* _result" +
											  ")\n";
	
	private final String KERNEL_SIGNATURE_B = "__kernel void " +
											  "$name(" +
											  	"__global signed char* _data, " +
											  	"__global int* _dataIndexes, " +
											  	"__global signed char* _result" +
											  ")\n";
	
	public String GetDeserialzationCode(JsonTupleDefinition pTupleDefinition)
	{
		StringBuilder vVariableDefinitions = new StringBuilder();
		StringBuilder  vDeserializationCode = new StringBuilder();
		StringBuilder vBuilder = new StringBuilder();
		Iterator<TType> vIterator = pTupleDefinition.cIterator();
		String vTName = "_T";
		int vI  = 0;
		int vOclI = 1 + pTupleDefinition.getArity();
		TType vTType;
		
		while (vIterator.hasNext())
		{
			vTType = vIterator.next();
			
			vVariableDefinitions.append(vTType.getT())
								.append(" ")
								.append(vTName)
								.append(vI)
								.append(";")
								.append("\n");
			
			if(vTType.isInteger())
			{
				vDeserializationCode.append(vTName)
									.append(vI)
									.append(" = ")
									.append(INT_DES_MACRO)
									.append("(");
			}
			
			vI++;
		}
		
		return vBuilder.toString();
	}
	
	public String GetSerialzationCode(JsonTupleDefinition pTupleDefinition)
	{
		StringBuilder vBuilder = new StringBuilder();
		
		
		return vBuilder.toString();
	}
	
	
}
