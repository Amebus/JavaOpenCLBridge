package Commons;

@FunctionalInterface
public interface ISupplier<T, O>
{
	T get(O pOptions);
}
