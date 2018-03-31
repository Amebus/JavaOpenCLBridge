package testHelpers;

import configuration.CTType;
import configuration.JavaTType;
import configuration.Settings;
import configuration.TType;

import static testHelpers.SettingsWrapper.loadSettings;

public class TTypesGetter
{
	public static Settings getSettings()
	{
		return loadSettings().getSettings();
	}

	public static Settings getSettings(LoadSettingsDirective pDirective)
	{
		return loadSettings(pDirective).getSettings();
	}

	public static JavaTType getJavaTInteger()
	{
		return new JavaTType.Builder(TType.ConfigTypes.INTEGER).build();
	}

	public static JavaTType getJavaTInt()
	{
		return new JavaTType.Builder(TType.ConfigTypes.INT).build();
	}

	public static JavaTType getJavaTDouble()
	{
		return new JavaTType.Builder(TType.ConfigTypes.DOUBLE).build();
	}

	public static JavaTType getJavaTString()
	{
		return new JavaTType.Builder(TType.ConfigTypes.STRING).build();
	}

	public static JavaTType getJavaTCString()
	{
		return new JavaTType.Builder(TType.ConfigTypes.CSTRING).build();
	}

	public static JavaTType getJavaTString(int pMaxDim)
	{
		return new JavaTType.Builder(TType.ConfigTypes.STRING + ":" + pMaxDim).build();
	}

	public static JavaTType getJavaTCString(int pMaxDim)
	{
		return new JavaTType.Builder(TType.ConfigTypes.CSTRING + ":" + pMaxDim).build();
	}

	public static CTType getCTInteger()
	{
		return new CTType.Builder(TType.ConfigTypes.INTEGER).build();
	}

	public static CTType getCTInt()
	{
		return new CTType.Builder(TType.ConfigTypes.INT).build();
	}

	public static CTType getCTDouble()
	{
		return new CTType.Builder(TType.ConfigTypes.DOUBLE).build();
	}

	public static CTType getCTString()
	{
		return new CTType.Builder(TType.ConfigTypes.STRING).build();
	}

	public static CTType getCTCString()
	{
		return new CTType.Builder(TType.ConfigTypes.CSTRING).build();
	}

	public static CTType getCTString(int pMaxDim)
	{
		return new CTType.Builder(TType.ConfigTypes.STRING + ":" + pMaxDim).build();
	}

	public static CTType getCTCString(int pMaxDim)
	{
		return new CTType.Builder(TType.ConfigTypes.CSTRING + ":" + pMaxDim).build();
	}
}
