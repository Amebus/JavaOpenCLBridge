package ocl;

import com.sun.istack.internal.NotNull;
import ocl.kernels.EKernelReturnType;
import ocl.kernels.KernelBuilder;

public class OclContext extends Ocl
{

	private SupportStorage mStorage;

	public OclContext()
	{
		super();
		mStorage = new SupportStorage();
	}

	public void open() { super.Open(); }
	public void close() { super.Close(); }

	public int[] oclMap(@NotNull int[] data, @NotNull String kernelName)
	{
		if(!mStorage.containsKernel(kernelName))
		{
			throw new IllegalArgumentException(Ocl.ErrorMessages.NO_KERNELS_FOUND_WITH_NAME + kernelName);
		}
		return oclMap(data, kernelName, "");
	}

	public int[] oclMap(@NotNull int[] data, @NotNull String kernelName, @NotNull String mapLogic)
	{
		return oclMap(data, kernelName, mapLogic, "");
	}

	public int[] oclMap(@NotNull int[] data, @NotNull String kernelName, @NotNull String mapLogic, @NotNull String parameterDefinition)
	{
		return oclMap(data, kernelName, mapLogic, parameterDefinition,"");
	}

	public int[] oclMap(@NotNull int[] data, @NotNull String kernelName, @NotNull String mapLogic, @NotNull String parameterDefinition, @NotNull String postLogic)
	{
		String kernel = null;
		KernelBuilder builder = new KernelBuilder(kernelName, EKernelReturnType.INT);

		if(!mStorage.containsKernel(kernelName))
		{
			kernel = builder.withLogic(mapLogic)
					.withParameterDefinition(parameterDefinition)
					.withPostExecution(postLogic)
					.buildMap();
			mStorage.addKernel(kernelName);
		}
		return super.OclMap(data, kernelName, kernel);
	}

	public double[] oclMap(@NotNull double[] data, @NotNull String kernelName)
	{
		if(!mStorage.containsKernel(kernelName))
		{
			throw new IllegalArgumentException(ErrorMessages.NO_KERNELS_FOUND_WITH_NAME + kernelName);
		}
		return oclMap(data, kernelName, "");
	}

	public double[] oclMap(@NotNull double[] data, @NotNull String kernelName, @NotNull String mapLogic)
	{
		return oclMap(data, kernelName, mapLogic, "");
	}
	public double[] oclMap(@NotNull double[] data, @NotNull String kernelName, @NotNull String mapLogic, @NotNull String parameterDefinition)
	{
		return oclMap(data, kernelName, mapLogic, parameterDefinition,"");
	}

	public double[] oclMap(@NotNull double[] data, @NotNull String kernelName, @NotNull String mapLogic, @NotNull String parameterDefinition, @NotNull String postLogic)
	{
		String kernel = null;
		KernelBuilder builder = new KernelBuilder(kernelName, EKernelReturnType.DOUBLE);

		if(!mStorage.containsKernel(kernelName))
		{
			kernel = builder.withLogic(mapLogic)
					.withParameterDefinition(parameterDefinition)
					.withPostExecution(postLogic)
					.buildMap();
			mStorage.addKernel(kernelName);
		}
		return super.OclMap(data, kernelName, kernel);
	}


	public int[] oclTake(int[] data, int n)
	{
		return super.OclTake(data, n);
	}
}
