package flinkOcl.buildEngine;

public interface IUserFunction
{
	String getType();
	
	String getName();
	String getFunction();
	String getInputTupleName();
	String getOutputTupleName();
	
	
	//Transformations
	String MAP = "map";
	String FLAT_MAP = "flatMap";
	String FILTER = "filter";
	
	boolean isMap();
	boolean isFlatMap();
	boolean isFilter();
	
	//Actions
	String REDUCE = "reduce";
	
	boolean isReduce();
	
	default boolean isTransformation()
	{
		return isMap() || isFlatMap() || isFilter();
	}
	
	default boolean isAction()
	{
		return  isReduce();
	}
	
	default boolean isOfKnownType()
	{
		return isTransformation() || isAction();
	}
	
	default boolean isOfUnknownType()
	{
		return !isOfKnownType();
	}
}
