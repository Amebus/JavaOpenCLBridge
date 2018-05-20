package Commons.mappers;

import Commons.IMapper;
import Commons.OnDemandLoader;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMapper<K, T, O> implements IMapper<K, T, O>
{
	
	private Map<K, OnDemandLoader<T, O>> mOnDemandLoaderMap;
	
	public AbstractMapper()
	{
		mOnDemandLoaderMap = new HashMap<>();
	}
	
	@Override
	public T resolve(K pKey, O pOptions)
	{
		return mOnDemandLoaderMap.get(pKey).get(pOptions);
	}
	
	@Override
	public void register(K pKey, OnDemandLoader<T, O> pOnDemandLoaderContainer)
	{
		mOnDemandLoaderMap.put(pKey, pOnDemandLoaderContainer);
	}
}
