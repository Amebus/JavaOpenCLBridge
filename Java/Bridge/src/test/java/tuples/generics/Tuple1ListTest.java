package tuples.generics;

import org.junit.jupiter.api.*;
import tuples.serialization.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static testHelpers.StreamsGetter.*;
import static testHelpers.TupleGetters.*;
import static tuples.generics.TupleChecker.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Tuple1ListTest
{
	@Test
	void StreamFromTuple1IntegerList_Ok()
	{
		checkExpectedList(getIntegerTupleList());
	}

	@Test
	void TupleListFromTuple1IntegerList_Ok()
	{
		List<Tuple1<Integer>> wvExpectedList = getIntegerTupleList();
		StreamReader wvReader = getStreamReaderFrom(wvExpectedList);

		assertEquals(wvExpectedList, wvReader.getTupleList());
	}

	@Test
	void StreamFromTuple1DoubleList_Ok()
	{
		checkExpectedList(getDoubleTupleList());
	}

	@Test
	void TupleListFromTuple1DoubleList_Ok()
	{
		List<Tuple1<Double>> wvExpectedList = getDoubleTupleList();
		StreamReader wvReader = getStreamReaderFrom(wvExpectedList);

		assertEquals(wvExpectedList, wvReader.getTupleList());
	}

	@Test
	void StreamFromTuple1StringList_Ok()
	{
		checkExpectedList(getStringTupleList());
	}

	@Test
	void TupleListFromTuple1StringList_Ok()
	{
		List<Tuple1<String>> wvExpectedList = getStringTupleList();
		StreamReader wvReader = getStreamReaderFrom(wvExpectedList);

		assertEquals(wvExpectedList, wvReader.getTupleList());
	}
}
