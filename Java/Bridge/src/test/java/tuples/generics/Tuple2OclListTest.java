package tuples.generics;

import org.junit.jupiter.api.*;
import tuples.serialization.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static test.helpers.StreamsGetter.*;
import static test.helpers.TupleGetters.*;
import static tuples.generics.TupleChecker.checkExpectedList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Tuple2OclListTest
{

	@Test
	void StreamFromTuple2_IntegerInteger_List_Ok()
	{
		checkExpectedList(getIntegerIntegerTupleList());
	}

	@Test
	void StreamFromTuple2_IntegerDouble_List_Ok()
	{
		checkExpectedList(getIntegerDoubleTupleList());
	}

	@Test
	void StreamFromTuple2_IntegerString_List_Ok()
	{
		checkExpectedList(getIntegerStringTupleList());
	}

	@Test
	void TupleListFromTuple2_IntegerInteger_List_Ok()
	{
		List<Tuple2Ocl<Integer, Integer>> vExpectedList = getIntegerIntegerTupleList();
		StreamReader vReader = getStreamReaderFrom(vExpectedList);

		assertEquals(vExpectedList, vReader.getTupleList());
	}

	@Test
	void TupleListFromTuple2_IntegerDouble_List_Ok()
	{
		List<Tuple2Ocl<Integer, Double>> vExpectedList = getIntegerDoubleTupleList();
		StreamReader vReader = getStreamReaderFrom(vExpectedList);

		assertEquals(vExpectedList, vReader.getTupleList());
	}

	@Test
	void TupleListFromTuple2_IntegerString_List_Ok()
	{
		List<Tuple2Ocl<Integer, String>> vExpectedList = getIntegerStringTupleList();
		StreamReader vReader = getStreamReaderFrom(vExpectedList);

		assertEquals(vExpectedList, vReader.getTupleList());
	}

	@Test
	void StreamFromTuple2_DoubleDouble_List_Ok()
	{
		checkExpectedList(getDoubleDoubleTupleList());
	}

	@Test
	void StreamFromTuple2_DoubleInteger_List_Ok()
	{
		checkExpectedList(getDoubleIntegerTupleList());
	}

	@Test
	void StreamFromTuple2_DoubleString_List_Ok()
	{
		checkExpectedList(getDoubleStringTupleList());
	}

	@Test
	void TupleListFromTuple2_DoubleDouble_List_Ok()
	{
		List<Tuple2Ocl<Double, Double>> vExpectedList = getDoubleDoubleTupleList();
		StreamReader vReader = getStreamReaderFrom(vExpectedList);

		assertEquals(vExpectedList, vReader.getTupleList());
	}

	@Test
	void TupleListFromTuple2_DoubleInteger_List_Ok()
	{
		List<Tuple2Ocl<Double, Integer>> vExpectedList = getDoubleIntegerTupleList();
		StreamReader vReader = getStreamReaderFrom(vExpectedList);

		assertEquals(vExpectedList, vReader.getTupleList());
	}

	@Test
	void TupleListFromTuple2_DoubleString_List_Ok()
	{
		List<Tuple2Ocl<Double, String>> vExpectedList = getDoubleStringTupleList();
		StreamReader vReader = getStreamReaderFrom(vExpectedList);

		assertEquals(vExpectedList, vReader.getTupleList());
	}

	@Test
	void StreamFromTuple2_StringString_List_Ok()
	{
		checkExpectedList(getStringStringTupleList());
	}

	@Test
	void StreamFromTuple2_StringInteger_List_Ok()
	{
		checkExpectedList(getStringIntegerTupleList());
	}

	@Test
	void StreamFromTuple2_StringDouble_List_Ok()
	{
		checkExpectedList(getStringDoubleTupleList());
	}

	@Test
	void TupleListFromTuple2_StringString_List_Ok()
	{
		List<Tuple2Ocl<String, String>> vExpectedList = getStringStringTupleList();
		StreamReader vReader = getStreamReaderFrom(vExpectedList);

		assertEquals(vExpectedList, vReader.getTupleList());
	}

	@Test
	void TupleListFromTuple2_StringInteger_List_Ok()
	{
		List<Tuple2Ocl<String, Integer>> vExpectedList = getStringIntegerTupleList();
		StreamReader vReader = getStreamReaderFrom(vExpectedList);

		assertEquals(vExpectedList, vReader.getTupleList());
	}

	@Test
	void TupleListFromTuple2_StringDouble_List_Ok()
	{
		List<Tuple2Ocl<String, Double>> vExpectedList = getStringDoubleTupleList();
		StreamReader vReader = getStreamReaderFrom(vExpectedList);

		assertEquals(vExpectedList, vReader.getTupleList());
	}

}
