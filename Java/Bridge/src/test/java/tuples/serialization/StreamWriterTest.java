package tuples.serialization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import tuples.generics.Tuple1;
import tuples.generics.Tuple2;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static testHelpers.StreamsGetter.*;
import static testHelpers.Constants.*;
import static testHelpers.TupleGetters.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StreamWriterTest
{

	@Test
	void Writer_EmptyStreamFromNullList_Ok()
	{
		StreamWriter wvWriter = StreamWriter.getStreamWriter();
		StreamWriterResult wvResult = wvWriter.writeStream();

		assertTrue(0 == wvResult.getStream().length);
		assertTrue(0 == wvResult.getPositions().length);
	}

	@Test
	void Writer_EmptyStreamFormEmptyList_Ok()
	{
		StreamWriter wvWriter = StreamWriter.getStreamWriter().setTupleList(getEmptyTupleList());
		StreamWriterResult wvResult = wvWriter.writeStream();

		assertTrue(0 == wvResult.getStream().length);
		assertTrue(0 == wvResult.getPositions().length);
	}

	@Test
	void Writer_StreamFormUnsupportedTuple_Error()
	{
		StreamWriter wvWriter = StreamWriter.getStreamWriter().setTupleList(getListWithUsupportedT());
		boolean wvError = false;

		try
		{
			wvWriter.writeStream();
		}
		catch (IllegalArgumentException ex)
		{
			wvError = true;
		}
		assertTrue(wvError);
	}

	@Test
	void WriteTuple1_Integer_Ok()
	{
		Tuple1<Integer> wvTuple = new Tuple1<>(ITV_0);
		
		byte[] wvStream = getStreamFrom(wvTuple);

		assertEquals(6, wvStream.length);

		assertEquals(wvTuple.getArity(), wvStream[0]);
		assertEquals(Types.INT, wvStream[1]);
	}


	@Test
	void WriteTuple1_Double_Ok()
	{
		Tuple1<Double> wvTuple = new Tuple1<>(DTV_0);
		
		byte[] wvStream = getStreamFrom(wvTuple);

		assertEquals(10, wvStream.length);

		assertEquals(wvTuple.getArity(), wvStream[0]);
		assertEquals(Types.DOUBLE, wvStream[1]);
	}

	@Test
	void WriteTuple1_String_Ok()
	{
		Tuple1<String> wvTuple = new Tuple1<>(STV_0);
		
		byte[] wvStream = getStreamFrom(wvTuple);

		assertEquals(2 + 4 + STV_0.length(), wvStream.length);

		assertEquals(wvTuple.getArity(), wvStream[0]);
		assertEquals(Types.STRING, wvStream[1]);

		byte[] wvStringLength = Arrays.copyOfRange(wvStream, 2, 6);

		assertEquals(STV_0.length(), ByteBuffer.wrap(wvStringLength).getInt());
	}

	@Test
	void WriteTuple2_IntegerInteger_Ok()
	{
		Tuple2<Integer, Integer> wvTuple = new Tuple2<>(ITV_1, ITV_2);
		
		byte[] wvStream = getStreamFrom(wvTuple);

		assertEquals(11, wvStream.length);

		assertEquals(wvTuple.getArity(), wvStream[0]);
		assertEquals(Types.INT, wvStream[1]);
		assertEquals(Types.INT, wvStream[2]);
	}

	@Test
	void WriteTuple2_IntegerDouble_Ok()
	{
		Tuple2<Integer, Double> wvTuple = new Tuple2<>(ITV_1, DTV_2);
		byte[] wvStream = getStreamFrom(wvTuple);

		assertEquals(15, wvStream.length);

		assertEquals(wvTuple.getArity(), wvStream[0]);
		assertEquals(Types.INT, wvStream[1]);
		assertEquals(Types.DOUBLE, wvStream[2]);
	}

	@Test
	void WriteTuple2_IntegerString_Ok()
	{
		Tuple2<Integer, String> wvTuple = new Tuple2<>(ITV_1, STV_2);
		byte[] wvStream = getStreamFrom(wvTuple);

		assertEquals(11 + STV_2.length(), wvStream.length);

		assertEquals(wvTuple.getArity(), wvStream[0]);
		assertEquals(Types.INT, wvStream[1]);
		assertEquals(Types.STRING, wvStream[2]);
	}

	@Test
	void WriteTuple2_DoubleInteger_Ok()
	{
		Tuple2<Double, Integer> wvTuple = new Tuple2<>(DTV_1, ITV_2);
		byte[] wvStream = getStreamFrom(wvTuple);

		assertEquals(15, wvStream.length);

		assertEquals(wvTuple.getArity(), wvStream[0]);
		assertEquals(Types.DOUBLE, wvStream[1]);
		assertEquals(Types.INT, wvStream[2]);
	}

	@Test
	void WriteTuple2_DoubleDouble_Ok()
	{
		Tuple2<Double, Double> wvTuple = new Tuple2<>(DTV_1, DTV_2);
		byte[] wvStream = getStreamFrom(wvTuple);

		assertEquals(19, wvStream.length);

		assertEquals(wvTuple.getArity(), wvStream[0]);
		assertEquals(Types.DOUBLE, wvStream[1]);
		assertEquals(Types.DOUBLE, wvStream[2]);
	}

	@Test
	void WriteTuple2_DoubleString_Ok()
	{
		Tuple2<Double, String> wvTuple = new Tuple2<>(DTV_1, STV_2);
		byte[] wvStream = getStreamFrom(wvTuple);

		assertEquals(15 + STV_2.length(), wvStream.length);

		assertEquals(wvTuple.getArity(), wvStream[0]);
		assertEquals(Types.DOUBLE, wvStream[1]);
		assertEquals(Types.STRING, wvStream[2]);
	}

	@Test
	void WriteTuple2_StringString_Ok()
	{
		Tuple2<String, String> wvTuple = new Tuple2<>(STV_1, STV_2);
		byte[] wvStream = getStreamFrom(wvTuple);

		assertEquals(1 + 10 + STV_1.length() + STV_2.length(), wvStream.length);

		assertEquals(wvTuple.getArity(), wvStream[0]);
		assertEquals(Types.STRING, wvStream[1]);
		assertEquals(Types.STRING, wvStream[2]);
	}

	@Test
	void WriteTuple2_StringInteger_Ok()
	{
		Tuple2<String, Integer> wvTuple = new Tuple2<>(STV_1, ITV_2);
		byte[] wvStream = getStreamFrom(wvTuple);

		assertEquals(1 + 10 + STV_1.length(), wvStream.length);

		assertEquals(wvTuple.getArity(), wvStream[0]);
		assertEquals(Types.STRING, wvStream[1]);
		assertEquals(Types.INT, wvStream[2]);
	}

	@Test
	void WriteTuple2_StringDouble_Ok()
	{
		Tuple2<String, Double> wvTuple = new Tuple2<>(STV_1, DTV_2);
		byte[] wvStream = getStreamFrom(wvTuple);

		assertEquals(1 + 14 + STV_1.length(), wvStream.length);

		assertEquals(wvTuple.getArity(), wvStream[0]);
		assertEquals(Types.STRING, wvStream[1]);
		assertEquals(Types.DOUBLE, wvStream[2]);
	}

	@Test
	void WriteMultipleTuple1_Integer_Ok()
	{
		List<Tuple1<Integer>> wvList = getIntegerTupleList();

		StreamWriter wvStreamWriter = getStreamWriterFrom(wvList);

		Tuple2<byte[], int[]> wvResult = wvStreamWriter.writeStream();
		int wvExpectedLength = 2 + wvList.size() * Dimensions.INT;
		byte[] wvStream = wvResult.getT1();
		int[] wvTupleIndexes = wvResult.getT2();


		assertEquals( wvExpectedLength , wvStream.length);
		assertEquals( wvList.size() , wvTupleIndexes.length);

		assertEquals( 1, wvStream[0]);
		assertEquals( Types.INT, wvStream[1]);

	}
}
