package ocl;

import static org.junit.jupiter.api.Assertions.*;

class OclTest
{

	private Ocl context;

	@org.junit.jupiter.api.BeforeEach
	void setUp()
	{
		context = new Ocl();
		context.Open();
	}

	@org.junit.jupiter.api.AfterEach
	void tearDown()
	{

		if (context!=null)
			context.Close();
	}

	@org.junit.jupiter.api.Test
	void oclMap()
	{

		int[] data = new int[10];

		for (int i = 0; i < data.length; i++) {
			data[i] = i;
		}

		context.OclMap(data, null);

		assertTrue(true);

	}

}