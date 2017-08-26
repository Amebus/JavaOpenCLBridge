package ocl;

public class CharArrayOCLCommandRunner
{

	//Transformation
	public native char[] OclFilter(char[] data, String parameterDefinition, String filterLogic);

	public native char[] OclMap(char[] data, String parameterDefinition, String mapLogic);

	public native char[] OclSample(char[] data, boolean withReplacement, float fraction);

	public native char[] OclSample(char[] data, boolean withReplacement, float fraction, int seed);

	public native char[] OclUnion(char[] data, char[] otherDataSet);

	public native char[] OclIntersection(char[] data, char[] otherDataSet);


	//Actions
	public native char[] OclTake(char[] data, int n);

	public native char[] OclTakeSample(char[] data, boolean withReplacement, int num);

	public native char[] OclTakeSample(char[] data, boolean withReplacement, int num, int seed);

	public native char OclReduce(char[] data, String parameterDefinition, String reduceLogic);

	public int OclCount(char[] data)
	{
		return data.length;
	}
}
