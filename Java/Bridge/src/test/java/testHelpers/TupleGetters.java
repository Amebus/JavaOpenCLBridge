package testHelpers;

import tuples.generics.*;
import tuples.serialization.Dimensions;

import java.util.ArrayList;
import java.util.List;

import static testHelpers.Constants.*;

public class TupleGetters
{

	public static class UnsupportedT
	{

	}

	public static int getExpectedStreamLength (List<? extends IOclTuple> prmExpectedList)
	{
		List<? extends IOclTuple> wvNewList = new ArrayList<>(prmExpectedList);
		wvNewList.add(null);
		int[] wvPositions = getTupleExpectedPositions(wvNewList);
		return wvPositions[wvPositions.length - 1];
		// wvExpectedStreamLength += wvExpectedList.stream().mapToInt(x -> x.getT1().length()).sum(); only for strings
	}

	public static int[] getTupleExpectedPositions(List<? extends IOclTuple> prmExpectedList)
	{
		int[] wvExpectedPositions = new int[prmExpectedList.size()];
		wvExpectedPositions[0] = 1 + prmExpectedList.get(0).getArity();

		for (int i = 1; i < wvExpectedPositions.length; i++)
		{
			int j = i - 1;
			wvExpectedPositions[i] = wvExpectedPositions[j];

			int wvI = i;
			prmExpectedList.get(j).forEach(x->
											{
												switch (x.getClass().getName())
												{
													case "java.lang.Double":
														wvExpectedPositions[wvI] += Dimensions.DOUBLE;
														break;
													case "java.lang.String":
														wvExpectedPositions[wvI] += ((String)x).length();
													case "java.lang.Integer":
														wvExpectedPositions[wvI] += Dimensions.INT;
														break;
													default:
														throw new IllegalArgumentException("Object type not recognized, unable to serialize it");
												}
											});
		}

		return wvExpectedPositions;
	}

	public static List<IOclTuple> getEmptyTupleList()
	{
		return new ArrayList<>();
	}

	public static List<IOclTuple> getListWithUsupportedT()
	{
		List<IOclTuple> wvList = new ArrayList<>();

		wvList.add(new Tuple1<>(new UnsupportedT()));

		return wvList;
	}

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

	public static List<Tuple2<Integer, Integer>> getIntegerIntegerTupleList()
	{
		List<Tuple2<Integer, Integer>> wvResult = new ArrayList<>(QUANTITY);

		wvResult.add(new Tuple2<>(ITV_1, ITV_4));
		wvResult.add(new Tuple2<>(ITV_2, ITV_3));
		wvResult.add(new Tuple2<>(ITV_3, ITV_2));
		wvResult.add(new Tuple2<>(ITV_4, ITV_1));

		return wvResult;
	}

	public static List<Tuple2<Integer, Double>> getIntegerDoubleTupleList()
	{
		List<Tuple2<Integer, Double>> wvResult = new ArrayList<>(QUANTITY);

		wvResult.add(new Tuple2<>(ITV_1, DTV_4));
		wvResult.add(new Tuple2<>(ITV_2, DTV_3));
		wvResult.add(new Tuple2<>(ITV_3, DTV_2));
		wvResult.add(new Tuple2<>(ITV_4, DTV_1));

		return wvResult;
	}

	public static List<Tuple2<Integer, String>> getIntegerStringTupleList()
	{
		List<Tuple2<Integer, String>> wvResult = new ArrayList<>(QUANTITY);

		wvResult.add(new Tuple2<>(ITV_1, STV_4));
		wvResult.add(new Tuple2<>(ITV_2, STV_3));
		wvResult.add(new Tuple2<>(ITV_3, STV_2));
		wvResult.add(new Tuple2<>(ITV_4, STV_1));

		return wvResult;
	}

	public static List<Tuple2<Double, Double>> getDoubleDoubleTupleList()
	{
		List<Tuple2<Double, Double>> wvResult = new ArrayList<>(QUANTITY);

		wvResult.add(new Tuple2<>(DTV_1, DTV_4));
		wvResult.add(new Tuple2<>(DTV_2, DTV_3));
		wvResult.add(new Tuple2<>(DTV_3, DTV_2));
		wvResult.add(new Tuple2<>(DTV_4, DTV_1));

		return wvResult;
	}

	public static List<Tuple2<Double, Integer>> getDoubleIntegerTupleList()
	{
		List<Tuple2<Double, Integer>> wvResult = new ArrayList<>(QUANTITY);

		wvResult.add(new Tuple2<>(DTV_1, ITV_4));
		wvResult.add(new Tuple2<>(DTV_2, ITV_3));
		wvResult.add(new Tuple2<>(DTV_3, ITV_2));
		wvResult.add(new Tuple2<>(DTV_4, ITV_1));

		return wvResult;
	}

	public static List<Tuple2<Double, String>> getDoubleStringTupleList()
	{
		List<Tuple2<Double, String>> wvResult = new ArrayList<>(QUANTITY);

		wvResult.add(new Tuple2<>(DTV_1, STV_4));
		wvResult.add(new Tuple2<>(DTV_2, STV_3));
		wvResult.add(new Tuple2<>(DTV_3, STV_2));
		wvResult.add(new Tuple2<>(DTV_4, STV_1));

		return wvResult;
	}

	public static List<Tuple2<String, String>> getStringStringTupleList()
	{
		List<Tuple2<String, String>> wvResult = new ArrayList<>(QUANTITY);

		wvResult.add(new Tuple2<>(STV_1, STV_4));
		wvResult.add(new Tuple2<>(STV_2, STV_3));
		wvResult.add(new Tuple2<>(STV_3, STV_2));
		wvResult.add(new Tuple2<>(STV_4, STV_1));

		return wvResult;
	}

	public static List<Tuple2<String, Integer>> getStringIntegerTupleList()
	{
		List<Tuple2<String, Integer>> wvResult = new ArrayList<>(QUANTITY);

		wvResult.add(new Tuple2<>(STV_1, ITV_4));
		wvResult.add(new Tuple2<>(STV_2, ITV_3));
		wvResult.add(new Tuple2<>(STV_3, ITV_2));
		wvResult.add(new Tuple2<>(STV_4, ITV_1));

		return wvResult;
	}

	public static List<Tuple2<String, Double>> getStringDoubleTupleList()
	{
		List<Tuple2<String, Double>> wvResult = new ArrayList<>(QUANTITY);

		wvResult.add(new Tuple2<>(STV_1, DTV_4));
		wvResult.add(new Tuple2<>(STV_2, DTV_3));
		wvResult.add(new Tuple2<>(STV_3, DTV_2));
		wvResult.add(new Tuple2<>(STV_4, DTV_1));

		return wvResult;
	}
}
