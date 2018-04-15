package configuration;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import testHelpers.Constants;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static testHelpers.TTypesGetter.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TupleDefinitionTest
{

	@Test
	void TupleDefinition_Equals_Ok()
	{
		LoadSettingsDirective vSettingsDirective = new LoadSettingsDirective(Constants.RESOURCES_DIR, "tupleEqualsTest.json");
		Settings vSettings = getSettings(vSettingsDirective);
		Iterable<TupleDefinition> vDefinitions = vSettings.getTupleDefinitions().asIterable();

		vDefinitions.forEach( x -> assertTrue(x.equals(x)));

		vDefinitions.forEach( x -> assertTrue(x.equals(new TupleDefinition(x))));

		vDefinitions.forEach( x -> assertFalse(x.equals(null)));

		vDefinitions.forEach( x -> assertFalse(x.equals("string")));

		Iterator<TupleDefinition> vIterator = vDefinitions.iterator();

		TupleDefinition vOne, vTwo;

		vOne = vIterator.next();
		vTwo = vIterator.next();

		assertFalse(vOne.equals(vTwo));
		assertFalse(vTwo.equals(vOne));

		vTwo = vIterator.next();

		assertFalse(vOne.equals(vTwo));
		assertFalse(vTwo.equals(vOne));
	}

	@Test
	void TupleDefinition_HashCode_Ok()
	{
		LoadSettingsDirective vSettingsDirective = new LoadSettingsDirective(Constants.RESOURCES_DIR, "tupleEqualsTest.json");
		Settings vSettings = getSettings(vSettingsDirective);
		Iterable<TupleDefinition> vDefinitions = vSettings.getTupleDefinitions().asIterable();

		vDefinitions.forEach( x -> assertEquals(x.hashCode(), x.hashCode()));

		vDefinitions.forEach( x -> assertEquals(x.hashCode(), new TupleDefinition(x).hashCode()));

		Iterator<TupleDefinition> vIterator = vDefinitions.iterator();

		TupleDefinition vOne, vTwo;

		vOne = vIterator.next();
		vTwo = vIterator.next();

		assertNotEquals(vOne.hashCode(), vTwo.hashCode());
	}

	@Test
	void TupleDefinition_TWithIndexOutOfBoundIsNull_Ok()
	{
		Settings vSettings = getSettings();
		Iterator<TupleDefinition> vIterator = vSettings.getTupleDefinitions().asIterable().iterator();

		TupleDefinition vOne = vIterator.next();

		assertNull(vOne.getT(5));
		assertNull(vOne.getT(TupleDefinition.T_LIMIT + 1));

		assertNull(vOne.getJavaT(5));
		assertNull(vOne.getJavaT(TupleDefinition.T_LIMIT + 1));

		assertNull(vOne.getCT(5));
		assertNull(vOne.getCT(TupleDefinition.T_LIMIT + 1));
	}

	@Test
	void TupleDefinition_Iterator_Ok()
	{
		Settings vSettings = getSettings();

		Iterable<TupleDefinition> vDefinitions = vSettings.getTupleDefinitions().asIterable();

		int expectedCount = 4;
		final int[] actualCount = {0};

		Iterator<TupleDefinition> vTupleIterator = vDefinitions.iterator();

		TupleDefinition vTupleDefinition = vTupleIterator.next();

		assertTrue(expectedCount == vTupleDefinition.getArity());
		assertEquals("tupleOne", vTupleDefinition.getName());

		final TType[] expectedTypes =
				{
					getJavaTInteger(),
					getJavaTString(),
					getJavaTDouble(),
					getJavaTInteger()
				};

		vTupleDefinition.forEach( x ->
								   {
									   	assertEquals(expectedTypes[actualCount[0]], x);
										actualCount[0]++;
								   });

		assertEquals(expectedCount, actualCount[0]);

		expectedCount = 1;
		actualCount[0] = 0;
		expectedTypes[0] = getJavaTDouble();


		vTupleDefinition = vTupleIterator.next();
		vTupleDefinition.forEach( x ->
								   {
									   assertEquals(expectedTypes[actualCount[0]], x);
									   actualCount[0]++;
								   });

		assertEquals(expectedCount, actualCount[0]);


		assertFalse(vTupleIterator.hasNext());
	}

	@Test
	void TupleDefinition_ReverseIterator_Ok()
	{
		Settings vSettings = getSettings();

		Iterable<TupleDefinition> vDefinitions = vSettings.getTupleDefinitions().asIterable();

		int expectedCount = 4;
		final int[] actualCount = {0};

		Iterator<TupleDefinition> vTupleIterator = vDefinitions.iterator();

		TupleDefinition vTupleDefinition = vTupleIterator.next();

		assertTrue(expectedCount == vTupleDefinition.getArity());
		assertEquals("tupleOne", vTupleDefinition.getName());

		final TType[] expectedTypes =
				{
						getJavaTInteger(),
						getJavaTDouble(),
						getJavaTString(),
						getJavaTInteger()
				};

		vTupleDefinition.reverseIterator().forEachRemaining( x ->
								   {
									   assertEquals(expectedTypes[actualCount[0]], x);
									   actualCount[0]++;
								   });

		assertEquals(expectedCount, actualCount[0]);

		expectedCount = 1;
		actualCount[0] = 0;
		expectedTypes[0] = getJavaTDouble();


		vTupleDefinition = vTupleIterator.next();
		vTupleDefinition.reverseIterator().forEachRemaining( x ->
								   {
									   assertEquals(expectedTypes[actualCount[0]], x);
									   actualCount[0]++;
								   });

		assertEquals(expectedCount, actualCount[0]);


		assertFalse(vTupleIterator.hasNext());
	}

	@Test
	void TupleDefinition_CIterator_Ok()
	{
		Settings vSettings = getSettings();

		Iterable<TupleDefinition> vDefinitions = vSettings.getTupleDefinitions().asIterable();

		int expectedCount = 4;
		final int[] actualCount = {0};

		Iterator<TupleDefinition> vTupleIterator = vDefinitions.iterator();

		TupleDefinition vTupleDefinition = vTupleIterator.next();

		assertTrue(expectedCount == vTupleDefinition.getArity());
		assertEquals("tupleOne", vTupleDefinition.getName());

		final TType[] expectedTypes =
				{
						getCTInteger(),
						getCTString(),
						getCTDouble(),
						getCTInteger()
				};

		vTupleDefinition.cIterator().forEachRemaining( x ->
								   {
									   assertEquals(expectedTypes[actualCount[0]], x);
									   actualCount[0]++;
								   });

		assertEquals(expectedCount, actualCount[0]);

		expectedCount = 1;
		actualCount[0] = 0;
		expectedTypes[0] = getCTDouble();


		vTupleDefinition = vTupleIterator.next();
		vTupleDefinition.cIterator().forEachRemaining( x ->
								   {
									   assertEquals(expectedTypes[actualCount[0]], x);
									   actualCount[0]++;
								   });

		assertEquals(expectedCount, actualCount[0]);


		assertFalse(vTupleIterator.hasNext());
	}

	@Test
	void TupleDefinition_ReverseCIterator_Ok()
	{
		Settings vSettings = getSettings();

		Iterable<TupleDefinition> vDefinitions = vSettings.getTupleDefinitions().asIterable();

		int expectedCount = 4;
		final int[] actualCount = {0};

		Iterator<TupleDefinition> vTupleIterator = vDefinitions.iterator();

		TupleDefinition vTupleDefinition = vTupleIterator.next();

		assertTrue(expectedCount == vTupleDefinition.getArity());
		assertEquals("tupleOne", vTupleDefinition.getName());

		final TType[] expectedTypes =
				{
						getCTInteger(),
						getCTDouble(),
						getCTString(),
						getCTInteger()
				};

		vTupleDefinition.cReverseIterator().forEachRemaining( x ->
															  {
																  assertEquals(expectedTypes[actualCount[0]], x);
																  actualCount[0]++;
															  });

		assertEquals(expectedCount, actualCount[0]);

		expectedCount = 1;
		actualCount[0] = 0;
		expectedTypes[0] = getCTDouble();


		vTupleDefinition = vTupleIterator.next();
		vTupleDefinition.cReverseIterator().forEachRemaining( x ->
															  {
																  assertEquals(expectedTypes[actualCount[0]], x);
																  actualCount[0]++;
															  });

		assertEquals(expectedCount, actualCount[0]);


		assertFalse(vTupleIterator.hasNext());
	}

}
