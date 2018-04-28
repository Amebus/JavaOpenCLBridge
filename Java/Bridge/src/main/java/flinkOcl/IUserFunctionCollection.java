package flinkOcl;

import flinkOcl.buildEngine.IUserFunction;

public interface IUserFunctionCollection<T extends IUserFunction> extends Iterable<T>
{
	Iterable<T> getUserFunctions();
}
