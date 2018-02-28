package configuration;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Settings
{
	@SerializedName("oclSettings")
	@Expose
	private OclSettings mOclSettings;
	@SerializedName("tuples")
	@Expose
	private List<TupleDefinition> mTupleDefinitionList;

	public Settings()
	{
		mOclSettings = new OclSettings();
		mTupleDefinitionList = new ArrayList<>();
	}

	public OclSettings getOclSettings()
	{
		return mOclSettings;
	}

	public Iterable<TupleDefinition> getTupleDefinitions()
	{
		return mTupleDefinitionList;
	}
}
