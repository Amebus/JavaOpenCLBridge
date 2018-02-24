package serialization;

import java.nio.ByteBuffer;

/**
 *
 * @author federico
 */
public class Serializer
{
	private static ByteBuffer mIntBuffer = ByteBuffer.allocate(4);
	private static ByteBuffer mLongBuffer = ByteBuffer.allocate(8);
	private static ByteBuffer mDoubleBuffer = ByteBuffer.allocate(8);
	private static ByteBuffer mCharBuffer = ByteBuffer.allocate(2);

	public static byte[] toByteArray(Integer prmValue)
	{
		return mIntBuffer.putInt(0, prmValue).array();
	}

	public static byte[] toByteArray(Long prmValue)
	{
		return mLongBuffer.putLong(0, prmValue).array();
	}

	public static byte[] toByteArray(Double prmValue)
	{
		return mDoubleBuffer.putDouble(0, prmValue).array();
	}

	/**
	 * TODO modificare per fare 1 char = 1 byte
	 * @param prmValue
	 * @return
	 */
	public static byte[] toByteArray(Character prmValue)
	{
		return mCharBuffer.putChar(0, prmValue).array();
	}

	public static byte[] toByteArray(String prmValue)
	{
		return prmValue.getBytes();
	}

	public static Integer toInt(byte[] prmValue)
	{
		return ByteBuffer.wrap(prmValue).getInt();
	}

	public static Long toLong(byte[] prmValue)
	{
		return ByteBuffer.wrap(prmValue).getLong();
	}

	public static Double toDouble(byte[] prmValue)
	{
		return ByteBuffer.wrap(prmValue).getDouble();
	}

	public static String toString(byte[] prmValue)
	{
		return new String(prmValue);
	}
}
