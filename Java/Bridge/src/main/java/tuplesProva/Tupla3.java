package tuplesProva;

public class Tupla3<T1, T2, T3>
{
	private T1 mT1;
	private T2 mT2;
	private T3 mT3;


	public Tupla3(T1 prmT1, T2 prmT2, T3 prmT3)
	{
		mT1 = prmT1;
		mT2 = prmT2;
		mT3 = prmT3;
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
}
