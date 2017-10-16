package ocl;

import com.sun.istack.internal.NotNull;
public abstract class Ocl
{

	static class ErrorMessages
	{
		public static final String NO_KERNELS_FOUND_WITH_NAME = "No Kernels found with name: ";
	}


	protected Ocl ()
	{
		System.loadLibrary("OCL");
	}


	//Context
	protected final native void Open();
	protected final native void Close();

	//Transformations
	protected final native byte[][] OclMap(byte[][] data, @NotNull String kernelName, @NotNull String kernel);
	protected final native int[] OclMap(int[] data, @NotNull String kernelName, @NotNull String kernel);
	protected final native double[] OclMap(double[] data, @NotNull String kernelName, @NotNull String kernel);

	//filter
//	public native char[] OclFilter(char[] data, String parameterDefinition, String filterLogic);
//	public native int[] OclFilter(int[] data, String parameterDefinition, String filterLogic);
//	public native double[] OclFilter(double[] data, String parameterDefinition, String filterLogic);

	//Map
//	public native char[] OclMapToCharArray(char[] data, String parameterDefinition, String mapLogic);
//	public native char[] OclMapToCharArray(int[] data, String parameterDefinition, String mapLogic);
//	public native char[] OclMapToCharArray(double[] data, String parameterDefinition, String mapLogic);

//	public native int[] OclMapToIntArray(char[] data, String parameterDefinition, String mapLogic);
//	public native int[] OclMapToIntArray(int[] data, String parameterDefinition, String mapLogic);
//	public native int[] OclMapToIntArray(double[] data, String parameterDefinition, String mapLogic);
//
//	public native double[] OclMapToDoubleArray(char[] data, String parameterDefinition, String mapLogic);
//	public native double[] OclMapToDoubleArray(int[] data, String parameterDefinition, String mapLogic);
//	public native double[] OclMapToDoubleArray(double[] data, String parameterDefinition, String mapLogic);

	//Sample
//	public native char[] OclSample(char[] data, boolean withReplacement, float fraction);
//	public native int[] OclSample(int[] data, boolean withReplacement, float fraction);
//	public native double[] OclSample(double[] data, boolean withReplacement, float fraction);
//
//
//	public native char[] OclSample(char[] data, boolean withReplacement, float fraction, int seed);
//	public native int[] OclSample(int[] data, boolean withReplacement, float fraction, int seed);
//	public native double[] OclSample(double[] data, boolean withReplacement, float fraction, int seed);

	//Union
//	public native char[] OclUnion(char[] data, char[] otherDataSet);
//	public native int[] OclUnion(int[] data, int[] otherDataSet);
//	public native double[] OclUnion(double[] data, double[] otherDataSet);

	//Intersection
//	public native char[] OclIntersection(char[] data, char[] otherDataSet);
//	public native int[] OclIntersection(int[] data, int[] otherDataSet);
//	public native double[] OclIntersection(double[] data, double[] otherDataSet);


	//Actions

	//Take
//	public native char[] OclTake(char[] data, int n);
	protected final native int[] OclTake(int[] data, int n);
//	public native double[] OclTake(double[] data, int n);



	//TakeSample
//	public native char[] OclTakeSample(char[] data, boolean withReplacement, int num);
//	public native int[] OclTakeSample(int[] data, boolean withReplacement, int num);
//	public native double[] OclTakeSample(double[] data, boolean withReplacement, int num);

//	public native char[] OclTakeSample(char[] data, boolean withReplacement, int num, int seed);
//	public native int[] OclTakeSample(int[] data, boolean withReplacement, int num, int seed);
//	public native double[] OclTakeSample(double[] data, boolean withReplacement, int num, int seed);

	//Reduce
//	public native char OclReduce(char[] data, String parameterDefinition, String reduceLogic);
//	public native int OclReduce(int[] data, String parameterDefinition, String reduceLogic);
//	public native double OclReduce(double[] data, String parameterDefinition, String reduceLogic);

	//Count
	public int OclCount(char[] data)
	{
		return data.length;
	}
	public int OclCount(int[] data)
	{
		return data.length;
	}
	public int OclCount(double[] data)
	{
		return data.length;
	}
}
