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

	public static Settings loadSettings(String prmConfigFileDirectory) throws FileNotFoundException
	{
		return loadSettings(prmConfigFileDirectory, CONFIG_FILE_NAME);
	}

	public static Settings loadSettings(String prmConfigFileDirectory, String prmFileName) throws FileNotFoundException
	{
		return loadSettings(Paths.get(prmConfigFileDirectory).normalize().resolve(prmFileName).toAbsolutePath());
	}

	public static Settings loadSettings(Path prmPathToConfigFile) throws FileNotFoundException
	{
		return loadSettings(prmPathToConfigFile.toFile());
	}

	public static Settings loadSettings(File prmConfigFile) throws FileNotFoundException
	{
		FileReader wvReader = new FileReader(prmConfigFile);
		Gson wvJsonSerializer = new GsonFireBuilder()
				.enableHooks(Settings.class)
				.enableHooks(TupleDefinition.class)
				.createGson();
		return wvJsonSerializer.fromJson(wvReader, Settings.class);
	}
}
