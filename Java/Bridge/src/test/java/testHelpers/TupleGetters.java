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

	public static int getExpectedStreamLength (List<? extends IOclTuple> pExpectedList)
	{
		List<? extends IOclTuple> vNewList = new ArrayList<>(pExpectedList);
		vNewList.add(null);
		int[] vPositions = getTupleExpectedPositions(vNewList);
		return vPositions[vPositions.length - 1];
		// vExpectedStreamLength += vExpectedList.stream().mapToInt(x -> x.getT1().length()).sum(); only for strings
	}

	public static int[] getTupleExpectedPositions(List<? extends IOclTuple> pExpectedList)
	{
		int[] vExpectedPositions = new int[pExpectedList.size()];
		vExpectedPositions[0] = 1 + pExpectedList.get(0).getArity();

		for (int i = 1; i < vExpectedPositions.length; i++)
		{
			int j = i - 1;
			vExpectedPositions[i] = vExpectedPositions[j];

			int vI = i;
			pExpectedList.get(j).forEach(x->
											{
												switch (x.getClass().getName())
												{
													case "java.lang.Double":
														vExpectedPositions[vI] += Dimensions.DOUBLE;
														break;
													case "java.lang.String":
														vExpectedPositions[vI] += ((String)x).length();
													case "java.lang.Integer":
														vExpectedPositions[vI] += Dimensions.INT;
														break;
													default:
														throw new IllegalArgumentException("Object type not recognized, unable to serialize it");
												}
											});
		}

		return vExpectedPositions;
	}

	public static List<IOclTuple> getEmptyTupleList()
	{
		return new ArrayList<>();
	}

	public static List<IOclTuple> getListWithUsupportedT()
	{
		List<IOclTuple> vList = new ArrayList<>();

		vList.add(new Tuple1<>(new UnsupportedT()));

		return vList;
	}

	public static int QUANTITY = 4;

	public static List<Tuple1<Integer>> getIntegerTupleList()
	{
		List<Tuple1<Integer>> vResult = new ArrayList<>(QUANTITY);

		vResult.add(new Tuple1<>(ITV_1));
		vResult.add(new Tuple1<>(ITV_2));
		vResult.add(new Tuple1<>(ITV_3));
		vResult.add(new Tuple1<>(ITV_4));

		return vResult;
	}

	public static List<Tuple1<Double>> getDoubleTupleList()
	{
		List<Tuple1<Double>> vResult = new ArrayList<>(QUANTITY);

		vResult.add(new Tuple1<>(DTV_1));
		vResult.add(new Tuple1<>(DTV_2));
		vResult.add(new Tuple1<>(DTV_3));
		vResult.add(new Tuple1<>(DTV_4));

		return vResult;
	}

	public static List<Tuple1<String>> getStringTupleList()
	{
		List<Tuple1<String>> vResult = new ArrayList<>(QUANTITY);

		vResult.add(new Tuple1<>(STV_1));
		vResult.add(new Tuple1<>(STV_2));
		vResult.add(new Tuple1<>(STV_3));
		vResult.add(new Tuple1<>(STV_4));

		return vResult;
	}

	public static List<Tuple2<Integer, Integer>> getIntegerIntegerTupleList()
	{
		List<Tuple2<Integer, Integer>> vResult = new ArrayList<>(QUANTITY);

		vResult.add(new Tuple2<>(ITV_1, ITV_4));
		vResult.add(new Tuple2<>(ITV_2, ITV_3));
		vResult.add(new Tuple2<>(ITV_3, ITV_2));
		vResult.add(new Tuple2<>(ITV_4, ITV_1));

		return vResult;
	}

	public static List<Tuple2<Integer, Double>> getIntegerDoubleTupleList()
	{
		List<Tuple2<Integer, Double>> vResult = new ArrayList<>(QUANTITY);

		vResult.add(new Tuple2<>(ITV_1, DTV_4));
		vResult.add(new Tuple2<>(ITV_2, DTV_3));
		vResult.add(new Tuple2<>(ITV_3, DTV_2));
		vResult.add(new Tuple2<>(ITV_4, DTV_1));

		return vResult;
	}

	public static List<Tuple2<Integer, String>> getIntegerStringTupleList()
	{
		List<Tuple2<Integer, String>> vResult = new ArrayList<>(QUANTITY);

		vResult.add(new Tuple2<>(ITV_1, STV_4));
		vResult.add(new Tuple2<>(ITV_2, STV_3));
		vResult.add(new Tuple2<>(ITV_3, STV_2));
		vResult.add(new Tuple2<>(ITV_4, STV_1));

		return vResult;
	}

	public static List<Tuple2<Double, Double>> getDoubleDoubleTupleList()
	{
		List<Tuple2<Double, Double>> vResult = new ArrayList<>(QUANTITY);

		vResult.add(new Tuple2<>(DTV_1, DTV_4));
		vResult.add(new Tuple2<>(DTV_2, DTV_3));
		vResult.add(new Tuple2<>(DTV_3, DTV_2));
		vResult.add(new Tuple2<>(DTV_4, DTV_1));

		return vResult;
	}

	public static List<Tuple2<Double, Integer>> getDoubleIntegerTupleList()
	{
		List<Tuple2<Double, Integer>> vResult = new ArrayList<>(QUANTITY);

		vResult.add(new Tuple2<>(DTV_1, ITV_4));
		vResult.add(new Tuple2<>(DTV_2, ITV_3));
		vResult.add(new Tuple2<>(DTV_3, ITV_2));
		vResult.add(new Tuple2<>(DTV_4, ITV_1));

		return vResult;
	}

	public static List<Tuple2<Double, String>> getDoubleStringTupleList()
	{
		List<Tuple2<Double, String>> vResult = new ArrayList<>(QUANTITY);

		vResult.add(new Tuple2<>(DTV_1, STV_4));
		vResult.add(new Tuple2<>(DTV_2, STV_3));
		vResult.add(new Tuple2<>(DTV_3, STV_2));
		vResult.add(new Tuple2<>(DTV_4, STV_1));

		return vResult;
	}

	public static List<Tuple2<String, String>> getStringStringTupleList()
	{
		List<Tuple2<String, String>> vResult = new ArrayList<>(QUANTITY);

		vResult.add(new Tuple2<>(STV_1, STV_4));
		vResult.add(new Tuple2<>(STV_2, STV_3));
		vResult.add(new Tuple2<>(STV_3, STV_2));
		vResult.add(new Tuple2<>(STV_4, STV_1));

		return vResult;
	}

	public static List<Tuple2<String, Integer>> getStringIntegerTupleList()
	{
		List<Tuple2<String, Integer>> vResult = new ArrayList<>(QUANTITY);

		vResult.add(new Tuple2<>(STV_1, ITV_4));
		vResult.add(new Tuple2<>(STV_2, ITV_3));
		vResult.add(new Tuple2<>(STV_3, ITV_2));
		vResult.add(new Tuple2<>(STV_4, ITV_1));

		return vResult;
	}

	public static List<Tuple2<String, Double>> getStringDoubleTupleList()
	{
		List<Tuple2<String, Double>> vResult = new ArrayList<>(QUANTITY);

		vResult.add(new Tuple2<>(STV_1, DTV_4));
		vResult.add(new Tuple2<>(STV_2, DTV_3));
		vResult.add(new Tuple2<>(STV_3, DTV_2));
		vResult.add(new Tuple2<>(STV_4, DTV_1));

		return vResult;
	}
}
