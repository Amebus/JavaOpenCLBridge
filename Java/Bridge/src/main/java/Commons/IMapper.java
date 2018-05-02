package Commons;

public interface IMapper<K, T, O>
{
//	Map<String, Lazy<T, O>> mLazyMap;
	T get(K pKey, O pOptions);

	void register(K pKey, Lazy<T, O> pLazyContainer);
	
}