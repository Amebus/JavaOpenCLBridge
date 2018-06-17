package ocl.kernels.Builders;

import ocl.kernels.EKernelReturnType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KernelBuilderBuildersTester
{

	@Test
	void kernelBuilds_KernelNameNull_Error()
	{
		MapKernelBuilder builder = new MapKernelBuilder(null, EKernelReturnType.INT);

		assertThrows(IllegalArgumentException.class, builder::build);
	}

	@Test
	void kernelBuilds_KernelNameEmpty_Error()
	{
		MapKernelBuilder builder = new MapKernelBuilder("", EKernelReturnType.INT);

		assertThrows(IllegalArgumentException.class, builder::build);
	}

	@Test
	void kernelBuilds_KernelNameWhiteSpaces_Error()
	{
		MapKernelBuilder builder = new MapKernelBuilder("         ", EKernelReturnType.INT);

		assertThrows(IllegalArgumentException.class, builder::build);
	}

	@Test
	void kernelBuilds_ReturnTypeNull_Error()
	{
		MapKernelBuilder builder = new MapKernelBuilder("Test", null);

		assertThrows(IllegalArgumentException.class, builder::build);
	}

	@Test
	void kernelBuilds_ExecutionLogicNull_Error()
	{
		MapKernelBuilder builder = new MapKernelBuilder("Test", EKernelReturnType.INT);

		assertThrows(IllegalArgumentException.class, builder::build);
	}

	@Test
	void kernelBuilds_ExecutionLogicEmpty_Error()
	{
		MapKernelBuilder builder = new MapKernelBuilder("Test", EKernelReturnType.INT);

		builder.withLogic("");

		assertThrows(IllegalArgumentException.class, builder::build);
	}

	@Test
	void kernelBuilds_ExecutionLogicWhiteSpace_Error()
	{
		MapKernelBuilder builder = new MapKernelBuilder("Test", EKernelReturnType.INT);

		builder.withLogic("      ");

		assertThrows(IllegalArgumentException.class, builder::build);
	}

	@Test
	void kernelBuilds_ExecutionLogicDefined_Ok()
	{
		MapKernelBuilder builder = new MapKernelBuilder("Test", EKernelReturnType.INT);

		builder.withLogic("  adassada  ");

		assertTrue(builder.build().length() > 0);
	}



}
