package flink.ocl;

import java.util.Collection;

public interface IUserFunctionsRepository
{

	IUserFunction getUserFunctionByName(String pUserFunctionName);
	
	Collection<IUserFunction> getUserFunctions();

}
