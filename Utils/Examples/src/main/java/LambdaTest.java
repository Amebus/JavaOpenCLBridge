import java.util.function.BiFunction;

/**
 * Created by Karat on 11/05/2017.
 */
public class LambdaTest
{


	public static int testTwoArgIntOperator(TwoArgIntOperator operator)
	{
		return operator.operator(5,10);
	}

	public static int otherTest(BiFunction<Integer,Integer, Integer> f)
	{
		return f.apply(5,5);
	}



	public interface TwoArgIntOperator
	{
		int operator(int a, int b);
	}
}