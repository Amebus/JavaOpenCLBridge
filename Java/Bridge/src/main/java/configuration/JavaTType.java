package configuration;

public class JavaTType extends TType
{

	public static class JavaTypes
	{
		static final String INTEGER = "Integer";
		static final String DOUBLE = "Double";
		static final String STRING = "String";
	}

	public JavaTType(String prmType, int prmByteDimension, int prmMaxByteDimension)
	{
		super(computeType(prmType), prmByteDimension, prmMaxByteDimension);
	}

	public JavaTType(JavaTType prmJavaTType)
	{
		super(prmJavaTType);
	}

	private static String computeType(String prmType)
	{
		if (isInteger(prmType))
			return JavaTypes.INTEGER;
		else if (isDouble(prmType))
			return JavaTypes.DOUBLE;
		else if (isString(prmType))
			return JavaTypes.STRING;

		throw new IllegalArgumentException("Error with var type " + prmType
										   +". The var type must be one between: int|integer or char|character or double or string");
	}
}
