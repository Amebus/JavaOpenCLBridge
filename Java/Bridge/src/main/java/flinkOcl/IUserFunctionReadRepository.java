package flinkOcl;

public interface IUserFunctionReadRepository
{

	IUserFunction getUserFunctionByName(String pUserFunctionName);
	
	Iterable<? extends IUserFunction> getUserFunctions();

}
