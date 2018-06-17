package flinkOcl;

public interface IUserFunctionCollection<T extends IUserFunction> extends Iterable<T>
{
	Iterable<T> getUserFunctions();
}
