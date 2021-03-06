package test.helpers;

import tuples.generics.IOclTuple;
import tuples.serialization.StreamReader;
import tuples.serialization.StreamWriter;
import tuples.serialization.StreamWriterResult;

import java.util.LinkedList;
import java.util.List;

public class StreamsGetter
{

	private static List<? extends IOclTuple> toList(IOclTuple pTuple)
	{
		List<IOclTuple> vList = new LinkedList<>();
		vList.add(pTuple);
		return vList;
	}

	public static byte[] getStreamFrom(IOclTuple pTuple)
	{
		return getStreamFrom(toList(pTuple));
	}

	public static byte[] getStreamFrom(List<? extends IOclTuple> pTupleList)
	{
		return getStreamWriterFrom(pTupleList).writeStream().getT1();
	}


	public static StreamWriterResult getStreamWriterResultFrom(IOclTuple pTuple)
	{
		return getStreamWriterResultFrom(toList(pTuple));
	}

	public static StreamWriterResult getStreamWriterResultFrom(List<? extends IOclTuple> pTupleList)
	{
		return new StreamWriterResult(getStreamWriterFrom(pTupleList).writeStream());
	}

	public static StreamWriter getStreamWriterFrom(IOclTuple pTuple)
	{
		return getStreamWriterFrom(toList(pTuple));
	}

	public static StreamWriter getStreamWriterFrom(List<? extends IOclTuple> pTupleList)
	{
		return StreamWriter.getStreamWriter().setTupleList(pTupleList);
	}

	public static StreamReader getStreamReaderFrom(IOclTuple pTuple)
	{
		return getStreamReaderFrom(toList(pTuple));
	}

	public static StreamReader getStreamReaderFrom(List<? extends IOclTuple> pTupleList)
	{
		return StreamReader.getStreamReader().setStream(getStreamWriterFrom(pTupleList).writeStream().getT1());
	}

}
