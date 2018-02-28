package configuration;

import org.junit.jupiter.api.*;
import testHelpers.Constants;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SettingsTest
{

	@Test
	void loadSettings_Ok()
	{
		Settings wvSettings = new Settings();
		boolean wvLoadWithoutError;
		try
		{
			wvSettings = SettingsLoader.loadSettings(Constants.RESOURCES_DIR);
			wvLoadWithoutError = true;
		}
		catch (FileNotFoundException prmE)
		{
			prmE.printStackTrace();
			wvLoadWithoutError = false;
		}

		assertTrue(wvLoadWithoutError);
		assertNotNull(wvSettings.getOclSettings());
		assertNotNull(wvSettings.getTupleDefinitions());

		int expectedCount = 2;
		final int[] actualCount = {0};
		wvSettings.getTupleDefinitions().forEach( x -> actualCount[0]++);

		assertEquals(expectedCount, actualCount[0]);
	}

	@Test
	void loadSettings_FileNotFound_Error()
	{
		boolean wvLoadWithoutError;
		try
		{
			SettingsLoader.loadSettings("somewhere");
			wvLoadWithoutError = true;
		}
		catch (FileNotFoundException prmE)
		{
			wvLoadWithoutError = false;
		}

		assertFalse(wvLoadWithoutError);
	}

}
