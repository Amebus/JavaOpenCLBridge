package flinkOcl;

import Commons.JsonLoader;
import Commons.JsonLoaderOptions;
import flinkOcl.buildEngine.IUserFunction;
import io.gsonfire.gson.HookInvocationException;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JsonUserFunctionRepository implements IUserFunctionReadRepository
{
	public static final String FUNCTIONS_FILE_NAME = "functions.json";
	
	private IUserFunctionCollection<? extends IUserFunction> mUserFunctions;
	private Map<String, IUserFunction> mUserFunctionMap;
	
	private String mFilePath;
	private String mFileName;
	
	private boolean mAreFunctionsNotLoadedYet;
	
	public JsonUserFunctionRepository(String pFilePath) throws FileNotFoundException
	{
		this(pFilePath, FUNCTIONS_FILE_NAME);
	}
	
	public JsonUserFunctionRepository(String pFilePath, String pFileName) throws FileNotFoundException
	{
		mAreFunctionsNotLoadedYet = true;
		mUserFunctionMap = new HashMap<>();
		mFilePath = pFilePath;
		mFileName = pFileName;
		checkIfFileExists();
	}
	
	@Override
	public IUserFunction getUserFunctionByName(String pUserFunctionName)
	{
		if(mAreFunctionsNotLoadedYet) loadFunctions();
		return mUserFunctionMap.get(pUserFunctionName);
	}
	
	@Override
	public Collection<IUserFunction> getUserFunctions()
	{
		if(mAreFunctionsNotLoadedYet) loadFunctions();
		return mUserFunctionMap.values();
	}
	
	private void checkIfFileExists() throws FileNotFoundException
	{
		if(Files.notExists(Paths.get(mFilePath).normalize().resolve(mFileName).toAbsolutePath()))
			throw new FileNotFoundException("The file \"" + mFileName + "\" can't be found under the folder \"" + mFilePath + "\".");
	}
	
	private void loadFunctions()
	{
		try
		{
			mUserFunctions = JsonLoader.loadJsonObject( new JsonLoaderOptions
																.JsonLoaderOptionsBuilder<JsonUserFunctionCollection>()
											   .setSource(mFilePath, mFileName)
											   .shouldHookClass(JsonUserFunction.class)
											   .setBeanClass(JsonUserFunctionCollection.class)
											   .build()
													  );
			
			mUserFunctions.forEach(x -> mUserFunctionMap.put(x.getName(), (JsonUserFunction) x));
			mAreFunctionsNotLoadedYet = false;
		}
		catch (HookInvocationException ex)
		{
			throw new IllegalArgumentException(ex.getCause());
		}
		catch (FileNotFoundException pE)
		{
			pE.printStackTrace();
		}
	}
}
