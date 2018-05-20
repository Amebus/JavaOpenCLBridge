package configuration.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import configuration.IOclContextOptions;
import configuration.IOclKernelsOptions;
import configuration.IOclSettings;

public class OclSettings implements IOclSettings
{
	@SerializedName("contextOptions")
	@Expose
	private OclContextOptions mContextOptions;
	
	@SerializedName("kernelOptions")
	@Expose
	private OclKernelsOptions mOclKernelOptions;
	
	@Override
	public IOclContextOptions getContextOptions()
	{
		return mContextOptions;
	}
	
	@Override
	public IOclKernelsOptions getOclKernelOptions()
	{
		return mOclKernelOptions;
	}
}
