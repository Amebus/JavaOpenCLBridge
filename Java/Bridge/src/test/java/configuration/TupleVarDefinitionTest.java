package configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static testHelpers.TTypesGetter.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TupleVarDefinitionTest
{
	@Test
	void TupleDefinition_GetT_Ok()
	{
		TupleVarDefinition wvDefinition = new TupleVarDefinition(getJavaTString().getT());

		assertEquals(getJavaTString(), wvDefinition.getJavaT());
		assertEquals(getCTString(), wvDefinition.getCT());

		wvDefinition = new TupleVarDefinition(TType.ConfigTypes.STRING);

		assertEquals(getJavaTString(), wvDefinition.getJavaT());
		assertEquals(getCTString(), wvDefinition.getCT());
	}

	@Test
	void TupleDefinition_Hash_Ok()
	{
		TupleVarDefinition wvDefinition1 = new TupleVarDefinition(getJavaTString().getT());
		TupleVarDefinition wvDefinition2 = new TupleVarDefinition(TType.ConfigTypes.STRING);

		assertEquals(wvDefinition1.hashCode(), wvDefinition2.hashCode());
	}

	@Test
	void TupleDefinition_Equal_Ok()
	{
		TupleVarDefinition wvDefinition1 = new TupleVarDefinition(getJavaTString().getT());

		assertFalse(wvDefinition1.equals(null));
		assertFalse(wvDefinition1.equals("ciao"));
		assertTrue(wvDefinition1.equals(wvDefinition1));

		TupleVarDefinition wvDefinition2 = new TupleVarDefinition(getCTDouble().getT());
		assertFalse(wvDefinition1.equals(wvDefinition2));
		assertFalse(wvDefinition2.equals(wvDefinition1));
	}

	@Test
	void TupleDefinition_FromTupleDefinition_Ok()
	{
		TupleVarDefinition wvDefinition1 = new TupleVarDefinition(getJavaTString().getT());
		TupleVarDefinition wvDefinition2 = new TupleVarDefinition(wvDefinition1);

		assertEquals(wvDefinition1, wvDefinition2);
		assertEquals(wvDefinition2, wvDefinition1);

		assertTrue(wvDefinition1 != wvDefinition2);

		assertEquals(wvDefinition1.hashCode(), wvDefinition2.hashCode());
	}
}
