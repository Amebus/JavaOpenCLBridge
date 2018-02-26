package testHelpers;

import configuration.Settings;

import java.io.FileNotFoundException;

public class SettingsWrapper
{
	private Settings mSettings;

	public SettingsWrapper()
	{
		this(null);
	}

	public SettingsWrapper(Settings prmSettings)
	{
		mSettings = prmSettings;
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
		return loadSettings(new LoadSettingsDirective());
	}

	public static SettingsWrapper loadSettings(LoadSettingsDirective prmDirective)
	{
		SettingsWrapper wvWrapper;

		try
		{
			wvWrapper = new SettingsWrapper(prmDirective.getLoadFunction().loadSettings());
		}
		catch (FileNotFoundException prmE)
		{
			prmE.printStackTrace();
			wvWrapper = new SettingsWrapper();
		}

		return wvWrapper;
	}
}
