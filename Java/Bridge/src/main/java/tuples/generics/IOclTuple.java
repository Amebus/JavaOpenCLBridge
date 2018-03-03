package tuples.generics;

public interface IOclTuple
{

	byte[] toByteStream();

	void fromByteStream(byte[] prmInputStream);

	byte getArity();

}
