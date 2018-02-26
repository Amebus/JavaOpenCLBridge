package configuration;

public class CTType extends TType
{

	public static class CTypes
	{
		static final String INTEGER = "int";
		static final String DOUBLE = "double";
		static final String STRING = "char*";
	}

	public CTType(String prmType, int prmByteDimension, int prmMaxByteDimension)
	{
		super(computeType(prmType), prmByteDimension, prmMaxByteDimension);
	}

	public CTType(CTType prmCTType)
	{
		super(prmCTType);
	}

	private static String computeType(String prmType)
	{
		if (isInteger(prmType))
			return CTypes.INTEGER;
		else if (isDouble(prmType))
			return CTypes.DOUBLE;
		else if (isString(prmType))
			return CTypes.STRING;

		throw new IllegalArgumentException("Error with var type " + prmType
										   +". The var type must be one between: int|integer or char|character or double or string");
	}
}
