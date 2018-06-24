package configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static test.helpers.TTypesGetter.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TupleVarDefinitionTest
{
	@Test
	void TupleDefinition_GetT_Ok()
	{
		TupleVarDefinition vDefinition = new TupleVarDefinition(getJavaTString().getT());

		assertEquals(getJavaTString(), vDefinition.getJavaT());
		assertEquals(getCTString(), vDefinition.getCT());

		vDefinition = new TupleVarDefinition(TType.ConfigTypes.STRING);

		assertEquals(getJavaTString(), vDefinition.getJavaT());
		assertEquals(getCTString(), vDefinition.getCT());
	}

	@Test
	void TupleDefinition_Hash_Ok()
	{
		TupleVarDefinition vDefinition1 = new TupleVarDefinition(getJavaTString().getT());
		TupleVarDefinition vDefinition2 = new TupleVarDefinition(TType.ConfigTypes.STRING);

		assertEquals(vDefinition1.hashCode(), vDefinition2.hashCode());
	}

	@Test
	void TupleDefinition_Equal_Ok()
	{
		TupleVarDefinition vDefinition1 = new TupleVarDefinition(getJavaTString().getT());

		assertFalse(vDefinition1.equals(null));
		assertFalse(vDefinition1.equals("ciao"));
		assertTrue(vDefinition1.equals(vDefinition1));

		TupleVarDefinition vDefinition2 = new TupleVarDefinition(getCTDouble().getT());
		assertFalse(vDefinition1.equals(vDefinition2));
		assertFalse(vDefinition2.equals(vDefinition1));
	}

	@Test
	void TupleDefinition_FromTupleDefinition_Ok()
	{
		TupleVarDefinition vDefinition1 = new TupleVarDefinition(getJavaTString().getT());
		TupleVarDefinition vDefinition2 = new TupleVarDefinition(vDefinition1);

		assertEquals(vDefinition1, vDefinition2);
		assertEquals(vDefinition2, vDefinition1);

		assertTrue(vDefinition1 != vDefinition2);

		assertEquals(vDefinition1.hashCode(), vDefinition2.hashCode());
	}
}
