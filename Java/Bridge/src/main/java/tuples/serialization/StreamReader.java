package tuples.serialization;

import tuples.generics.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StreamReader implements Iterable<IOclTuple>
{

	private byte mArity;
	private byte[] mStream;

	public StreamReader(byte[] prmStream)
	{
		mStream = prmStream;
		mArity = mStream[0];
	}

	public StreamReader(StreamReader prmStreamReader)
	{
		this(prmStreamReader.mStream);
	}

	public byte[] getStream()
	{
		return mStream;
	}

	public byte getArity()
	{
		return mArity;
	}

	public <R extends IOclTuple> List<R> getTupleList()
	{
		List<R> wvResult = new LinkedList<>();

		for (IOclTuple wvTuple : this)
		{
			wvResult.add((R)wvTuple);
		}

		return wvResult;
	}


	@Override
	public Iterator<IOclTuple> iterator()
	{
		switch (getArity())
		{
			case 1:
				return new Tuple1Iterator(this);
			case 2:
				return new Tuple2Iterator(this);
			case 3:
				return new Tuple3Iterator(this);
			default:
				throw new IllegalArgumentException("Tuple dimension not supported");
		}
	}

	public IStreamReaderIterator streamReaderIterator()
	{
		return (IStreamReaderIterator)iterator();
	}

	public interface IStreamReaderIterator extends Iterator<IOclTuple>
	{
		<R extends IOclTuple> R nextTuple();
	}

	private static abstract class StreamIterator implements IStreamReaderIterator
	{
		private int mArity;
		private byte[] mStream;
		private Object[] mResult;
		private int mIndex;
		private int mTypeIndex;
		private int mResultIndex;
		private int mStringLength;


		StreamIterator(StreamReader prmStreamReader)
		{
			mStream = prmStreamReader.getStream();
			mArity = prmStreamReader.getArity();
			mResult = new Object[mArity];
			mIndex = 1 + mArity;
			mTypeIndex = 1;
			mResultIndex = 0;
			mStringLength = 0;

		}

		Object[] readValuesFromStream()
		{
			byte wvType;

			for (mResultIndex = 0; mResultIndex < mArity; mResultIndex++)
			{
				wvType = mStream[mTypeIndex++];
				switch (wvType)
				{
					case Types.INT:
						integerFromByteArray();
						break;
					case Types.DOUBLE:
						doubleFromByteArray();
						break;
					case Types.STRING:
						stringFromByteArray();
						break;
					default:
						throw new IllegalArgumentException("Object type not recognized, unable to serialize it");
				}
			}
			mTypeIndex = 1;
			return mResult;
		}

		private void integerFromByteArray() {
			mResult[mResultIndex] = (mStream[mIndex++]       ) << 24 |
									(mStream[mIndex++] & 0xFF) << 16 |
									(mStream[mIndex++] & 0xFF) << 8  |
									(mStream[mIndex++] & 0xFF);
		}

		private void doubleFromByteArray() {
			mResult[mResultIndex] = Double
					.longBitsToDouble(((long)mStream[mIndex++]		 ) << 56 |
									  ((long)mStream[mIndex++] & 0xFF) << 48 |
									  ((long)mStream[mIndex++] & 0xFF) << 40 |
									  ((long)mStream[mIndex++] & 0xFF) << 32 |
									  ((long)mStream[mIndex++] & 0xFF) << 24 |
									  ((long)mStream[mIndex++] & 0xFF) << 16 |
									  ((long)mStream[mIndex++] & 0xFF) << 8  |
									  ((long)mStream[mIndex++] & 0xFF));
		}

		private void stringLengthFromByteArray()
		{
			mStringLength = (mStream[mIndex++]       ) << 24 |
							(mStream[mIndex++] & 0xFF) << 16 |
							(mStream[mIndex++] & 0xFF) << 8  |
							(mStream[mIndex++] & 0xFF);
		}

		private void stringFromByteArray()
		{
			stringLengthFromByteArray();
			mResult[mResultIndex] = new String(mStream, mIndex, mStringLength);
			mIndex+=mStringLength;
		}

		@Override
		public boolean hasNext()
		{
			return mIndex < mStream.length;
		}
	}

	private static class Tuple1Iterator extends StreamIterator
	{

		Tuple1Iterator(StreamReader prmStreamReader)
		{
			super(prmStreamReader);
		}

		@Override
		public IOclTuple next()
		{
			Tuple1 wvTuple = new Tuple1(null);
			Object[] wvValues = readValuesFromStream();

			wvTuple.setT1(wvValues[0]);

			return wvTuple;
		}

		@Override
		public <R extends IOclTuple> R nextTuple()
		{
			return (R) next();
		}
	}

	private static class Tuple2Iterator extends StreamIterator
	{
		Tuple2Iterator(StreamReader prmStreamReader)
		{
			super(prmStreamReader);
		}

		@Override
		public IOclTuple next()
		{
			Tuple2 wvTuple = new Tuple2(null, null);
			Object[] wvValues = readValuesFromStream();

			wvTuple.setT1(wvValues[0]);
			wvTuple.setT2(wvValues[1]);

			return wvTuple;
		}

		@Override
		public <R extends IOclTuple> R nextTuple()
		{
			return (R) next();
		}
	}

	private static class Tuple3Iterator extends StreamIterator
	{
		Tuple3Iterator(StreamReader prmStreamReader)
		{
			super(prmStreamReader);
		}

		@Override
		public IOclTuple next()
		{
			Tuple3 wvTuple = new Tuple3(null, null, null);
			Object[] wvValues = readValuesFromStream();

			wvTuple.setT1(wvValues[0]);
			wvTuple.setT2(wvValues[1]);
			wvTuple.setT3(wvValues[2]);

			return wvTuple;
		}

		@Override
		public <R extends IOclTuple> R nextTuple()
		{
			return (R)next();
		}
	}
}
