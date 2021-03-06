package commons;

public class OnDemandLoader<T, O>
{
	private ISupplier<T, O> mSupplier;
	
	public OnDemandLoader(ISupplier<T, O> pSupplier) {
		mSupplier = pSupplier;
	}
	
	public T get(O pOptions) {
		return mSupplier.get(pOptions);
	}
}
