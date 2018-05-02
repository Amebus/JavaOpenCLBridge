package flinkOcl.buildEngine;

import configuration.OclSettings;
import configuration.TupleDefinitions;
import flinkOcl.IUserFunction;

public class BuildEngine
{
	
	private OclSettings mOclSettings;
	private Iterable<OclKernel> mKernels;
	private CppLibraryInfo mCppLibraryInfo;
	
	public BuildEngine(OclSettings pOclSettings)
	{
		mOclSettings = pOclSettings;
	}
	
	public BuildEngine generateKernels(TupleDefinitions pTupleDefinitions, Iterable<? extends IUserFunction> pUserFunctions)
	{
		mCppLibraryInfo = new KernelCodeBuilderEngine(mOclSettings, pTupleDefinitions, pUserFunctions).generateKernels();
		return this;
	}
	
	public void loadCppLibrary()
	{
//		System.loadLibrary("OCL");
	}
	
}
