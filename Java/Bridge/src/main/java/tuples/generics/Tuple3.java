package tuples.generics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tuple3 <T1, T2, T3> implements IOclTuple
{
	private T1 mT1;
	private T2 mT2;
	private T3 mT3;

	public Tuple3(T1 pT1, T2 pT2, T3 pT3)
	{
		setT1(pT1);
		setT2(pT2);
		setT3(pT3);
	}

	@Override
	public byte getArity()
	{
		return 3;
	}

	public T1 getT1()
	{
		return mT1;
	}

	public T2 getT2()
	{
		return mT2;
	}

	public T3 getT3()
	{
		return mT3;
	}

	public void setT1(T1 pT1)
	{
		mT1 = pT1;
	}

	public void setT2(T2 pT2)
	{
		mT2 = pT2;
	}

	public void setT3(T3 pT3)
	{
		mT3 = pT3;
	}

	@Override
	public Iterator iterator()
	{
		List vList = new ArrayList(getArity());

		vList.add(getT1());
		vList.add(getT2());
		vList.add(getT3());

		return vList.iterator();
	}

	@Override
	public boolean equals(Object pOther)
	{
		return pOther != null &&
			   (pOther == this || pOther instanceof IOclTuple && equals((IOclTuple) pOther));
	}
}
