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
	private static class Keys
	{
		private static final String T = "t";
	}

	public static final transient int T_LIMIT = 22;
	private transient int mHashCode;
	private transient byte mArity;

	@SerializedName("name")
	@Expose
	private String mName;

	@SerializedName("types")
	@Expose
	private Map<String, String> mTypesMap = new HashMap<>();

	private transient Map<String, TupleVarDefinition> mVarDefinitionMap = new HashMap<>();


	public TupleDefinition()
	{
		mArity = 0;
	}

	public TupleDefinition(TupleDefinition prmDefinition)
	{
		this();
		mName = prmDefinition.getName();
		mTypesMap = new HashMap<>(prmDefinition.mTypesMap);
		mVarDefinitionMap = new HashMap<>(prmDefinition.mVarDefinitionMap);
		mArity = prmDefinition.mArity;

		computeHashCode();
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

		mArity = (byte)mTypesMap.size();

		mTypesMap.forEach( (k,v) -> mVarDefinitionMap.put(k, new TupleVarDefinition(v)));

		computeHashCode();
	}

	private void computeHashCode()
	{
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
	public Byte getArity()
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

		forEach( x -> wvBuilder.append(x, wvRhsIterator.next()));

		return wvBuilder.append(getName(), rhs.getName()).isEquals();
	}

	/**
	 * Return the Java equivalent type
	 * @param prmIndex T index
	 * @return String representing the Java type name
	 */
	public TType getJavaT(int prmIndex)
	{
		TupleVarDefinition wvT = getT(prmIndex);
		return wvT == null ? null : wvT.getJavaT();
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
		TupleVarDefinition wvT = getT(prmIndex);
		return wvT == null ? null : wvT.getCT();
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
