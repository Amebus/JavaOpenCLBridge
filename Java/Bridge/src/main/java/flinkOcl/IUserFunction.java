package flinkOcl;

import Commons.IBuilder;

import javax.validation.constraints.NotNull;

public interface IUserFunction
{
	IType getType();
	String getName();
	
	String getFunction();
	
	String getInputTupleName();
	String getOutputTupleName();
	
	interface IUserFunctionBuilder extends IBuilder<IUserFunction>
	{
		IUserFunctionBuilder setTpe(@NotNull IType pType);
		IUserFunctionBuilder setName(@NotNull String pName);
		IUserFunctionBuilder setInputTupleName(@NotNull String pTupleName);
		IUserFunctionBuilder setOutputTupleName(@NotNull String pTupleName);
		
		IUserFunctionBuilder addUserFunctionLine(@NotNull String pLine);
	}
	
	interface IType
	{
		String map = "map";
		String faltMap = "flatMap";
		String filter = "filter";
		String reduce = "reduce";
		
		//Transformations
		boolean isMap();
		boolean isFlatMap();
		boolean isFilter();
		
		//Actions
		boolean isReduce();
		
		String toString();
	}
}
