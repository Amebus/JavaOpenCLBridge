package flinkOcl.buildEngine;

import Commons.IBuilder;
import configuration.TupleDefinition;

public interface IKernelBuildOptionsBuilder extends IBuilder<KernelOptions>
{
	
	IKernelBuildOptionsBuilder setKernelName(String pKernelName);
	
	IKernelBuildOptionsBuilder setInputTuple(TupleDefinition pInputTuple);
	IKernelBuildOptionsBuilder setOutputTuple(TupleDefinition pOutputTuple);
	
}
