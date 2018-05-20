package Commons;

public interface IMapper<K, T, O>
{

	T resolve(K pKey, O pOptions);

	void register(K pKey, OnDemandLoader<T, O> pOnDemandLoaderContainer);
	
}