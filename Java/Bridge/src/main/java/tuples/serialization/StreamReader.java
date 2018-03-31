package tuples.serialization;

import tuples.generics.*;

import javax.validation.constraints.NotNull;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StreamReader implements Iterable<IOclTuple>
{
	public static final String DIMENSION_ERROR = "Tuple dimension not supported";
	public static final String DESERIALIZATION_ERROR = "Object type not recognized, unable to deserialize it";


	private byte mArity;
	private byte[] mStream;

	private Tuple1Iterator mTuple1Iterator;
	private Tuple2Iterator mTuple2Iterator;
	private Tuple3Iterator mTuple3Iterator;

	private static StreamReader sStreamReader = new StreamReader();

	public static StreamReader getStreamReader()
	{
		return sStreamReader;
	}

	private StreamReader()
	{
		mTuple1Iterator = new Tuple1Iterator();
		mTuple2Iterator = new Tuple2Iterator();
		mTuple3Iterator = new Tuple3Iterator();
	}

	public StreamReader setStream(byte[] prmStream)
	{
		mStream = prmStream;
		mArity = mStream[0];
		return this;
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
	public @NotNull Iterator<IOclTuple> iterator()
	{
		switch (getArity())
		{
			case 1:
				return mTuple1Iterator.setStreamReader(this);
			case 2:
				return mTuple2Iterator.setStreamReader(this);
			case 3:
				return mTuple3Iterator.setStreamReader(this);
			default:
				throw new IllegalArgumentException(DIMENSION_ERROR);
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


		StreamIterator()
		{

		}

		// StreamIterator(StreamReader prmStreamReader)
		// {
		// 	setStreamReader(prmStreamReader);
		// }

		StreamIterator setStreamReader(StreamReader prmStreamReader)
		{
			byte wvArity = prmStreamReader.getArity();
			mStream = prmStreamReader.getStream();
			if (mArity != wvArity)
			{
				mArity = wvArity;
				mResult = new Object[wvArity];
			}
			mIndex = 1 + mArity;
			mTypeIndex = 1;
			mResultIndex = 0;
			mStringLength = 0;
			return this;
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
						throw new IllegalArgumentException(DESERIALIZATION_ERROR);
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

		Tuple1Iterator()
		{
			super();
		}

		// Tuple1Iterator(StreamReader prmStreamReader)
		// {
		// 	super(prmStreamReader);
		// }

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

		Tuple2Iterator()
		{
			super();
		}

		// Tuple2Iterator(StreamReader prmStreamReader)
		// {
		// 	super(prmStreamReader);
		// }

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

		Tuple3Iterator()
		{
			super();
		}

		// Tuple3Iterator(StreamReader prmStreamReader)
		// {
		// 	super(prmStreamReader);
		// }

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
