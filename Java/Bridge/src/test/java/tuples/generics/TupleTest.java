package tuples.generics;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
import static test.helpers.Constants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TupleTest
{
	@Test
	void Tuple1_Equals_Ok()
	{
		Tuple1Ocl<Integer> vTuple = new Tuple1Ocl<>(null);

		assertNotEquals(vTuple, null);
		assertNotEquals(vTuple, 3);
		assertNotEquals(vTuple, new Tuple2Ocl<>(null, null));
		assertEquals(vTuple, vTuple);
	}

	@Test
	void Tuple1_Arity_Ok()
	{
		Tuple1Ocl<Integer> vTuple = new Tuple1Ocl<>(null);

		assertEquals(1, vTuple.getArityOcl());
	}

	@Test
	void Tuple1_Integer_Ok ()
	{
		Tuple1Ocl<Integer> vTuple, vTupleB;

		vTuple = new Tuple1Ocl<>(null);
		assertEquals(null, vTuple.getField(0));

		vTuple = new Tuple1Ocl<>(ITV_0);
		assertEquals(ITV_0, vTuple.getField(0));

		assertEquals(vTuple, vTuple);

		vTupleB = new Tuple1Ocl<>(ITV_0);

		assertEquals(vTuple, vTupleB);
		assertEquals(vTupleB, vTuple);
	}

	@Test
	void Tuple1_Double_Ok ()
	{
		Tuple1Ocl<Double> vTuple, vTupleB;

		vTuple = new Tuple1Ocl<>(null);
		assertEquals(null, vTuple.getField(0));

		vTuple = new Tuple1Ocl<>(DTV_0);
		assertEquals(DTV_0, vTuple.getField(0));

		vTupleB = new Tuple1Ocl<>(DTV_0);

		assertEquals(vTuple, vTupleB);
		assertEquals(vTupleB, vTuple);
	}

	@Test
	void Tuple1_String_Ok ()
	{
		Tuple1Ocl<String> vTuple, vTupleB;

		vTuple = new Tuple1Ocl<>(null);
		assertEquals(null, vTuple.getField(0));

		vTuple = new Tuple1Ocl<>(STV_0);
		assertEquals(STV_0, vTuple.getField(0));

		vTupleB = new Tuple1Ocl<>(STV_0);

		assertEquals(vTuple, vTupleB);
		assertEquals(vTupleB, vTuple);
	}

	@Test
	void Tuple2_Equals_Ok()
	{
		Tuple2Ocl<Integer, Double> vTuple = new Tuple2Ocl<>(null, null);

		assertNotEquals(vTuple, null);
		assertNotEquals(vTuple, 3);
		assertNotEquals(vTuple, new Tuple1Ocl<>(null));
		assertEquals(vTuple, vTuple);
	}

	@Test
	void Tuple2_Arity_Ok()
	{
		Tuple2Ocl<Integer, Double> vTuple = new Tuple2Ocl<>(null, null);

		assertEquals(2, vTuple.getArityOcl());
	}

	@Test
	void Tuple2_Integer_Integer_Ok ()
	{
		Tuple2Ocl<Integer, Integer> vTuple, vTupleB;

		vTuple = new Tuple2Ocl<>(null, null);
		assertEquals(null, vTuple.getField(0));
		assertEquals(null, vTuple.getField(1));

		vTuple = new Tuple2Ocl<>(ITV_1, ITV_2);
		assertEquals(ITV_1, vTuple.getField(0));
		assertEquals(ITV_2, vTuple.getField(1));

		assertEquals(vTuple, vTuple);

		vTupleB = new Tuple2Ocl<>(ITV_1, ITV_2);

		assertEquals(vTuple, vTupleB);
		assertEquals(vTupleB, vTuple);
	}

	@Test
	void Tuple2_Double_Double_Ok ()
	{
		Tuple2Ocl<Double, Double> vTuple, vTupleB;

		vTuple = new Tuple2Ocl<>(null, null);
		assertEquals(null, vTuple.getField(0));
		assertEquals(null, vTuple.getField(1));

		vTuple = new Tuple2Ocl<>(DTV_1, DTV_2);
		assertEquals(DTV_1, vTuple.getField(0));
		assertEquals(DTV_2, vTuple.getField(1));

		vTupleB = new Tuple2Ocl<>(DTV_1, DTV_2);

		assertEquals(vTuple, vTupleB);
		assertEquals(vTupleB, vTuple);
	}

	@Test
	void Tuple2_String_String_Ok ()
	{
		Tuple2Ocl<String, String> vTuple, vTupleB;

		vTuple = new Tuple2Ocl<>(null, null);
		assertEquals(null, vTuple.getField(0));
		assertEquals(null, vTuple.getField(1));

		vTuple = new Tuple2Ocl<>(STV_1, STV_2);
		assertEquals(STV_1, vTuple.getField(0));
		assertEquals(STV_2, vTuple.getField(1));

		vTupleB = new Tuple2Ocl<>(STV_1, STV_2);

		assertEquals(vTuple, vTupleB);
		assertEquals(vTupleB, vTuple);
	}

}
