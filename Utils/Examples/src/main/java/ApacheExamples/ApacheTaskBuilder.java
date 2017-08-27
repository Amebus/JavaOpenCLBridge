package ApacheExamples;

import ApacheExamples.UniqueLocationProductSold.UniqueLocationProductSold;
import ApacheExamples.WordCount.WordCount;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import javax.validation.constraints.NotNull;

import java.util.Date;

import static Utils.Test.isNullOrEmptyOrWhiteSpace;

/**
 * Created by Karat on 31/03/2017.
 */
public class ApacheTaskBuilder
{

	private static final String DEFAULT_APP_NAME = "ApacheExample";
	private static final String DEFAULT_MASTER = "local[*]";

	private SparkConf attSparkConf;
	private JavaSparkContext attJavaSparkContext;

	private String attAppName;
	private String attMaster;

	private String attTaskClassName;

	public ApacheTaskBuilder setTaskClass(@NotNull Class prmTaskClass)
	{
		attTaskClassName = prmTaskClass.getName();
		return this;
	}

	public ApacheTaskBuilder withAppName(@NotNull String prmAppName)
	{
		attAppName = prmAppName;
		return this;
	}

	public ApacheTaskBuilder withMaster(@NotNull String prmMaster)
	{
		attMaster = prmMaster;
		return this;
	}

	public BaseApacheTask build()
	{

		if (isNullOrEmptyOrWhiteSpace(attAppName))
			attAppName = DEFAULT_APP_NAME+new Date();

		if (isNullOrEmptyOrWhiteSpace(attMaster))
			attMaster = DEFAULT_MASTER;

		attSparkConf = new SparkConf().setAppName(attAppName).setMaster(attMaster);
		attJavaSparkContext = new JavaSparkContext(attSparkConf);

		if (isNullOrEmptyOrWhiteSpace(attTaskClassName) )
			return new WordCount(attSparkConf, attJavaSparkContext);

		if (attTaskClassName.equals(UniqueLocationProductSold.class.getName()))
			return new UniqueLocationProductSold(attSparkConf, attJavaSparkContext);

		return new WordCount(attSparkConf, attJavaSparkContext);
	}
}