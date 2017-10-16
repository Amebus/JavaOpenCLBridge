package ocl.kernels;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KernelBuildersTester
{

	@Test
	void kernelBuilds_KernelNameNull_Error()
	{
		KernelBuilder builder = new KernelBuilder(null, EKernelReturnType.INT);

		assertThrows(IllegalArgumentException.class, builder::buildMap);
	}

	@Test
	void kernelBuilds_KernelNameEmpty_Error()
	{
		KernelBuilder builder = new KernelBuilder("", EKernelReturnType.INT);

		assertThrows(IllegalArgumentException.class, builder::buildMap);
	}

	@Test
	void kernelBuilds_KernelNameWhiteSpaces_Error()
	{
		KernelBuilder builder = new KernelBuilder("         ", EKernelReturnType.INT);

		assertThrows(IllegalArgumentException.class, builder::buildMap);
	}

	@Test
	void kernelBuilds_ReturnTypeNull_Error()
	{
		KernelBuilder builder = new KernelBuilder("Test", null);

		assertThrows(IllegalArgumentException.class, builder::buildMap);
	}

	@Test
	void kernelBuilds_ExecutionLogicNull_Error()
	{
		KernelBuilder builder = new KernelBuilder("Test", EKernelReturnType.INT);

		assertThrows(IllegalArgumentException.class, builder::buildMap);
	}

	@Test
	void kernelBuilds_ExecutionLogicEmpty_Error()
	{
		KernelBuilder builder = new KernelBuilder("Test", EKernelReturnType.INT);

		builder.withLogic("");

		assertThrows(IllegalArgumentException.class, builder::buildMap);
	}

	@Test
	void kernelBuilds_ExecutionLogicWhiteSpace_Error()
	{
		KernelBuilder builder = new KernelBuilder("Test", EKernelReturnType.INT);

		builder.withLogic("      ");

		assertThrows(IllegalArgumentException.class, builder::buildMap);
	}

	@Test
	void kernelBuilds_ExecutionLogicDefined_Ok()
	{
		KernelBuilder builder = new KernelBuilder("Test", EKernelReturnType.INT);

		builder.withLogic("  adassada  ");

		assertTrue(builder.buildMap().length() > 0);
	}



}
