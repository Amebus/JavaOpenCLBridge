package ocl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OclUnionTester
{

	private OclContext context;

	@BeforeAll
	void setUp()
	{
		context = new OclContext();
		context.open();
	}

	@AfterAll
	void tearDown()
	{

		if (context!=null)
			context.close();
	}

	@Test
	void oclUnionIntDataSetBigger_OK()
	{
		Random rnd = new Random();
		int dataLength = 100000;
		int otherDataLength = 1000;
		int[] data = new int[dataLength];
		int[] otherData = new int[otherDataLength];
		int[] union = new int[dataLength + otherDataLength];

		for (int i = 0; i < data.length; i++) {
			data[i] = rnd.nextInt();
		}

		for (int i = 0; i < otherData.length; i++) {
			otherData[i] = rnd.nextInt();
		}

		int[] result = context.oclUnion(data, otherData);

		assertEquals(union.length, result.length);

		System.arraycopy(data, 0, union, 0, dataLength);

		System.arraycopy(otherData, 0, union, dataLength, otherData.length);

		assertArrayEquals(union, result);
	}

	@Test
	void oclUnionInt10000_OK()
	{
		Random rnd = new Random();
		int dataLength = 10000;
		int[] data = new int[dataLength];
		int[] otherData = new int[dataLength];
		int[] union = new int[dataLength*2];

		for (int i = 0; i < data.length; i++) {
			data[i] = rnd.nextInt();
		}

		for (int i = 0; i < otherData.length; i++) {
			otherData[i] = rnd.nextInt();
		}

		int[] result = context.oclUnion(data, otherData);

		assertEquals(union.length, result.length);

		System.arraycopy(data, 0, union, 0, dataLength);

		System.arraycopy(otherData, 0, union, dataLength, otherData.length);

		assertArrayEquals(union, result);
	}

	@Test
	void oclUnionIntOtherDataSetBigger_OK()
	{
		Random rnd = new Random();
		int dataLength = 1000;
		int otherDataLength = 100000;
		int[] data = new int[dataLength];
		int[] otherData = new int[otherDataLength];
		int[] union = new int[dataLength + otherDataLength];

		for (int i = 0; i < data.length; i++) {
			data[i] = rnd.nextInt();
		}

		for (int i = 0; i < otherData.length; i++) {
			otherData[i] = rnd.nextInt();
		}

		int[] result = context.oclUnion(data, otherData);

		assertEquals(union.length, result.length);

		System.arraycopy(data, 0, union, 0, dataLength);

		System.arraycopy(otherData, 0, union, dataLength, otherData.length);

		assertArrayEquals(union, result);
	}

	@Test
	void oclUnionDoubleDataSetBigger_OK()
	{
		Random rnd = new Random();
		int dataLength = 100000;
		int otherDataLength = 1000;
		double[] data = new double[dataLength];
		double[] otherData = new double[otherDataLength];
		double[] union = new double[dataLength + otherDataLength];

		for (int i = 0; i < data.length; i++) {
			data[i] = rnd.nextDouble();
		}

		for (int i = 0; i < otherData.length; i++) {
			otherData[i] = rnd.nextDouble();
		}

		double[] result = context.oclUnion(data, otherData);

		assertEquals(union.length, result.length);

		System.arraycopy(data, 0, union, 0, dataLength);

		System.arraycopy(otherData, 0, union, dataLength, otherData.length);

		assertArrayEquals(union, result);
	}

	@Test
	void oclUnionDouble10000_OK()
	{
		Random rnd = new Random();
		int dataLength = 10000;
		double[] data = new double[dataLength];
		double[] otherData = new double[dataLength];
		double[] union = new double[dataLength*2];

		for (int i = 0; i < data.length; i++) {
			data[i] = rnd.nextDouble();
		}

		for (int i = 0; i < otherData.length; i++) {
			otherData[i] = rnd.nextDouble();
		}

		double[] result = context.oclUnion(data, otherData);

		assertEquals(union.length, result.length);

		System.arraycopy(data, 0, union, 0, dataLength);

		System.arraycopy(otherData, 0, union, dataLength, otherData.length);

		assertArrayEquals(union, result);
	}

	@Test
	void oclUnionDoubleOtherDataSetBigger_OK()
	{
		Random rnd = new Random();
		int dataLength = 1000;
		int otherDataLength = 100000;
		double[] data = new double[dataLength];
		double[] otherData = new double[otherDataLength];
		double[] union = new double[dataLength + otherDataLength];

		for (int i = 0; i < data.length; i++) {
			data[i] = rnd.nextDouble();
		}

		for (int i = 0; i < otherData.length; i++) {
			otherData[i] = rnd.nextDouble();
		}

		double[] result = context.oclUnion(data, otherData);

		assertEquals(union.length, result.length);

		System.arraycopy(data, 0, union, 0, dataLength);

		System.arraycopy(otherData, 0, union, dataLength, otherData.length);

		assertArrayEquals(union, result);
	}

}
