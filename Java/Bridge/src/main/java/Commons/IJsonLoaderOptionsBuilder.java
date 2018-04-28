package Commons;

import java.io.File;
import java.nio.file.Path;

public interface IJsonLoaderOptionsBuilder<T> extends IBuilder<JsonLoaderOptions<T>>
{
	
	IJsonLoaderOptionsBuilder<T> setSource(String pFileDirectory, String pFileName);
	IJsonLoaderOptionsBuilder<T> setSource(Path pPathFile);
	IJsonLoaderOptionsBuilder<T> setSource(File pFile);
	
	
	IJsonLoaderOptionsBuilder<T> shouldHookClass(Class pClassToHook);
	IJsonLoaderOptionsBuilder<T> shouldHookClasses(Iterable<Class> pClassesToHook);
	
	IJsonLoaderOptionsBuilder<T> setBeanClass(Class<T> pBeanClass);
	
}
