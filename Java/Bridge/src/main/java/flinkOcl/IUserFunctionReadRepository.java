package flinkOcl;

import flinkOcl.buildEngine.IUserFunction;

import java.util.Collection;

public interface IUserFunctionReadRepository
{

	IUserFunction getUserFunctionByName(String pUserFunctionName);
	
	Collection<IUserFunction> getUserFunctions();

}
