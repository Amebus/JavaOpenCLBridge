package Commons.mappers;

import Commons.IMapper;
import Commons.Lazy;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMapper<K, T, O> implements IMapper<K, T, O>
{
	
	private Map<K, Lazy<T, O>> mLazyMap;
	
	public AbstractMapper()
	{
		mLazyMap = new HashMap<>();
	}
	
	@Override
	public T get(K pKey, O pOptions)
	{
		return mLazyMap.get(pKey).get(pOptions);
	}
	
	@Override
	public void register(K pKey, Lazy<T, O> pLazyContainer)
	{
		mLazyMap.put(pKey, pLazyContainer);
	}
}
