package tuples.generics;

import org.junit.jupiter.api.*;
import testHelpers.StreamWriterResult;
import tuples.serialization.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static testHelpers.StreamsGetter.*;
import static testHelpers.TupleGetters.*;

public final class TupleChecker
{

	public static void checkExpectedList(List<? extends IOclTuple> prmExpectedList)
	{
		IOclTuple wvFirstTuple = prmExpectedList.get(0);
		int wvExpectedStreamLength = getExpectedStreamLength(prmExpectedList);

		StreamWriterResult wvWriterResult = getStreamWriterResultFrom(prmExpectedList);
		byte[] wvStream = wvWriterResult.getStream();

		assertEquals(wvFirstTuple.getArity(), wvStream[0]);
		assertEquals(wvExpectedStreamLength, wvStream.length);

		assertArrayEquals(getTupleExpectedPositions(prmExpectedList), wvWriterResult.getPositions());
	}

}
