package flinkOcl;

import flinkOcl.buildEngine.IUserFunction;

public interface IUserFunctionWriteRepository
{
	void addUserFunction(IUserFunction pUserFunction);
	
	void addUserFunctions(Iterable<IUserFunction> pUserFunctions);
}
