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
		boolean wvError = false;

		try
		{
			new StreamReader(new byte[] { 100 }).getTupleList();
		}
		catch (IllegalArgumentException ex)
		{
			assertEquals(StreamReader.DIMENSION_ERROR, ex.getMessage());
			wvError = true;
		}

		assertTrue(wvError);
	}

	@Test
	void Read_UnsupportedT_Error()
	{
		boolean wvError = false;

		try
		{
			new StreamReader(new byte[] { 1, 100, 1 }).getTupleList();
		}
		catch (IllegalArgumentException ex)
		{
			assertEquals(StreamReader.DESERIALIZATION_ERROR, ex.getMessage());
			wvError = true;
		}

		assertTrue(wvError);
	}

	@Test
	void ReadTuple1_Integer_Ok()
	{
		Tuple1<Integer> wvTuple1 = new Tuple1<>(ITV_0);
		Tuple1<Integer> wvTuple2;

		IStreamReaderIterator wvIterator = getStreamReaderFrom(wvTuple1).streamReaderIterator();
		assertTrue(wvIterator.hasNext());
		wvTuple2 = wvIterator.nextTuple();

		assertEquals(wvTuple1.getArity(), wvTuple2.getArity());
		assertEquals(wvTuple1.getT1(), wvTuple2.getT1());
	}

	@Test
	void ReadTuple1_Double_Ok()
	{
		Tuple1<Double> wvTuple1 = new Tuple1<>(DTV_0);
		Tuple1<Double> wvTuple2;

		IStreamReaderIterator wvIterator = getStreamReaderFrom(wvTuple1).streamReaderIterator();
		assertTrue(wvIterator.hasNext());
		wvTuple2 = wvIterator.nextTuple();

		assertEquals(wvTuple1.getArity(), wvTuple2.getArity());
		assertEquals(wvTuple1.getT1(), wvTuple2.getT1());
	}

	@Test
	void ReadTuple1_String_Ok()
	{
		Tuple1<String> wvTuple1 = new Tuple1<>(STV_0);
		Tuple1<String> wvTuple2;

		IStreamReaderIterator wvIterator = getStreamReaderFrom(wvTuple1).streamReaderIterator();
		assertTrue(wvIterator.hasNext());
		wvTuple2 = wvIterator.nextTuple();

		assertEquals(wvTuple1.getArity(), wvTuple2.getArity());
		assertEquals(wvTuple1.getT1(), wvTuple2.getT1());
	}

	@Test
	void ReadTuple2_IntegerInteger_Ok()
	{
		Tuple2<Integer, Integer> wvTuple1 = new Tuple2<>(ITV_1, ITV_2);

		IStreamReaderIterator wvIterator = getStreamReaderFrom(wvTuple1).streamReaderIterator();
		assertTrue(wvIterator.hasNext());
		Tuple2<Integer, Integer> wvTuple2 = wvIterator.nextTuple();

		assertEquals(wvTuple1.getArity(), wvTuple2.getArity());
		assertEquals(wvTuple1.getT1(), wvTuple2.getT1());
		assertEquals(wvTuple1.getT2(), wvTuple2.getT2());
	}

	@Test
	void ReadTuple2_IntegerDouble_Ok()
	{
		Tuple2<Integer, Double> wvTuple1 = new Tuple2<>(ITV_1, DTV_2);

		IStreamReaderIterator wvIterator = getStreamReaderFrom(wvTuple1).streamReaderIterator();
		assertTrue(wvIterator.hasNext());
		Tuple2<Integer, Double> wvTuple2 = wvIterator.nextTuple();

		assertEquals(wvTuple1.getArity(), wvTuple2.getArity());
		assertEquals(wvTuple1.getT1(), wvTuple2.getT1());
		assertEquals(wvTuple1.getT2(), wvTuple2.getT2());
	}


	@Test
	void ReadTuple2_IntegerString_Ok()
	{
		Tuple2<Integer, String> wvTuple1 = new Tuple2<>(ITV_1, STV_2);

		IStreamReaderIterator wvIterator = getStreamReaderFrom(wvTuple1).streamReaderIterator();
		assertTrue(wvIterator.hasNext());
		Tuple2<Integer, String> wvTuple2 = wvIterator.nextTuple();

		assertEquals(wvTuple1.getArity(), wvTuple2.getArity());
		assertEquals(wvTuple1.getT1(), wvTuple2.getT1());
		assertEquals(wvTuple1.getT2(), wvTuple2.getT2());
	}


	@Test
	void ReadTuple2_DoubleInteger_Ok()
	{
		Tuple2<Double, Integer> wvTuple1 = new Tuple2<>(DTV_1, ITV_2);

		IStreamReaderIterator wvIterator = getStreamReaderFrom(wvTuple1).streamReaderIterator();
		assertTrue(wvIterator.hasNext());
		Tuple2<Double, Integer> wvTuple2 = wvIterator.nextTuple();

		assertEquals(wvTuple1.getArity(), wvTuple2.getArity());
		assertEquals(wvTuple1.getT1(), wvTuple2.getT1());
		assertEquals(wvTuple1.getT2(), wvTuple2.getT2());
	}


	@Test
	void ReadTuple2_DoubleDouble_Ok()
	{
		Tuple2<Double, Double> wvTuple1 = new Tuple2<>(DTV_1, DTV_2);

		IStreamReaderIterator wvIterator = getStreamReaderFrom(wvTuple1).streamReaderIterator();
		assertTrue(wvIterator.hasNext());
		Tuple2<Double, Double> wvTuple2 = wvIterator.nextTuple();

		assertEquals(wvTuple1.getArity(), wvTuple2.getArity());
		assertEquals(wvTuple1.getT1(), wvTuple2.getT1());
		assertEquals(wvTuple1.getT2(), wvTuple2.getT2());
	}


	@Test
	void ReadTuple2_DoubleString_Ok()
	{
		Tuple2<Double, String> wvTuple1 = new Tuple2<>(DTV_1, STV_2);

		IStreamReaderIterator wvIterator = getStreamReaderFrom(wvTuple1).streamReaderIterator();
		assertTrue(wvIterator.hasNext());
		Tuple2<Double, String> wvTuple2 = wvIterator.nextTuple();

		assertEquals(wvTuple1.getArity(), wvTuple2.getArity());
		assertEquals(wvTuple1.getT1(), wvTuple2.getT1());
		assertEquals(wvTuple1.getT2(), wvTuple2.getT2());
	}


	@Test
	void ReadTuple2_StringString_Ok()
	{
		Tuple2<String, String> wvTuple1 = new Tuple2<>(STV_1, STV_2);

		IStreamReaderIterator wvIterator = getStreamReaderFrom(wvTuple1).streamReaderIterator();
		assertTrue(wvIterator.hasNext());
		Tuple2<String, String> wvTuple2 = wvIterator.nextTuple();

		assertEquals(wvTuple1.getArity(), wvTuple2.getArity());
		assertEquals(wvTuple1.getT1(), wvTuple2.getT1());
		assertEquals(wvTuple1.getT2(), wvTuple2.getT2());
	}


	@Test
	void ReadTuple2_StringInteger_Ok()
	{
		Tuple2<String, Integer> wvTuple1 = new Tuple2<>(STV_1, ITV_2);

		IStreamReaderIterator wvIterator = getStreamReaderFrom(wvTuple1).streamReaderIterator();
		assertTrue(wvIterator.hasNext());
		Tuple2<String, Integer> wvTuple2 = wvIterator.nextTuple();

		assertEquals(wvTuple1.getArity(), wvTuple2.getArity());
		assertEquals(wvTuple1.getT1(), wvTuple2.getT1());
		assertEquals(wvTuple1.getT2(), wvTuple2.getT2());
	}

	@Test
	void ReadTuple2_StringDouble_Ok()
	{
		Tuple2<String, Double> wvTuple1 = new Tuple2<>(STV_1, DTV_2);

		IStreamReaderIterator wvIterator = getStreamReaderFrom(wvTuple1).streamReaderIterator();
		assertTrue(wvIterator.hasNext());
		Tuple2<String, Double> wvTuple2 = wvIterator.nextTuple();

		assertEquals(wvTuple1.getArity(), wvTuple2.getArity());
		assertEquals(wvTuple1.getT1(), wvTuple2.getT1());
		assertEquals(wvTuple1.getT2(), wvTuple2.getT2());
	}
}
