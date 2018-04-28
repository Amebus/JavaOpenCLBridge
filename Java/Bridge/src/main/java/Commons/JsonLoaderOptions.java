package Commons;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class JsonLoaderOptions<T>
{
	
	private File mFile;
	private Iterable<Class> mClassesToHook;
	private Class<T> mBeanClass;
	
	public JsonLoaderOptions(File pFile, Iterable<Class> pClassesToHook, Class<T> pBeanClass)
	{
		mFile = pFile;
		mClassesToHook = pClassesToHook;
		mBeanClass = pBeanClass;
	}
	
	public File getFile()
	{
		return mFile;
	}
	
	public Iterable<Class> getClassesToHook()
	{
		return mClassesToHook;
	}
	
	public Class<T> getBeanClass()
	{
		return mBeanClass;
	}
	
	public static class JsonLoaderOptionsBuilder<T> implements IJsonLoaderOptionsBuilder<T>
	{
		
		private File mSource;
		private List<Class> mClassesToHook;
		private Class<T> mBeanClass;
		private Set<Class> mClassSet;
		
		public JsonLoaderOptionsBuilder()
		{
			mClassesToHook = new LinkedList<>();
			mClassSet = new HashSet<>();
		}
		
		@Override
		public IJsonLoaderOptionsBuilder<T> setSource(String pFileDirectory, String pFileName)
		{
			return setSource(Paths.get(pFileDirectory).normalize().resolve(pFileName).toAbsolutePath());
		}
		
		@Override
		public IJsonLoaderOptionsBuilder<T> setSource(Path pPathFile)
		{
			return setSource(pPathFile.toFile());
		}
		
		@Override
		public IJsonLoaderOptionsBuilder<T> setSource(File pFile)
		{
			mSource = pFile;
			return this;
		}
		
		private IJsonLoaderOptionsBuilder<T> addClassToHook(Class pClass)
		{
			if (!mClassSet.contains(pClass))
			{
				mClassSet.add(pClass);
				mClassesToHook.add(pClass);
			}
			return this;
		}
		
		@Override
		public IJsonLoaderOptionsBuilder<T> shouldHookClass(Class pClassToHook)
		{
			return addClassToHook(pClassToHook);
		}
		
		@Override
		public IJsonLoaderOptionsBuilder<T> shouldHookClasses(Iterable<Class> pClassesToHook)
		{
			pClassesToHook.forEach(this::addClassToHook);
			return this;
		}
		
		@Override
		public IJsonLoaderOptionsBuilder<T> setBeanClass(Class<T> pBeanClass)
		{
			mBeanClass = pBeanClass;
			return shouldHookClass(pBeanClass);
		}
		
		@Override
		public JsonLoaderOptions<T> build()
		{
			return new JsonLoaderOptions<>(
					mSource,
					mClassesToHook,
					mBeanClass
			);
		}
	}
}
