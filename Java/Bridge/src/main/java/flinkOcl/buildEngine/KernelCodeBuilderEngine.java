package flinkOcl.buildEngine;

import Commons.OnDemandLoader;
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
	
	private ISettingsRepository mSettingsRepository;
	private ITupleDefinitionsRepository mTupleDefinitionsRepository;
	private Iterable<? extends IUserFunction> mUserFunctions;
	private KernelBuilderOptions.KernelOptionsBuilder mKernelBuilderOptionsBuilder;
	private StringKeyMapper<KernelBuilder, KernelBuilderOptions> mTypeKernelBuilderMapper;
	
	
	public KernelCodeBuilderEngine(ISettingsRepository pSettingsRepository, ITupleDefinitionsRepository pTupleDefinitionsRepository, Iterable<? extends IUserFunction> pUserFunctions)
	{
		mSettingsRepository = pSettingsRepository;
		mTupleDefinitionsRepository = pTupleDefinitionsRepository;
		mUserFunctions = pUserFunctions;
		
		mKernelBuilderOptionsBuilder = new KernelBuilderOptions.KernelOptionsBuilder()
				.setTupleDefinitionsRepository(mTupleDefinitionsRepository)
				.setContextOptions(mSettingsRepository.getContextOptions())
				.setKernelOptions(mSettingsRepository.getKernelsOptions());
		
		setUpMapper();
	}
	
	private void setUpMapper()
	{
		mTypeKernelBuilderMapper = new StringKeyMapper<>();
		
		//Transformations
		mTypeKernelBuilderMapper.register(IUserFunction.MAP, new OnDemandLoader<>(MapBuilder::new));
		mTypeKernelBuilderMapper.register(IUserFunction.FLAT_MAP, new OnDemandLoader<>(FlatMapBuilder::new));
		mTypeKernelBuilderMapper.register(IUserFunction.FILTER, new OnDemandLoader<>(FilterBuilder::new));
		
		//Actions
		mTypeKernelBuilderMapper.register(IUserFunction.REDUCE, new OnDemandLoader<>(ReduceBuilder::new));
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
		return mTypeKernelBuilderMapper.resolve(pUserFunction.getType(),
												mKernelBuilderOptionsBuilder
													.setUserFunction(pUserFunction)
													.build()
											   ).build();
	}
}