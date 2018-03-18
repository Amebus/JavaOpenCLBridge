package testHelpers;

import tuples.generics.Tuple2;

public class StreamWriterResult extends Tuple2<byte[], int[]>
{
	public StreamWriterResult(byte[] prmBytes, int[] prmInts)
	{
		super(prmBytes, prmInts);
	}

	public StreamWriterResult(Tuple2<byte[], int[]> prmTuple2)
	{
		super(prmTuple2.getT1(), prmTuple2.getT2());
	}

	public StreamWriterResult(StreamWriterResult prmResult)
	{
		super(prmResult.getStream(), prmResult.getPositions());
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
