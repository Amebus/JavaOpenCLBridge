package flinkOcl.buildEngine;

import configuration.*;

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
	
	private OclKernel generateKernel(IUserFunction pUserFunction)
	{
		TupleDefinition vInput = mTupleDefinitions.getTupleDefinition(pUserFunction.getInputTupleName());
		TupleDefinition vOutput = mTupleDefinitions.getTupleDefinition(pUserFunction.getOutputTupleName());
		
		String vKernelName = new StringBuilder()
				.append(pUserFunction.getType())
				.append("_")
				.append(pUserFunction.getName())
				.toString();
		
		return new OclKernel(vKernelName, generateKernelCode(pUserFunction, vInput, vOutput));
	}
	
	private String generateKernelCode(IUserFunction pFunction, TupleDefinition pInput, TupleDefinition pOutput)
	{
		OclContextOptions vContextOptions = mSettings.getContextOptions();
		OclKernelOptions vKernelOptions = mSettings.getOclKernelOptions();
		StringBuilder vKernelCode = new StringBuilder();
		
		
		
		
		
		return vKernelCode.toString();
	}
}
