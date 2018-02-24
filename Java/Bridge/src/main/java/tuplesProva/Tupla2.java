package tuplesProva;

public class Tupla2<T1, T2>
{
	private T1 mT1;
	private T2 mT2;

	public Tupla2(T1 prmT1, T2 prmT2)
	{
		mT1 = prmT1;
		mT2 = prmT2;
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
