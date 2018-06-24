package flinkOcl;

import configuration.*;
import flinkOcl.buildEngine.BuildEngine;
import flinkOcl.buildEngine.CppLibraryInfo;
import ocl.bridge.OclBridge;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class OclContext
{
	private ISettingsRepository mSettingsRepository;
	private ITupleDefinitionsRepository mTupleDefinitionsRepository;
	private IUserFunctionsRepository mFunctionRepository;
	
//	private String mApplicationDirectory;
	private CppLibraryInfo mCppLibraryInfo;
	
	private OclBridge mOclBridgeContext;
	
	public OclContext(ISettingsRepository pSettingsRepository,
					  ITupleDefinitionsRepository pTupleDefinitionsRepository,
					  IUserFunctionsRepository pRepository)
	{
//		mApplicationDirectory = System.getProperty("user.dir");
		
		mSettingsRepository = pSettingsRepository;
		mTupleDefinitionsRepository = pTupleDefinitionsRepository;
		mFunctionRepository = pRepository;
		mOclBridgeContext = new OclBridge();
	}
	
//	public String getApplicationDirectory()
//	{
//		return mApplicationDirectory;
//	}
	
	public void open()
	{
		createAndBuildAndLoadKernels();
		
		mOclBridgeContext.initialize(mCppLibraryInfo.getKernelsFolder());
	}
	
	public void close()
	{
		mOclBridgeContext.dispose();
		
		if(mSettingsRepository.getContextOptions().hasToRemoveTempFoldersOnClose())
			deleteLocalFiles();
	}

	private void createAndBuildAndLoadKernels()
	{
		BuildEngine vBuildEngine = new BuildEngine(mSettingsRepository)
				.generateKernels(mTupleDefinitionsRepository, mFunctionRepository.getUserFunctions());
		
		vBuildEngine.loadCppLibrary();
		mCppLibraryInfo = vBuildEngine.getCppLibraryInfo();
	}
	
	private void deleteLocalFiles()
	{
		Path vKernelsFolder = Paths.get(mCppLibraryInfo.getKernelsFolder());
		
		File vToFile = vKernelsFolder.toFile();
		
		if (vToFile.isFile())
			return;
		
		boolean vAllFilesDeleted = true;
		for (File vFile : Objects.requireNonNull(vToFile.listFiles()))
		{
			vAllFilesDeleted &= vFile.delete();
		}
		
		if (vAllFilesDeleted)
			vToFile.delete();
	}
}
