package configuration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.google.gson.internal.LinkedHashTreeMap;
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

	private String mName;
//	private Map<String, String> mTypesMap = new HashMap<>();
	private Map<String, TupleVarDefinition> mVarDefinitionMap = new HashMap<>();


	public TupleDefinition()
	{
		mArity = 0;
	}

	public TupleDefinition(String pName, Map<String, String> pTypesMap)
	{
		this();
		mName = pName;
//		mTypesMap = pTypesMap;
		postDeserialize(pTypesMap);
	}
	
	public TupleDefinition(TupleDefinition pDefinition)
	{
		this();
		mName = pDefinition.getName();
//		mTypesMap = new HashMap<>(pDefinition.mTypesMap);
		mVarDefinitionMap = new HashMap<>(pDefinition.mVarDefinitionMap);
		mArity = pDefinition.mArity;

		computeHashCode();
	}
	
	private void postDeserialize(Map<String, String> pTypesMap)
	{
		final RegularExpression vExpression = new RegularExpression("t\\d+");
//		pTypesMap = new LinkedHashMap<>(pTypesMap);
		Set<String> vKeySet = new HashSet<>(pTypesMap.keySet());
		vKeySet.forEach( x ->
						  {
							  if(vExpression.matches(x))
							  {
								  String vTempString = x.substring(1);
								  int vValue = Integer.parseInt(vTempString);
								  if( 0 <= vValue && vValue < T_LIMIT)
								  {
									  return;
								  }
							  }
							  pTypesMap.remove(x);
						  });

		mArity = (byte)pTypesMap.size();
		
		pTypesMap.forEach( (k,v) -> mVarDefinitionMap.put(k, new TupleVarDefinition(v)));

		computeHashCode();
	}

	private void computeHashCode()
	{
		HashCodeBuilder vBuilder = new HashCodeBuilder();
		reverseIterator().forEachRemaining(vBuilder::append);
		mHashCode = vBuilder
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

		EqualsBuilder vBuilder = new EqualsBuilder();

		Iterator<TType> vRhsIterator = rhs.iterator();

		forEach( x -> vBuilder.append(x, vRhsIterator.next()));

		return vBuilder.append(getName(), rhs.getName()).isEquals();
	}

	/**
	 * Return the Java equivalent type
	 * @param pIndex T index
	 * @return String representing the Java type name
	 */
	public TType getJavaT(int pIndex)
	{
		TupleVarDefinition vT = getT(pIndex);
		return vT == null ? null : vT.getJavaT();
	}

	public TupleVarDefinition getT(int pIndex)
	{
		return getT(getKey(pIndex));
	}

	private TupleVarDefinition getT(String pKey)
	{
		return mVarDefinitionMap.get(pKey);
	}

	/**
	 * Return the C equivalent type
	 * @param pIndex T index
	 * @return String representing the C type name
	 */
	public TType getCT(int pIndex)
	{
		TupleVarDefinition vT = getT(pIndex);
		return vT == null ? null : vT.getCT();
	}

	private String getKey(int pIndex)
	{
		return Keys.T + pIndex;
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
			mCurrentIndex = 0;
		}

		protected int advanceCurrentIndex()
		{
			return mCurrentIndex++;
		}

		@Override
		public boolean hasNext()
		{
			return mCurrentIndex < getArity() && mCurrentIndex < T_LIMIT;
		}
	}

	public abstract class ReverseTIterator extends TIterator
	{
		private int mCurrentIndex;

		private ReverseTIterator()
		{
			mCurrentIndex = mVarDefinitionMap.size() - 1;
		}

		protected int advanceCurrentIndex()
		{
			return mCurrentIndex--;
		}

		@Override
		public boolean hasNext()
		{
			return mCurrentIndex >= 0;
		}
	}
}
