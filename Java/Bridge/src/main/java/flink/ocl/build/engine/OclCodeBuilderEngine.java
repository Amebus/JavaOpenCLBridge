package flink.ocl.build.engine;

import configuration.json.OclSettings;

public class OclCodeBuilderEngine
{
	
	private OclSettings mOclSettings;
	private Iterable<OclKernel> mKernels;
	
	public OclCodeBuilderEngine(OclSettings pOclSettings, Iterable<OclKernel> pKernels)
	{
		mOclSettings = pOclSettings;
		mKernels = pKernels;
	}
	
	public CppLibraryInfo generateCppLibraryCode()
	{
		CppLibraryInfo vResult = new CppLibraryInfo("");
		
		//TODO generare libreria cpp da compilare
		
		return vResult;
	}
}
