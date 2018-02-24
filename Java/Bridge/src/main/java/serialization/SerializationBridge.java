package serialization;

public class SerializationBridge
{

	public SerializationBridge()
	{
		System.loadLibrary("SerializationBridge");
	}

	public static native byte[] BackToBack(byte[] prmData);

	public static native void Print(byte[] prmData);

	public static native String toString(byte[] prmStream);

	public static native byte[] Adds(byte[] stream, byte valueToAdd);
}
