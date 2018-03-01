package configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import static testHelpers.TTypesGetter.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TTTypeTest
{
	@Test
	void JavaTypes_Builder_Ok()
	{
		TType wvType;

		//String
		wvType = getJavaTString();
		assertEquals(wvType.getT(), JavaTType.JavaTypes.STRING);
		wvType = getJavaTCString();
		assertEquals(wvType.getT(), JavaTType.JavaTypes.STRING);
		//String:100
		wvType = getJavaTString(100);
		assertEquals(wvType.getT(), JavaTType.JavaTypes.STRING);
		wvType = getJavaTCString(100);
		assertEquals(wvType.getT(), JavaTType.JavaTypes.STRING);

		//Integer
		wvType = getJavaTInteger();
		assertEquals(wvType.getT(), JavaTType.JavaTypes.INTEGER);
		wvType = getJavaTInt();
		assertEquals(wvType.getT(), JavaTType.JavaTypes.INTEGER);

		//Integer
		wvType = getJavaTDouble();
		assertEquals(wvType.getT(), JavaTType.JavaTypes.DOUBLE);
	}

	@Test
	void CTypes_Builder_Ok()
	{
		TType wvType;

		//String
		wvType = getCTString();
		assertEquals(wvType.getT(), CTType.CTypes.STRING);
		wvType = getCTCString();
		assertEquals(wvType.getT(), CTType.CTypes.STRING);
		//String:100
		wvType = getCTString(100);
		assertEquals(wvType.getT(), CTType.CTypes.STRING);
		wvType = getCTCString(100);
		assertEquals(wvType.getT(), CTType.CTypes.STRING);

		//Integer
		wvType = getCTInteger();
		assertEquals(wvType.getT(), CTType.CTypes.INTEGER);
		wvType = getCTInt();
		assertEquals(wvType.getT(), CTType.CTypes.INTEGER);

		//Integer
		wvType = getCTDouble();
		assertEquals(wvType.getT(), CTType.CTypes.DOUBLE);
	}

	@Test
	void TTypes_HashCode_Ok()
	{
		TType wvType1, wvType2;

		wvType1 = getJavaTString();
		wvType2 = getJavaTString();

		assertTrue(wvType1.equals(wvType2));
		assertEquals(wvType1.hashCode(), wvType2.hashCode());

		wvType2 = getJavaTInteger();
		assertFalse(wvType1.equals(wvType2));
		assertNotEquals(wvType1.hashCode(), wvType2.hashCode());

		wvType1 = getJavaTInteger();
		assertTrue(wvType1.equals(wvType2));
		assertEquals(wvType1.hashCode(), wvType2.hashCode());

		wvType2 = getJavaTDouble();
		assertFalse(wvType1.equals(wvType2));
		assertNotEquals(wvType1.hashCode(), wvType2.hashCode());

		wvType1 = getJavaTDouble();
		assertTrue(wvType1.equals(wvType2));
		assertEquals(wvType1.hashCode(), wvType2.hashCode());

	}

	@Test
	void TTypes_Builder_Error()
	{
		javaTTypeError(null);
		javaTTypeError("");
		javaTTypeError("a");

		cTTypeError(null);
		cTTypeError("");
		cTTypeError("a");
	}

	private void javaTTypeError(String prmType)
	{
		boolean wvExceptionRaised = false;

		try
		{
			new JavaTType.Builder(prmType).build();
		}
		catch (IllegalArgumentException ex)
		{
			wvExceptionRaised = true;
		}

		assertTrue(wvExceptionRaised);
	}

	private void cTTypeError(String prmType)
	{
		boolean wvExceptionRaised = false;

		try
		{
			new CTType.Builder(prmType).build();
		}
		catch (IllegalArgumentException ex)
		{
			wvExceptionRaised = true;
		}

		assertTrue(wvExceptionRaised);
	}

	@Test
	void TTypes_Builder_String_Ok()
	{
		TType wvType;

		//String
		wvType = getJavaTString();
		assertEquals(wvType.getT(), JavaTType.JavaTypes.STRING);
		assertEquals(-1, wvType.getByteDimension());
		assertEquals(100, wvType.getMaxByteDimension());

		wvType = getJavaTCString();
		assertEquals(wvType.getT(), JavaTType.JavaTypes.STRING);
		assertEquals(-1, wvType.getByteDimension());
		assertEquals(100, wvType.getMaxByteDimension());

		//String
		wvType = getCTString();
		assertEquals(wvType.getT(), CTType.CTypes.STRING);
		assertEquals(-1, wvType.getByteDimension());
		assertEquals(100, wvType.getMaxByteDimension());

		wvType = getCTCString();
		assertEquals(wvType.getT(), CTType.CTypes.STRING);
		assertEquals(-1, wvType.getByteDimension());
		assertEquals(100, wvType.getMaxByteDimension());
	}

	@Test
	void TTypes_Builder_StringWithMaxDim_Ok()
	{
		TType wvType;

		//String
		wvType = getJavaTString(33);
		assertEquals(wvType.getT(), JavaTType.JavaTypes.STRING);
		assertEquals(-1, wvType.getByteDimension());
		assertEquals(33, wvType.getMaxByteDimension());

		wvType = getJavaTCString(33);
		assertEquals(wvType.getT(), JavaTType.JavaTypes.STRING);
		assertEquals(-1, wvType.getByteDimension());
		assertEquals(33, wvType.getMaxByteDimension());

		//String
		wvType = getCTString(33);
		assertEquals(wvType.getT(), CTType.CTypes.STRING);
		assertEquals(-1, wvType.getByteDimension());
		assertEquals(33, wvType.getMaxByteDimension());

		wvType = getCTCString(33);
		assertEquals(wvType.getT(), CTType.CTypes.STRING);
		assertEquals(-1, wvType.getByteDimension());
		assertEquals(33, wvType.getMaxByteDimension());
	}

	@Test
	void TTypes_Builder_Integer_Ok()
	{
		TType wvType;

		//
		wvType = getJavaTInteger();
		assertEquals(wvType.getT(), JavaTType.JavaTypes.INTEGER);
		assertEquals(4, wvType.getByteDimension());
		assertEquals(4, wvType.getMaxByteDimension());

		wvType = getJavaTInt();
		assertEquals(wvType.getT(), JavaTType.JavaTypes.INTEGER);
		assertEquals(4, wvType.getByteDimension());
		assertEquals(4, wvType.getMaxByteDimension());

		//
		wvType = getCTInteger();
		assertEquals(wvType.getT(), CTType.CTypes.INTEGER);
		assertEquals(4, wvType.getByteDimension());
		assertEquals(4, wvType.getMaxByteDimension());

		wvType = getCTInt();
		assertEquals(wvType.getT(), CTType.CTypes.INTEGER);
		assertEquals(4, wvType.getByteDimension());
		assertEquals(4, wvType.getMaxByteDimension());
	}

	@Test
	void TTypes_Builder_Double_Ok()
	{
		TType wvType;

		wvType = getJavaTDouble();
		assertEquals(wvType.getT(), JavaTType.JavaTypes.DOUBLE);
		assertEquals(8, wvType.getByteDimension());
		assertEquals(8, wvType.getMaxByteDimension());

		wvType = getCTDouble();
		assertEquals(wvType.getT(), CTType.CTypes.DOUBLE);
		assertEquals(8, wvType.getByteDimension());
		assertEquals(8, wvType.getMaxByteDimension());
	}

	@Test
	void JavaTTypes_Equal_Ok()
	{
		TType wvTypeJ, wvActualType;
		wvTypeJ = getJavaTString();

		assertFalse(wvTypeJ.equals(null));
		assertTrue(wvTypeJ.equals(wvTypeJ));

		wvActualType = getJavaTString();

		assertTrue(wvTypeJ.equals(wvActualType));
		assertTrue( wvActualType.equals(wvTypeJ));

		wvActualType = getJavaTInteger();

		assertFalse(wvTypeJ.equals(wvActualType));
		assertFalse( wvActualType.equals(wvTypeJ));
	}

	@Test
	void CTTypes_Equal_Ok()
	{
		TType wvTypeC, wvActualType;
		wvTypeC = getCTString();

		assertFalse(wvTypeC.equals(null));
		assertTrue(wvTypeC.equals(wvTypeC));

		wvActualType = getCTString();

		assertTrue(wvTypeC.equals(wvActualType));
		assertTrue( wvActualType.equals(wvTypeC));

		wvActualType = getCTInteger();

		assertFalse(wvTypeC.equals(wvActualType));
		assertFalse( wvActualType.equals(wvTypeC));
	}

	@Test
	void TTypes_Builder_BuildsFromOtherTTypes_Ok()
	{
		TType wvTypeJ, wvTypeC, wvActualType;

		wvTypeJ = getJavaTString();
		wvTypeC = getCTString();

		//Java from Java
		wvActualType = new JavaTType.Builder(wvTypeJ).build();

		assertEquals(wvTypeJ, wvActualType);
		assertTrue(wvActualType.equals(wvTypeJ));

		//Java from C
		wvActualType = new JavaTType.Builder(wvTypeC).build();

		assertEquals(wvTypeJ, wvActualType);
		assertTrue(wvActualType.equals(wvTypeJ));

		//C from Java
		wvActualType = new CTType.Builder(wvTypeJ).build();

		assertEquals(wvTypeC, wvActualType);
		assertTrue(wvActualType.equals(wvTypeC));

		//C from C
		wvActualType = new CTType.Builder(wvTypeC).build();

		assertEquals(wvTypeC, wvActualType);
		assertTrue(wvActualType.equals(wvTypeC));
	}

}
