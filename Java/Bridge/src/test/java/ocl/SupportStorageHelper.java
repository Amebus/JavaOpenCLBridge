package ocl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SupportStorageHelper
{

	@Test
	void supportStorageEmptyAtCreation_OK()
	{
		SupportStorage storage = new SupportStorage();

		assertFalse(storage.containsKernel("something"));
	}

	@Test
	void addKernel_OK()
	{
		String kernelName = "testKernel";
		SupportStorage storage = new SupportStorage();

		storage.addKernel(kernelName);

		assertTrue(storage.containsKernel(kernelName));
	}

	@Test
	void notContainsKernel_OK()
	{
		String kernelName = "testKernel";
		SupportStorage storage = new SupportStorage();

		storage.addKernel(kernelName);

		assertFalse(storage.containsKernel(kernelName + "difference"));
	}

	@Test
	void removeKernel_OK()
	{

		String kernelName = "testKernel";
		SupportStorage storage = new SupportStorage();

		storage.addKernel(kernelName);

		assertTrue(storage.containsKernel(kernelName));

		storage.removeKernel(kernelName);

		assertFalse(storage.containsKernel(kernelName));
	}

}
