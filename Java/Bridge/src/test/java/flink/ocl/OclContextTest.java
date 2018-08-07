package flink.ocl;

import configuration.json.JsonSettingsRepository;
import configuration.json.JsonTupleDefinitionsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import test.helpers.Constants;
import tuples.generics.IOclTuple;
import tuples.generics.Tuple1Ocl;

import java.util.ArrayList;
import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OclContextTest
{
	
	@Test
	void A()
	{
		//new OclBridge().listDevices();
		
		OclContext vContext = new OclContext(new JsonSettingsRepository(Constants.RESOURCES_DIR),
											 new JsonTupleDefinitionsRepository(Constants.RESOURCES_DIR),
											 new JsonUserFunctionRepository(Constants.FUNCTIONS_DIR,
																			"filterFunction2.json"));
		vContext.open();
		
		
		List<IOclTuple> vTuples = new ArrayList<>();
		
		vTuples.add(new Tuple1Ocl<>(0));
		vTuples.add(new Tuple1Ocl<>(1));
		vTuples.add(new Tuple1Ocl<>(2));
		vTuples.add(new Tuple1Ocl<>(3));
		
		List<? extends IOclTuple> vResult = vContext.filter("filterFunction", vTuples);

		System.out.println(vResult.size());

		vResult.forEach(x ->
						{
							Tuple1Ocl<Integer> vT = (Tuple1Ocl<Integer>)x;
							System.out.println(vT.<Integer>getField(0));
						});
		
		int i = 0;
		
		vContext.close();
	}
	
}
