package flinkOcl.buildEngine;

import configuration.TupleDefinition;

public class KernelOptions
{
	
	private String mKernelName;
	
	private TupleDefinition mInputTuple;
	private TupleDefinition mOutputTuple;
	
	public KernelOptions(String pKernelName, TupleDefinition pInputTuple, TupleDefinition pOutputTuple)
	{
		mKernelName = pKernelName;
		mInputTuple = pInputTuple;
		mOutputTuple = pOutputTuple;
	}
	
	public String getKernelName()
	{
		return mKernelName;
	}
	
	public TupleDefinition getInputTuple()
	{
		return mInputTuple;
	}
	
	public TupleDefinition getOutputTuple()
	{
		return mOutputTuple;
	}
	
	public static class KernelOptionsBuilder implements IKernelBuildOptionsBuilder
	{
		private String mKernelName;
		
		private TupleDefinition mInputTuple;
		private TupleDefinition mOutputTuple;
		
		@Override
		public IKernelBuildOptionsBuilder setKernelName(String pKernelName)
		{
			mKernelName = pKernelName;
			return this;
		}
		
		@Override
		public IKernelBuildOptionsBuilder setInputTuple(TupleDefinition pInputTuple)
		{
			mInputTuple = pInputTuple;
			return null;
		}
		
		@Override
		public IKernelBuildOptionsBuilder setOutputTuple(TupleDefinition pOutputTuple)
		{
			mOutputTuple = pOutputTuple;
			return null;
		}
		
		@Override
		public KernelOptions build()
		{
			return new KernelOptions(
					mKernelName,
					mInputTuple,
					mOutputTuple
			);
		}
	}
}
