package configuration;

import Commons.Json.JsonLoader;
import Commons.Json.JsonLoaderOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

public final class SettingsLoader
{

	private SettingsLoader()
	{

	}

	public static final String CONFIG_FILE_NAME = "oclConfig.json";

	public static Settings loadSettings(String pConfigFileDirectory) throws FileNotFoundException
	{
		return loadSettings(pConfigFileDirectory, CONFIG_FILE_NAME);
	}

	public static Settings loadSettings(String pConfigFileDirectory, String pFileName) throws FileNotFoundException
	{
		return loadSettings(new JsonLoaderOptions.JsonLoaderOptionsBuilder<Settings>().setSource(pConfigFileDirectory, pFileName));
	}

	public static Settings loadSettings(Path pPathToConfigFile) throws FileNotFoundException
	{
		return loadSettings(new JsonLoaderOptions.JsonLoaderOptionsBuilder<Settings>().setSource(pPathToConfigFile));
	}

	public static Settings loadSettings(File pConfigFile) throws FileNotFoundException
	{
		return loadSettings(new JsonLoaderOptions.JsonLoaderOptionsBuilder<Settings>().setSource(pConfigFile));
	}
	
	private static Settings loadSettings(JsonLoaderOptions.JsonLoaderOptionsBuilder<Settings> pOptions) throws FileNotFoundException
	{
		return JsonLoader.loadJsonObject(pOptions.setBeanClass(Settings.class).build());
	}
}
