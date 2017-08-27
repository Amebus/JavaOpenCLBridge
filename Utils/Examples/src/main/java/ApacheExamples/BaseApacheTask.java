package ApacheExamples;



import Utils.DefaultValues;
import Utils.FileWriter;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Karat on 31/03/2017.
 */
public abstract class BaseApacheTask
{
	protected final String OUTPUT_FILES_PATH = DefaultValues.OUTPUT_PATH+ getClass().getSimpleName()+"\\";

	protected void prepareOutputFolder()
	{

		if (Files.isDirectory(Paths.get(OUTPUT_FILES_PATH)))
			return;

		try
		{
			Files.createDirectory(Paths.get(OUTPUT_FILES_PATH));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	protected void prepareOutputFolder(String prmPath)
	{
		if (Files.isDirectory(Paths.get(prmPath)))
			return;

		try
		{
			Files.createDirectory(Paths.get(prmPath));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	protected abstract FileWriter.File getWritableResult(FileWriter.FileLines prmLines);

	public abstract void executeTask();
}