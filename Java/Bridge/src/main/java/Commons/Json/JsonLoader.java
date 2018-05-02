package Commons.Json;

import io.gsonfire.GsonFireBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonLoader
{
	public static <T> T loadJsonObject(JsonLoaderOptions<T> pOptions) throws FileNotFoundException
	{
		FileReader vReader = new FileReader(pOptions.getFile());
		GsonFireBuilder vBuilder = new GsonFireBuilder();
		
		pOptions.getClassesToHook().forEach(vBuilder::enableHooks);
		
		return vBuilder.createGson().fromJson(vReader, pOptions.getBeanClass());
	}
}
