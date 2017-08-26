package ocl;

public class IntArrayOCLCommandRunner
{

	//Transformation
	public native int[] OclFilter(int[] data, String parameterDefinition, String filterLogic);

	public native int[] OclMap(int[] data, String parameterDefinition, String mapLogic);

	public native int[] OclSample(int[] data, boolean withReplacement, float fraction);

	public native int[] OclSample(int[] data, boolean withReplacement, float fraction, int seed);

	public native int[] OclUnion(int[] data, int[] otherDataSet);

	public native int[] OclIntersection(int[] data, int[] otherDataSet);


	//Actions
	public native int[] OclTake(int[] data, int n);

	public native int[] OclTakeSample(int[] data, boolean withReplacement, int num);

	public native int[] OclTakeSample(int[] data, boolean withReplacement, int num, int seed);

	public native int OclReduce(int[] data, String parameterDefinition, String reduceLogic);

	public int OclCount(int[] data)
	{
		return data.length;
	}
}
