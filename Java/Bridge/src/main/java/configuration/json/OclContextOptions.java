package configuration.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import configuration.IOclContextOptions;

import java.nio.file.Path;

public class OclContextOptions implements IOclContextOptions
{
	
	@SerializedName("kernelsBuildFolder")
	@Expose
	private String mKernelsBuildFolder;
	
	public String getKernelsBuildFolder()
	{
		return mKernelsBuildFolder;
	}
}
