package tuples.generics;

import org.junit.jupiter.api.*;
import testHelpers.StreamWriterResult;
import tuples.serialization.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static testHelpers.StreamsGetter.*;
import static testHelpers.TupleGetters.*;
import static tuples.generics.TupleChecker.checkExpectedList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Tuple2ListTest
{

	@Test
	void StreamFromTuple2_IntegerInteger_List_Ok()
	{
		checkExpectedList(getIntegerIntegerTupleList());
	}

	@Test
	void TupleListFromTuple2_IntegerInteger_List_Ok()
	{
		List<Tuple2<Integer, Integer>> wvExpectedList = getIntegerIntegerTupleList();
		StreamReader wvReader = getStreamReaderFrom(wvExpectedList);

		assertEquals(wvExpectedList, wvReader.getTupleList());
	}

	@Test
	void StreamFromTuple2_DoubleDouble_List_Ok()
	{
		checkExpectedList(getDoubleDoubleTupleList());
	}

	@Test
	void TupleListFromTuple2_DoubleDouble_List_Ok()
	{
		List<Tuple2<Double, Double>> wvExpectedList = getDoubleDoubleTupleList();
		StreamReader wvReader = getStreamReaderFrom(wvExpectedList);

		assertEquals(wvExpectedList, wvReader.getTupleList());
	}

	@Test
	void StreamFromTuple2_StringString_List_Ok()
	{
		checkExpectedList(getStringStringTupleList());
	}

	@Test
	void TupleListFromTuple2_StringString_List_Ok()
	{
		List<Tuple2<String, String>> wvExpectedList = getStringStringTupleList();
		StreamReader wvReader = getStreamReaderFrom(wvExpectedList);

		assertEquals(wvExpectedList, wvReader.getTupleList());
	}

}
