package tuples.generics;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Tuple implements IOclTuple
{
	private byte mArity;
	private int mResultDim = 1;

	private List<SerializationInfo> mSerializationChunks;

	protected Tuple(byte prmArity)
	{
		mArity = prmArity;
		mSerializationChunks = new LinkedList<>();
	}

	@Override
	public byte getArity()
	{
		return mArity;
	}

	protected void addT(Object prmT)
	{
		SerializationInfo wvInfo = toByteArray(prmT);
		mResultDim += (wvInfo.getLength());
		mSerializationChunks.add(wvInfo);
	}

	@Override
	public byte[] toByteStream()
	{
		byte[] wvResult = new byte[mResultDim];

		wvResult[0] = mArity;

		int wvIndex = 1;
		byte wvType;
		int wvLength;
		//Here set the types and the length of the string (type 3) if any
		for (SerializationInfo wvInfo: mSerializationChunks)
		{
			wvType = wvInfo.getType();

			wvResult[wvIndex] = wvType;
			wvIndex++;

			if (wvType == Types.STRING)
			{
				wvLength = wvInfo.getStream().length;
				byteArrayCopy(wvResult, wvIndex, toByteArray(wvLength));
				wvIndex += 4;
			}
		}

		byte[] wvStream;
		//Here copy the serialized data
		for (SerializationInfo wvInfo: mSerializationChunks)
		{
			wvStream = wvInfo.getStream();
			wvLength = wvStream.length;
			byteArrayCopy(wvResult, wvIndex, wvStream);
			wvIndex += wvLength;
		}

		return wvResult;
	}

	protected List<Object> tValuesFromByteStream(byte[] prmInputStream)
	{
		int wvArity = mArity;
		List<Object> wvResult = new ArrayList<>(wvArity);
		Object wvTemp;
		int wvIndex = 1;
		byte wvType;
		byte[] wvTempStream;

		for (int i = 0; i < mArity; i++)
		{
			wvType = prmInputStream[wvIndex];
			wvIndex ++;
			switch (wvType)
			{
				case Types.INT:
					wvTempStream = new byte[4];
					byteArrayCopy(wvTempStream, prmInputStream, wvIndex, wvTempStream.length);
					wvTemp = toInteger(wvTempStream);
					break;
				case Types.DOUBLE:
					wvTempStream = new byte[8];
					byteArrayCopy(wvTempStream, prmInputStream, wvIndex, wvTempStream.length);
					wvTemp = toDouble(wvTempStream);
					break;
				case Types.STRING:
					wvTempStream = new byte[4];
					byteArrayCopy(wvTempStream, prmInputStream, wvIndex, wvTempStream.length);
					Integer wvStringLength = toInteger(wvTempStream);
					wvIndex += wvTempStream.length;
					wvTemp = new String(prmInputStream, wvIndex, wvStringLength);
					break;
				default:
					throw new IllegalArgumentException("Object type not recognized, unable to serialize it");
			}
			wvResult.add(i, wvTemp);
			wvIndex += wvTempStream.length;
		}

		return wvResult;
	}

	private static void byteArrayCopy(byte[] prmDest, int prmDestStartIndex, byte[] prmSource)
	{
		byteArrayCopy(prmDest, prmDestStartIndex, prmSource, 0, prmSource.length);
	}

	private static void byteArrayCopy(byte[] prmDest, byte[] prmSource, int prmSourceStartIndex, int prmSourceLength)
	{
		byteArrayCopy(prmDest, 0, prmSource, prmSourceStartIndex, prmSourceLength);
	}

	private static void byteArrayCopy(byte[] prmDest, int prmDestStartIndex, byte[] prmSource, int prmSourceStartIndex, int prmSourceLength)
	{
		for (int dI = prmDestStartIndex, sI = 0; dI < prmDest.length && sI < prmSourceLength ; dI++, sI++)
		{
			prmDest[dI] = prmSource[prmSourceStartIndex + sI];
		}
	}

	private static ByteBuffer mIntBuffer = ByteBuffer.allocate(4);
	private static ByteBuffer mDoubleBuffer = ByteBuffer.allocate(8);

	static final class Types
	{
		static final byte INT = 1;
		static final byte DOUBLE = 2;
		static final byte STRING = 3;
	}

	private static byte[] toByteArray(Integer prmValue)
	{
		return mIntBuffer.putInt(0, prmValue).array();
	}

	private static byte[] toByteArray(Double prmValue)
	{
		return mDoubleBuffer.putDouble(0, prmValue).array();
	}

	private static SerializationInfo toByteArray(Object prmValue)
	{
		switch (prmValue.getClass().getName())
		{
			case "java.lang.Integer":
				return new SerializationInfo(Types.INT, toByteArray((Integer) prmValue));
			case "java.lang.Double":
				return new SerializationInfo(Types.DOUBLE, toByteArray((Double) prmValue));
			case "java.lang.String":
				return new SerializationInfo(Types.STRING, ((String)prmValue).getBytes());
			default:
				throw new IllegalArgumentException("Object type not recognized, unable to serialize it");
		}
	}

	public static Integer toInteger(byte[] prmValue)
	{
		return ByteBuffer.wrap(prmValue).getInt();
	}

	public static Double toDouble(byte[] prmValue)
	{
		return ByteBuffer.wrap(prmValue).getDouble();
	}

	public static class SerializationInfo
	{
		private byte mType;
		private byte[] mStream;
		private int mLength;

		public SerializationInfo(byte prmType, byte[] prmStream)
		{
			mType = prmType;
			mStream = prmStream;
			mLength = mStream.length + 1;
			if(prmType == Types.STRING)
			{
				mLength += 4;
			}
		}

		public byte getType()
		{
			return mType;
		}

		public byte[] getStream()
		{
			return mStream;
		}

		public int getLength()
		{
			return mLength;
		}
	}

}
