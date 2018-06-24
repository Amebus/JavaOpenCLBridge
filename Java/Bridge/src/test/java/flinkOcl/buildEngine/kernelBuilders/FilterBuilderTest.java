package flinkOcl.buildEngine.kernelBuilders;


import commons.IBuilder;
import configuration.ISettingsRepository;
import configuration.ITupleDefinitionsRepository;
import configuration.json.JsonSettingsRepository;
import configuration.json.JsonTupleDefinitionsRepository;
import flinkOcl.IUserFunctionsRepository;
import flinkOcl.JsonUserFunctionRepository;
import flinkOcl.buildEngine.KernelBuilderOptions;
import flinkOcl.buildEngine.OclKernel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import testHelpers.Constants;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FilterBuilderTest
{
	@Test
	void A()
	{
		ISettingsRepository vSettingsRepository =
				new JsonSettingsRepository(Constants.RESOURCES_DIR);
		ITupleDefinitionsRepository vTupleDefinitionsRepository =
				new JsonTupleDefinitionsRepository(Constants.RESOURCES_DIR);
		IUserFunctionsRepository vUserFunctionRepository =
				new JsonUserFunctionRepository(Constants.FUNCTIONS_DIR, "filterFunction.json");
		
		KernelBuilderOptions vOptions = new KernelBuilderOptions(
				vUserFunctionRepository.getUserFunctionByName("filterFunction"),
				vTupleDefinitionsRepository,
				vSettingsRepository.getContextOptions(),
				vSettingsRepository.getKernelsOptions()
		);
		
		
		IBuilder<OclKernel> vBuilder = new FilterBuilder(vOptions);
		
		OclKernel vKernel = vBuilder.build();
		
		System.out.println("Name:");
		System.out.println(vKernel.getName());
		System.out.println();
		System.out.println("Code:");
		System.out.println();
		System.out.println(vKernel.getCode());
		
		System.out.println();
		System.out.println(System.getProperty("user.home"));
		System.out.println();
		
	}
}
