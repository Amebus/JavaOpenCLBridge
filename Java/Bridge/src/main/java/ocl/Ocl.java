package ocl;

public class Ocl
{
	//Transformations

	//filter
	public native Character[] OclFilter(Character[] data, String parameterDefinition, String filterLogic);
	public native Integer[] OclFilter(Integer[] data, String parameterDefinition, String filterLogic);
	public native Double[] OclFilter(Double[] data, String parameterDefinition, String filterLogic);

	//Map
	public native Character[] OclMapToCharArray(Character[] data, String parameterDefinition, String mapLogic);
	public native Character[] OclMapToCharArray(Integer[] data, String parameterDefinition, String mapLogic);
	public native Character[] OclMapToCharArray(Double[] data, String parameterDefinition, String mapLogic);

	public native Integer[] OclMapToIntArray(Character[] data, String parameterDefinition, String mapLogic);
	public native Integer[] OclMapToIntArray(Integer[] data, String parameterDefinition, String mapLogic);
	public native Integer[] OclMapToIntArray(Double[] data, String parameterDefinition, String mapLogic);

	public native Double[] OclMapToDoubleArray(Character[] data, String parameterDefinition, String mapLogic);
	public native Double[] OclMapToDoubleArray(Integer[] data, String parameterDefinition, String mapLogic);
	public native Double[] OclMapToDoubleArray(Double[] data, String parameterDefinition, String mapLogic);

	//Sample
	public native Integer[] OclSample(Integer[] data, boolean withReplacement, float fraction);
	public native Double[] OclSample(Character[] data, boolean withReplacement, float fraction);
	public native Double[] OclSample(Double[] data, boolean withReplacement, float fraction);


	public native Double[] OclSample(Character[] data, boolean withReplacement, float fraction, int seed);
	public native Integer[] OclSample(Integer[] data, boolean withReplacement, float fraction, int seed);
	public native Double[] OclSample(Double[] data, boolean withReplacement, float fraction, int seed);

	//Union
	public native Character[] OclUnion(Character[] data, Character[] otherDataSet);
	public native Integer[] OclUnion(Integer[] data, Integer[] otherDataSet);
	public native Double[] OclUnion(Double[] data, Double[] otherDataSet);

	//Intersection
	public native Integer[] OclIntersection(Character[] data, Character[] otherDataSet);
	public native Integer[] OclIntersection(Integer[] data, Integer[] otherDataSet);
	public native Integer[] OclIntersection(Double[] data, Double[] otherDataSet);


	//Actions

	//Take
	public native Character[] OclTake(Character[] data, int n);
	public native Integer[] OclTake(Integer[] data, int n);
	public native Double[] OclTake(Double[] data, int n);

	//TakeSample
	public native Character[] OclTakeSample(Character[] data, boolean withReplacement, int num);
	public native Character[] OclTakeSample(Integer[] data, boolean withReplacement, int num);
	public native Character[] OclTakeSample(Double[] data, boolean withReplacement, int num);

	public native Character[] OclTakeSample(Character[] data, boolean withReplacement, int num, int seed);
	public native Character[] OclTakeSample(Integer[] data, boolean withReplacement, int num, int seed);
	public native Character[] OclTakeSample(Double[] data, boolean withReplacement, int num, int seed);

	//Reduce
	public native Character OclReduce(Character[] data, String parameterDefinition, String reduceLogic);
	public native Character OclReduce(Integer[] data, String parameterDefinition, String reduceLogic);
	public native Character OclReduce(Double[] data, String parameterDefinition, String reduceLogic);

	//Count
	public Integer OclCount(Character[] data)
	{
		return data.length;
	}
	public Integer OclCount(Integer[] data)
	{
		return data.length;
	}
	public Integer OclCount(Double[] data)
	{
		return data.length;
	}
}
