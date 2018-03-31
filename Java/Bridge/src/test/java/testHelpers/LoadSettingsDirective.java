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

	public LoadSettingsDirective(String pFileName)
	{
		this(pFileName, null, null);
	}

	public LoadSettingsDirective(Path pPath)
	{
		this(null, pPath, null);
	}

	public LoadSettingsDirective(File pFile)
	{
		this(null, null, pFile);
	}

	private LoadSettingsDirective(String pFileName, Path pPath, File pFile)
	{
		this();
		mFileName = pFileName;
		mPath = pPath;
		mFile = pFile;
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
		ILoadFunction vLoadFunction;

		if (loadWithFileName())
		{
			vLoadFunction = () -> SettingsLoader.loadSettings(mFileDirectory, mFileName);
		}
		else if (loadWithPath())
		{
			vLoadFunction = () -> SettingsLoader.loadSettings(mPath);
		}
		else if (loadWithFile())
		{
			vLoadFunction = () -> SettingsLoader.loadSettings(mFile);
		}
		else
		{
			vLoadFunction = () -> SettingsLoader.loadSettings(mFileDirectory);
		}
		return vLoadFunction;
	}

	public interface ILoadFunction
	{
		Settings loadSettings() throws FileNotFoundException;
	}
}
