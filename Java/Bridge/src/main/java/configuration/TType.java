package configuration;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public abstract class TType
{

	public static class ConfigTypes
	{
		public static final String INT = "int";
		public static final String INTEGER = "integer";
		public static final String DOUBLE = "double";
		public static final String CSTRING = "char*";
		public static final String STRING = "string";

		private static final String REGEX_STRING = "(" + CSTRING + "|" + STRING + ")(" + COLUMN + "\\d+)?";
		static final RegularExpression EXPRESSION = new RegularExpression(REGEX_STRING, "i");
	}

	static final String COLUMN = ":";
	private String mType;
	private int mByteDimension;
	private int mMaxByteDimension;

	protected TType(String prmType, int prmByteDimension, int prmMaxByteDimension)
	{
		mType = prmType;
		mByteDimension = prmByteDimension;
		mMaxByteDimension = prmMaxByteDimension;
	}

	// protected TType(TType prmTType)
	// {
	// 	this(prmTType.getT(), prmTType.getByteDimension(), prmTType.getMaxByteDimension());
	// }

	public String getT()
	{
		return mType;
	}

	public int getByteDimension()
	{
		return mByteDimension;
	}

	public int getMaxByteDimension()
	{
		return mMaxByteDimension;
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder()
				.append(getByteDimension())
				.append(getMaxByteDimension())
				.append(getT())
				.toHashCode();
	}


	@Override
	public boolean equals(Object prmOther)
	{
		return prmOther != null && prmOther instanceof TType && equals((TType) prmOther);
	}

	public boolean equals(TType prmOther)
	{
		return prmOther != null &&
			   (prmOther == this ||
				new EqualsBuilder()
						.append(getT(), prmOther.getT())
						.append(getByteDimension(), prmOther.getByteDimension())
						.append(getMaxByteDimension(), prmOther.getMaxByteDimension())
						.isEquals());
	}

	public static boolean isInteger (String prmT)
	{
		if (prmT == null)
			return false;
		prmT = prmT.toLowerCase();
		return prmT.equals(ConfigTypes.INT) || prmT.equals(ConfigTypes.INTEGER);
	}

	public static boolean isDouble (String prmT)
	{
		return prmT != null && prmT.toLowerCase().equals(ConfigTypes.DOUBLE);
	}

	public static boolean isString (String prmT)
	{
		if (prmT == null)
			return false;
		prmT = prmT.toLowerCase();
		return ConfigTypes.EXPRESSION.matches(prmT);
	}

	protected static abstract class TTypeBuilder
	{
		private String mType;
		private int mByteDimension;
		private int mMaxByteDimension;


		public TTypeBuilder(String prmType)
		{
			mByteDimension = 0;
			mMaxByteDimension = 100;
			mType = prmType;
			setBuildParameters(prmType);
		}

		public TTypeBuilder(TType prmType)
		{
			setBuildParameters(prmType);
		}

		private void setBuildParameters(TType prmType)
		{
			mByteDimension = prmType.getByteDimension();
			mMaxByteDimension = prmType.getMaxByteDimension();
			mType = prmType.getT();
		}

		private void setBuildParameters(String prmType)
		{
			if (isString(prmType))
			{
				asStringType(prmType);
			}
			else if (isInteger(prmType))
			{
				asIntegerType();
			}
			else if (isDouble(prmType))
			{
				asDoubleType();
			}
			else
			{
				mType = "";
				throw new IllegalArgumentException("Error with var type " + prmType
												   +". The var type must be one between: int|integer or char|character or double or string");
			}
		}

		private void asStringType(String prmType)
		{
			String[] wvStrings = prmType.split(COLUMN);
			mByteDimension = -1;

			if (wvStrings.length > 1)
			{
				mMaxByteDimension = Integer.parseInt(wvStrings[1]);
			}
			mType = wvStrings[0];
		}

		private void asIntegerType()
		{
			mByteDimension = 4;
			mMaxByteDimension = 4;
		}

		private void asDoubleType()
		{
			mByteDimension = 8;
			mMaxByteDimension = 8;
		}

		public String getType()
		{
			return mType;
		}

		public int getByteDimension()
		{
			return mByteDimension;
		}

		public int getMaxByteDimension()
		{
			return mMaxByteDimension;
		}

	}
}
