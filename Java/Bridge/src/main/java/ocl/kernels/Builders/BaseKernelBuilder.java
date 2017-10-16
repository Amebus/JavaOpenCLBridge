package ocl.kernels.Builders;

import com.sun.istack.internal.NotNull;
import ocl.kernels.EKernelReturnType;
import utils.ObjectHelper;

public abstract class BaseKernelBuilder
{

	private static class ErrorMessages
	{
		private static final String THE_EXECUTION_LOGIC_MUST_BE_DEFINED = "The execution logic must be defined";
		private static final String THE_KERNEL_NAME_MUST_BE_SPECIFIED = "The Kernel name must be specified";
		private static final String THE_RETURN_TYPE_MUST_BE_DEFINED = "The return type must be defined";
	}

	private String mKernelName;
	private String mParametersDefinition;
	private String mExecutionLogic;
	private String mPostExecutionLogic;

	private EKernelReturnType mReturnType;

	protected BaseKernelBuilder(@NotNull String kernelName, @NotNull EKernelReturnType returnType)
	{
		mKernelName = kernelName;
		mReturnType = returnType;
	}

	public String getKernelName()
	{
		return mKernelName;
	}

	public String getParametersDefinition()
	{
		return mParametersDefinition;
	}

	public String getExecutionLogic()
	{
		return mExecutionLogic;
	}

	public String getPostExecutionLogic()
	{
		return mPostExecutionLogic;
	}

	public EKernelReturnType getReturnType()
	{
		return mReturnType;
	}

	public BaseKernelBuilder withParameterDefinition(String parameterDefinition)
	{
		mParametersDefinition = parameterDefinition;
		return this;
	}

	public BaseKernelBuilder withLogic(@NotNull String executionLogic)
	{
		mExecutionLogic = executionLogic;
		return this;
	}

	public BaseKernelBuilder withPostExecution(String postExecutionLogic)
	{
		mPostExecutionLogic = postExecutionLogic;
		return this;
	}

	protected void checkMandatoryElements()
	{
		if(ObjectHelper.isNullOrEmptyOrWhiteSpace(getKernelName()))
		{
			throw new IllegalArgumentException(ErrorMessages.THE_KERNEL_NAME_MUST_BE_SPECIFIED);
		}

		if(ObjectHelper.isNull(getReturnType()))
		{
			throw new IllegalArgumentException(ErrorMessages.THE_RETURN_TYPE_MUST_BE_DEFINED);
		}

		if(ObjectHelper.isNullOrEmptyOrWhiteSpace(getExecutionLogic()))
		{
			throw new IllegalArgumentException(ErrorMessages.THE_EXECUTION_LOGIC_MUST_BE_DEFINED);
		}
	}

	public abstract String build();

}
