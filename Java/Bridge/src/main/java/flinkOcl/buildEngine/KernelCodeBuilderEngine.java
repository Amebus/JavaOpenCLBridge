package flinkOcl.buildEngine;

import Commons.IBuilder;
import configuration.OclSettings;
import configuration.TupleDefinition;
import configuration.TupleDefinitions;
import flinkOcl.IUserFunction;

import java.util.LinkedList;
import java.util.List;

public class KernelCodeBuilderEngine
{
	
	private OclSettings mSettings;
	private TupleDefinitions mTupleDefinitions;
	private Iterable<? extends IUserFunction> mUserFunctions;
	
	public KernelCodeBuilderEngine(OclSettings pSettings, TupleDefinitions pTupleDefinitions, Iterable<? extends IUserFunction> pUserFunctions)
	{
		mSettings = pSettings;
		mTupleDefinitions = pTupleDefinitions;
		mUserFunctions = pUserFunctions;
	}
	
	public CppLibraryInfo generateKernels()
	{
		List<OclKernel> vResult = new LinkedList<>();
		for (IUserFunction vUserFunction : mUserFunctions)
		{
			vResult.add(generateKernel(vUserFunction));
		}
		
		//TODO salvare codice dei kernel su files
		
		return new CppLibraryInfo();
	}
	
	public OclKernel generateKernel(IUserFunction pUserFunction)
	{
		TupleDefinition vInput = mTupleDefinitions.getTupleDefinition(pUserFunction.getInputTupleName());
		TupleDefinition vOutput = mTupleDefinitions.getTupleDefinition(pUserFunction.getOutputTupleName());
		
		IUserFunction.IType vKernelType = pUserFunction.getType();
		String vKernelName = new StringBuilder()
				.append(vKernelType.toString())
				.append("_")
				.append(pUserFunction.getName())
				.toString();
		StringBuilder vKernelCode = new StringBuilder();
		
		//TODO generare codice kernel ocl
		
		return new OclKernel(vKernelName, vKernelCode.toString());
	}
	
}
