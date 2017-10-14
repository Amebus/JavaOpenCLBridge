package ocl.kernels;

import com.sun.istack.internal.NotNull;
import utils.*;

public class KernelBuilder
{

	static class Kernels
	{

		static class ErrorMessages
		{
			static final String THE_EXECUTION_LOGIC_MUST_BE_DEFINED = "The execution logic must be defined";
		}

		static class Map
		{
			static final String INT_MAP_START = "__kernel void map(global int* _data)\n" +
												"{\n" +
												"\tint _gId = get_global_id(0);" +
												"\tint _r;" + "\n";

			static final String MAP_ASSIGNATION = "\t" + "_data[_gId] = _r;" + "\n";
			static final String MAP_END = "}\n";

			static final int INT_MAP_LENGTH = INT_MAP_START.length() + MAP_ASSIGNATION.length() + MAP_END.length();

		}

	}

	private StringBuilder mKernelBuilder;
	private String mParametersDefinition;
	private String mExecutionLogic;
	private String mPostExecutionLogic;

	public KernelBuilder()
	{

	}

	public KernelBuilder withParameterDefinition(String parameterDefinition)
	{
		mParametersDefinition = parameterDefinition;
		return this;
	}

	public KernelBuilder withLogic(@NotNull String executionLogic)
	{
		mExecutionLogic = executionLogic;
		return this;
	}

	public KernelBuilder withPostExecution(String postExecutionLogic)
	{
		mPostExecutionLogic = postExecutionLogic;
		return this;
	}

	public String buildMap()
	{
		int length = getStringLength();

		mKernelBuilder = new StringBuilder(length);

		mKernelBuilder.append(Kernels.Map.INT_MAP_START);

		addParametersDefinition();

		addExecutionLogicDefinition();

		mKernelBuilder.append(Kernels.Map.MAP_ASSIGNATION);

		addPostExecutionLogicDefinition();

		mKernelBuilder.append(Kernels.Map.MAP_END);

		return  mKernelBuilder.toString();
	}

	private int getStringLength()
	{
		int result = Kernels.Map.INT_MAP_LENGTH;

		if(!ObjectHelper.isNullOrEmptyOrWhiteSpace(mParametersDefinition))
		{
			result += mParametersDefinition.length();
		}

		if(ObjectHelper.isNullOrEmptyOrWhiteSpace(mExecutionLogic))
		{
			throw new IllegalArgumentException(Kernels.ErrorMessages.THE_EXECUTION_LOGIC_MUST_BE_DEFINED);
		}
		else
		{
			result += mExecutionLogic.length();
		}

		if (!ObjectHelper.isNullOrEmptyOrWhiteSpace(mPostExecutionLogic))
		{
			result += mPostExecutionLogic.length();
		}

		return result;
	}

	private void addParametersDefinition()
	{

		if (mParametersDefinition != null)
		{
			mKernelBuilder.append(mParametersDefinition);
			mKernelBuilder.append("\n");
		}
	}

	private void addExecutionLogicDefinition()
	{
		if (ObjectHelper.isNullOrEmptyOrWhiteSpace(mExecutionLogic))
		{
			throw new IllegalArgumentException(Kernels.ErrorMessages.THE_EXECUTION_LOGIC_MUST_BE_DEFINED);
		}
		else
		{
			mKernelBuilder.append(mExecutionLogic);
			mKernelBuilder.append("\n");
		}
	}

	private void addPostExecutionLogicDefinition()
	{
		if (mPostExecutionLogic != null)
		{
			mKernelBuilder.append(mPostExecutionLogic);
			mKernelBuilder.append("\n");
		}
	}

}
