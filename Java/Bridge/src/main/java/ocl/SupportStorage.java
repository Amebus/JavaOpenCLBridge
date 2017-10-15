package ocl;

import java.util.HashSet;

public class SupportStorage
{

	private HashSet<String> storedKernels;

	public SupportStorage()
	{
		storedKernels = new HashSet<>();
	}

	public void addKernel(String kernelName)
	{
		storedKernels.add(kernelName);
	}

	public boolean containsKernel(String kernelName)
	{
		return storedKernels.contains(kernelName);
	}

	public void removeKernel(String kernelName)
	{
		storedKernels.remove(kernelName);
	}
}
