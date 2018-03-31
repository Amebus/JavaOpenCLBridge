package tuples.generics;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
import static testHelpers.Constants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TupleTest
{
	@Test
	void Tuple1_Equals_Ok()
	{
		Tuple1<Integer> vTuple = new Tuple1<>(null);

		assertNotEquals(vTuple, null);
		assertNotEquals(vTuple, 3);
		assertNotEquals(vTuple, new Tuple2<>(null, null));
		assertEquals(vTuple, vTuple);
	}

	@Test
	void Tuple1_Arity_Ok()
	{
		Tuple1<Integer> vTuple = new Tuple1<>(null);

		assertEquals(1, vTuple.getArity());
	}

	@Test
	void Tuple1_Integer_Ok ()
	{
		Tuple1<Integer> vTuple, vTupleB;

		vTuple = new Tuple1<>(null);
		assertEquals(null, vTuple.getT1());

		vTuple = new Tuple1<>(ITV_0);
		assertEquals(ITV_0, vTuple.getT1());

		assertEquals(vTuple, vTuple);

		vTupleB = new Tuple1<>(ITV_0);

		assertEquals(vTuple, vTupleB);
		assertEquals(vTupleB, vTuple);
	}

	@Test
	void Tuple1_Double_Ok ()
	{
		Tuple1<Double> vTuple, vTupleB;

		vTuple = new Tuple1<>(null);
		assertEquals(null, vTuple.getT1());

		vTuple = new Tuple1<>(DTV_0);
		assertEquals(DTV_0, vTuple.getT1());

		vTupleB = new Tuple1<>(DTV_0);

		assertEquals(vTuple, vTupleB);
		assertEquals(vTupleB, vTuple);
	}

	@Test
	void Tuple1_String_Ok ()
	{
		Tuple1<String> vTuple, vTupleB;

		vTuple = new Tuple1<>(null);
		assertEquals(null, vTuple.getT1());

		vTuple = new Tuple1<>(STV_0);
		assertEquals(STV_0, vTuple.getT1());

		vTupleB = new Tuple1<>(STV_0);

		assertEquals(vTuple, vTupleB);
		assertEquals(vTupleB, vTuple);
	}

	@Test
	void Tuple2_Equals_Ok()
	{
		Tuple2<Integer, Double> vTuple = new Tuple2<>(null, null);

		assertNotEquals(vTuple, null);
		assertNotEquals(vTuple, 3);
		assertNotEquals(vTuple, new Tuple1<>(null));
		assertEquals(vTuple, vTuple);
	}

	@Test
	void Tuple2_Arity_Ok()
	{
		Tuple2<Integer, Double> vTuple = new Tuple2<>(null, null);

		assertEquals(2, vTuple.getArity());
	}

	@Test
	void Tuple2_Integer_Integer_Ok ()
	{
		Tuple2<Integer, Integer> vTuple, vTupleB;

		vTuple = new Tuple2<>(null, null);
		assertEquals(null, vTuple.getT1());
		assertEquals(null, vTuple.getT2());

		vTuple = new Tuple2<>(ITV_1, ITV_2);
		assertEquals(ITV_1, vTuple.getT1());
		assertEquals(ITV_2, vTuple.getT2());

		assertEquals(vTuple, vTuple);

		vTupleB = new Tuple2<>(ITV_1, ITV_2);

		assertEquals(vTuple, vTupleB);
		assertEquals(vTupleB, vTuple);
	}

	@Test
	void Tuple2_Double_Double_Ok ()
	{
		Tuple2<Double, Double> vTuple, vTupleB;

		vTuple = new Tuple2<>(null, null);
		assertEquals(null, vTuple.getT1());
		assertEquals(null, vTuple.getT2());

		vTuple = new Tuple2<>(DTV_1, DTV_2);
		assertEquals(DTV_1, vTuple.getT1());
		assertEquals(DTV_2, vTuple.getT2());

		vTupleB = new Tuple2<>(DTV_1, DTV_2);

		assertEquals(vTuple, vTupleB);
		assertEquals(vTupleB, vTuple);
	}

	@Test
	void Tuple2_String_String_Ok ()
	{
		Tuple2<String, String> vTuple, vTupleB;

		vTuple = new Tuple2<>(null, null);
		assertEquals(null, vTuple.getT1());
		assertEquals(null, vTuple.getT2());

		vTuple = new Tuple2<>(STV_1, STV_2);
		assertEquals(STV_1, vTuple.getT1());
		assertEquals(STV_2, vTuple.getT2());

		vTupleB = new Tuple2<>(STV_1, STV_2);

		assertEquals(vTuple, vTupleB);
		assertEquals(vTupleB, vTuple);
	}

}
