package ocl.kernels.Builders;

import com.sun.istack.internal.NotNull;
import ocl.kernels.EKernelReturnType;
import ocl.kernels.Vars;
import utils.*;

public class MapKernelBuilder extends BaseKernelBuilder
{

	static class Kernels
	{

		static final String MAP_START = "__kernel void ";

		static final String INT_MAP_SIGNATURE = "(__global int* " + Vars.Data + ")\n" +
												"{\n" +
												"\tint " + Vars.MapMidResult + ";\n";

		static final String CHAR_MAP_SIGNATURE = "(__global char* " + Vars.Data + ")\n" +
												"{\n" +
												"\tchar " + Vars.MapMidResult + ";\n";

		static final String DOUBLE_MAP_SIGNATURE = "(__global double* " + Vars.Data + ")\n" +
												"{\n" +
												"\tdouble " + Vars.MapMidResult + ";\n";


		static final String MAP_BODY = "\tint " + Vars.GlobalId + " = get_global_id(0);";

		static final String MAP_ASSIGNATION = "\t" + Vars.Data + "[" + Vars.GlobalId + "] = " + Vars.MapMidResult + ";\n";
		static final String MAP_END = "}\n";

		private static final int INT_MAP_LENGTH = MAP_START.length() + INT_MAP_SIGNATURE.length() + MAP_BODY.length() +
										  MAP_ASSIGNATION.length() + MAP_END.length();
		private static final int DOUBLE_MAP_LENGTH = MAP_START.length() + DOUBLE_MAP_SIGNATURE.length() + MAP_BODY.length() +
											 MAP_ASSIGNATION.length() + MAP_END.length();
		private static final int CHAR_MAP_LENGTH = MAP_START.length() + CHAR_MAP_SIGNATURE.length() + MAP_BODY.length() +
												   MAP_ASSIGNATION.length() + MAP_END.length();

		static int getMapLength(EKernelReturnType returnType)
		{
			switch (returnType)
			{
				case INT:
					return INT_MAP_LENGTH;
				case DOUBLE:
					return DOUBLE_MAP_LENGTH;
				case CHAR:
					return CHAR_MAP_LENGTH;
			}
			return 0;
		}


	}

	private StringBuilder mKernelSourceBuilder;

	public MapKernelBuilder(@NotNull String kernelName, @NotNull EKernelReturnType returnType)
	{
		super(kernelName, returnType);
	}

	public String build()
	{

		if (ObjectHelper.isNull(mKernelSourceBuilder))
		{

			checkMandatoryElements();

			buildKernel();

		}

		return  mKernelSourceBuilder.toString();
	}

	private void buildKernel()
	{
		mKernelSourceBuilder = new StringBuilder(getStringLength(Kernels.getMapLength(getReturnType())));

		mKernelSourceBuilder.append(Kernels.MAP_START);

		mKernelSourceBuilder.append(getKernelName());

		addSignature();

		mKernelSourceBuilder.append(Kernels.MAP_BODY);

		addParametersDefinition();

		addExecutionLogicDefinition();

		mKernelSourceBuilder.append(Kernels.MAP_ASSIGNATION);

		addPostExecutionLogicDefinition();

		mKernelSourceBuilder.append(Kernels.MAP_END);
	}

	private int getStringLength(int start)
	{
		int result = start;

		result += (getKernelName().length() + getExecutionLogic().length());

		if(!ObjectHelper.isNullOrEmptyOrWhiteSpace(getParametersDefinition()))
		{
			result += getParametersDefinition().length();
		}

		if (!ObjectHelper.isNullOrEmptyOrWhiteSpace(getPostExecutionLogic()))
		{
			result += getPostExecutionLogic().length();
		}

		//This is because the ; at the end of some statements could be forgot
		return result + 3;
	}

	private void addSignature()
	{
		switch (getReturnType())
		{
			case INT:
				mKernelSourceBuilder.append(Kernels.INT_MAP_SIGNATURE);
				break;
			case CHAR:
				mKernelSourceBuilder.append(Kernels.CHAR_MAP_SIGNATURE);
				break;
			case DOUBLE:
				mKernelSourceBuilder.append(Kernels.DOUBLE_MAP_SIGNATURE);
				break;
		}
	}

	private void addParametersDefinition()
	{
		correctlyAddIfPassTest(getParametersDefinition());
	}

	private void addExecutionLogicDefinition()
	{
		correctlyAddIfPassTest(getExecutionLogic());
	}

	private void addPostExecutionLogicDefinition()
	{
		correctlyAddIfPassTest(getPostExecutionLogic());
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
