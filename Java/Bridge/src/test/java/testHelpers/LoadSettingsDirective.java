package testHelpers;

import configuration.Settings;
import configuration.SettingsLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

public class LoadSettingsDirective
{
	private String mFileDirectory;
	private String mFileName;
	private Path mPath;
	private File mFile;

	public LoadSettingsDirective()
	{
		mFileDirectory = Constants.RESOURCES_DIR;
	}

	public LoadSettingsDirective(String prmFileName)
	{
		this(prmFileName, null, null);
	}

	public LoadSettingsDirective(Path prmPath)
	{
		this(null, prmPath, null);
	}

	public LoadSettingsDirective(File prmFile)
	{
		this(null, null, prmFile);
	}

	private LoadSettingsDirective(String prmFileName, Path prmPath, File prmFile)
	{
		this();
		mFileName = prmFileName;
		mPath = prmPath;
		mFile = prmFile;
	}

	public String getFileDirectory()
	{
		return mFileDirectory;
	}

	private String getFileName()
	{
		return mFileName;
	}

	private Path getPath()
	{
		return mPath;
	}

	private File getFile()
	{
		return mFile;
	}

	private boolean loadWithFileName()
	{
		return getFileName() != null;
	}

	private boolean loadWithPath()
	{
		return getPath() != null;
	}

	private boolean loadWithFile()
	{
		return getFile() != null;
	}

	public ILoadFunction getLoadFunction()
	{
		ILoadFunction wvLoadFunction;

		if (loadWithFileName())
		{
			wvLoadFunction = () -> SettingsLoader.loadSettings(mFileDirectory, mFileName);
		}
		else if (loadWithPath())
		{
			wvLoadFunction = () -> SettingsLoader.loadSettings(mPath);
		}
		else if (loadWithFile())
		{
			wvLoadFunction = () -> SettingsLoader.loadSettings(mFile);
		}
		else
		{
			wvLoadFunction = () -> SettingsLoader.loadSettings(mFileDirectory);
		}
		return wvLoadFunction;
	}

	public interface ILoadFunction
	{
		Settings loadSettings() throws FileNotFoundException;
	}
}
