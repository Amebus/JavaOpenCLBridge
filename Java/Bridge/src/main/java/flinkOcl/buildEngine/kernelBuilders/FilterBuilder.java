package flinkOcl.buildEngine.kernelBuilders;

import flinkOcl.buildEngine.KernelBuilderOptions;

public class FilterBuilder extends MapBuilder
{
	public FilterBuilder(KernelBuilderOptions pKernelBuilderOptions)
	{
		super(pKernelBuilderOptions);
	}
	
	@Override
	protected String getOutputVarDeclaration()
	{
		return "unsigned char _r0 = 0;\n";
	}
	
	@Override
	protected String getOutputSection()
	{
		return K_RESULT + " = _r0;";
	}
}
