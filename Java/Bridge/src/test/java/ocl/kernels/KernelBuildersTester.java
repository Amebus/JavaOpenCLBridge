package ocl.kernels;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KernelBuildersTester
{

	@Test
	void kernelMapBuilds_ExecutionLogicNull_Error()
	{
		KernelBuilder builder = new KernelBuilder();

		Throwable exception = assertThrows(IllegalArgumentException.class, builder::buildMap);

		assertEquals(KernelBuilder.Kernels.ErrorMessages.THE_EXECUTION_LOGIC_MUST_BE_DEFINED , exception.getMessage());
	}

	@Test
	void kernelMapBuilds_ExecutionLogicEmpty_Error()
	{
		KernelBuilder builder = new KernelBuilder();

		builder.withLogic("");

		Throwable exception = assertThrows(IllegalArgumentException.class, builder::buildMap);

		assertEquals(KernelBuilder.Kernels.ErrorMessages.THE_EXECUTION_LOGIC_MUST_BE_DEFINED , exception.getMessage());
	}

	@Test
	void kernelMapBuilds_ExecutionLogicWhiteSpace_Error()
	{
		KernelBuilder builder = new KernelBuilder();

		builder.withLogic("      ");

		Throwable exception = assertThrows(IllegalArgumentException.class, builder::buildMap);

		assertEquals(KernelBuilder.Kernels.ErrorMessages.THE_EXECUTION_LOGIC_MUST_BE_DEFINED , exception.getMessage());
	}

	@Test
	void kernelMapBuilds_ExecutionLogicDefined_Ok()
	{
		KernelBuilder builder = new KernelBuilder();

		builder.withLogic("  adassada  ");

		assertTrue(builder.buildMap().length() > 0);
	}

}
