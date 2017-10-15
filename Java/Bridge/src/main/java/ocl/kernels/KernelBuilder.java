package ocl.kernels;

import com.sun.istack.internal.NotNull;
import jdk.nashorn.internal.objects.annotations.Constructor;
import utils.*;

public class KernelBuilder
{

	static class Kernels
	{

		static class ErrorMessages
		{
			static final String THE_EXECUTION_LOGIC_MUST_BE_DEFINED = "The execution logic must be defined";
			static final String THE_KERNEL_NAME_MUST_BE_SPECIFIED = "The Kernel name must be specified";
			static final String THE_RETURN_TYPE_MUST_BE_DEFINED = "The return type must be defined";
		}

		static class Map
		{
			static final String MAP_START = "__kernel void ";

			static final String INT_MAP_SIGNATURE = "(global int* " + Vars.Data + ")\n" +
													"{\n" +
													"\tint " + Vars.MapMidResult + ";\n";

			static final String CHAR_MAP_SIGNATURE = "(global char* " + Vars.Data + ")\n" +
													"{\n" +
													"\tchar " + Vars.MapMidResult + ";\n";

			static final String DOUBLE_MAP_SIGNATURE = "(global double* " + Vars.Data + ")\n" +
													"{\n" +
													"\tdouble " + Vars.MapMidResult + ";\n";


			static final String MAP_BODY = "\tint " + Vars.GlobalId + " = get_global_id(0);";

			static final String MAP_ASSIGNATION = "\t" + Vars.Data + "[" + Vars.GlobalId + "] = " + Vars.MapMidResult + ";\n";
			static final String MAP_END = "}\n";

			static final int INT_MAP_LENGTH = MAP_START.length() + INT_MAP_SIGNATURE.length() + MAP_BODY.length() + MAP_ASSIGNATION.length() + MAP_END.length();

		}

	}

	private StringBuilder mKernelSourceBuilder;
	private String mKernelName;
	private String mParametersDefinition;
	private String mExecutionLogic;
	private String mPostExecutionLogic;

	private EKernelReturnType mReturnType;

	public KernelBuilder(@NotNull String kernelName, @NotNull EKernelReturnType returnType)
	{
		mKernelName = kernelName;
		mReturnType = returnType;
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

		if(ObjectHelper.isNull(mReturnType))
		{
			throw new IllegalArgumentException(Kernels.ErrorMessages.THE_RETURN_TYPE_MUST_BE_DEFINED);
		}

		int length = getStringLength(Kernels.Map.INT_MAP_LENGTH);

		mKernelSourceBuilder = new StringBuilder(length);

		mKernelSourceBuilder.append(Kernels.Map.MAP_START);

		mKernelSourceBuilder.append(mKernelName);

		addSignature();

		mKernelSourceBuilder.append(Kernels.Map.MAP_BODY);

		addParametersDefinition();

		addExecutionLogicDefinition();

		mKernelSourceBuilder.append(Kernels.Map.MAP_ASSIGNATION);

		addPostExecutionLogicDefinition();

		mKernelSourceBuilder.append(Kernels.Map.MAP_END);

		return  mKernelSourceBuilder.toString();
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

	private void addSignature()
	{
		switch (mReturnType)
		{
			case INT:
				mKernelSourceBuilder.append(Kernels.Map.INT_MAP_SIGNATURE);
				break;
			case CHAR:
				mKernelSourceBuilder.append(Kernels.Map.CHAR_MAP_SIGNATURE);
				break;
			case DOUBLE:
				mKernelSourceBuilder.append(Kernels.Map.DOUBLE_MAP_SIGNATURE);
				break;
		}
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
			mKernelSourceBuilder.append(str);
			if(!str.endsWith(";"))
			{
				mKernelSourceBuilder.append(";");
			}
			mKernelSourceBuilder.append("\n");
		}
	}
}
