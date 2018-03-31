package configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.gsonfire.GsonFireBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

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
		return loadSettings(Paths.get(pConfigFileDirectory).normalize().resolve(pFileName).toAbsolutePath());
	}

	public static Settings loadSettings(Path pPathToConfigFile) throws FileNotFoundException
	{
		return loadSettings(pPathToConfigFile.toFile());
	}

	public static Settings loadSettings(File pConfigFile) throws FileNotFoundException
	{
		FileReader vReader = new FileReader(pConfigFile);
		Gson vJsonSerializer = new GsonFireBuilder()
				.enableHooks(Settings.class)
				.enableHooks(TupleDefinition.class)
				.createGson();
		return vJsonSerializer.fromJson(vReader, Settings.class);
	}
}
