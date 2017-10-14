package ocl.kernels;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KernelBuildersTester
{

	@Test
	void kernelBuilds_ExecutionLogicNull_Error()
	{
		KernelBuilder builder = new KernelBuilder();

		Throwable exception = assertThrows(IllegalArgumentException.class, builder::buildMap);

		assertEquals(KernelBuilder.Kernels.ErrorMessages.THE_EXECUTION_LOGIC_MUST_BE_DEFINED , exception.getMessage());
	}

	@Test
	void kernelBuilds_ExecutionLogicEmpty_Error()
	{
		KernelBuilder builder = new KernelBuilder();

		builder.withLogic("");

		Throwable exception = assertThrows(IllegalArgumentException.class, builder::buildMap);

		assertEquals(KernelBuilder.Kernels.ErrorMessages.THE_EXECUTION_LOGIC_MUST_BE_DEFINED , exception.getMessage());
	}

	@Test
	void kernelBuilds_ExecutionLogicWhiteSpace_Error()
	{
		KernelBuilder builder = new KernelBuilder();

		builder.withLogic("      ");

		Throwable exception = assertThrows(IllegalArgumentException.class, builder::buildMap);

		assertEquals(KernelBuilder.Kernels.ErrorMessages.THE_EXECUTION_LOGIC_MUST_BE_DEFINED , exception.getMessage());
	}

	@Test
	void kernelBuilds_ExecutionLogicDefined_Ok()
	{
		KernelBuilder builder = new KernelBuilder();

		builder.withLogic("  adassada  ");

		assertTrue(builder.buildMap().length() > 0);
	}



}
