import ApacheExamples.ApacheTaskBuilder;
import ApacheExamples.BaseApacheTask;
import ApacheExamples.UniqueLocationProductSold.UniqueLocationProductSold;
import ApacheExamples.WordCount.WordCount;
import AparapiMapReduceTest.OclIntRDD;
import com.aparapi.Kernel;
import com.aparapi.Range;

import javax.validation.constraints.NotNull;

/**
 * Created by Karat on 31/03/2017.
 */
public class Program
{

	public static void main(String[] args)
	{
		//System.setProperty("hadoop.home.dir","C:\\Users\\Karat\\HadoopWinUtils");


		//AparapiTest aparapiTest = new AparapiTest();

		//aparapiTest.run();

		//BaseApacheTask wvApacheTask;


		//wvApacheTask = getApacheTask(EApacheTask.WorldCount);

		//wvApacheTask.executeTask();


		//LambdaTest.testTwoArgIntOperator((a,b) -> a + b );

		int dim = 100000;


		final int[] kInput = new int[dim];

		System.out.println("Valorizzazione sequenziale");
		for (int i=0; i < dim; i++)
			kInput[i] = 1;
		System.out.println("fine valorizzazione sequenziale");

		System.out.println("Conta sequenziale");
		int conta = 0;
		for (int i=0; i < dim; i++)
			conta += kInput[i];
		System.out.println("Conta: " + conta);
		System.out.println("Conta sequenziale");

		System.out.println("map di inserimento");

		Kernel kernel = new Kernel() {
			@Override
			public void run() {
				int globalId = getGlobalId();
				kInput[globalId]=1;
			}
		};
		Range range = Range.create(dim);
		kernel.execute(range);

		System.out.println("fine map inserimento");


		System.out.println("inizio reduce somma");
		OclIntRDD rdd = new OclIntRDD(kInput);

		int result = rdd.oclSumReduce();
		System.out.println("fine reduce somma");

		System.out.println(result);

		System.out.println(log32(dim));


	}

	public static int log2(int n){
		if(n <= 0) throw new IllegalArgumentException();
		return 31 - Integer.numberOfLeadingZeros(n);
	}

	static int log32(int n)
	{
		if (n <=0) throw  new IllegalArgumentException("n cannot be lesser then 1");
		return 6 - Integer.numberOfLeadingZeros(n)/5;
	}

	public static int log4(int n){
		if(n <= 0) throw new IllegalArgumentException();
		return 15 - Integer.numberOfLeadingZeros(n)/2;
	}

	private static BaseApacheTask getApacheTask(@NotNull EApacheTask prmApacheTask)
	{
		ApacheTaskBuilder wvBuilder = new ApacheTaskBuilder();

		switch (prmApacheTask)
		{
			case WorldCount:
				wvBuilder.setTaskClass(WordCount.class);
				wvBuilder.withAppName(WordCount.class.getName());
				break;
			case UniqueLocationProductSold:
				wvBuilder.setTaskClass(UniqueLocationProductSold.class);
				wvBuilder.withAppName(UniqueLocationProductSold.class.getName());
				break;
		}

		return wvBuilder.build();
	}

	private enum EApacheTask
	{
		WorldCount,
		UniqueLocationProductSold
	}

}