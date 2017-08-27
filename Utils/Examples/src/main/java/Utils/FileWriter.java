package Utils;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static Utils.Test.isNull;
import static Utils.Test.isNullOrEmptyOrWhiteSpace;

/**
 * Created by Karat on 31/03/2017.
 */
public class FileWriter
{

	private static FileWriter attInstance;

	private List<File> attFileList;

	private FileWriter()
	{
		attFileList = new ArrayList<>();
	}

	public static FileWriter getInstance()
	{
		if (attInstance==null)
			attInstance = new FileWriter();
		return attInstance;
	}

	public void addFileToWrite(File prmFileToWrite)
	{
		attFileList.add(prmFileToWrite);
	}

	public void addFileListToWrite(Collection<? extends File> prmFileListToWrite)
	{
		attFileList.addAll(prmFileListToWrite);
	}

	/**
	 * Scrive tutti i file aggiunti e poi cancella lo storico
	 */
	public void writeFiles()
	{
		for (File f : attFileList)
		{
			Path wvFile = Paths.get(f.getFullPath());
			//Files.write(file, lines, Charset.forName("UTF-8"));
			try
			{
				if(Files.exists(wvFile))
					Files.delete(wvFile);
				Files.write( wvFile,
							 f.getLines(),
							 Charset.forName("UTF-8"),
							 StandardOpenOption.CREATE,
							 StandardOpenOption.WRITE
						   );
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		attFileList.clear();
	}

	public static class FileLines
	{
		private FileHeaderLine attHeaderLine;
		private List<FileLine> attLines;
		private ESeparationChar attSeparationChar;

		FileLines(@NotNull List<FileLine> prmLines, FileHeaderLine prmFileHeaderLine, @NotNull ESeparationChar prmSeparationChar)
		{
			attLines = prmLines;
			attHeaderLine = prmFileHeaderLine;
			attSeparationChar = prmSeparationChar;
		}

		@NotNull List<String> getLines()
		{
			List<String> wvLines = new ArrayList<>();

			if (isHeaderLineDefined())
				wvLines.add(attHeaderLine.getLine(attSeparationChar));

			for (FileLine fl : attLines)
			{
				wvLines.add(fl.getLine(attSeparationChar));
			}

			return wvLines;
		}

		private boolean isHeaderLineDefined()
		{
			return
					!(
							isNull(attHeaderLine)
							||	isNullOrEmptyOrWhiteSpace(attHeaderLine.getLine(ESeparationChar._DEFAULT))
					);
		}

		public enum ESeparationChar
		{
			_DEFAULT(';'),
			DOT('.'),
			COMMA(','),
			COLUMN(':'),
			SEMI_COLUMN(';'),
			PIPE('|'),
			SLASH('/'),
			BACK_SLASH('\\'),
			TAB('\t');

			private char attSeparationChar;

			ESeparationChar(char prmSeparationChar) { attSeparationChar = prmSeparationChar; }

			char getChar() { return attSeparationChar; }

		}

		public static class FileLine
		{
			private List<String> attCellList;

			FileLine(@NotNull List<String> prmCellList)
			{
				attCellList = prmCellList;
			}

			String getLine(ESeparationChar prmSeparationChar)
			{
				String wvLine = "";
				for (Iterator<String> i = attCellList.iterator(); i.hasNext();)
				{
					wvLine += i.next();

					if (i.hasNext())
						wvLine +=	prmSeparationChar.getChar();
				}
				return wvLine;
			}



			public static class Builder
			{
				private List<String> attCellList;

				public Builder() { attCellList = new ArrayList<>(); }

				public Builder addCell(@NotNull Object prmCellContent)
				{
					attCellList.add(prmCellContent.toString());
					return this;
				}

				public FileLine build()	{ return new FileLine(attCellList);	}
			}

		}

		public static class FileHeaderLine
				extends FileLine
		{
			FileHeaderLine(List<String> prmCellList) { super(prmCellList); }

			public static class Builder
			{
				private List<String> attHeaderList;

				public Builder() { attHeaderList = new ArrayList<>(); }

				public Builder addColumnHeader(@NotNull Object prmCellContent)
				{
					attHeaderList.add(prmCellContent.toString());
					return this;
				}

				public FileHeaderLine build() {	return new FileHeaderLine(attHeaderList); }
			}
		}

		public static class Builder
		{
			private FileHeaderLine attHeaderLine;
			private List<FileLine> attLines;
			private ESeparationChar attSeparationChar;

			public Builder() { attLines = new ArrayList<>(); }

			public Builder withSeparationChar(@NotNull ESeparationChar prmSeparationChar)
			{
				attSeparationChar = prmSeparationChar;
				return this;
			}

			public Builder withHeaderLine(@NotNull FileHeaderLine prmFileHeaderLine)
			{
				attHeaderLine = prmFileHeaderLine;
				return this;
			}

			public Builder addFileLines(@NotNull Collection<FileLine> prmFileLineList)
			{
				attLines.addAll(prmFileLineList);
				return this;
			}

			public Builder addFileLine(@NotNull FileLine prmFileLine)
			{
				attLines.add(prmFileLine);
				return this;
			}

			public FileLines build()
			{
				if (isNull(attSeparationChar))
					attSeparationChar = ESeparationChar._DEFAULT;

				return new FileLines(attLines, attHeaderLine, attSeparationChar);
			}
		}

	}

	public static class File
	{
		private String attName;
		private String attPath;
		private EExtensions attExtension;
		private FileLines attLines;

		private File (@NotNull String prmName,
					  @NotNull String prmPath,
					  @NotNull EExtensions prmExtensions,
					  @NotNull FileLines prmFileLines)
		{
			attName = prmName;
			attPath = prmPath;
			attExtension = prmExtensions;
			attLines = prmFileLines;
		}

		public @NotNull String getPath() { return attPath; }

		public @NotNull String getName() { return attName; }

		public @NotNull EExtensions getExtension() { return attExtension; }

		public @NotNull String getFullPath() { return attPath + attName + attExtension.getExtension(); }

		@NotNull List<String> getLines()
		{
			return attLines.getLines();
		}

		public enum EExtensions
		{
			TXT,
			CSV;

			String getExtension()
			{
				return "." + this.toString();
			}
		}

	}

	public static class FileBuilder
	{
		private static final String DEFAULT_FILE_NAME = "apache_example_file-";
		private static final String DEFAULT_FILE_PATH = DefaultValues.OUTPUT_PATH;
		private static final File.EExtensions DEFAULT_FILE_EXTENSION = File.EExtensions.TXT;

		private String attFilePath;
		private String attFileName;
		private File.EExtensions attFileExtension;
		private FileLines attLines;


		public FileBuilder() { }

		public FileBuilder setFileName(@NotNull String prmFileName)
		{
			attFileName = prmFileName;

			if (attFileName.charAt(0)=='\\')
				attFileName = attFileName.substring(1);

			return this;
		}

		public FileBuilder withFilePath(@NotNull String prmFilePath)
		{
			attFilePath = prmFilePath;

			if (attFilePath.charAt(attFilePath.length() - 1)!='\\')
				attFilePath+="\\";

			return this;
		}

		public FileBuilder withExtension(@NotNull File.EExtensions prmFileExtension)
		{
			attFileExtension = prmFileExtension;
			return this;
		}

		public FileBuilder withLines(@NotNull FileLines prmLines)
		{
			attLines = prmLines;
			return this;
		}

		public File build()
		{
			if (isNull(attLines))
				throw new NullPointerException();
			if (isNullOrEmptyOrWhiteSpace(attFileName))
				attFileName = DEFAULT_FILE_NAME + new Date().toString().replace(' ', '-').replace(':','-');
			if (isNullOrEmptyOrWhiteSpace(attFilePath))
				attFilePath = DEFAULT_FILE_PATH;
			if (isNull(attFileExtension))
				attFileExtension = DEFAULT_FILE_EXTENSION;


			return new File(attFileName, attFilePath, attFileExtension, attLines);
		}
	}
}