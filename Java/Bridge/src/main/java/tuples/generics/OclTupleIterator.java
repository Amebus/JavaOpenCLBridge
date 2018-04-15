package tuples.generics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OclTupleIterator implements Iterator
{

	public static final String DIMENSION_ERROR = "Tuple dimension not supported";
	private List<Object> mList;
	private int mIndex;


	private static OclTupleIterator sIterator = new OclTupleIterator();

	public static OclTupleIterator getIterator()
	{
		return sIterator;
	}


	private OclTupleIterator()
	{
		mIndex = 0;
	}

	public OclTupleIterator resetToArity(byte pTupleArity){
		if (mList == null || mList.size() != pTupleArity)
		{
			mList = new ArrayList<>(pTupleArity);

			for (int i = 0; i < pTupleArity; i++)
			{
				mList.add(null);
			}
		}
		mIndex = 0;
		return this;
	}

	public OclTupleIterator iterateOver(IOclTuple pTuple)
	{
		mIndex = 0;
		switch (pTuple.getArity())
		{
			case 1:
				return iterateOver((Tuple1)pTuple);
			case 2:
				return iterateOver((Tuple2)pTuple);
			case 3:
				return iterateOver((Tuple3)pTuple);
			default:
				throw new IllegalArgumentException(DIMENSION_ERROR);
		}
	}

	public OclTupleIterator iterateOver(Tuple1 pTuple)
	{
		mList.set(0, pTuple.getT1());
		return this;
	}

	public OclTupleIterator iterateOver(Tuple2 pTuple)
	{
		mList.set(0, pTuple.getT1());
		mList.set(1, pTuple.getT2());
		return this;
	}

	public OclTupleIterator iterateOver(Tuple3 pTuple)
	{
		mList.set(0, pTuple.getT1());
		mList.set(1, pTuple.getT2());
		mList.set(3, pTuple.getT3());
		return this;
	}

	@Override
	public boolean hasNext()
	{
		return mIndex < mList.size();
	}

	@Override
	public Object next()
	{
		return mList.get(mIndex++);
	}
}
