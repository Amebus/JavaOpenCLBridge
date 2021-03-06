package flink.ocl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import flink.ocl.build.engine.UserFunction;
import io.gsonfire.annotations.PostDeserialize;

import java.util.ArrayList;
import java.util.List;

public class JsonUserFunction extends UserFunction implements IUserFunction
{
	@SerializedName("type")
	@Expose
	private String mType;
	@SerializedName("input")
	@Expose
	private String mInputTupleName;
	@SerializedName("output")
	@Expose
	private String mOutputTupleName;
	@SerializedName("name")
	@Expose
	private String mName;
	@SerializedName("function")
	@Expose
	private List<String> mFunctionLines;
	
	private transient String mFunction;
	
	public JsonUserFunction()
	{
		mFunctionLines = new ArrayList<>();
		mFunction = "";
	}
	
	@PostDeserialize
	private void postDeserialize()
	{
		checkType();
		checkOutputTuple();
		
		if(mFunctionLines.size() == 0)
		{
			throw new IllegalArgumentException("The function " + getName() + " has no body.");
		}
		
		StringBuilder vResult = new StringBuilder();
		
		mFunctionLines.forEach( x -> vResult.append(x).append(";\n"));
		
		mFunction = vResult.toString();
	}
	
	@Override
	public String getType()
	{
		return mType;
	}
	
	@Override
	public String getName()
	{
		return mName;
	}
	
	@Override
	public String getFunction()
	{
		return mFunction;
	}
	
	@Override
	public String getInputTupleName()
	{
		return mInputTupleName;
	}
	
	@Override
	public String getOutputTupleName()
	{
		return mOutputTupleName;
	}
}
