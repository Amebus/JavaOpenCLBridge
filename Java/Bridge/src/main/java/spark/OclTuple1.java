package spark;

import scala.Option;
import scala.Tuple1;
import scala.Tuple1$;

public class OclTuple1<T1> extends Tuple1<T1> implements IOclTuple
{

	//To understand
	public static <T1> Option<T1> unapply(Tuple1<T1> var0) {
		return Tuple1$.MODULE$.unapply(var0);
	}

	//To understand
	public static <T1> Tuple1<T1> apply(T1 var0) {
		return Tuple1$.MODULE$.apply(var0);
	}

	public OclTuple1(T1 _1)
	{
		super(_1);
	}

	@Override
	public <T1> OclTuple1<T1> copy(T1 _1)
	{
		return new OclTuple1<>(_1);
	}

	@Override
	public String productPrefix()
	{
		return "OclTuple1";
	}

	@Override
	public boolean canEqual(Object other)
	{
		return other instanceof OclTuple1;
	}

	public boolean equals(Object other)
	{
		return this == other || (other instanceof OclTuple1 && super.equals(other));
	}

	@Override
	public byte[] oclSerialize()
	{

		return new byte[0];
	}
}
