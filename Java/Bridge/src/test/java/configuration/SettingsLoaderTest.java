package configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import testHelpers.LoadSettingsDirective;
import testHelpers.SettingsWrapper;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static testHelpers.SettingsWrapper.loadSettings;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SettingsLoaderTest
{
	private static final String FILE_NAME = SettingsLoader.CONFIG_FILE_NAME;

	@Test
	void loadSettings_Ok()
	{
		SettingsWrapper wvWrapper = loadSettings();

		assertTrue(wvWrapper.isEverythingOk());
	}

	@Test
	void loadSettings_NameSet_Ok()
	{
		LoadSettingsDirective wvDirective = new LoadSettingsDirective(FILE_NAME);
		SettingsWrapper wvWrapper = loadSettings(wvDirective);

		assertTrue(wvWrapper.isEverythingOk());
	}

	@Test
	void loadSettings_Path_Ok()
	{
		LoadSettingsDirective wvDirective = new LoadSettingsDirective();
		Path wvPath = Paths.get(wvDirective.getFileDirectory()).normalize().resolve(FILE_NAME).toAbsolutePath();
		wvDirective = new LoadSettingsDirective(wvPath);
		SettingsWrapper wvWrapper = loadSettings(wvDirective);

		assertTrue(wvWrapper.isEverythingOk());
	}

	@Test
	void loadSettings_File_Ok()
	{
		LoadSettingsDirective wvDirective = new LoadSettingsDirective();
		Path wvPath = Paths.get(wvDirective.getFileDirectory()).normalize().resolve(FILE_NAME).toAbsolutePath();
		wvDirective = new LoadSettingsDirective(new File(wvPath.toString()));
		SettingsWrapper wvWrapper = loadSettings(wvDirective);

		assertTrue(wvWrapper.isEverythingOk());
	}
}