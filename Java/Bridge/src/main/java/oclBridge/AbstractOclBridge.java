package oclBridge;

public abstract class AbstractOclBridge
{
	
	protected AbstractOclBridge()
	{
		System.loadLibrary("AbstractOclBridge");
	}
	
	
	//Context
	protected final native void Initialize(String pKernelsFolders);
	protected final native void Dispose();
	
	//Transformations
	protected final native byte[] OclMap(String pKernelName, byte[] pData, int[] pIndexes);
	protected final native boolean[] OclFilter(String pKernelName, byte[] pData, int[] pIndexes);
	
	//Actions
	protected final native byte[] OclReduce(String pKernelName, byte[] pData, int[] pIndexes);
}
