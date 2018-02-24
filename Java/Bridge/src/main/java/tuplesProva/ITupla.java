package tuplesProva;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public interface ITupla
{



}

class DeserializationIntent
{
	private Queue<DeserializationAction> mDeserializationIntentQueue;

	public DeserializationIntent()
	{
		mDeserializationIntentQueue = new ArrayDeque<>();
	}

	public DeserializationIntent desrialize(byte[] prmBytes)
	{

		int id = prmBytes[0] * 255 + prmBytes[1];

		switch (id)
		{
			case 1:
				mDeserializationIntentQueue.offer(new DeserializeToString(prmBytes));

		}

		return this;
	}

	public Object deseriaize()
	{
		// switch (mDeserializationIntentQueue.size())
		// {
		// 	case 1:
		// 		return new Tupla1<>();
		// }
		return null;
	}
}

interface DeserializationAction<T>
{
	T deserialize();
}

class DeserializeToString implements DeserializationAction<String>
{
	private byte[] mBytes;

	public DeserializeToString(byte[] prmBytes)
	{
		mBytes = prmBytes;
	}


	@Override
	public String deserialize()
	{
		StringBuilder wvBuilder = new StringBuilder(mBytes.length);

		for (byte wvB: mBytes)
		{
			wvBuilder.append(wvB);
		}
		return wvBuilder.toString();
	}
}