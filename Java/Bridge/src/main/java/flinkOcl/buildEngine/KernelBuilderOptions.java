package flinkOcl.buildEngine;

import Commons.IBuilder;
import configuration.OclContextOptions;
import configuration.OclKernelOptions;
import configuration.TupleDefinitions;
import flinkOcl.IUserFunction;

public class KernelBuilderOptions
{
	private IUserFunction mUserFunction;
	private TupleDefinitions mTupleDefinitions;
	private OclContextOptions mContextOptions;
	private OclKernelOptions mKernelOptions;
	
	public KernelBuilderOptions(
			IUserFunction pUserFunction,
			TupleDefinitions pTupleDefinitions,
			OclContextOptions pContextOptions,
			OclKernelOptions pKernelOptions)
	{
		mUserFunction = pUserFunction;
		mTupleDefinitions = pTupleDefinitions;
		mContextOptions = pContextOptions;
		mKernelOptions = pKernelOptions;
	}
	
	public IUserFunction getUserFunction()
	{
		return mUserFunction;
	}
	
	public TupleDefinitions getTupleDefinitions()
	{
		return mTupleDefinitions;
	}
	
	public OclContextOptions getContextOptions()
	{
		return mContextOptions;
	}
	
	public OclKernelOptions getKernelOptions()
	{
		return mKernelOptions;
	}
	
	public static class KernelOptionsBuilder implements IBuilder<KernelBuilderOptions>
	{
		private IUserFunction mUserFunction;
		private TupleDefinitions mTupleDefinitions;
		private OclContextOptions mContextOptions;
		private OclKernelOptions mKernelOptions;
		
		public KernelOptionsBuilder setUserFunction(IUserFunction pUserFunction)
		{
			mUserFunction = pUserFunction;
			return this;
		}
		
		public KernelOptionsBuilder setTupleDefinitions(TupleDefinitions pTupleDefinitions)
		{
			mTupleDefinitions = pTupleDefinitions;
			return this;
		}
		
		public KernelOptionsBuilder setContextOptions(OclContextOptions pContextOptions)
		{
			mContextOptions = pContextOptions;
			return this;
		}
		
		public KernelOptionsBuilder setKernelOptions(OclKernelOptions pKernelOptions)
		{
			mKernelOptions = pKernelOptions;
			return this;
		}
		
		@Override
		public KernelBuilderOptions build()
		{
			return new KernelBuilderOptions(
					mUserFunction,
					mTupleDefinitions,
					mContextOptions,
					mKernelOptions
			);
		}
	}
}
