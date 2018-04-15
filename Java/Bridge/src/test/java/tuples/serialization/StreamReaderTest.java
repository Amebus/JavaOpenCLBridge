package tuples.serialization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import tuples.generics.Tuple1;
import tuples.generics.Tuple2;
import tuples.serialization.StreamReader.*;

import static org.junit.jupiter.api.Assertions.*;
import static testHelpers.StreamsGetter.*;
import static testHelpers.Constants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StreamReaderTest
{

	@Test
	void Read_Tuple100_Error()
	{
		boolean vError = false;

		try
		{
			StreamReader.getStreamReader().setStream(new byte[] { 100 }).getTupleList();
		}
		catch (IllegalArgumentException ex)
		{
			assertEquals(StreamReader.DIMENSION_ERROR, ex.getMessage());
			vError = true;
		}

		assertTrue(vError);
	}

	@Test
	void Read_UnsupportedT_Error()
	{
		boolean vError = false;

		try
		{
			StreamReader.getStreamReader().setStream(new byte[] { 1, 100, 1 }).getTupleList();
		}
		catch (IllegalArgumentException ex)
		{
			assertEquals(StreamReader.DESERIALIZATION_ERROR, ex.getMessage());
			vError = true;
		}

		assertTrue(vError);
	}

	@Test
	void ReadTuple1_Integer_Ok()
	{
		Tuple1<Integer> vTuple1 = new Tuple1<>(ITV_0);
		Tuple1<Integer> vTuple2;

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		vTuple2 = vIterator.nextTuple();

		assertEquals(vTuple1.getArity(), vTuple2.getArity());
		assertEquals(vTuple1.getT1(), vTuple2.getT1());
	}

	@Test
	void ReadTuple1_Double_Ok()
	{
		Tuple1<Double> vTuple1 = new Tuple1<>(DTV_0);
		Tuple1<Double> vTuple2;

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		vTuple2 = vIterator.nextTuple();

		assertEquals(vTuple1.getArity(), vTuple2.getArity());
		assertEquals(vTuple1.getT1(), vTuple2.getT1());
	}

	@Test
	void ReadTuple1_String_Ok()
	{
		Tuple1<String> vTuple1 = new Tuple1<>(STV_0);
		Tuple1<String> vTuple2;

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		vTuple2 = vIterator.nextTuple();

		assertEquals(vTuple1.getArity(), vTuple2.getArity());
		assertEquals(vTuple1.getT1(), vTuple2.getT1());
	}

	@Test
	void ReadTuple2_IntegerInteger_Ok()
	{
		Tuple2<Integer, Integer> vTuple1 = new Tuple2<>(ITV_1, ITV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2<Integer, Integer> vTuple2 = vIterator.nextTuple();

		assertEquals(vTuple1.getArity(), vTuple2.getArity());
		assertEquals(vTuple1.getT1(), vTuple2.getT1());
		assertEquals(vTuple1.getT2(), vTuple2.getT2());
	}

	@Test
	void ReadTuple2_IntegerDouble_Ok()
	{
		Tuple2<Integer, Double> vTuple1 = new Tuple2<>(ITV_1, DTV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2<Integer, Double> vTuple2 = vIterator.nextTuple();

		assertEquals(vTuple1.getArity(), vTuple2.getArity());
		assertEquals(vTuple1.getT1(), vTuple2.getT1());
		assertEquals(vTuple1.getT2(), vTuple2.getT2());
	}


	@Test
	void ReadTuple2_IntegerString_Ok()
	{
		Tuple2<Integer, String> vTuple1 = new Tuple2<>(ITV_1, STV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2<Integer, String> vTuple2 = vIterator.nextTuple();

		assertEquals(vTuple1.getArity(), vTuple2.getArity());
		assertEquals(vTuple1.getT1(), vTuple2.getT1());
		assertEquals(vTuple1.getT2(), vTuple2.getT2());
	}


	@Test
	void ReadTuple2_DoubleInteger_Ok()
	{
		Tuple2<Double, Integer> vTuple1 = new Tuple2<>(DTV_1, ITV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2<Double, Integer> vTuple2 = vIterator.nextTuple();

		assertEquals(vTuple1.getArity(), vTuple2.getArity());
		assertEquals(vTuple1.getT1(), vTuple2.getT1());
		assertEquals(vTuple1.getT2(), vTuple2.getT2());
	}


	@Test
	void ReadTuple2_DoubleDouble_Ok()
	{
		Tuple2<Double, Double> vTuple1 = new Tuple2<>(DTV_1, DTV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2<Double, Double> vTuple2 = vIterator.nextTuple();

		assertEquals(vTuple1.getArity(), vTuple2.getArity());
		assertEquals(vTuple1.getT1(), vTuple2.getT1());
		assertEquals(vTuple1.getT2(), vTuple2.getT2());
	}


	@Test
	void ReadTuple2_DoubleString_Ok()
	{
		Tuple2<Double, String> vTuple1 = new Tuple2<>(DTV_1, STV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2<Double, String> vTuple2 = vIterator.nextTuple();

		assertEquals(vTuple1.getArity(), vTuple2.getArity());
		assertEquals(vTuple1.getT1(), vTuple2.getT1());
		assertEquals(vTuple1.getT2(), vTuple2.getT2());
	}


	@Test
	void ReadTuple2_StringString_Ok()
	{
		Tuple2<String, String> vTuple1 = new Tuple2<>(STV_1, STV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2<String, String> vTuple2 = vIterator.nextTuple();

		assertEquals(vTuple1.getArity(), vTuple2.getArity());
		assertEquals(vTuple1.getT1(), vTuple2.getT1());
		assertEquals(vTuple1.getT2(), vTuple2.getT2());
	}


	@Test
	void ReadTuple2_StringInteger_Ok()
	{
		Tuple2<String, Integer> vTuple1 = new Tuple2<>(STV_1, ITV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2<String, Integer> vTuple2 = vIterator.nextTuple();

		assertEquals(vTuple1.getArity(), vTuple2.getArity());
		assertEquals(vTuple1.getT1(), vTuple2.getT1());
		assertEquals(vTuple1.getT2(), vTuple2.getT2());
	}

	@Test
	void ReadTuple2_StringDouble_Ok()
	{
		Tuple2<String, Double> vTuple1 = new Tuple2<>(STV_1, DTV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2<String, Double> vTuple2 = vIterator.nextTuple();

		assertEquals(vTuple1.getArity(), vTuple2.getArity());
		assertEquals(vTuple1.getT1(), vTuple2.getT1());
		assertEquals(vTuple1.getT2(), vTuple2.getT2());
	}
}
