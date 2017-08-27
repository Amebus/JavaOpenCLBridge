package OpenCL;


import java.io.IOException;

/**
 * Created by Karat on 11/05/2017.
 */
public class Device
{

	int numPlatforms;
	int err;

	public Device()
	{
		//System.loadLibrary("");

		try
		{
			NativeLoader.load();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		//err = clGetPlatformIDs(0, 0, numPlatforms);
	}

}