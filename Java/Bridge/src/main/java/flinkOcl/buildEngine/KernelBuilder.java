package flinkOcl.buildEngine;


import Commons.IBuilder;
import configuration.OclContextOptions;
import configuration.OclKernelOptions;
import configuration.TupleDefinition;
import configuration.TupleDefinitions;
import flinkOcl.IUserFunction;

import java.util.ArrayList;
import java.util.List;

public abstract class KernelBuilder implements IBuilder<OclKernel>
{
	
	public final String G_ID = "_gId";
	public final String RESULT = "_result";
	
	private IUserFunction mUserFunction;
	private TupleDefinitions mTupleDefinitions;
	private OclContextOptions mOclContextOptions;
	private OclKernelOptions mOclKernelOptions;
	
	public KernelBuilder(KernelBuilderOptions pKernelBuilderOptions)
	{
		mUserFunction = pKernelBuilderOptions.getUserFunction();
		mTupleDefinitions = pKernelBuilderOptions.getTupleDefinitions();
		mOclContextOptions = pKernelBuilderOptions.getContextOptions();
		mOclKernelOptions = pKernelBuilderOptions.getKernelOptions();
	}
	
	protected IUserFunction getUserFunction()
	{
		return mUserFunction;
	}
	
	
	protected OclContextOptions getOclContextOptions()
	{
		return mOclContextOptions;
	}
	
	protected OclKernelOptions getOclKernelOptions()
	{
		return mOclKernelOptions;
	}
	
	
	protected TupleDefinitions getTupleDefinitions()
	{
		return mTupleDefinitions;
	}
	
	protected TupleDefinition getInputTuple()
	{
		return mTupleDefinitions.getTupleDefinition(mUserFunction.getInputTupleName());
	}
	
	protected Iterable<String> getInputTupleVariablesAsInput()
	{
		return getTupleVariables(getInputTuple(), "t");
	}
	
	protected Iterable<String> getInputTupleVariablesAsResult()
	{
		return getTupleVariables(getInputTuple(), "r");
	}
	
	protected Iterable<String> getTupleVariables(TupleDefinition pTuple, String pName)
	{
		final int[] vIndex = {0};
		List<String> vResult = new ArrayList<>(pTuple.getArity());
		getInputTuple().cIterator().forEachRemaining( t -> vResult.add(t + " _" + pName + vIndex[0]++));
		return vResult;
	}
	
	
	protected String getKernelName()
	{
		return getUserFunction().getName();
	}
	protected abstract String getKernelSignature();
	
	protected String getInputSection()
	{
		StringBuilder vBuilder = new StringBuilder();
		
		getInputTupleVariablesAsInput().forEach( x -> vBuilder.append(x)
																.append(";\n"));
		
		return vBuilder.toString();
	}
	protected abstract String getOutputVarDeclaration();
	protected abstract String getOutputSection();
	
	
	protected String getKernelCode()
	{
		return getSerializationMacros() +
			   getDeserializationMacros() +
			   getKernelSignature() +
			   "\n{" +
			   getOutputVarDeclaration() +
			   getInputSection() +
			   getUserFunction().getFunction() +
			   getOutputSection() +
			   "\n}";
	}
	
	@Override
	public OclKernel build()
	{
		return new OclKernel(getKernelName(), getKernelCode());
	}
	
	protected String getDeserializationMacroForInt()
	{
		return "";
	}
	protected String getDeserializationMacroForDouble()
	{
		return "";
	}
	protected String getDeserializationMacroForString()
	{
		return "";
	}
	
	protected String getSerializationMacroForInt()
	{
		return "";
	}
	protected String getSerializationMacroForDouble()
	{
		return "";
	}
	protected String getSerializationMacroForString()
	{
		return "";
	}
	
	private String getSerializationMacros()
	{
		return "";
	}
	private String getDeserializationMacros()
	{
		return "";
	}
}
