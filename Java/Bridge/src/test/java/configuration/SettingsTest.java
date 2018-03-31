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
		Settings vSettings = new Settings();
		boolean vLoadWithoutError;
		try
		{
			vSettings = SettingsLoader.loadSettings(Constants.RESOURCES_DIR);
			vLoadWithoutError = true;
		}
		catch (FileNotFoundException prmE)
		{
			prmE.printStackTrace();
			vLoadWithoutError = false;
		}

		assertTrue(vLoadWithoutError);
		assertNotNull(vSettings.getOclSettings());
		assertNotNull(vSettings.getTupleDefinitions());

		int expectedCount = 2;
		final int[] actualCount = {0};
		vSettings.getTupleDefinitions().forEach( x -> actualCount[0]++);

		assertEquals(expectedCount, actualCount[0]);
	}

	@Test
	void loadSettings_FileNotFound_Error()
	{
		boolean vLoadWithoutError;
		try
		{
			SettingsLoader.loadSettings("somewhere");
			vLoadWithoutError = true;
		}
		catch (FileNotFoundException prmE)
		{
			vLoadWithoutError = false;
		}

		assertFalse(vLoadWithoutError);
	}

}
