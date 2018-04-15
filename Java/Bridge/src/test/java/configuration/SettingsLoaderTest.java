package configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import testHelpers.Constants;
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
		SettingsWrapper vWrapper = loadSettings();

		assertTrue(vWrapper.isEverythingOk());
	}

	@Test
	void loadSettings_NameSet_Ok()
	{
		LoadSettingsDirective vDirective = new LoadSettingsDirective(Constants.RESOURCES_DIR, FILE_NAME);
		SettingsWrapper vWrapper = loadSettings(vDirective);

		assertTrue(vWrapper.isEverythingOk());
	}

	@Test
	void loadSettings_Path_Ok()
	{
		LoadSettingsDirective vDirective = new LoadSettingsDirective(Constants.RESOURCES_DIR);
		Path vPath = Paths.get(vDirective.getFileDirectory()).normalize().resolve(FILE_NAME).toAbsolutePath();
		vDirective = new LoadSettingsDirective(vPath);
		SettingsWrapper vWrapper = loadSettings(vDirective);

		assertTrue(vWrapper.isEverythingOk());
	}

	@Test
	void loadSettings_File_Ok()
	{
		LoadSettingsDirective vDirective = new LoadSettingsDirective(Constants.RESOURCES_DIR);
		Path vPath = Paths.get(vDirective.getFileDirectory()).normalize().resolve(FILE_NAME).toAbsolutePath();
		vDirective = new LoadSettingsDirective(new File(vPath.toString()));
		SettingsWrapper vWrapper = loadSettings(vDirective);

		assertTrue(vWrapper.isEverythingOk());
	}
}