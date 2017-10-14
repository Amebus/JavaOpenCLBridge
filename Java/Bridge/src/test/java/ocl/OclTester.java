package ocl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OclTester
{

	private Ocl context;

	@BeforeEach
	void setUp()
	{
		context = new Ocl();
		context.Open();
	}

	@AfterEach
	void tearDown()
	{

		if (context!=null)
			context.Close();
	}

	@Test
	void oclMap()
	{

		String mapLogic = "_r = _data[_gid] * 10";
		int[] data = new int[10];

		for (int i = 0; i < data.length; i++) {
			data[i] = i;
		}

		int[] result = context.OclMap(data, "map_int_test", null, mapLogic, null);

		for (int i = 0; i < data.length; i++) {
			data[i]*=10;
		}


		assertArrayEquals(data, result);

	}

}