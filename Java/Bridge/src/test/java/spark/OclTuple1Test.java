// package spark;
//
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestInstance;
// import scala.Int;
// import scala.Tuple1;
// import scala.Tuple2;
//
// import static org.junit.jupiter.api.Assertions.*;
//
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
// public class OclTuple1Test
// {
//
// 	@Test
// 	public void OclTuple_DoubleValue_Ok()
// 	{
// 		OclTuple1<Double> wvTuple1 = new OclTuple1<>(10.4);
//
// 		assertEquals((Double)10.4, wvTuple1._1());
// 	}
//
// 	@Test
// 	public void OclTuple_IntegerValue_Ok()
// 	{
// 		OclTuple1<Integer> wvTuple1 = new OclTuple1<>(10);
//
// 		assertEquals((Integer) 10, wvTuple1._1());
// 	}
//
// 	@Test
// 	public void OclTuple_CharacterValue_Ok()
// 	{
// 		OclTuple1<Character> wvTuple1 = new OclTuple1<>('A');
//
// 		assertEquals((Character) 'A', wvTuple1._1());
// 	}
//
// 	@Test
// 	public void OclTuple_StringValue_Ok()
// 	{
// 		OclTuple1<String> wvTuple1 = new OclTuple1<>("Ciao");
//
// 		assertEquals( "Ciao", wvTuple1._1());
// 	}
//
// 	@Test
// 	public void OclTuple_CanEqual_Ok()
// 	{
// 		OclTuple1<Integer> wvIntegerOclTuple1 = new OclTuple1<>(10);
// 		OclTuple1<Double> wvDoubleOclTuple1 = new OclTuple1<>(10.4);
// 		OclTuple1<Character> wvCharacterOclTuple1 = new OclTuple1<>('A');
// 		OclTuple1<String> wvStringOclTuple1 = new OclTuple1<>("Ciao");
// 		Tuple1<Integer> wvIntTuple1 = new Tuple1<>(10);
//
// 		assertTrue(wvIntegerOclTuple1.canEqual(wvIntegerOclTuple1));
// 		assertTrue(wvIntegerOclTuple1.canEqual(wvDoubleOclTuple1));
// 		assertTrue(wvIntegerOclTuple1.canEqual(wvCharacterOclTuple1));
// 		assertTrue(wvIntegerOclTuple1.canEqual(wvStringOclTuple1));
// 		assertFalse(wvIntegerOclTuple1.canEqual(wvIntTuple1));
//
// 		assertTrue(wvDoubleOclTuple1.canEqual(wvIntegerOclTuple1));
// 		assertTrue(wvDoubleOclTuple1.canEqual(wvDoubleOclTuple1));
// 		assertTrue(wvDoubleOclTuple1.canEqual(wvCharacterOclTuple1));
// 		assertTrue(wvDoubleOclTuple1.canEqual(wvStringOclTuple1));
// 		assertFalse(wvDoubleOclTuple1.canEqual(wvIntTuple1));
//
// 		assertTrue(wvCharacterOclTuple1.canEqual(wvIntegerOclTuple1));
// 		assertTrue(wvCharacterOclTuple1.canEqual(wvDoubleOclTuple1));
// 		assertTrue(wvCharacterOclTuple1.canEqual(wvCharacterOclTuple1));
// 		assertTrue(wvCharacterOclTuple1.canEqual(wvStringOclTuple1));
// 		assertFalse(wvCharacterOclTuple1.canEqual(wvIntTuple1));
//
// 		assertTrue(wvStringOclTuple1.canEqual(wvIntegerOclTuple1));
// 		assertTrue(wvStringOclTuple1.canEqual(wvDoubleOclTuple1));
// 		assertTrue(wvStringOclTuple1.canEqual(wvCharacterOclTuple1));
// 		assertTrue(wvStringOclTuple1.canEqual(wvStringOclTuple1));
// 		assertFalse(wvStringOclTuple1.canEqual(wvIntTuple1));
//
// 		assertTrue(wvIntegerOclTuple1.canEqual(wvIntegerOclTuple1));
// 		assertTrue(wvIntegerOclTuple1.canEqual(wvDoubleOclTuple1));
// 		assertTrue(wvIntegerOclTuple1.canEqual(wvCharacterOclTuple1));
// 		assertTrue(wvIntegerOclTuple1.canEqual(wvStringOclTuple1));
// 	}
//
// 	@Test void OclTuple_AreEqual_Ok()
// 	{
// 		OclTuple1<Integer> wvIntegerOclTuple1A = new OclTuple1<>(10);
// 		OclTuple1<Integer> wvIntegerOclTuple1B = new OclTuple1<>(10);
//
// 		assertTrue(wvIntegerOclTuple1A.canEqual(wvIntegerOclTuple1B));
// 		assertEquals(wvIntegerOclTuple1A, wvIntegerOclTuple1B);
// 	}
//
// 	@Test void OclTuple_AreNotEqual_Ok()
// 	{
// 		OclTuple1<Integer> wvIntegerOclTuple1A = new OclTuple1<>(10);
// 		OclTuple1<Integer> wvIntegerOclTuple1B = new OclTuple1<>(11);
//
// 		assertTrue(wvIntegerOclTuple1A.canEqual(wvIntegerOclTuple1B));
// 		assertNotEquals(wvIntegerOclTuple1A, wvIntegerOclTuple1B);
// 	}
//
//
//
// }
