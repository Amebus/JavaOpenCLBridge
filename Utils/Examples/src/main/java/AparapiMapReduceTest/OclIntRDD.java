package AparapiMapReduceTest;


import AparapiMapReduceTest.Interfaces.IKernelWrapper;
import com.aparapi.Kernel;
import com.aparapi.Range;
import com.aparapi.opencl.OpenCL;

import static AparapiMapReduceTest.Utils.log32;

/**
 * Created by Karat on 18/05/2017.
 */
public class OclIntRDD
{

	private int[] data;

	private final int association = 32;

	private int mutableDimension;
	private int dimension;


	public OclIntRDD(int[] input)
	{
		data = input;
		dimension = input.length;
	}


	private int[] oclCopyDataSet(final int[] input, final int dimensionToCopy)
	{

		final int[] output = new int[dimensionToCopy];
		Kernel kernel = new Kernel() {
			@Override
			public void run() {
				int globalId = getGlobalId();
				output[globalId]=input[globalId];
			}
		};
		Range range = Range.create(dimensionToCopy);
		kernel.execute(range);

		return output;

	}

	public int oclSumReduce()
	{
		mutableDimension = dimension;
		int result = 0;

		final int[][] midResults = new int[2][];

		midResults[0] = oclCopyDataSet(data, dimension);
		midResults[1] = new int[dimension];

		int stepIndex = 0;
		int loops = log32(mutableDimension);

		do
		{
			result += restSum(stepIndex%2, midResults, mutableDimension, association);
			mutableDimension = correctDimension(mutableDimension, association);

			new ReduceKernelWrapper(stepIndex, midResults, association, mutableDimension).run();

			stepIndex++;
		} while (stepIndex < loops);

		stepIndex = stepIndex%2;

		for (int i = 0; i < mutableDimension; i++)
			result+=midResults[stepIndex][i];

		return result;
	}

	//fare in modo di accettare una funzione definita dall'utente
	public OclIntRDD map()
	{

		final int[] kInput = new int[dimension];

		Kernel kernel = new Kernel()
		{
			@Override
			public void run()
			{
				int globalId = getGlobalId();
				kInput[globalId]=1;
			}
		};

		Range range = Range.create(dimension);
		kernel.execute(range);

		return new OclIntRDD(kInput);

	}


	public int Count()
	{
		return data.length;
	}

	private int restSum(int stepIndex, int[][] mid_result, int dimension, int association)
	{
		int sum = 0;
		int rest = dimension%association;
		if (rest!=0)
		{
			for (int i = dimension - 1; i >= dimension - rest; i-- )
				sum += mid_result[stepIndex][i];
		}

		return sum;
	}

	private int correctDimension(int actualDimension, int association)
	{
		int rest = actualDimension%association;

		actualDimension-=rest;

		return actualDimension/association;
	}

	private class ReduceKernelWrapper
			implements IKernelWrapper
	{
		Kernel kernel;
		int stepDimension;
		int kStep;

		public ReduceKernelWrapper(int step, int[][] midResults, int association, int stepDimension)
		{
			kStep = step%2;
			this.stepDimension = stepDimension;

			kernel = new Kernel() {
				public int source = kStep%2;
				public int dest = 1 - source;
				@Override
				public void run() {
					int destId = getGlobalId();
					int sourceID = destId * association;

					midResults[dest][destId] = midResults[source][sourceID];

					for (int i = 1; i < association ; i++)
						midResults[dest][destId] += midResults[source][sourceID + i];


				}
			};


		}

		class MyKernel extends Kernel
		{
			int source;
			int dest = 1 - source;
			int[][] kMidResults;

			@Override
			public void run()
			{
				int destId = getGlobalId();
				int sourceID = destId * association;

				kMidResults[dest][destId] = kMidResults[source][sourceID];

				for (int i = 1; i < association ; i++)
					kMidResults[dest][destId] += kMidResults[source][sourceID + i];
			}
		}

		public void setStepDimension(int value)
		{
			stepDimension = value;
		}

		@Override
		public void run()
		{
			if (stepDimension > 0)
				kernel.execute(Range.create(stepDimension));
		}
	}

}