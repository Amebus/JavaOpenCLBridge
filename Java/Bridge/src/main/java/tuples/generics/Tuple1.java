package tuples.generics;

import java.util.List;

public class Tuple1 <T1> extends Tuple implements IOclTuple
{

	private static final byte ARITY = 1;
	private T1 mT1;

	private Tuple1()
	{
		super(ARITY);
	}

	public Tuple1(T1 prmT1)
	{
		this();
		mT1 = prmT1;
	}

	@Override
	public byte[] toByteStream()
	{
		addT(mT1);
		return super.toByteStream();
	}

	@Override
	public void fromByteStream(byte[] prmInputStream)
	{
		List<Object> wvTemp = tValuesFromByteStream(prmInputStream);

		mT1 = (T1)wvTemp.get(0);
	}

	public T1 getT1()
	{
		return mT1;
	}
}
