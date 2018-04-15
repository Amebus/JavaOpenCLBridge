package configuration;

public class JavaTType extends TType
{

	public static class JavaTypes
	{
		static final String INTEGER = "Integer";
		static final String DOUBLE = "Double";
		static final String STRING = "String";
	}

	private JavaTType(String pType, int pByteDimension, int pMaxByteDimension)
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

		public JavaTType build()
		{
			String vType;
			if (isInteger(getType()))
				vType = JavaTypes.INTEGER;
			else if (isDouble(getType()))
				vType = JavaTypes.DOUBLE;
			else
				vType = JavaTypes.STRING;
			return new JavaTType(vType, getByteDimension(), getMaxByteDimension());
		}
	}
}
