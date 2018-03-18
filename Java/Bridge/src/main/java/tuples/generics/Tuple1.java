package tuples.generics;

import java.util.Iterator;

public class Tuple1 <T1> implements IOclTuple
{

	private T1 mT1;

	public Tuple1(T1 prmT1)
	{
		setT1(prmT1);
	}

	@Override
	public byte getArity()
	{
		return 1;
	}

	public T1 getT1()
	{
		return mT1;
	}

	public void setT1(T1 prmT1)
	{
		mT1 = prmT1;
	}

	@Override
	public Iterator iterator()
	{
		return new Iterator()
		{
			private int mIndex = 0;

			@Override
			public boolean hasNext()
			{
				return mIndex == 0;
			}

			@Override
			public Object next()
			{
				mIndex++;
				return getT1();
			}
		};
	}

	@Override
	public boolean equals(Object prmOther)
	{
		return prmOther != null &&
			   (prmOther == this || prmOther instanceof IOclTuple && equals((IOclTuple) prmOther));
	}
}
