package ApacheExamples.WordCount;

import java.util.*;
import java.util.Arrays;

import ApacheExamples.BaseApacheTask;
import Utils.DefaultValues;
import Utils.FileWriter;
import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import scala.Tuple2;


/**
 * Created by Karat on 16/03/2017.
 */
public class WordCount
		extends BaseApacheTask
{
	private static final String INPUT_FILE_NAME = "lorem_ipsum.txt";

	private static final String INPUT_FILE_FULL_PATH = DefaultValues.INPUT_PATH + INPUT_FILE_NAME;

	private static final String OUTPUT_FILE_NAME = "lorem_ipsum_world_count";

	private SparkConf attSparkConf;
	private JavaSparkContext attJavaSparkContext;

	private JavaPairRDD<String, Integer> attCounts;

	private FileWriter attFileWriter;

	public WordCount(SparkConf prmSparkConf, JavaSparkContext javaSparkContext)
	{
		attSparkConf = prmSparkConf;
		attJavaSparkContext = javaSparkContext;
		prepareOutputFolder();
		attFileWriter = FileWriter.getInstance();
	}

	@Override
	public void executeTask()
	{

		JavaRDD<String> textFile = attJavaSparkContext.textFile(INPUT_FILE_FULL_PATH);
		attCounts = textFile
				.flatMap(s -> Arrays.asList(s.toLowerCase().split("\\W")).iterator())
				.mapToPair(word -> new Tuple2<>(word, 1))
				.reduceByKey((a, b) -> a + b)
				//.mapToPair(item -> item.swap())
				.sortByKey(true);
		//.mapToPair(item -> item.swap());
		//JavaPairRDD

		//textFile.sample()

		generateLines(attCounts.collect());


		JavaRDD<Character> wvSomething = textFile
				.flatMap(s -> Arrays.asList(s.toLowerCase().split("\\W")).iterator())
				.reduce()

		JavaRDD<Integer> wvSum = attCounts.map(Tuple2::_2);

		Integer integer = wvSum.reduce((a,b) -> a + b);

		//Integer integer = sum.fold(0, (a, b) -> a + b);
		attJavaSparkContext.close();
		System.out.println(integer);
		//JavaRDD<Integer> sum = attCounts.cou
	}

	private void generateLines(List<Tuple2<String, Integer>> prmTuple)
	{

		FileWriter.FileLines.Builder wvFileLinesBuilder = new FileWriter.FileLines.Builder()
				.withSeparationChar(FileWriter.FileLines.ESeparationChar.SEMI_COLUMN)
				.withHeaderLine(new FileWriter.FileLines.FileHeaderLine.Builder()
										.addColumnHeader("Word")
										.addColumnHeader("Count")
										.build());


		for (Tuple2<String, Integer> tuple : prmTuple)
		{
			if (!tuple._1().equals(""))
			{
				wvFileLinesBuilder.addFileLine(new FileWriter.FileLines.FileLine.Builder()
													   .addCell(tuple._1())
													   .addCell(tuple._2())
													   .build());
			}
		}

		attFileWriter.addFileToWrite(getWritableResult(wvFileLinesBuilder.build()));

		attFileWriter.writeFiles();
	}

	@Override
	protected FileWriter.File getWritableResult(FileWriter.FileLines prmLines)
	{
		FileWriter.FileBuilder wvBuilder =
				new FileWriter.FileBuilder()
						.setFileName(OUTPUT_FILE_NAME)
						.withFilePath(OUTPUT_FILES_PATH)
						.withExtension(FileWriter.File.EExtensions.CSV)
						.withLines(prmLines);
		return wvBuilder.build();
	}
}