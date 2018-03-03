package tuples.generics;

import java.util.List;

public class Tuple2 <T1, T2> extends Tuple implements IOclTuple
{

	private static final byte ARITY = 2;
	private T1 mT1;
	private T2 mT2;

	private Tuple2()
	{
		super(ARITY);
	}

	public Tuple2(T1 prmT1, T2 prmT2)
	{
		this();
		mT1 = prmT1;
		mT2 = prmT2;
	}

	@Override
	public byte[] toByteStream()
	{
		addT(mT1);
		addT(mT2);
		return super.toByteStream();
	}

	@Override
	public void fromByteStream(byte[] prmInputStream)
	{
		List<Object> wvTemp = tValuesFromByteStream(prmInputStream);

		mT1 = (T1)wvTemp.get(0);
		mT2 = (T2)wvTemp.get(1);
	}

	public T1 getT1()
	{
		return mT1;
	}

	public T2 getT2()
	{
		return mT2;
	}
}

