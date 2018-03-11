package tuples.generics;

import org.junit.jupiter.api.*;
import tuples.serialization.StreamWriter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static testHelpers.Constants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Tuple1ListTest
{
	// @Test
	// void StreamFromTuple1IntegerList_Ok()
	// {
	// 	List<Tuple1<Integer>> wvExpectedList = getIntegerTupleList();
	// 	Tuple wvFirstTuple = wvExpectedList.get(0);
	// 	int wvExpectedStreamLength = 1 + wvFirstTuple.getArity() + wvExpectedList.size() * Tuple.Dimensions.INT;
	//
	// 	final StreamWriter wvWriter = StreamWriter.getFromTupleList(wvExpectedList);
	// 	byte[] wvStream = wvWriter.getStream();
	//
	// 	wvExpectedList.forEach( x -> x.intoByteStream(wvWriter));
	//
	// 	assertEquals(wvFirstTuple.getArity(), wvStream[0]);
	// 	assertEquals(Tuple.Types.INT, wvStream[1]);
	// 	assertEquals(wvExpectedStreamLength, wvStream.length);
	// }
	//
	// @Test
	// void StreamFromTuple1DoubleList_Ok()
	// {
	// 	List<Tuple1<Double>> wvExpectedList = getDoubleTupleList();
	// 	Tuple wvFirstTuple = wvExpectedList.get(0);
	// 	int wvExpectedStreamLength = 1 + wvFirstTuple.getArity() + wvExpectedList.size() * Tuple.Dimensions.DOUBLE;
	//
	//
	// 	final StreamWriter wvWriter = StreamWriter.getFromTupleList(wvExpectedList);
	// 	byte[] wvStream = wvWriter.getStream();
	//
	// 	wvExpectedList.forEach( x -> x.intoByteStream(wvWriter));
	//
	// 	assertEquals(wvFirstTuple.getArity(), wvStream[0]);
	// 	assertEquals(Tuple.Types.DOUBLE, wvStream[1]);
	// 	assertEquals(wvExpectedStreamLength, wvStream.length);
	// }
	//
	// @Test
	// void StreamFromTuple1StringList_Ok()
	// {
	// 	List<Tuple1<String>> wvExpectedList = getStringTupleList();
	// 	Tuple wvFirstTuple = wvExpectedList.get(0);
	// 	int wvExpectedStreamLength = 1 + wvFirstTuple.getArity() + wvExpectedList.size() * Tuple.Dimensions.INT;
	//
	// 	wvExpectedStreamLength += wvExpectedList.stream().mapToInt(x -> x.getT1().length()).sum();
	//
	// 	final StreamWriter wvWriter = StreamWriter.getFromTupleList(wvExpectedList);
	// 	byte[] wvStream = wvWriter.getStream();
	//
	// 	wvExpectedList.forEach( x -> x.intoByteStream(wvWriter));
	//
	// 	assertEquals(wvFirstTuple.getArity(), wvStream[0]);
	// 	assertEquals(Tuple.Types.STRING, wvStream[1]);
	// 	assertEquals(wvExpectedStreamLength, wvStream.length);
	// }

}
