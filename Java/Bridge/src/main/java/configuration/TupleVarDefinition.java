package configuration;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import static configuration.TType.*;

public class TupleVarDefinition
{
	private static final String COLUMN = ":";

	private String mType;
	private int mByteDimension;
	private int mMaxByteDimension;
	private TType mJavaTType;
	private TType mCTType;
	private transient int mHashCode;

	public TupleVarDefinition(String prmJsonVarType)
	{
		setInternalValues(prmJsonVarType);
	}

	public TupleVarDefinition(String prmVarType, int prmByteDimension, int prmMaxByteDimension)
	{
		setInternalValues(prmVarType, prmByteDimension, prmMaxByteDimension);
	}

	public TupleVarDefinition(TupleVarDefinition prmVarDefinition)
	{
		this(prmVarDefinition.mType, prmVarDefinition.getByteDimension(), prmVarDefinition.getMaxByteDimension());
	}

	public int getByteDimension()
	{
		return mByteDimension;
	}

	public int getMaxByteDimension()
	{
		return mMaxByteDimension;
	}

	/**
	 * Return the Java equivalent type
	 * @return String representing the Java type name
	 */
	public TType getJavaT()
	{
		return mJavaTType;
	}

	/**
	 * Return the C equivalent type
	 * @return String representing the C type name
	 */
	public TType getCT()
	{
		return mCTType;
	}

	private void setInternalValues(String prmJsonVarType)
	{
		int wvByteDimension = 0;
		int wvMaxByteDimension = 100;
		String wvVarType = prmJsonVarType;

		if (isString(prmJsonVarType))
		{
			String[] wvStrings = prmJsonVarType.split(COLUMN);
			wvByteDimension = -1;

			if (wvStrings.length > 1)
			{
				wvMaxByteDimension = Integer.parseInt(wvStrings[2]);
			}
			wvVarType = wvStrings[0];
		}
		else if (isInteger(prmJsonVarType))
		{
			wvByteDimension = 4;
			wvMaxByteDimension = 4;
		}
		else if (isDouble(prmJsonVarType))
		{
			wvByteDimension = 8;
			wvMaxByteDimension = 8;
		}

		setInternalValues(wvVarType, wvByteDimension, wvMaxByteDimension);
	}

	private void setInternalValues (String prmVarType, int prmByteDimension, int prmMaxByteDimension)
	{
		mType = prmVarType;
		mByteDimension = prmByteDimension;
		mMaxByteDimension = prmMaxByteDimension;

		mJavaTType = new JavaTType(prmVarType, prmByteDimension, prmMaxByteDimension);
		mCTType = new CTType(prmVarType, prmByteDimension, prmMaxByteDimension);

		mHashCode = new HashCodeBuilder()
				.append(getByteDimension())
				.append(getMaxByteDimension())
				.append(getJavaT())
				.toHashCode();
	}

	@Override
	public int hashCode()
	{
		return mHashCode;
	}

	@Override
	public boolean equals(Object prmOther)
	{
		if (prmOther == null)
		{
			return false;
		}
		if (prmOther == this)
		{
			return true;
		}
		if (!(prmOther instanceof TupleVarDefinition))
		{
			return false;
		}
		TupleVarDefinition rhs = ((TupleVarDefinition) prmOther);

		return new EqualsBuilder()
				.append(getJavaT(), rhs.getJavaT())
				.append(getByteDimension(), rhs.getByteDimension())
				.append(getMaxByteDimension(), rhs.getMaxByteDimension())
				.isEquals();
	}
}