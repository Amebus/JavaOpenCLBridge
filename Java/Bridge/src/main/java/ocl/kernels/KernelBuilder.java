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
			static final String THE_KERNEL_NAME_MUST_BE_SPECIFIED = "The Kernel name must be specified";
		}

		static class Map
		{
			static final String MAP_START = "__kernel void ";

			static final String INT_MAP_BODY = "(global int* " + Vars.Data + ")\n" +
											   "{\n" +
											   "\tint " + Vars.GlobalId + " = get_global_id(0);" +
											   "\tint " + Vars.MapMidResult + ";\n";

			static final String MAP_ASSIGNATION = "\t" + Vars.Data + "[" + Vars.GlobalId + "] = " + Vars.MapMidResult + ";\n";
			static final String MAP_END = "}\n";

			static final int INT_MAP_LENGTH = MAP_START.length() + INT_MAP_BODY.length() + MAP_ASSIGNATION.length() + MAP_END.length();

		}

	}

	private StringBuilder mKernelBuilder;
	private String mKernelName;
	private String mParametersDefinition;
	private String mExecutionLogic;
	private String mPostExecutionLogic;

	public KernelBuilder(@NotNull String kernelName)
	{
		mKernelName = kernelName;
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

		if(ObjectHelper.isNullOrEmptyOrWhiteSpace(mKernelName))
		{
			throw new IllegalArgumentException(Kernels.ErrorMessages.THE_KERNEL_NAME_MUST_BE_SPECIFIED);
		}

		if(ObjectHelper.isNullOrEmptyOrWhiteSpace(mExecutionLogic))
		{
			throw new IllegalArgumentException(Kernels.ErrorMessages.THE_EXECUTION_LOGIC_MUST_BE_DEFINED);
		}

		int length = getStringLength(Kernels.Map.INT_MAP_LENGTH);

		mKernelBuilder = new StringBuilder(length);

		mKernelBuilder.append(Kernels.Map.MAP_START);

		mKernelBuilder.append(mKernelName);
		mKernelBuilder.append(Kernels.Map.INT_MAP_BODY);

		addParametersDefinition();

		addExecutionLogicDefinition();

		mKernelBuilder.append(Kernels.Map.MAP_ASSIGNATION);

		addPostExecutionLogicDefinition();

		mKernelBuilder.append(Kernels.Map.MAP_END);

		return  mKernelBuilder.toString();
	}

	private int getStringLength(int start)
	{
		int result = start;

		result += (mKernelName.length() + mExecutionLogic.length());

		if(!ObjectHelper.isNullOrEmptyOrWhiteSpace(mParametersDefinition))
		{
			result += mParametersDefinition.length();
		}

		if (!ObjectHelper.isNullOrEmptyOrWhiteSpace(mPostExecutionLogic))
		{
			result += mPostExecutionLogic.length();
		}

		//This is because the ; at th end of some statements could be forgot
		return result + 3;
	}

	private void addParametersDefinition()
	{
		correctlyAddIfPassTest(mParametersDefinition);
	}

	private void addExecutionLogicDefinition()
	{
		correctlyAddIfPassTest(mExecutionLogic);
	}

	private void addPostExecutionLogicDefinition()
	{
		correctlyAddIfPassTest(mPostExecutionLogic);
	}

	private void correctlyAddIfPassTest(String str)
	{
		if(!ObjectHelper.isNullOrEmptyOrWhiteSpace(str))
		{
			mKernelBuilder.append(str);
			if(!str.endsWith(";"))
			{
				mKernelBuilder.append(";");
			}
			mKernelBuilder.append("\n");
		}
	}
}
