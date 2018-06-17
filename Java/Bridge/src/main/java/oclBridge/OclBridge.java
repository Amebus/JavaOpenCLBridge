package oclBridge;

import tuples.generics.IOclTuple;
import tuples.serialization.StreamWriter;
import tuples.serialization.StreamWriterResult;

import java.util.List;

public class OclBridge extends AbstractOclBridge
{
	
	public OclBridge()
	{
		super("OclBridge");
	}
	
	public void initialize(String pKernelsFolders) { super.Initialize(pKernelsFolders); }
	public void dispose() { super.Dispose(); }
	
	public boolean[] filter(String pUserFunctionName, List< ? extends IOclTuple> pTuples)
	{
		StreamWriterResult vWriterResult =
				StreamWriter.getStreamWriter().setTupleList(pTuples).writeStream();
		
		return super.OclFilter(pUserFunctionName, vWriterResult.getStream(), vWriterResult.getPositions());
	}
	
}
