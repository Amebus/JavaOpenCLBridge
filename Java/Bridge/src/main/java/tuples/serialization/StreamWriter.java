package tuples.serialization;

import tuples.generics.IOclTuple;
import tuples.generics.Tuple2;

import java.util.List;


public class StreamWriter
{
	private List<? extends IOclTuple> mTupleList;

	private byte[] mVarTypes;
	private int mIndex;

	private int mTempInteger;
	private Double mTempDouble;
	private String mTempString;

	public StreamWriter(List<? extends IOclTuple> prmTupleList)
	{
		mTupleList = prmTupleList;
	}

	public Tuple2<byte[], int[]> writeStream()
	{
		if (mTupleList == null || mTupleList.size() == 0)
		{
			// throw new IllegalArgumentException("The list cannot be empty");
			return new Tuple2<>(new byte[0], new int[0]);
		}

		IOclTuple wvTemplateTuple = mTupleList.get(0);
		byte wvArity = wvTemplateTuple.getArity();
		int wvStreamLength = 1 + wvArity;
		mVarTypes = getTypes(wvTemplateTuple);
		mIndex = wvStreamLength;

		for (IOclTuple wvTuple : mTupleList)
		{
			wvStreamLength += getBytesDim(wvTuple);
		}

		final byte[] wvStream = new byte[wvStreamLength];
		final int[] wvTupleIndexes = new int[mTupleList.size()];

		wvStream[0] = wvArity;

		System.arraycopy(mVarTypes, 0, wvStream, 1, mVarTypes.length);

		int wvI = 0;
		for (IOclTuple wvTuple : mTupleList)
		{
			wvTupleIndexes[wvI++] = writeStream(wvTuple, wvStream);
		}

		return new Tuple2<>(wvStream, wvTupleIndexes);
	}


	private byte[] getTypes(IOclTuple prmTuple)
	{
		byte[] wvResult = new byte[prmTuple.getArity()];
		final int[] wvI = {0};

		prmTuple.iterator()
				.forEachRemaining( x ->
								   {
									   switch (x.getClass().getName())
									   {
										   case "java.lang.Integer":
											   wvResult[wvI[0]++] = Types.INT;
											   break;
										   case "java.lang.Double":
											   wvResult[wvI[0]++] = Types.DOUBLE;
											   break;
										   case "java.lang.String":
											   wvResult[wvI[0]++] = Types.STRING;
											   break;
										   default:
											   throw new IllegalArgumentException("Object type not recognized, unable to serialize it");
									   }
								   });
		return wvResult;
	}

	private int getBytesDim(IOclTuple prmTuple)
	{
		int wvDim = 0;
		int wvIndex = 0;

		for (Object wvT : prmTuple)
		{
			switch (mVarTypes[wvIndex++])
			{
				case Types.DOUBLE:
					wvDim += Dimensions.DOUBLE;
					break;
				case Types.STRING:
					wvDim += ((String)wvT).length();
				case Types.INT:
					wvDim += Dimensions.INT;
			}
		}

		return wvDim;
	}

	public int writeStream(IOclTuple prmTuple, byte[] prmStream)
	{
		int wvStartIndex = mIndex;
		int i = 0;

		for (Object wvObj : prmTuple)
		{
			switch (mVarTypes[i])
			{
				case Types.INT:
					mTempInteger = (int) wvObj;
					insertInt(prmStream);
					break;
				case Types.DOUBLE:
					mTempDouble = (Double) wvObj;
					insertDouble(prmStream);
					break;
				case Types.STRING:
					mTempString = (String) wvObj;
					insertString(prmStream);
					break;
			}
			i++;
		}
		return wvStartIndex;
	}

	private void insertInt(byte[] prmStream)
	{
		prmStream[mIndex++] = (byte)(mTempInteger >> 24);
		prmStream[mIndex++] = (byte)(mTempInteger >> 16);
		prmStream[mIndex++] = (byte)(mTempInteger >> 8);
		prmStream[mIndex++] = (byte) mTempInteger;
	}

	private void insertDouble(byte[] prmStream)
	{
		long wvL = Double.doubleToLongBits(mTempDouble);
		prmStream[mIndex++] = (byte)((wvL >> 56) & 0xFF);
		prmStream[mIndex++] = (byte)((wvL >> 48) & 0xFF);
		prmStream[mIndex++] = (byte)((wvL >> 40) & 0xFF);
		prmStream[mIndex++] = (byte)((wvL >> 32) & 0xFF);
		prmStream[mIndex++] = (byte)((wvL >> 24) & 0xFF);
		prmStream[mIndex++] = (byte)((wvL >> 16) & 0xFF);
		prmStream[mIndex++] = (byte)((wvL >> 8) & 0xFF);
		prmStream[mIndex++] = (byte)(wvL & 0xFF);
	}

	private void insertString(byte[] prmStream)
	{
		byte[] wvStream = mTempString.getBytes();
		insertStringLength(prmStream, wvStream.length);
		for (int i = 0; i < wvStream.length && mIndex < prmStream.length; i++, mIndex++)
		{
			prmStream[mIndex] = wvStream[i];
		}

		// int wvLength = prmValue.length();
		// insertStringLength(prmStream, wvLength);
		// for (int i = 0; i < wvLength && mIndex < prmStream.length; i++, mIndex++)
		// {
		// 	prmStream[mIndex] = (byte) prmValue.charAt(i);
		// }
	}

	private void insertStringLength(byte[] prmStream, int prmValue)
	{
		prmStream[mIndex++] = (byte)(prmValue >> 24);
		prmStream[mIndex++] = (byte)(prmValue >> 16);
		prmStream[mIndex++] = (byte)(prmValue >> 8);
		prmStream[mIndex++] = (byte)prmValue;
	}
}
