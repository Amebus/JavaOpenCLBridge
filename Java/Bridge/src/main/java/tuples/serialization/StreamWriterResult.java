package tuples.serialization;

import tuples.generics.Tuple2;

public class StreamWriterResult extends Tuple2<byte[], int[]>
{
	public StreamWriterResult(byte[] pBytes, int[] pInts)
	{
		super(pBytes, pInts);
	}

	public StreamWriterResult(StreamWriterResult pResult)
	{
		super(pResult.getStream(), pResult.getPositions());
	}


	public byte[] getStream()
	{
		return super.getT1();
	}

	public int[] getPositions()
	{
		return super.getT2();
	}
}
