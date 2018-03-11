package testHelpers;

import tuples.generics.Tuple1;

import java.util.ArrayList;
import java.util.List;

import static testHelpers.Constants.*;
import static testHelpers.Constants.STV_3;
import static testHelpers.Constants.STV_4;

public class TuplesGetter
{

	public static int QUANTITY = 4;

	public static List<Tuple1<Integer>> getIntegerTupleList()
	{
		List<Tuple1<Integer>> wvResult = new ArrayList<>(QUANTITY);

		wvResult.add(new Tuple1<>(ITV_1));
		wvResult.add(new Tuple1<>(ITV_2));
		wvResult.add(new Tuple1<>(ITV_3));
		wvResult.add(new Tuple1<>(ITV_4));

		return wvResult;
	}

	public static List<Tuple1<Double>> getDoubleTupleList()
	{
		List<Tuple1<Double>> wvResult = new ArrayList<>(QUANTITY);

		wvResult.add(new Tuple1<>(DTV_1));
		wvResult.add(new Tuple1<>(DTV_2));
		wvResult.add(new Tuple1<>(DTV_3));
		wvResult.add(new Tuple1<>(DTV_4));

		return wvResult;
	}

	public static List<Tuple1<String>> getStringTupleList()
	{
		List<Tuple1<String>> wvResult = new ArrayList<>(QUANTITY);

		wvResult.add(new Tuple1<>(STV_1));
		wvResult.add(new Tuple1<>(STV_2));
		wvResult.add(new Tuple1<>(STV_3));
		wvResult.add(new Tuple1<>(STV_4));

		return wvResult;
	}

}
