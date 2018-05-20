package flinkOcl.buildEngine;


import Commons.IBuilder;
import configuration.*;
import flinkOcl.IUserFunction;

import java.util.ArrayList;
import java.util.List;

public abstract class KernelBuilder implements IBuilder<OclKernel>
{
	
	public final String G_ID = "_gId";
	public final String RESULT = "_result";
	public final String K_RESULT = RESULT + "[" + G_ID + "]";
	public final String DATA = "_data";
	
	public static final class MACRO
	{
		public static final String SER_INT = "#define SER_INT(r, num, i)          \\\n" +
											 "        r[i] = (num >> 24) & 0xFF;  \\\n" +
											 "        i++;                        \\\n" +
											 "        r[i] = (num >> 16) & 0xFF;  \\\n" +
											 "        i++;                        \\\n" +
											 "        r[i] = (num >> 8) & 0xFF;   \\\n" +
											 "        i++;                        \\\n" +
											 "        r[i] = num & 0xFF;          \\";
		
		public static final String DESER_INT = "#define DESER_INT(d, si, r) \\\n" +
											   "            r <<= 8;        \\\n" +
											   "            r |= d[si];     \\\n" +
											   "            si++;           \\\n" +
											   "            r <<= 8;        \\\n" +
											   "            r |= d[si];     \\\n" +
											   "            si++;           \\\n" +
											   "            r <<= 8;        \\\n" +
											   "            r |= d[si];     \\\n" +
											   "            si++;           \\\n" +
											   "            r <<= 8;        \\\n" +
											   "            r |= d[si];     \\";
		
		public static final String DESER_NUM = "#define DESER_NUM(d, si, r, dim)            \\\n" +
											   "        for (int i = 0 ; i != dim ; i++) {  \\\n" +
											   "            r <<= 8;                        \\\n" +
											   "            r |= d[si+i];                   \\\n" +
											   "        }";
	}
	
	private IUserFunction mUserFunction;
	private ITupleDefinitionsRepository mTupleDefinitions;
	private IOclContextOptions mOclContextOptions;
	private IOclKernelsOptions mOclKernelOptions;
	
	public KernelBuilder(KernelBuilderOptions pKernelBuilderOptions)
	{
		mUserFunction = pKernelBuilderOptions.getUserFunction();
		mTupleDefinitions = pKernelBuilderOptions.getTupleDefinitionsRepository();
		mOclContextOptions = pKernelBuilderOptions.getContextOptions();
		mOclKernelOptions = pKernelBuilderOptions.getKernelOptions();
	}
	
	protected IUserFunction getUserFunction()
	{
		return mUserFunction;
	}
	
	protected IOclContextOptions getOclContextOptions()
	{
		return mOclContextOptions;
	}
	protected IOclKernelsOptions getOclKernelOptions()
	{
		return mOclKernelOptions;
	}
	
	
	protected ITupleDefinitionsRepository getTupleDefinitions()
	{
		return mTupleDefinitions;
	}
	protected ITupleDefinition getInputTuple()
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
	protected Iterable<String> getTupleVariables(ITupleDefinition pTuple, String pName)
	{
		final int[] vIndex = {0};
		List<String> vResult = new ArrayList<>(pTuple.getArity());
		getInputTuple().cIterator().forEachRemaining( t -> vResult.add(t.getT() + " _" + pName + vIndex[0]++));
		return vResult;
	}
	
	
	protected String getKernelName()
	{
		return getUserFunction().getName();
	}
	
	protected String getKernelCode()
	{
		return getSerializationMacros() +
			   getDeserializationMacros() +
			   getKernelSignature() +
			   "\n{\n" +
			   getUtilityVars() + "\n" +
			   getOutputVarDeclaration() + "\n" +
			   getInputSection() + "\n" +
			   getUserFunction().getFunction() + "\n" +
			   getOutputSection() +
			   "\n}\n";
	}
	
	@Override
	public OclKernel build()
	{
		return new OclKernel(getKernelName(), getKernelCode());
	}
	
	protected String getDeserializationMacroForInt()
	{
		return MACRO.SER_INT;
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
		return MACRO.DESER_INT;
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
		return getSerializationMacroForInt() + "\n" +
			   getSerializationMacroForDouble() + "\n" +
			   getSerializationMacroForString() + "\n";
	}
	private String getDeserializationMacros()
	{
		return getDeserializationMacroForInt() + "\n" +
			   getDeserializationMacroForDouble() + "\n" +
			   getDeserializationMacroForString() + "\n";
	}
	
	protected String getKernelSignature()
	{
		return "__kernel void " +
			   getKernelName() +
			   "(__global unsigned char* " + DATA + ", " +
			   "__global int _dataIndexes, " +
			   "__global unsigned char* " + RESULT + ", " +
			   "__local unsigned char*" +
			   ")";
	}
	
	protected String getUtilityVars()
	{
		return "int " + G_ID + " = get_global_id(0);\n";
	}
	
	protected String getInputSection()
	{
		StringBuilder vBuilder = new StringBuilder();
		
		getInputTupleVariablesAsInput().forEach( x -> vBuilder.append(x)
															  .append(";\n"));
		
		return vBuilder.toString();
	}
	protected abstract String getOutputVarDeclaration();
	protected abstract String getOutputSection();
}
