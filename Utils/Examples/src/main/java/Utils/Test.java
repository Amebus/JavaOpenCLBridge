package Utils;

import java.util.List;
import java.util.Objects;

/**
 * Created by Karat on 31/03/2017.
 */
public class Test
{

	private static boolean isEmptyList(List<Object> prmListToTest)
	{
		return isNull(prmListToTest) || prmListToTest.size() == 0;
	}

	public static boolean isNullOrEmptyOrWhiteSpace(String prmStringToTest)
	{
		return isNull(prmStringToTest) || prmStringToTest.isEmpty() || containsOnlyWhiteSpaces(prmStringToTest);
	}

	public static boolean containsOnlyWhiteSpaces(String prmStringToTest)
	{
		return prmStringToTest.replace(" ", "").isEmpty();
	}

	public static boolean isNull(Object prmObjectToTest)
	{
		return prmObjectToTest == null;
	}

}