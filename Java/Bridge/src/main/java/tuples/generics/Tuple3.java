package tuples.generics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tuple3 <T1, T2, T3> implements IOclTuple
{
	private T1 mT1;
	private T2 mT2;
	private T3 mT3;

	public Tuple3(T1 prmT1, T2 prmT2, T3 prmT3)
	{
		setT1(prmT1);
		setT2(prmT2);
		setT3(prmT3);
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

	public void setT1(T1 prmT1)
	{
		mT1 = prmT1;
	}

	public void setT2(T2 prmT2)
	{
		mT2 = prmT2;
	}

	public void setT3(T3 prmT3)
	{
		mT3 = prmT3;
	}

	@Override
	public Iterator iterator()
	{
		List wvList = new ArrayList(getArity());

		wvList.add(getT1());
		wvList.add(getT2());
		wvList.add(getT3());

		return wvList.iterator();
	}
}
