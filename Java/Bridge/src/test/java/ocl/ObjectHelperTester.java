package ocl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import utils.ObjectHelper;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ObjectHelperTester
{

	@Test
	void testNullObject_OK()
	{
		assertTrue(ObjectHelper.isNull(null));
	}

	@Test
	void testNotNullObject_OK()
	{
		assertFalse(ObjectHelper.isNull("lel"));
	}

	@Test
	void testIsNullOrEmptyString_OK()
	{
		String str = null;
		assertTrue(ObjectHelper.isNullOrEmpty(str));

		str = "";
		assertTrue(ObjectHelper.isNullOrEmpty(str));

		str = "ciao";
		assertFalse(ObjectHelper.isNullOrEmpty(str));
	}

	@Test
	void testIsNullOrWhiteSpace_OK()
	{
		String str = null;
		assertTrue(ObjectHelper.isNullOrWhiteSpace(str));

		str = "    ";
		assertTrue(ObjectHelper.isNullOrWhiteSpace(str));

		str = "    ciao     ";
		assertFalse(ObjectHelper.isNullOrWhiteSpace(str));
	}

	@Test
	void testIsNullOrEmptyOrWhiteSpace_OK()
	{
		String str = null;
		assertTrue(ObjectHelper.isNullOrEmptyOrWhiteSpace(str));

		str = "";
		assertTrue(ObjectHelper.isNullOrEmptyOrWhiteSpace(str));

		str = "    ";
		assertTrue(ObjectHelper.isNullOrEmptyOrWhiteSpace(str));

		str = "ciao";
		assertFalse(ObjectHelper.isNullOrEmptyOrWhiteSpace(str));

		str = "   ciao     ";
		assertFalse(ObjectHelper.isNullOrEmptyOrWhiteSpace(str));
	}


}
