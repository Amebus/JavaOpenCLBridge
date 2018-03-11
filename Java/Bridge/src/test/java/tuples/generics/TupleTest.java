package tuples.generics;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static testHelpers.Constants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TupleTest
{
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

		wvTuple = new Tuple1<>(ITV_0);
		assertEquals(ITV_0, wvTuple.getT1());
	}

	@Test
	void Tuple1_Double_Ok ()
	{
		Tuple1<Double> wvTuple;

		wvTuple = new Tuple1<>(null);
		assertEquals(null, wvTuple.getT1());

		wvTuple = new Tuple1<>(DTV_0);
		assertEquals(DTV_0, wvTuple.getT1());
	}

	@Test
	void Tuple1_String_Ok ()
	{
		Tuple1<String> wvTuple;

		wvTuple = new Tuple1<>(null);
		assertEquals(null, wvTuple.getT1());

		wvTuple = new Tuple1<>(STV_0);
		assertEquals(STV_0, wvTuple.getT1());
	}

	@Test
	void Tuple2_Arity_Ok()
	{
		Tuple2<Integer, Double> wvTuple;

		wvTuple = new Tuple2<>(null, null);
		assertEquals(2, wvTuple.getArity());
	}

	@Test
	void Tuple2_Integer_Integer_Ok ()
	{
		Tuple2<Integer, Integer> wvTuple;

		wvTuple = new Tuple2<>(null, null);
		assertEquals(null, wvTuple.getT1());
		assertEquals(null, wvTuple.getT2());

		wvTuple = new Tuple2<>(ITV_1, ITV_2);
		assertEquals(ITV_1, wvTuple.getT1());
		assertEquals(ITV_2, wvTuple.getT2());
	}

	@Test
	void Tuple2_Double_Double_Ok ()
	{
		Tuple2<Double, Double> wvTuple;

		wvTuple = new Tuple2<>(null, null);
		assertEquals(null, wvTuple.getT1());
		assertEquals(null, wvTuple.getT2());

		wvTuple = new Tuple2<>(DTV_1, DTV_2);
		assertEquals(DTV_1, wvTuple.getT1());
		assertEquals(DTV_2, wvTuple.getT2());
	}

	@Test
	void Tuple2_String_String_Ok ()
	{
		Tuple2<String, String> wvTuple;

		wvTuple = new Tuple2<>(null, null);
		assertEquals(null, wvTuple.getT1());
		assertEquals(null, wvTuple.getT2());

		wvTuple = new Tuple2<>(STV_1, STV_2);
		assertEquals(STV_1, wvTuple.getT1());
		assertEquals(STV_2, wvTuple.getT2());
	}

}
