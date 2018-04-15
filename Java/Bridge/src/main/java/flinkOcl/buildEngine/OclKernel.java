package flinkOcl.buildEngine;

public class OclKernel
{
	
	private String mName;
	private String mCode;
	
	public OclKernel(String pName, String pCode)
	{
		mName = pName;
		mCode = pCode;
	}
	
	public String getName()
	{
		return mName;
	}
	
	public String getCode()
	{
		return mCode;
	}
}
