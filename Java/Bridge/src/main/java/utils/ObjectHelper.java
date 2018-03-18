package utils;

public class ObjectHelper
{

	public static boolean isNull(Object obj)
	{
		return obj == null;
	}





	public static boolean isNullOrEmptyOrWhiteSpace(String str)
	{
		return str == null || str.trim().equals("") ;
	}

	public static boolean isNullOrEmpty(String str)
	{
		return str == null || str.equals("");
	}

	public static boolean isNullOrWhiteSpace(String str)
	{
		return str == null || str.trim().equals("");
	}

}
