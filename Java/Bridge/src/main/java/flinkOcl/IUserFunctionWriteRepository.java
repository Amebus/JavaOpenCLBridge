package flinkOcl;

public interface IUserFunctionWriteRepository
{
	void addUserFunction(IUserFunction pUserFunction);
	
	void addUserFunctions(Iterable<IUserFunction> pUserFunctions);
}
