package tuples.generics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tuple2 <T1, T2> implements IOclTuple
{
	private T1 mT1;
	private T2 mT2;

	public Tuple2(T1 pT1, T2 pT2)
	{
		setT1(pT1);
		setT2(pT2);
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

	public void setT1(T1 pT1)
	{
		mT1 = pT1;
	}

	public void setT2(T2 pT2)
	{
		mT2 = pT2;
	}

	@Override
	public Iterator iterator()
	{
		List vList = new ArrayList(getArity());

		vList.add(getT1());
		vList.add(getT2());

		return vList.iterator();
	}

	@Override
	public boolean equals(Object pOther)
	{
		return pOther != null &&
			   (pOther == this || pOther instanceof IOclTuple && equals((IOclTuple) pOther));
	}

}

