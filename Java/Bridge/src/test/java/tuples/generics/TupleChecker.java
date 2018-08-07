package tuples.generics;

import tuples.serialization.StreamWriterResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static test.helpers.StreamsGetter.*;
import static test.helpers.TupleGetters.*;

public final class TupleChecker
{

	public static void checkExpectedList(List<? extends IOclTuple> prmExpectedList)
	{
		IOclTuple vFirstTuple = prmExpectedList.get(0);
		int vExpectedStreamLength = getExpectedStreamLength(prmExpectedList);

		StreamWriterResult vWriterResult = getStreamWriterResultFrom(prmExpectedList);
		byte[] vStream = vWriterResult.getStream();

		assertEquals(vFirstTuple.getArityOcl(), vStream[0]);
		assertEquals(vExpectedStreamLength, vStream.length);

		assertArrayEquals(getTupleExpectedPositions(prmExpectedList), vWriterResult.getPositions());
	}

}
