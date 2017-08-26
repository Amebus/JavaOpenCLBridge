package ocl;

public class DoubleArrayOCLCommandRunner
{

	//Transformation
	public native double[] OclFilter(double[] data, String parameterDefinition, String filterLogic);

	public native double[] OclMap(double[] data, String parameterDefinition, String mapLogic);

	public native double[] OclSample(double[] data, boolean withReplacement, float fraction);

	public native double[] OclSample(double[] data, boolean withReplacement, float fraction, int seed);

	public native double[] OclUnion(double[] data, double[] otherDataSet);

	public native double[] OclIntersection(double[] data, double[] otherDataSet);


	//Actions
	public native double[] OclTake(double[] data, int n);

	public native double[] OclTakeSample(double[] data, boolean withReplacement, int num);

	public native double[] OclTakeSample(double[] data, boolean withReplacement, int num, int seed);

	public native double OclReduce(double[] data, String parameterDefinition, String reduceLogic);

	public int OclCount(double[] data)
	{
		return data.length;
	}
}
