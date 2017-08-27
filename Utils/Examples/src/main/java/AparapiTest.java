import com.aparapi.Kernel;
import com.aparapi.Range;

/**
 * Created by Karat on 05/05/2017.
 */
public class AparapiTest
{


	public void run()
	{




		final float inA[] = {1 , 2.3f , 4.4f };
		final float inB[] = {1 , 2.3f , 4.4f };
		final float[] result = new float[inA.length];

		final float[][] total = {inA, inB, result};

		final AparapiTestObject testObject = new AparapiTestObject();

		Kernel kernel = new Kernel() {
			@Override
			public void run() {
				int globalId = getGlobalId();
				total[2][globalId] = total[0][globalId] + total[1][globalId];
			}
		};

		Range range = Range.create(total.length);
		kernel.execute(range);

		//System.out.println("Execution mode = "+kernel.getExecutionMode());

		for (float r : result)
		{
			System.out.println("r: " + r + "");
		}
	}


}

class AparapiTestObject
{
	final float inA[] = {1 , 2.3f , 4.4f };
	final float inB[] = {1 , 2.3f , 4.4f };
	final float[] result = new float[inA.length];

	public float[] getInA()
	{
		return inA;
	}

	public float[] getInB()
	{
		return inB;
	}

	float[] getResult()
	{
		return result;
	}

	void sum(int id)
	{
		result[id] = inA[id] + inB[id];
	}
}