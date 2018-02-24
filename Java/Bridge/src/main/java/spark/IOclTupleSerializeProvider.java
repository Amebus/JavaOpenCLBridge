package spark;

public interface IOclTupleSerializeProvider
{

	byte[] serialize(Object input);

	<T extends IOclTuple> T deserialize(byte[] input);

}
