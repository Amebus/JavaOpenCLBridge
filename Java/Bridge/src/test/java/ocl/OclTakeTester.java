package ocl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OclTakeTester
{

	private OclContext context;

	@BeforeAll
	void setUp()
	{
		context = new OclContext();
		context.open();
	}

	@AfterAll
	void tearDown()
	{

		if (context!=null)
			context.close();
	}

	@Test
	void oclTake1000_OK()
	{
		int nToTake = 1000;
		int[] data = new int[10000];
		int[] dataToTake = new int[nToTake];

		for (int i = 0; i < data.length; i++) {
			data[i] = i;
		}

		int[] result = context.oclTake(data, nToTake);

		assertEquals(nToTake, result.length);

		System.arraycopy(data, 0, dataToTake, 0, nToTake);

		assertArrayEquals(dataToTake, result);
	}

	@Test
	void oclTake100_OK()
	{
		int nToTake = 100;
		int[] data = new int[10000];
		int[] dataToTake = new int[nToTake];

		for (int i = 0; i < data.length; i++) {
			data[i] = i;
		}

		int[] result = context.oclTake(data, nToTake);

		assertEquals(nToTake, result.length);

		System.arraycopy(data, 0, dataToTake, 0, nToTake);

		assertArrayEquals(dataToTake, result);

	}

}
