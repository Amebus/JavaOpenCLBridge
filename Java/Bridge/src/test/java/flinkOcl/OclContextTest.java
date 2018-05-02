package flinkOcl;

import configuration.LoadSettingsDirective;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import testHelpers.Constants;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OclContextTest
{
	
	@Test
	void A()
	{
		OclContext vContext = new OclContext(new JsonUserFunctionRepository(Constants.FUNCTIONS_DIR), new LoadSettingsDirective(Constants.RESOURCES_DIR));
		
		try
		{
			vContext.open();
		}
		catch (FileNotFoundException pE)
		{
			pE.printStackTrace();
			fail("file no found");
		}
	}
	
}
