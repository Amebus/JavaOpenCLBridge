package flinkOcl;

import configuration.*;
import flinkOcl.buildEngine.BuildEngine;

public class OclContext
{
	private ISettingsRepository mSettingsRepository;
	private ITupleDefinitionsRepository mTupleDefinitionsRepository;
	private IUserFunctionsRepository mFunctionRepository;
	
	private String mApplicationDirectory;
	
	public OclContext(ISettingsRepository pSettingsRepository,
					  ITupleDefinitionsRepository pTupleDefinitionsRepository,
					  IUserFunctionsRepository pRepository)
	{
		mApplicationDirectory = System.getProperty("user.dir");
		
		mSettingsRepository = pSettingsRepository;
		mTupleDefinitionsRepository = pTupleDefinitionsRepository;
		mFunctionRepository = pRepository;
	}
	
	public String getApplicationDirectory()
	{
		return mApplicationDirectory;
	}
	
	public void open()
	{
		createAndBuildAndLoadKernels();
	}
	
	public void close()
	{
		deleteLocalFiles();
	}

	private void createAndBuildAndLoadKernels()
	{
		new BuildEngine(mSettingsRepository)
				.generateKernels(mTupleDefinitionsRepository, mFunctionRepository.getUserFunctions())
				.loadCppLibrary();
	}
	
	private void deleteLocalFiles()
	{
		//TODO usare getApplicationDirectory per eliminare i file aggiunti
	}
}
