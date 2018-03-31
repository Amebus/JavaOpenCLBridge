package configuration;

public class CTType extends TType
{

	public static class CTypes
	{
		static final String INTEGER = "int";
		static final String DOUBLE = "double";
		static final String STRING = "char*";
	}

	private CTType(String pType, int pByteDimension, int pMaxByteDimension)
	{
		super(pType, pByteDimension, pMaxByteDimension);
	}

	public static class Builder extends TTypeBuilder
	{
		public Builder(String pType)
		{
			super(pType);
		}

		public Builder(TType pType)
		{
			super(pType);
		}

		public CTType build()
		{
			String vType;
			if (isInteger(getType()))
				vType = CTypes.INTEGER;
			else if (isDouble(getType()))
				vType = CTypes.DOUBLE;
			else
				vType = CTypes.STRING;
			return new CTType(vType, getByteDimension(), getMaxByteDimension());
		}
	}
}
