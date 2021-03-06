package configuration;

import java.util.Iterator;

public interface ITupleDefinition extends Iterable<TType>
{
	
	int T_LIMIT = 22;
	
	
	/**
	 * Return the Java equivalent type
	 * @param pIndex T index
	 * @return String representing the Java type name
	 */
	default TType getJavaT(int pIndex)
	{
		TupleVarDefinition vT = getT(pIndex);
		return vT == null ? null : vT.getJavaT();
	}
	
	/**
	 * Return the C equivalent type
	 * @param pIndex T index
	 * @return String representing the C type name
	 */
	default TType getCT(int pIndex)
	{
		TupleVarDefinition vT = getT(pIndex);
		return vT == null ? null : vT.getCT();
	}
	
	TupleVarDefinition getT(int pIndex);
	
	String getName();
	
	/**
	 * Return the tuple arity
	 * @return an int representing the arity of the tuple
	 */
	Byte getArity();
	
	Iterator<TType> cIterator();
	
	boolean equals(Object obj);
}
