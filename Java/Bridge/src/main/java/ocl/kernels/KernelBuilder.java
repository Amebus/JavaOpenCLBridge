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
			static final String INT_MAP_START = "__kernel void map(global int* " + Vars.Data + ")\n" +
												"{\n" +
												"\tint " + Vars.GlobalId + " = get_global_id(0);" +
												"\tint " + Vars.MapMidResult + ";\n";

			static final String MAP_ASSIGNATION = "\t" + Vars.Data + "[" + Vars.GlobalId + "] = " + Vars.MapMidResult + ";\n";
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
		int length = getStringLength(Kernels.Map.INT_MAP_LENGTH);

		mKernelBuilder = new StringBuilder(length);

		mKernelBuilder.append(Kernels.Map.INT_MAP_START);

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

		return result + 3;
	}

	private void addParametersDefinition()
	{

		if (!ObjectHelper.isNullOrEmptyOrWhiteSpace(mParametersDefinition))
		{
			mKernelBuilder.append(mParametersDefinition);
			mKernelBuilder.append("\n");
		}
	}

	private void addExecutionLogicDefinition()
	{
		correctlyAddIfPassTest(mExecutionLogic, true);
	}

	private void addPostExecutionLogicDefinition()
	{
		correctlyAddIfPassTest(mPostExecutionLogic);
	}

	private void correctlyAddIfPassTest(String str)
	{
		correctlyAddIfPassTest(str, false);
	}

	private void correctlyAddIfPassTest(String str, boolean throwException)
	{
		if (ObjectHelper.isNullOrEmptyOrWhiteSpace(str))
		{
			if (throwException)
				throw new IllegalArgumentException(Kernels.ErrorMessages.THE_EXECUTION_LOGIC_MUST_BE_DEFINED);
			return;
		}
		mKernelBuilder.append(str);
		if(!str.endsWith(";"))
		{
			mKernelBuilder.append(";");
		}
		mKernelBuilder.append("\n");
	}
}
