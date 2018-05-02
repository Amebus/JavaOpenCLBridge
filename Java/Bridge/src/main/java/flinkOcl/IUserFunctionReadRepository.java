package flinkOcl;

import java.util.Collection;

public interface IUserFunctionReadRepository
{

	IUserFunction getUserFunctionByName(String pUserFunctionName);
	
	Collection<IUserFunction> getUserFunctions();

}
