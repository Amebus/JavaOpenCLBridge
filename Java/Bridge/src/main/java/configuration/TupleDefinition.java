package configuration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;
import io.gsonfire.annotations.PostDeserialize;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


import java.util.*;

public class TupleDefinition implements Iterable<TType>
{

	public static class Types
	{

		private static class Config
		{
			static final String INT = "int";
			static final String INTEGER = "integer";
			static final String CHAR = "char";
			static final String CHARACTER = "character";
			static final String DOUBLE = "double";
			static final String CSTRING = "char*";
			static final String STRING = "string";
		}

		public static class Java
		{
			static final String INTEGER = "Integer";
			static final String DOUBLE = "Double";
			static final String CHAR = "Character";
			static final String STRING = "String";
		}

		public static class C
		{
			static final String INTEGER = "int";
			static final String DOUBLE = "double";
			static final String CHAR = "char";
			static final String STRING = "char*";
		}
	}

	private static class Keys
	{
		private static final String T = "t";
	}

	public static final transient int T_LIMIT = 22;
	private transient int mHashCode;
	private transient int mArity;

	@SerializedName("name")
	@Expose
	private String mName;

	@SerializedName("types")
	@Expose
	private Map<String, String> mTypesMap = new HashMap<>();

	private transient Map<String, TupleVarDefinition> mVarDefinitionMap = new HashMap<>();


	public TupleDefinition()
	{
		mArity = -1;
	}

	public TupleDefinition(TupleDefinition prmDefinition)
	{
		this();
		mName = prmDefinition.getName();
		mTypesMap = new HashMap<>(prmDefinition.mTypesMap);
	}

	@PostDeserialize
	public void postDeserialize()
	{
		final RegularExpression wvExpression = new RegularExpression("t\\d+");
		Set<String> wvKeySet = new HashSet<>(mTypesMap.keySet());
		wvKeySet.forEach( x ->
						  {
							if(wvExpression.matches(x))
							{
								String wvTempString = x.substring(1);
								int wvValue = Integer.parseInt(wvTempString);
								if( 0 < wvValue && wvValue <= T_LIMIT)
								{
									return;
								}
							}
							mTypesMap.remove(x);
						  });

		mArity = mTypesMap.size();

		mTypesMap.forEach( (k,v) -> mVarDefinitionMap.put(k, new TupleVarDefinition(v)));

		HashCodeBuilder wvBuilder = new HashCodeBuilder();
		reverseIterator().forEachRemaining(wvBuilder::append);
		mHashCode = wvBuilder
				.append(getName())
				.toHashCode();
	}

	public String getName()
	{
		return mName;
	}

	/**
	 * Return the tuple arity
	 * @return an int representing the arity of the tuple
	 */
	public Integer getArity()
	{
		return mArity;
	}

	@Override
	public int hashCode()
	{
		return mHashCode;
	}

	@Override
	public boolean equals(Object other)
	{
		if (other == null)
		{
			return false;
		}
		if (other == this)
		{
			return true;
		}
		if (!(other instanceof TupleDefinition))
		{
			return false;
		}
		TupleDefinition rhs = ((TupleDefinition) other);

		if (!getArity().equals(rhs.getArity()))
		{
			return false;
		}

		EqualsBuilder wvBuilder = new EqualsBuilder();

		Iterator<TType> wvRhsIterator = rhs.iterator();

		forEach( (x) -> wvBuilder.append(x, wvRhsIterator.next()));

		return wvBuilder.append(getName(), rhs.getName()).isEquals();
	}

	/**
	 * Return the Java equivalent type
	 * @param prmIndex T index
	 * @return String representing the Java type name
	 */
	public TType getJavaT(int prmIndex)
	{
		return getT(prmIndex).getJavaT();
	}

	public TupleVarDefinition getT(int prmIndex)
	{
		return getT(getKey(prmIndex));
	}

	private TupleVarDefinition getT(String prmKey)
	{
		return mVarDefinitionMap.get(prmKey);
	}

	/**
	 * Return the C equivalent type
	 * @param prmIndex T index
	 * @return String representing the C type name
	 */
	public TType getCT(int prmIndex)
	{
		return getT(prmIndex).getCT();
	}

	private String getKey(int prmIndex)
	{
		return Keys.T + prmIndex;
	}

	@Override
	public Iterator<TType> iterator()
	{
		return new JavaTIterator();
	}

	public Iterator<TType> reverseIterator ()
	{
		return new JavaTReverseIterator();
	}

	public Iterator<TType> cIterator ()
	{
		return new CTIterator();
	}

	public Iterator<TType> cReverseIterator ()
	{
		return new CTReverseIterator();
	}

	public class JavaTIterator extends ForwardTIterator
	{
		@Override
		public TType next()
		{
			return getJavaT(advanceCurrentIndex());
		}
	}

	public class JavaTReverseIterator extends ReverseTIterator
	{
		@Override
		public TType next()
		{
			return getJavaT(advanceCurrentIndex());
		}
	}

	public class CTIterator extends ForwardTIterator
	{
		@Override
		public TType next()
		{
			return getCT(advanceCurrentIndex());
		}
	}

	public class CTReverseIterator extends ReverseTIterator
	{
		@Override
		public TType next()
		{
			return getCT(advanceCurrentIndex());
		}
	}

	public abstract class TIterator implements Iterator<TType>
	{
		protected abstract int advanceCurrentIndex();
	}

	public abstract class ForwardTIterator extends TIterator
	{
		private int mCurrentIndex;
		private ForwardTIterator()
		{
			mCurrentIndex = 1;
		}

		protected int advanceCurrentIndex()
		{
			return mCurrentIndex++;
		}

		@Override
		public boolean hasNext()
		{
			return mCurrentIndex <= getArity() && mCurrentIndex <= T_LIMIT;
		}
	}

	public abstract class ReverseTIterator extends TIterator
	{
		private int mCurrentIndex;

		private ReverseTIterator()
		{
			mCurrentIndex = mVarDefinitionMap.size();
		}

		protected int advanceCurrentIndex()
		{
			return mCurrentIndex--;
		}

		@Override
		public boolean hasNext()
		{
			return mCurrentIndex > 0;
		}
	}
}
