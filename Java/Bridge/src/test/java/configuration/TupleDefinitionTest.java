package configuration;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import testHelpers.LoadSettingsDirective;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static testHelpers.TTypesGetter.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TupleDefinitionTest
{

	@Test
	void TupleDefinition_Equals_Ok()
	{
		LoadSettingsDirective wvSettingsDirective = new LoadSettingsDirective("tupleEqualsTest.json");
		Settings wvSettings = getSettings(wvSettingsDirective);
		Iterable<TupleDefinition> wvDefinitions = wvSettings.getTupleDefinitions();

		wvDefinitions.forEach( x -> assertTrue(x.equals(x)));

		wvDefinitions.forEach( x -> assertTrue(x.equals(new TupleDefinition(x))));

		wvDefinitions.forEach( x -> assertFalse(x.equals(null)));

		wvDefinitions.forEach( x -> assertFalse(x.equals("string")));

		Iterator<TupleDefinition> wvIterator = wvDefinitions.iterator();

		TupleDefinition wvOne, wvTwo;

		wvOne = wvIterator.next();
		wvTwo = wvIterator.next();

		assertFalse(wvOne.equals(wvTwo));
		assertFalse(wvTwo.equals(wvOne));

		wvTwo = wvIterator.next();

		assertFalse(wvOne.equals(wvTwo));
		assertFalse(wvTwo.equals(wvOne));
	}

	@Test
	void TupleDefinition_HashCode_Ok()
	{
		LoadSettingsDirective wvSettingsDirective = new LoadSettingsDirective("tupleEqualsTest.json");
		Settings wvSettings = getSettings(wvSettingsDirective);
		Iterable<TupleDefinition> wvDefinitions = wvSettings.getTupleDefinitions();

		wvDefinitions.forEach( x -> assertEquals(x.hashCode(), x.hashCode()));

		wvDefinitions.forEach( x -> assertEquals(x.hashCode(), new TupleDefinition(x).hashCode()));

		Iterator<TupleDefinition> wvIterator = wvDefinitions.iterator();

		TupleDefinition wvOne, wvTwo;

		wvOne = wvIterator.next();
		wvTwo = wvIterator.next();

		assertNotEquals(wvOne.hashCode(), wvTwo.hashCode());
	}

	@Test
	void TupleDefinition_TWithIndexOutOfBoundIsNull_Ok()
	{
		Settings wvSettings = getSettings();
		Iterator<TupleDefinition> wvIterator = wvSettings.getTupleDefinitions().iterator();

		TupleDefinition wvOne = wvIterator.next();

		assertNull(wvOne.getT(5));
		assertNull(wvOne.getT(TupleDefinition.T_LIMIT + 1));

		assertNull(wvOne.getJavaT(5));
		assertNull(wvOne.getJavaT(TupleDefinition.T_LIMIT + 1));

		assertNull(wvOne.getCT(5));
		assertNull(wvOne.getCT(TupleDefinition.T_LIMIT + 1));
	}

	@Test
	void TupleDefinition_Iterator_Ok()
	{
		Settings wvSettings = getSettings();

		Iterable<TupleDefinition> wvDefinitions = wvSettings.getTupleDefinitions();

		int expectedCount = 4;
		final int[] actualCount = {0};

		Iterator<TupleDefinition> wvTupleIterator = wvDefinitions.iterator();

		TupleDefinition wvTupleDefinition = wvTupleIterator.next();

		assertTrue(expectedCount == wvTupleDefinition.getArity());
		assertEquals("tupleOne", wvTupleDefinition.getName());

		final TType[] expectedTypes =
				{
					getJavaTInteger(),
					getJavaTString(),
					getJavaTDouble(),
					getJavaTInteger()
				};

		wvTupleDefinition.forEach( x ->
								   {
									   	assertEquals(expectedTypes[actualCount[0]], x);
										actualCount[0]++;
								   });

		assertEquals(expectedCount, actualCount[0]);

		expectedCount = 1;
		actualCount[0] = 0;
		expectedTypes[0] = getJavaTDouble();


		wvTupleDefinition = wvTupleIterator.next();
		wvTupleDefinition.forEach( x ->
								   {
									   assertEquals(expectedTypes[actualCount[0]], x);
									   actualCount[0]++;
								   });

		assertEquals(expectedCount, actualCount[0]);


		assertFalse(wvTupleIterator.hasNext());
	}

	@Test
	void TupleDefinition_ReverseIterator_Ok()
	{
		Settings wvSettings = getSettings();

		Iterable<TupleDefinition> wvDefinitions = wvSettings.getTupleDefinitions();

		int expectedCount = 4;
		final int[] actualCount = {0};

		Iterator<TupleDefinition> wvTupleIterator = wvDefinitions.iterator();

		TupleDefinition wvTupleDefinition = wvTupleIterator.next();

		assertTrue(expectedCount == wvTupleDefinition.getArity());
		assertEquals("tupleOne", wvTupleDefinition.getName());

		final TType[] expectedTypes =
				{
						getJavaTInteger(),
						getJavaTDouble(),
						getJavaTString(),
						getJavaTInteger()
				};

		wvTupleDefinition.reverseIterator().forEachRemaining( x ->
								   {
									   assertEquals(expectedTypes[actualCount[0]], x);
									   actualCount[0]++;
								   });

		assertEquals(expectedCount, actualCount[0]);

		expectedCount = 1;
		actualCount[0] = 0;
		expectedTypes[0] = getJavaTDouble();


		wvTupleDefinition = wvTupleIterator.next();
		wvTupleDefinition.reverseIterator().forEachRemaining( x ->
								   {
									   assertEquals(expectedTypes[actualCount[0]], x);
									   actualCount[0]++;
								   });

		assertEquals(expectedCount, actualCount[0]);


		assertFalse(wvTupleIterator.hasNext());
	}

	@Test
	void TupleDefinition_CIterator_Ok()
	{
		Settings wvSettings = getSettings();

		Iterable<TupleDefinition> wvDefinitions = wvSettings.getTupleDefinitions();

		int expectedCount = 4;
		final int[] actualCount = {0};

		Iterator<TupleDefinition> wvTupleIterator = wvDefinitions.iterator();

		TupleDefinition wvTupleDefinition = wvTupleIterator.next();

		assertTrue(expectedCount == wvTupleDefinition.getArity());
		assertEquals("tupleOne", wvTupleDefinition.getName());

		final TType[] expectedTypes =
				{
						getCTInteger(),
						getCTString(),
						getCTDouble(),
						getCTInteger()
				};

		wvTupleDefinition.cIterator().forEachRemaining( x ->
								   {
									   assertEquals(expectedTypes[actualCount[0]], x);
									   actualCount[0]++;
								   });

		assertEquals(expectedCount, actualCount[0]);

		expectedCount = 1;
		actualCount[0] = 0;
		expectedTypes[0] = getCTDouble();


		wvTupleDefinition = wvTupleIterator.next();
		wvTupleDefinition.cIterator().forEachRemaining( x ->
								   {
									   assertEquals(expectedTypes[actualCount[0]], x);
									   actualCount[0]++;
								   });

		assertEquals(expectedCount, actualCount[0]);


		assertFalse(wvTupleIterator.hasNext());
	}

	@Test
	void TupleDefinition_ReverseCIterator_Ok()
	{
		Settings wvSettings = getSettings();

		Iterable<TupleDefinition> wvDefinitions = wvSettings.getTupleDefinitions();

		int expectedCount = 4;
		final int[] actualCount = {0};

		Iterator<TupleDefinition> wvTupleIterator = wvDefinitions.iterator();

		TupleDefinition wvTupleDefinition = wvTupleIterator.next();

		assertTrue(expectedCount == wvTupleDefinition.getArity());
		assertEquals("tupleOne", wvTupleDefinition.getName());

		final TType[] expectedTypes =
				{
						getCTInteger(),
						getCTDouble(),
						getCTString(),
						getCTInteger()
				};

		wvTupleDefinition.cReverseIterator().forEachRemaining( x ->
															  {
																  assertEquals(expectedTypes[actualCount[0]], x);
																  actualCount[0]++;
															  });

		assertEquals(expectedCount, actualCount[0]);

		expectedCount = 1;
		actualCount[0] = 0;
		expectedTypes[0] = getCTDouble();


		wvTupleDefinition = wvTupleIterator.next();
		wvTupleDefinition.cReverseIterator().forEachRemaining( x ->
															  {
																  assertEquals(expectedTypes[actualCount[0]], x);
																  actualCount[0]++;
															  });

		assertEquals(expectedCount, actualCount[0]);


		assertFalse(wvTupleIterator.hasNext());
	}

}
