package configuration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OclSettings
{
	@SerializedName("contextOptions")
	@Expose
	private OclContextOptions mContextOptions;
	
	@SerializedName("kernelOptions")
	@Expose
	private OclKernelOptions mOclKernelOptions;
	
	public OclContextOptions getContextOptions()
	{
		return mContextOptions;
	}
	
	public OclKernelOptions getOclKernelOptions()
	{
		return mOclKernelOptions;
	}
}
