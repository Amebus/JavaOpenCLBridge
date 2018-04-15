package flinkOcl;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class UserFunction implements IUserFunction
{
	
	private String mName;
	private String mFunction;
	private String mInputTupleName;
	private String mOutputTupleName;
	
	public UserFunction(String pName, String pFunction, String pInputTupleName, String pOutputTupleName)
	{
		mName = pName;
		mFunction = pFunction;
		mInputTupleName = pInputTupleName;
		mOutputTupleName = pOutputTupleName;
	}
	
	@Override
	public String getName()
	{
		return mName;
	}
	
	@Override
	public String getFunction()
	{
		return mFunction;
	}
	
	@Override
	public String getInputTupleName()
	{
		return mInputTupleName;
	}
	
	@Override
	public String getOutputTupleName()
	{
		return mOutputTupleName;
	}
	
	public static class UserFunctionBuilder implements IUserFunctionBuilder
	{
		private String mFunctionName;
		private String mInputTupleName;
		private String mOutputTupleName;
		private StringBuilder mFunction;
		
		public UserFunctionBuilder(@NotNull String pFunctionName, @NotNull String pInputTupleName, @NotNull String pOutputTupleName)
		{
			mFunctionName = pFunctionName;
			mInputTupleName = pInputTupleName;
			mOutputTupleName = pOutputTupleName;
			mFunction = new StringBuilder();
		}
		
		@Override
		public IUserFunctionBuilder addUserFunctionLine(@NotNull String pLine)
		{
			mFunction.append(pLine);
			if (!(pLine.endsWith("{") || pLine.endsWith("}")))
			{
				if(pLine.endsWith(";"))
					mFunction.append("\n");
				else if (!pLine.endsWith(";\n"))
					mFunction.append(";\n");
			}
			else
			{
				mFunction.append("\n");
			}
			return this;
		}
		
		@Override
		public IUserFunctionBuilder setName(@NotNull String pName)
		{
			mFunctionName = pName;
			return this;
		}
		
		@Override
		public IUserFunctionBuilder setInputTupleName(@NotNull String pTupleName)
		{
			mInputTupleName = pTupleName;
			return this;
		}
		
		@Override
		public IUserFunctionBuilder setOutputTupleName(@NotNull String pTupleName)
		{
			mOutputTupleName = pTupleName;
			return this;
		}
		
		@Override
		public IUserFunction build()
		{
			Objects.requireNonNull(mFunctionName);
			Objects.requireNonNull(mInputTupleName);
			Objects.requireNonNull(mOutputTupleName);
			return new UserFunction(
					mFunctionName,
					mFunction.toString(),
					mInputTupleName,
					mOutputTupleName
			);
		}
	}
	
}
