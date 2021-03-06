package tuples.serialization;

import tuples.generics.IOclTuple;
import tuples.generics.OclTupleIterator;
import tuples.generics.Tuple2;

import java.util.Iterator;
import java.util.List;


public class StreamWriter
{
	private List<? extends IOclTuple> mTupleList;

	private byte[] mVarTypes;
	private int mIndex;

	private OclTupleIterator mIterator;
	private int mTempInteger;
	private Double mTempDouble;
	private String mTempString;

	private static StreamWriter sStreamWriter = new StreamWriter();

	public static StreamWriter getStreamWriter()
	{
		return sStreamWriter;
	}

	private StreamWriter()
	{
		mIterator = OclTupleIterator.getIterator();
	}

	public StreamWriter setTupleList(List<? extends IOclTuple> pTupleList)
	{
		mTupleList = pTupleList;
		return this;
	}

	public StreamWriterResult writeStream()
	{
		if (mTupleList == null || mTupleList.size() == 0)
		{
			// throw new IllegalArgumentException("The list cannot be empty");
			return new StreamWriterResult(new byte[0], new int[0]);
		}

		IOclTuple vTemplateTuple = mTupleList.get(0);
		byte vArity = vTemplateTuple.getArity();
		int vStreamLength = 1 + vArity;
		mVarTypes = getTypes(vTemplateTuple);
		mIndex = vStreamLength;

		for (IOclTuple vTuple : mTupleList)
		{
			vStreamLength += getBytesDim(vTuple);
		}

		mIterator.resetToArity(vTemplateTuple.getArity());

		final byte[] vStream = new byte[vStreamLength];
		final int[] vTupleIndexes = new int[mTupleList.size()];

		vStream[0] = vArity;

		System.arraycopy(mVarTypes, 0, vStream, 1, mVarTypes.length);

		int vI = 0;
		for (IOclTuple vTuple : mTupleList)
		{
			vTupleIndexes[vI++] = writeStream(vTuple, vStream);
		}

		return new StreamWriterResult(vStream, vTupleIndexes);
	}


	private byte[] getTypes(IOclTuple pTuple)
	{
		byte[] vResult = new byte[pTuple.getArity()];
		int vI = 0;

		for (Object vT : pTuple)
		{
			switch (vT.getClass().getName())
			{
				case "java.lang.Integer":
					vResult[vI++] = Types.INT;
					break;
				case "java.lang.Double":
					vResult[vI++] = Types.DOUBLE;
					break;
				case "java.lang.String":
					vResult[vI++] = Types.STRING;
					break;
				default:
					throw new IllegalArgumentException("Object type not recognized, unable to serialize it");
			}
		}
		return vResult;
	}

	private int getBytesDim(IOclTuple pTuple)
	{
		int vDim = 0;
		int vIndex = 0;

		for (Object vT : pTuple)
		{
			switch (mVarTypes[vIndex++])
			{
				case Types.DOUBLE:
					vDim += Dimensions.DOUBLE;
					break;
				case Types.STRING:
					vDim += ((String)vT).length();
				case Types.INT:
					vDim += Dimensions.INT;
			}
		}

		return vDim;
	}

	private int writeStream(IOclTuple pTuple, byte[] pStream)
	{
		int vStartIndex = mIndex;
		int i = 0;

		mIterator.iterateOver(pTuple);

		while (mIterator.hasNext())
		{
			switch (mVarTypes[i])
			{
				case Types.INT:
					mTempInteger = (int) mIterator.next();
					insertInt(pStream);
					break;
				case Types.DOUBLE:
					mTempDouble = (Double) mIterator.next();
					insertDouble(pStream);
					break;
				case Types.STRING:
					mTempString = (String) mIterator.next();
					insertString(pStream);
					break;
			}
			i++;
		}


		// for (Object vObj : pTuple)
		// {
		// 	switch (mVarTypes[i])
		// 	{
		// 		case Types.INT:
		// 			mTempInteger = (int) vObj;
		// 			insertInt(pStream);
		// 			break;
		// 		case Types.DOUBLE:
		// 			mTempDouble = (Double) vObj;
		// 			insertDouble(pStream);
		// 			break;
		// 		case Types.STRING:
		// 			mTempString = (String) vObj;
		// 			insertString(pStream);
		// 			break;
		// 	}
		// 	i++;
		// }
		return vStartIndex;
	}

	private void insertInt(byte[] pStream)
	{
		pStream[mIndex++] = (byte)(mTempInteger >> 24);
		pStream[mIndex++] = (byte)(mTempInteger >> 16);
		pStream[mIndex++] = (byte)(mTempInteger >> 8);
		pStream[mIndex++] = (byte) mTempInteger;
	}

	private void insertDouble(byte[] pStream)
	{
		long vL = Double.doubleToLongBits(mTempDouble);
		pStream[mIndex++] = (byte)((vL >> 56) & 0xFF);
		pStream[mIndex++] = (byte)((vL >> 48) & 0xFF);
		pStream[mIndex++] = (byte)((vL >> 40) & 0xFF);
		pStream[mIndex++] = (byte)((vL >> 32) & 0xFF);
		pStream[mIndex++] = (byte)((vL >> 24) & 0xFF);
		pStream[mIndex++] = (byte)((vL >> 16) & 0xFF);
		pStream[mIndex++] = (byte)((vL >> 8) & 0xFF);
		pStream[mIndex++] = (byte)(vL & 0xFF);
	}

	private void insertString(byte[] pStream)
	{
		byte[] vStream = mTempString.getBytes();
		insertStringLength(pStream, vStream.length);
		for (int i = 0; i < vStream.length && mIndex < pStream.length; i++, mIndex++)
		{
			pStream[mIndex] = vStream[i];
		}

		// int vLength = pValue.length();
		// insertStringLength(pStream, vLength);
		// for (int i = 0; i < vLength && mIndex < pStream.length; i++, mIndex++)
		// {
		// 	pStream[mIndex] = (byte) pValue.charAt(i);
		// }
	}

	private void insertStringLength(byte[] pStream, int pValue)
	{
		pStream[mIndex++] = (byte)(pValue >> 24);
		pStream[mIndex++] = (byte)(pValue >> 16);
		pStream[mIndex++] = (byte)(pValue >> 8);
		pStream[mIndex++] = (byte)pValue;
	}
}
