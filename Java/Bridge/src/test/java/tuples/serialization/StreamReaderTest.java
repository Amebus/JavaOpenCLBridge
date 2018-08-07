package tuples.serialization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import tuples.generics.Tuple1Ocl;
import tuples.generics.Tuple2Ocl;
import tuples.serialization.StreamReader.*;

import static org.junit.jupiter.api.Assertions.*;
import static test.helpers.StreamsGetter.*;
import static test.helpers.Constants.*;

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
		Tuple1Ocl<Integer> vTuple1Ocl = new Tuple1Ocl<>(ITV_0);
		Tuple1Ocl<Integer> vTuple2;

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1Ocl).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		vTuple2 = vIterator.nextTuple();

		assertEquals(vTuple1Ocl.getArityOcl(), vTuple2.getArityOcl());
		assertEquals(vTuple1Ocl.getField(0), vTuple2.getField(0));
	}

	@Test
	void ReadTuple1_Double_Ok()
	{
		Tuple1Ocl<Double> vTuple1Ocl = new Tuple1Ocl<>(DTV_0);
		Tuple1Ocl<Double> vTuple2;

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1Ocl).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		vTuple2 = vIterator.nextTuple();

		assertEquals(vTuple1Ocl.getArityOcl(), vTuple2.getArityOcl());
		assertEquals(vTuple1Ocl.getField(0), vTuple2.getField(0));
	}

	@Test
	void ReadTuple1_String_Ok()
	{
		Tuple1Ocl<String> vTuple1Ocl = new Tuple1Ocl<>(STV_0);
		Tuple1Ocl<String> vTuple2;

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1Ocl).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		vTuple2 = vIterator.nextTuple();

		assertEquals(vTuple1Ocl.getArityOcl(), vTuple2.getArityOcl());
		assertEquals(vTuple1Ocl.getField(0), vTuple2.getField(0));
	}

	@Test
	void ReadTuple2_IntegerInteger_Ok()
	{
		Tuple2Ocl<Integer, Integer> vTuple1 = new Tuple2Ocl<>(ITV_1, ITV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2Ocl<Integer, Integer> vTuple2Ocl = vIterator.nextTuple();

		assertEquals(vTuple1.getArityOcl(), vTuple2Ocl.getArityOcl());
		assertEquals(vTuple1.getField(0), vTuple2Ocl.getField(0));
		assertEquals(vTuple1.getField(1), vTuple2Ocl.getField(1));
	}

	@Test
	void ReadTuple2_IntegerDouble_Ok()
	{
		Tuple2Ocl<Integer, Double> vTuple1 = new Tuple2Ocl<>(ITV_1, DTV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2Ocl<Integer, Double> vTuple2Ocl = vIterator.nextTuple();

		assertEquals(vTuple1.getArityOcl(), vTuple2Ocl.getArityOcl());
		assertEquals(vTuple1.getField(0), vTuple2Ocl.getField(0));
		assertEquals(vTuple1.getField(1), vTuple2Ocl.getField(1));
	}


	@Test
	void ReadTuple2_IntegerString_Ok()
	{
		Tuple2Ocl<Integer, String> vTuple1 = new Tuple2Ocl<>(ITV_1, STV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2Ocl<Integer, String> vTuple2Ocl = vIterator.nextTuple();

		assertEquals(vTuple1.getArityOcl(), vTuple2Ocl.getArityOcl());
		assertEquals(vTuple1.getField(0), vTuple2Ocl.getField(0));
		assertEquals(vTuple1.getField(1), vTuple2Ocl.getField(1));
	}


	@Test
	void ReadTuple2_DoubleInteger_Ok()
	{
		Tuple2Ocl<Double, Integer> vTuple1 = new Tuple2Ocl<>(DTV_1, ITV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2Ocl<Double, Integer> vTuple2Ocl = vIterator.nextTuple();

		assertEquals(vTuple1.getArityOcl(), vTuple2Ocl.getArityOcl());
		assertEquals(vTuple1.getField(0), vTuple2Ocl.getField(0));
		assertEquals(vTuple1.getField(1), vTuple2Ocl.getField(1));
	}


	@Test
	void ReadTuple2_DoubleDouble_Ok()
	{
		Tuple2Ocl<Double, Double> vTuple1 = new Tuple2Ocl<>(DTV_1, DTV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2Ocl<Double, Double> vTuple2Ocl = vIterator.nextTuple();

		assertEquals(vTuple1.getArityOcl(), vTuple2Ocl.getArityOcl());
		assertEquals(vTuple1.getField(0), vTuple2Ocl.getField(0));
		assertEquals(vTuple1.getField(1), vTuple2Ocl.getField(1));
	}


	@Test
	void ReadTuple2_DoubleString_Ok()
	{
		Tuple2Ocl<Double, String> vTuple1 = new Tuple2Ocl<>(DTV_1, STV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2Ocl<Double, String> vTuple2Ocl = vIterator.nextTuple();

		assertEquals(vTuple1.getArityOcl(), vTuple2Ocl.getArityOcl());
		assertEquals(vTuple1.getField(0), vTuple2Ocl.getField(0));
		assertEquals(vTuple1.getField(1), vTuple2Ocl.getField(1));
	}


	@Test
	void ReadTuple2_StringString_Ok()
	{
		Tuple2Ocl<String, String> vTuple1 = new Tuple2Ocl<>(STV_1, STV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2Ocl<String, String> vTuple2Ocl = vIterator.nextTuple();

		assertEquals(vTuple1.getArityOcl(), vTuple2Ocl.getArityOcl());
		assertEquals(vTuple1.getField(0), vTuple2Ocl.getField(0));
		assertEquals(vTuple1.getField(1), vTuple2Ocl.getField(1));
	}


	@Test
	void ReadTuple2_StringInteger_Ok()
	{
		Tuple2Ocl<String, Integer> vTuple1 = new Tuple2Ocl<>(STV_1, ITV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2Ocl<String, Integer> vTuple2Ocl = vIterator.nextTuple();

		assertEquals(vTuple1.getArityOcl(), vTuple2Ocl.getArityOcl());
		assertEquals(vTuple1.getField(0), vTuple2Ocl.getField(0));
		assertEquals(vTuple1.getField(1), vTuple2Ocl.getField(1));
	}

	@Test
	void ReadTuple2_StringDouble_Ok()
	{
		Tuple2Ocl<String, Double> vTuple1 = new Tuple2Ocl<>(STV_1, DTV_2);

		IStreamReaderIterator vIterator = getStreamReaderFrom(vTuple1).streamReaderIterator();
		assertTrue(vIterator.hasNext());
		Tuple2Ocl<String, Double> vTuple2Ocl = vIterator.nextTuple();

		assertEquals(vTuple1.getArityOcl(), vTuple2Ocl.getArityOcl());
		assertEquals(vTuple1.getField(0), vTuple2Ocl.getField(0));
		assertEquals(vTuple1.getField(1), vTuple2Ocl.getField(1));
	}
}
