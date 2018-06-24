package flink.ocl.build.engine;

public class KenelCodeContainer
{
	
	private String mSignature;
	
	private Iterable<String> mInputDeclaration;
	private Iterable<String> mOutputDeclaration;
	
	private Iterable<String> mMacros;
	
	public String getSignature()
	{
		return mSignature;
	}
	
	public void setSignature(String pSignature)
	{
		mSignature = pSignature;
	}
	
	public Iterable<String> getInputDeclaration()
	{
		return mInputDeclaration;
	}
	
	public void setInputDeclaration(Iterable<String> pInputDeclaration)
	{
		mInputDeclaration = pInputDeclaration;
	}
	
	public Iterable<String> getOutputDeclaration()
	{
		return mOutputDeclaration;
	}
	
	public void setOutputDeclaration(Iterable<String> pOutputDeclaration)
	{
		mOutputDeclaration = pOutputDeclaration;
	}
	
	public Iterable<String> getMacros()
	{
		return mMacros;
	}
	
	public void setMacros(Iterable<String> pMacros)
	{
		mMacros = pMacros;
	}
}
