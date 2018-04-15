package testHelpers;

import configuration.LoadSettingsDirective;
import configuration.Settings;

import java.io.FileNotFoundException;

public class SettingsWrapper
{
	private Settings mSettings;

	public SettingsWrapper()
	{
		this(null);
	}

	public SettingsWrapper(Settings pSettings)
	{
		mSettings = pSettings;
	}

	public boolean isEverythingOk()
	{
		return mSettings != null;
	}

	public Settings getSettings()
	{
		return mSettings;
	}

	public static SettingsWrapper loadSettings()
	{
		return loadSettings(new LoadSettingsDirective(Constants.RESOURCES_DIR));
	}

	public static SettingsWrapper loadSettings(LoadSettingsDirective pDirective)
	{
		SettingsWrapper vWrapper;

		try
		{
			vWrapper = new SettingsWrapper(pDirective.getLoadFunction().loadSettings());
		}
		catch (FileNotFoundException pE)
		{
			pE.printStackTrace();
			vWrapper = new SettingsWrapper();
		}

		return vWrapper;
	}
}
