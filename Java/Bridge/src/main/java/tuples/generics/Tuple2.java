package tuples.generics;

public class Tuple2 <T0, T1> implements IOclTuple
{
	private T0 mT0;
	private T1 mT1;

	public Tuple2() { }
	
	public Tuple2(T0 pT0, T1 pT1)
	{
		setField(pT0, 0);
		setField(pT1, 1);
	}

	@Override
	public byte getArityOcl()
	{
		return 2;
	}
	
	@Override
	public Object getFieldOcl(int pos)
	{
		switch(pos) {
			case 0: return mT0;
			case 1: return mT1;
			default: throw new IndexOutOfBoundsException(String.valueOf(pos));
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> void setField(T value, int pos)
	{
		switch(pos) {
			case 0:
				this.mT0 = (T0) value;
				break;
			case 1:
				this.mT1 = (T1) value;
				break;
			default: throw new IndexOutOfBoundsException(String.valueOf(pos));
		}
	}

	@Override
	public boolean equals(Object pOther)
	{
		return pOther != null &&
			   (pOther == this || pOther instanceof IOclTuple && equals((IOclTuple) pOther));
	}

}

