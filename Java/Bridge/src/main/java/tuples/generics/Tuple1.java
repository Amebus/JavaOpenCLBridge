package tuples.generics;

public class Tuple1 <T0> implements IOclTuple
{

	private T0 mT0;

	public Tuple1() { }
	
	public Tuple1(T0 pT0)
	{
		setField(pT0, 0);
	}

	@Override
	public byte getArityOcl()
	{
		return 1;
	}
	
	@Override
	public Object getFieldOcl(int pos)
	{
		switch(pos) {
			case 0: return mT0;
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
