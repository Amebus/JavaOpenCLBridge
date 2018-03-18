package tuples.generics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tuple2 <T1, T2> implements IOclTuple
{
	private T1 mT1;
	private T2 mT2;

	public Tuple2(T1 prmT1, T2 prmT2)
	{
		setT1(prmT1);
		setT2(prmT2);
	}

	@Override
	public byte getArity()
	{
		return 2;
	}

	public T1 getT1()
	{
		return mT1;
	}

	public T2 getT2()
	{
		return mT2;
	}

	public void setT1(T1 prmT1)
	{
		mT1 = prmT1;
	}

	public void setT2(T2 prmT2)
	{
		mT2 = prmT2;
	}

	@Override
	public Iterator iterator()
	{
		List wvList = new ArrayList(getArity());

		wvList.add(getT1());
		wvList.add(getT2());

		return wvList.iterator();
	}

	@Override
	public boolean equals(Object prmOther)
	{
		return prmOther != null &&
			   (prmOther == this || prmOther instanceof IOclTuple && equals((IOclTuple) prmOther));
	}

}

