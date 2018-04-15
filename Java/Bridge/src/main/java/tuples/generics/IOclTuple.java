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

		boolean vResult = true;

		Iterator vIThis = iterator();
		Iterator vIOther = pOther.iterator();
		while (vIThis.hasNext())
		{
			vResult &= vIThis.next().equals(vIOther.next());
		}

		return vResult;
	}
}
