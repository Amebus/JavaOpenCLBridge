package configuration;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class TupleVarDefinition
{

	private JavaTType mJavaTType;
	private CTType mCTType;
	private transient int mHashCode;

	public TupleVarDefinition(String prmJsonVarType)
	{
		setInternalValues(prmJsonVarType);
	}

	public TupleVarDefinition(TupleVarDefinition prmVarDefinition)
	{
		setInternalValues(prmVarDefinition.getJavaT());
	}

	/**
	 * Return the Java equivalent type
	 * @return String representing the Java type name
	 */
	public JavaTType getJavaT()
	{
		return mJavaTType;
	}

	/**
	 * Return the C equivalent type
	 * @return String representing the C type name
	 */
	public CTType getCT()
	{
		return mCTType;
	}

	private void setInternalValues(String prmJsonVarType)
	{
		setInternalValues(new CTType.Builder(prmJsonVarType).build());
	}

	private void setInternalValues (TType prmType)
	{
		mCTType = new CTType.Builder(prmType).build();
		mJavaTType = new JavaTType.Builder(prmType).build();
		mHashCode = new HashCodeBuilder()
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
				.isEquals();
	}
}