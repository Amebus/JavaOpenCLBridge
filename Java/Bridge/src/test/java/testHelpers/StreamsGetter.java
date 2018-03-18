package testHelpers;

import tuples.generics.IOclTuple;
import tuples.serialization.StreamReader;
import tuples.serialization.StreamWriter;
import tuples.serialization.StreamWriterResult;

import java.util.LinkedList;
import java.util.List;

public class StreamsGetter
{

	private static List<? extends IOclTuple> toList(IOclTuple prmTuple)
	{
		List<IOclTuple> wvList = new LinkedList<>();
		wvList.add(prmTuple);
		return wvList;
	}

	public static byte[] getStreamFrom(IOclTuple prmTuple)
	{
		return getStreamFrom(toList(prmTuple));
	}

	public static byte[] getStreamFrom(List<? extends IOclTuple> prmTupleList)
	{
		return getStreamWriterFrom(prmTupleList).writeStream().getT1();
	}


	public static StreamWriterResult getStreamWriterResultFrom(IOclTuple prmTuple)
	{
		return getStreamWriterResultFrom(toList(prmTuple));
	}

	public static StreamWriterResult getStreamWriterResultFrom(List<? extends IOclTuple> prmTupleList)
	{
		return new StreamWriterResult(getStreamWriterFrom(prmTupleList).writeStream());
	}

	public static StreamWriter getStreamWriterFrom(IOclTuple prmTuple)
	{
		return getStreamWriterFrom(toList(prmTuple));
	}

	public static StreamWriter getStreamWriterFrom(List<? extends IOclTuple> prmTupleList)
	{
		return new StreamWriter(prmTupleList);
	}

	public static StreamReader getStreamReaderFrom(IOclTuple prmTuple)
	{
		return getStreamReaderFrom(toList(prmTuple));
	}

	public static StreamReader getStreamReaderFrom(List<? extends IOclTuple> prmTupleList)
	{
		return new StreamReader(getStreamWriterFrom(prmTupleList).writeStream().getT1());
	}

}
