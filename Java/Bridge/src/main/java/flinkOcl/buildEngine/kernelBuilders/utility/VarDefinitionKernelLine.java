package flinkOcl.buildEngine.kernelBuilders.utility;

import Commons.StreamUtility;
import java.util.List;
import java.util.stream.Collectors;

public abstract class VarDefinitionKernelLine
{
	private String mType;
	private List<String> mVarNames;
	
	public VarDefinitionKernelLine(String pType, Iterable<String> pVarDefinitions)
	{
		mType = pType;
		mVarNames = StreamUtility.streamFrom(pVarDefinitions).collect(Collectors.toList());
	}
	
	public boolean isEmtpy()
	{
		return mVarNames.size() == 0;
	}
	
	public String getType()
	{
		return mType;
	}
	
	public Iterable<String> getVarNames()
	{
		return mVarNames;
	}
	
	public String toKernelLine()
	{
		return mType +
			   " " +
			   StreamUtility.streamFrom(mVarNames).reduce((x, y) -> x + ", " + y).orElseGet( () ->"") +
			   ";\n";
	}
}
