package ocl;

import ocl.kernels.Vars;
import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OclMapTester
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
	void oclMap_Error()
	{
		int[] data = new int[10];

		for (int i = 0; i < data.length; i++) {
			data[i] = i;
		}
		assertThrows(IllegalArgumentException.class, () ->
				context.oclMap(data, "map_int_test", null, null, null));
	}

	@Test
	void oclSimpleMap_OK()
	{

		String mapLogic = Vars.MapMidResult + " = " + Vars.Data + "[" + Vars.GlobalId +"] * 10";
		int[] data = new int[10];

		for (int i = 0; i < data.length; i++) {
			data[i] = i;
		}

		int[] result = context.oclMap(data, "simple_map_int_test", mapLogic);

		for (int i = 0; i < data.length; i++) {
			data[i]*=10;
		}
		assertArrayEquals(data, result);
	}

	@Test
	void oclSimpleMapDoesNotExists_Error()
	{

		String kernelName = "kernel_that_does_not_exists";
		int[] data = new int[10000];

		for (int i = 0; i < data.length; i++) {
			data[i] = i;
		}

		Throwable exception = assertThrows(IllegalArgumentException.class, () -> context.oclMap(data, "kernel_that_does_not_exists"));

		assertEquals(OclContext.ErrorMessages.NO_KERNELS_FOUND_WITH_NAME + kernelName, exception.getMessage());
	}

	@Test
	void oclSimpleSameNameMap_OK()
	{

		int[] data = new int[10000];

		for (int i = 0; i < data.length; i++) {
			data[i] = i;
		}

		int[] result = context.oclMap(data, "simple_map_int_test");

		for (int i = 0; i < data.length; i++) {
			data[i]*=10;
		}
		assertArrayEquals(data, result);
	}

	@Test
	void oclSimpleSameNameMapDifferentResult_OK()
	{

		int[] data = new int[10000];

		for (int i = 0; i < data.length; i++) {
			data[i] = i;
		}

		int[] result = context.oclMap(data, "simple_map_int_test");

		for (int i = 0; i < data.length; i++) {
			data[i]*=11;
		}
		assertFalse(Arrays.equals(data, result));
	}

	@Test
	void oclEvenMap_OK()
	{

		String mapLogic = "if(" + Vars.GlobalId + "%2 == 0) " +
						  Vars.MapMidResult + " = " + Vars.Data + "[" + Vars.GlobalId +"] * 10; " +
						  "else " + Vars.MapMidResult + " = " + Vars.Data + "[" + Vars.GlobalId +"]";

		int[] data = new int[10000];

		for (int i = 0; i < data.length; i++) {
			data[i] = i;
		}

		int[] result = context.oclMap(data, "even_map_int_test", mapLogic);

		for (int i = 0; i < data.length; i++) {
			if (i%2 == 0)
				data[i]*=10;
		}
		assertArrayEquals(data, result);
	}

	@Test
	void oclDoubleMap_OK()
	{
		String mapLogic = "if(" + Vars.GlobalId + "%2 == 0) " +
						  Vars.MapMidResult + " = " + Vars.Data + "[" + Vars.GlobalId +"] * 10; " +
						  "else " + Vars.MapMidResult + " = " + Vars.Data + "[" + Vars.GlobalId +"]";

		double[] data = new double[10000];

		for (int i = 0; i < data.length; i++) {
			if (i%2 == 0)
				data[i] = 0.15;
			else
				data[i] = 0.18;
		}

		double[] result = context.oclMap(data, "even_map_char_test", mapLogic);

		for (int i = 0; i < data.length; i++) {
			if (i%2 == 0)
				data[i] *= 10;
		}

		assertArrayEquals(data, result);

	}

}