package flinkOcl;

import configuration.*;

import java.io.FileNotFoundException;

public class OclContext
{
	private LoadSettingsDirective.ILoadSettingsFunction mLoadSettingsFunction;
	private Settings mSettings;
	private OclSettings mOclSettings;
	private TupleDefinitions mTupleDefinitions;
	private IUserFunctionReadRepository mFunctionRepository;
	private String mApplicationDirectory;
	
	public OclContext(IUserFunctionReadRepository pRepository)
	{
		this(pRepository, new LoadSettingsDirective(System.getProperty("user.dir")));
	}
	
	public OclContext(IUserFunctionReadRepository pRepository, LoadSettingsDirective pLoadSettingsDirective)
	{
		mApplicationDirectory = System.getProperty("user.dir");
		mFunctionRepository = pRepository;
		mLoadSettingsFunction = pLoadSettingsDirective.getLoadFunction();
	}
	
	public String getApplicationDirectory()
	{
		return mApplicationDirectory;
	}
	
	public void open() throws FileNotFoundException
	{
		mSettings = loadSettings();
		mOclSettings = mSettings.getOclSettings();
		mTupleDefinitions = mSettings.getTupleDefinitions();
		createAndBuildAndLoadKernels();
	}
	
	public void close() throws FileNotFoundException
	{
		deleteLocalFiles();
	}
	
	private Settings loadSettings() throws FileNotFoundException
	{
		return mLoadSettingsFunction.loadSettings();
	}

	private void createAndBuildAndLoadKernels()
	{
		new BuildEngine(mOclSettings)
				.generateKernels(mTupleDefinitions, mFunctionRepository.getUserFunctions())
				.loadCppLibrary();
	}
	
	private void deleteLocalFiles()
	{
		//TODO usare getApplicationDirectory per eliminare i file aggiunti
	}
}
