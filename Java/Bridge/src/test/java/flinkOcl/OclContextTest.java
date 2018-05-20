package flinkOcl;

import configuration.json.JsonSettingsRepository;
import configuration.json.JsonTupleDefinitionsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import testHelpers.Constants;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OclContextTest
{
	
	@Test
	void A()
	{
		OclContext vContext = new OclContext(new JsonSettingsRepository(Constants.FUNCTIONS_DIR),
											 new JsonTupleDefinitionsRepository(Constants.FUNCTIONS_DIR),
											 new JsonUserFunctionRepository(Constants.FUNCTIONS_DIR));
		vContext.open();
	}
	
}
