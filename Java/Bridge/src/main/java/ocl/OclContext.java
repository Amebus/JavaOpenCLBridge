package ocl;

import com.sun.istack.internal.NotNull;
import ocl.kernels.EKernelReturnType;
import ocl.kernels.Builders.MapKernelBuilder;

public class OclContext extends Ocl
{

	static class ErrorMessages
	{
		public static final String NO_KERNELS_FOUND_WITH_NAME = "No Kernels found with name: ";
	}

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
			throw new IllegalArgumentException(ErrorMessages.NO_KERNELS_FOUND_WITH_NAME + kernelName);
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
		return super.OclMap(data, kernelName, buildKernel(EKernelReturnType.INT, kernelName, mapLogic, parameterDefinition, postLogic));
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
		return super.OclMap(data, kernelName, buildKernel(EKernelReturnType.DOUBLE, kernelName, mapLogic, parameterDefinition, postLogic));
	}

	private String buildKernel(@NotNull EKernelReturnType kernelReturnType, @NotNull String kernelName, @NotNull String mapLogic, @NotNull String parameterDefinition, @NotNull String postLogic)
	{
		String kernel = "";
		if(!mStorage.containsKernel(kernelName))
		{
			kernel = new MapKernelBuilder(kernelName, kernelReturnType)
					.withLogic(mapLogic)
					.withParameterDefinition(parameterDefinition)
					.withPostExecution(postLogic)
					.build();

			mStorage.addKernel(kernelName);
		}
		return kernel;
	}


	public int[] oclUnion(int[] data, int[] otherDataSet)
	{
		return super.OclUnion(data, otherDataSet);
	}

	public double[] oclUnion(double[] data, double[] otherDataSet)
	{
		return super.OclUnion(data, otherDataSet);
	}

	//Actions

	public int[] oclTake(int[] data, int n)
	{
		return super.OclTake(data, n);
	}

	public double[] oclTake(double[] data, int n)
	{
		return super.OclTake(data, n);
	}


}
