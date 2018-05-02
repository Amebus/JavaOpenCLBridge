package Commons;

public class Lazy<T, O>
{
	private ISupplier<T, O> mSupplier;
	
	public Lazy(ISupplier<T, O> pSupplier) {
		mSupplier = pSupplier;
	}
	
	public T get(O pOptions) {
		return mSupplier.get(pOptions);
	}
}
