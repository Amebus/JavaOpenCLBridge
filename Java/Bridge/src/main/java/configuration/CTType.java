package configuration;

public class CTType extends TType
{

	public static class CTypes
	{
		static final String INTEGER = "int";
		static final String DOUBLE = "double";
		static final String STRING = "char*";
	}

	private CTType(String prmType, int prmByteDimension, int prmMaxByteDimension)
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

		public CTType build()
		{
			String wvType;
			if (isInteger(getType()))
				wvType = CTypes.INTEGER;
			else if (isDouble(getType()))
				wvType = CTypes.DOUBLE;
			else
				wvType = CTypes.STRING;
			return new CTType(wvType, getByteDimension(), getMaxByteDimension());
		}
	}
}
