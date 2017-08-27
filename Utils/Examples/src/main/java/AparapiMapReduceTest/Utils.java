package AparapiMapReduceTest;

/**
 * Created by Karat on 19/05/2017.
 */
public class Utils
{

	public static int log4(int n){
		if(n <= 0) throw new IllegalArgumentException();
		return 15 - Integer.numberOfLeadingZeros(n)/2;
	}
	public static int log32(int n)
	{
		if (n <=0) throw  new IllegalArgumentException("n cannot be lesser then 1");
		return 6 - Integer.numberOfLeadingZeros(n)/5;
	}

}