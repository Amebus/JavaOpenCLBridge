package tuples.generics;

import org.junit.jupiter.api.*;
import tuples.serialization.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static test.helpers.StreamsGetter.*;
import static test.helpers.TupleGetters.*;
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
		List<Tuple1<Integer>> vExpectedList = getIntegerTupleList();
		StreamReader vReader = getStreamReaderFrom(vExpectedList);

		assertEquals(vExpectedList, vReader.getTupleList());
	}

	@Test
	void StreamFromTuple1DoubleList_Ok()
	{
		checkExpectedList(getDoubleTupleList());
	}

	@Test
	void TupleListFromTuple1DoubleList_Ok()
	{
		List<Tuple1<Double>> vExpectedList = getDoubleTupleList();
		StreamReader vReader = getStreamReaderFrom(vExpectedList);

		assertEquals(vExpectedList, vReader.getTupleList());
	}

	@Test
	void StreamFromTuple1StringList_Ok()
	{
		checkExpectedList(getStringTupleList());
	}

	@Test
	void TupleListFromTuple1StringList_Ok()
	{
		List<Tuple1<String>> vExpectedList = getStringTupleList();
		StreamReader vReader = getStreamReaderFrom(vExpectedList);

		assertEquals(vExpectedList, vReader.getTupleList());
	}
}
