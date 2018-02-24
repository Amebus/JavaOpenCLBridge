package serialization;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static serialization.Serializer.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SerializationBridgeTest
{

	private SerializationBridge mSerializationBridge;

	@BeforeAll
	void setUp()
	{
		mSerializationBridge = new SerializationBridge();
	}

	// @Test
	// void A()
	// {
	//
	// 	byte[] expected = new byte[] { 'g' };
	//
	// 	new SerializationBridge().Print(expected);
	//
	// 	byte[] actual = new SerializationBridge().BackToBack(expected);
	//
	// 	assertArrayEquals(expected, actual);
	//
	// }


	@Test
	void B()
	{
		String wvExpected = "string";

		byte wvToAdd = (byte)2;
		byte[] wvStream = toByteArray(wvExpected);

		SerializationBridge.BackToBack(wvStream);

		// SerializationBridge.Print(wvStream);

		// System.out.println();
		//
		// for (int i = 0; i < wvStream.length; i++)
		// {
		// 	System.out.print(wvStream[i] + "|");
		// }
		//
		// System.out.println();

		byte[] wvActual = SerializationBridge.Adds(wvStream, wvToAdd);
		// SerializationBridge.Print(wvActual);

		for (int i = 0; i < wvStream.length; i++)
		{
			wvStream[i] += wvToAdd;
		}

		// SerializationBridge.Print(wvStream);

		assertArrayEquals(wvStream, wvActual);
	}
}
