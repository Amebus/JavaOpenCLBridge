package configuration;

public interface ITupleDefinitionsRepository<T extends ITupleDefinition>
{
	
	Iterable<T> getTupleDefinitions();
	T getTupleDefinition(String pTupleName);
}
