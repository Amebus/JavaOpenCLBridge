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

	public OclTupleIterator resetToArity(byte prmTupleArity){
		if (mList == null || mList.size() != prmTupleArity)
		{
			mList = new ArrayList<>(prmTupleArity);

			for (int i = 0; i < prmTupleArity; i++)
			{
				mList.add(null);
			}
		}
		mIndex = 0;
		return this;
	}

	public OclTupleIterator iterateOver(IOclTuple prmTuple)
	{
		mIndex = 0;
		switch (prmTuple.getArity())
		{
			case 1:
				return iterateOver((Tuple1)prmTuple);
			case 2:
				return iterateOver((Tuple2)prmTuple);
			case 3:
				return iterateOver((Tuple3)prmTuple);
			default:
				throw new IllegalArgumentException(DIMENSION_ERROR);
		}
	}

	public OclTupleIterator iterateOver(Tuple1 prmTuple)
	{
		mList.set(0, prmTuple.getT1());
		return this;
	}

	public OclTupleIterator iterateOver(Tuple2 prmTuple)
	{
		mList.set(0, prmTuple.getT1());
		mList.set(1, prmTuple.getT2());
		return this;
	}

	public OclTupleIterator iterateOver(Tuple3 prmTuple)
	{
		mList.set(0, prmTuple.getT1());
		mList.set(1, prmTuple.getT2());
		mList.set(3, prmTuple.getT3());
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
