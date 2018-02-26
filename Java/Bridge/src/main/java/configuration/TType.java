package configuration;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public abstract class TType
{

	private static class ConfigTypes
	{
		static final String INT = "int";
		static final String INTEGER = "integer";
		static final String DOUBLE = "double";
		static final String CSTRING = "char*";
		static final String STRING = "string";
	}

	private String mType;
	private int mByteDimension;
	private int mMaxByteDimension;

	public TType(String prmType, int prmByteDimension, int prmMaxByteDimension)
	{
		mType = prmType;
		mByteDimension = prmByteDimension;
		mMaxByteDimension = prmMaxByteDimension;
	}

	public TType(TType prmTType)
	{
		this(prmTType.getT(), prmTType.getByteDimension(), prmTType.getMaxByteDimension());
	}

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
		return prmT.startsWith(ConfigTypes.CSTRING) || prmT.startsWith(ConfigTypes.STRING);
	}
}
