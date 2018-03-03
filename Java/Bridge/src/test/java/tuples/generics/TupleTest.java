package tuples.generics;

import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Tuple1Test
{
	private static final Integer INTEGER_TEST_VALUE = -12;
	private static final Double DOUBLE_TEST_VALUE = -12.5;
	private static final String STRING_TEST_VALUE = "string";
	
	@Test
	void Tuple1_Arity_Ok()
	{
		Tuple1<Integer> wvTuple;

		wvTuple = new Tuple1<>(null);
		assertEquals(1, wvTuple.getArity());
	}

	@Test
	void Tuple1_Integer_Ok ()
	{
		Tuple1<Integer> wvTuple;

		wvTuple = new Tuple1<>(null);
		assertEquals(null, wvTuple.getT1());

		wvTuple = new Tuple1<>(INTEGER_TEST_VALUE);
		assertEquals(INTEGER_TEST_VALUE, wvTuple.getT1());
	}

	@Test
	void Tuple1_Integer_ToByteStream_Ok()
	{
		Tuple1<Integer> wvTuple1 = new Tuple1<>(INTEGER_TEST_VALUE);
		byte[] wvStream = wvTuple1.toByteStream();

		assertEquals(6, wvStream.length);

		assertEquals(wvTuple1.getArity(), wvStream[0]);
		assertEquals(Tuple.Types.INT, wvStream[1]);
	}

	@Test
	void Tuple1_Integer_FromByteStream_Ok()
	{
		Tuple1<Integer> wvTuple1 = new Tuple1<>(INTEGER_TEST_VALUE);
		Tuple1<Integer> wvTuple2 = new Tuple1<>(null);
		byte[] wvStream = wvTuple1.toByteStream();

		wvTuple2.fromByteStream(wvStream);

		assertEquals(wvTuple1.getArity(), wvTuple2.getArity());
		assertEquals(wvTuple1.getT1(), wvTuple2.getT1());
	}

	@Test
	void Tuple1_Double_Ok ()
	{
		Tuple1<Double> wvTuple;

		wvTuple = new Tuple1<>(null);
		assertEquals(null, wvTuple.getT1());

		wvTuple = new Tuple1<>(DOUBLE_TEST_VALUE);
		assertEquals(DOUBLE_TEST_VALUE, wvTuple.getT1());
	}

	@Test
	void Tuple1_Double_ToByteStream_Ok()
	{
		Tuple1<Double> wvTuple = new Tuple1<>(DOUBLE_TEST_VALUE);
		byte[] wvStream = wvTuple.toByteStream();

		assertEquals(10, wvStream.length);

		assertEquals(wvTuple.getArity(), wvStream[0]);
		assertEquals(Tuple.Types.DOUBLE, wvStream[1]);
	}

	@Test
	void Tuple1_Double_FromByteStream_Ok()
	{
		Tuple1<Double> wvTuple1 = new Tuple1<>(DOUBLE_TEST_VALUE);
		Tuple1<Double> wvTuple2 = new Tuple1<>(null);
		byte[] wvStream = wvTuple1.toByteStream();

		wvTuple2.fromByteStream(wvStream);

		assertEquals(wvTuple1.getArity(), wvTuple2.getArity());
		assertEquals(wvTuple1.getT1(), wvTuple2.getT1());
	}

	@Test
	void Tuple1_String_Ok ()
	{
		Tuple1<String> wvTuple;

		wvTuple = new Tuple1<>(null);
		assertEquals(null, wvTuple.getT1());

		wvTuple = new Tuple1<>(STRING_TEST_VALUE);
		assertEquals(STRING_TEST_VALUE, wvTuple.getT1());
	}

	@Test
	void Tuple1_String_ToByteStream_Ok()
	{
		Tuple1<String> wvTuple1 = new Tuple1<>(STRING_TEST_VALUE);
		byte[] wvStream = wvTuple1.toByteStream();

		assertEquals( 2 + 4 + STRING_TEST_VALUE.length(), wvStream.length);

		assertEquals(wvTuple1.getArity(), wvStream[0]);
		assertEquals(Tuple.Types.STRING, wvStream[1]);

		byte[] wvStringLength = Arrays.copyOfRange(wvStream, 2, 6);

		assertEquals((Integer) STRING_TEST_VALUE.length(), Tuple.toInteger(wvStringLength));
	}

	@Test
	void Tuple1_String_FromByteStream_Ok()
	{
		Tuple1<String> wvTuple1 = new Tuple1<>(STRING_TEST_VALUE);
		Tuple1<String> wvTuple2 = new Tuple1<>(null);
		byte[] wvStream = wvTuple1.toByteStream();

		wvTuple2.fromByteStream(wvStream);

		assertEquals(wvTuple1.getArity(), wvTuple2.getArity());
		assertEquals(wvTuple1.getT1(), wvTuple2.getT1());
	}

}
