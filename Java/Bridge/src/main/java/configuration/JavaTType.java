package configuration;

public class JavaTType extends TType
{

	public static class JavaTypes
	{
		static final String INTEGER = "Integer";
		static final String DOUBLE = "Double";
		static final String STRING = "String";
	}

	private JavaTType(String prmType, int prmByteDimension, int prmMaxByteDimension)
	{
		super(prmType, prmByteDimension, prmMaxByteDimension);
	}

	public static class Builder extends TTypeBuilder
	{
		public Builder(String prmType)
		{
			super(prmType);
		}

		public Builder(TType prmType)
		{
			super(prmType);
		}

		public JavaTType build()
		{
			String wvType;
			if (isInteger(getType()))
				wvType = JavaTypes.INTEGER;
			else if (isDouble(getType()))
				wvType = JavaTypes.DOUBLE;
			else
				wvType = JavaTypes.STRING;
			return new JavaTType(wvType, getByteDimension(), getMaxByteDimension());
		}
	}
}
