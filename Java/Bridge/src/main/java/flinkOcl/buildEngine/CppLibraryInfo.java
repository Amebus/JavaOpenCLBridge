package flinkOcl.buildEngine;

public class CppLibraryInfo
{
	private String mKernelsFolder;
	
	public CppLibraryInfo(String pKernelsFolder)
	{
		mKernelsFolder = pKernelsFolder;
	}
	
	public String getKernelsFolder()
	{
		return mKernelsFolder;
	}
}
