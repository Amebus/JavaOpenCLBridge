package configuration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.nio.file.Path;

public class OclContextOptions
{
	
	@SerializedName("kernelsBuildFolder")
	@Expose
	private String mKernelsBuildFolder;
	
	public String getKernelsBuildFolder()
	{
		return mKernelsBuildFolder;
	}
}
