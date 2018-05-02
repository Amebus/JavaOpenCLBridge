package flinkOcl.buildEngine;

import Commons.Lazy;
import Commons.mappers.StringKeyMapper;
import configuration.*;
import flinkOcl.IUserFunction;
import flinkOcl.buildEngine.kernelBuilders.FilterBuilder;
import flinkOcl.buildEngine.kernelBuilders.FlatMapBuilder;
import flinkOcl.buildEngine.kernelBuilders.MapBuilder;
import flinkOcl.buildEngine.kernelBuilders.ReduceBuilder;

import java.util.LinkedList;
import java.util.List;

public class KernelCodeBuilderEngine
{
	
	private OclSettings mSettings;
	private TupleDefinitions mTupleDefinitions;
	private Iterable<? extends IUserFunction> mUserFunctions;
	private KernelBuilderOptions.KernelOptionsBuilder mKernelBuilderOptionsBuilder;
	private StringKeyMapper<KernelBuilder, KernelBuilderOptions> mTypeKernelBuilderMapper;
	
	
	public KernelCodeBuilderEngine(OclSettings pSettings, TupleDefinitions pTupleDefinitions, Iterable<? extends IUserFunction> pUserFunctions)
	{
		mSettings = pSettings;
		mTupleDefinitions = pTupleDefinitions;
		mUserFunctions = pUserFunctions;
		
		mKernelBuilderOptionsBuilder = new KernelBuilderOptions.KernelOptionsBuilder()
				.setTupleDefinitions(mTupleDefinitions)
				.setContextOptions(mSettings.getContextOptions())
				.setKernelOptions(mSettings.getOclKernelOptions());
		
		setUpMapper();
	}
	
	private void setUpMapper()
	{
		mTypeKernelBuilderMapper = new StringKeyMapper<>();
		
		//Transformations
		mTypeKernelBuilderMapper.register(IUserFunction.MAP, new Lazy<>(MapBuilder::new));
		mTypeKernelBuilderMapper.register(IUserFunction.FLAT_MAP, new Lazy<>(FlatMapBuilder::new));
		mTypeKernelBuilderMapper.register(IUserFunction.FILTER, new Lazy<>(FilterBuilder::new));
		
		//Actions
		mTypeKernelBuilderMapper.register(IUserFunction.REDUCE, new Lazy<>(ReduceBuilder::new));
	}
	
	public CppLibraryInfo generateKernels()
	{
		List<OclKernel> vResult = new LinkedList<>();
		for (IUserFunction vUserFunction : mUserFunctions)
		{
			vResult.add(generateKernel(vUserFunction));
		}
		
		//TODO salvare codice dei kernel su files
		
		return new CppLibraryInfo();
	}
	
	private OclKernel generateKernel(IUserFunction pUserFunction)
	{
		return mTypeKernelBuilderMapper.get( pUserFunction.getType(),
											 mKernelBuilderOptionsBuilder
													.setUserFunction(pUserFunction)
													.build()
										   ).build();
	}
}
