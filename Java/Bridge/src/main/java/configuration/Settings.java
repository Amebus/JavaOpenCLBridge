package configuration;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.gsonfire.annotations.PostDeserialize;

import java.util.LinkedHashMap;
import java.util.Map;

public class Settings
{
	@SerializedName("oclSettings")
	@Expose
	private OclSettings mOclSettings;
	@SerializedName("tuples")
	@Expose
	private Map<String, Map<String, String>> mTuples;

	private transient TupleDefinitions mTupleDefinitions;
	
	public Settings()
	{
		mOclSettings = new OclSettings();
		mTuples = new LinkedHashMap<>();
	}

	public OclSettings getOclSettings()
	{
		return mOclSettings;
	}
	
	public TupleDefinitions getTupleDefinitions()
	{
		return mTupleDefinitions;
	}
	
	@PostDeserialize
	public void postDeserialize()
	{
		TupleDefinitions.TupleDefinitionsBuilder vBuilder = new TupleDefinitions.TupleDefinitionsBuilder(mTuples.size());
		
		mTuples.forEach( (k, v) -> {
			vBuilder.addTupleDefinition(new TupleDefinition(k, v));
		});
		
		mTupleDefinitions = vBuilder.build();
	}
}
