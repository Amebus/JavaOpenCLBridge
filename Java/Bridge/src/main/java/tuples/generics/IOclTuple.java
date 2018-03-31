package tuples.generics;

import java.util.Iterator;

public interface IOclTuple extends Iterable
{
	byte getArity();

	default boolean equals(IOclTuple pOther)
	{
		if (getArity() != pOther.getArity())
		{
			return false;
		}

		boolean wvResult = true;

		Iterator wvIThis = iterator();
		Iterator wvIOther = pOther.iterator();
		while (wvIThis.hasNext())
		{
			wvResult &= wvIThis.next().equals(wvIOther.next());
		}

		return wvResult;
	}
}
