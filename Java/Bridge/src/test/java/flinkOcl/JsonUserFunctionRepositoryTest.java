package flinkOcl;

import flinkOcl.buildEngine.IUserFunction;
import flinkOcl.buildEngine.UserFunction;
import io.gsonfire.gson.HookInvocationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import testHelpers.Constants;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static testHelpers.SettingsWrapper.loadSettings;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonUserFunctionRepositoryTest
{
	
	private boolean ufEqualsToUf(IUserFunction p1, IUserFunction p2)
	{
		if(p1 == p2)
			return true;
		
		return p1 != null && p2!=null &&
			   p1.getName().equals(p2.getName()) &&
			   p1.getInputTupleName().equals(p2.getInputTupleName()) &&
			   p1.getOutputTupleName().equals(p2.getOutputTupleName()) &&
			   p1.getFunction().equals(p2.getFunction());
	}
	
	private IUserFunction getMap1()
	{
		return new UserFunction()
		{
			@Override
			public String getType()
			{
				return "map";
			}
			
			@Override
			public String getName()
			{
				return "map1";
			}
			
			@Override
			public String getFunction()
			{
				return "map1_line1\nmap1_line2\nmap1_line3\n";
			}
			
			@Override
			public String getInputTupleName()
			{
				return "tupleOne";
			}
			
			@Override
			public String getOutputTupleName()
			{
				return "tupleTwo";
			}
		};
	}
	
	private IUserFunction getMap2()
	{
		return new UserFunction()
		{
			@Override
			public String getType()
			{
				return "map";
			}
			
			@Override
			public String getName()
			{
				return "map2";
			}
			
			@Override
			public String getFunction()
			{
				return "map2_line1\nmap2_line2\nmap2_line3\n";
			}
			
			@Override
			public String getInputTupleName()
			{
				return "tupleTwo";
			}
			
			@Override
			public String getOutputTupleName()
			{
				return "tupleOne";
			}
		};
	}
	
	private IUserFunctionReadRepository getRepository(String pFileName)
	{
		try
		{
			return new JsonUserFunctionRepository(Constants.FUNCTIONS_DIR, pFileName);
		}
		catch (FileNotFoundException pE)
		{
			pE.printStackTrace();
			fail("The " + Constants.FUNCTIONS_DIR + " directory does not contains the specified files");
		}
		return null;
	}
	
	private Collection<IUserFunction> getUserFunctions(String pFileName)
	{
		return Objects.requireNonNull(getRepository(pFileName)).getUserFunctions();
	}
	
	@Test
	void LoadFunctions_Ok()
	{
		Collection<IUserFunction> userFunctions = getUserFunctions("functions.json");
		
		Optional<IUserFunction> vOptional;
		IUserFunction vTempUF;
		
		assertTrue(userFunctions.stream().allMatch(IUserFunction::isMap));
		assertEquals(1L, userFunctions.stream().filter(x -> x.getName().equals("map1")).count());
		
		vOptional = userFunctions.stream().filter(x -> x.getName().equals("map1")).findFirst();
		assertTrue(vOptional.isPresent());
		vTempUF = vOptional.get();
		assertTrue(ufEqualsToUf(vTempUF, getMap1()));
		
		assertEquals(1L, userFunctions.stream().filter(x -> x.getName().equals("map2")).count());
		vOptional = userFunctions.stream().filter(x -> x.getName().equals("map2")).findFirst();
		assertTrue(vOptional.isPresent());
		vTempUF = vOptional.get();
		assertTrue(ufEqualsToUf(vTempUF, getMap2()));
		
	}
	
	@Test
	void OneFunctionPerType_Ok()
	{
		Collection<? extends IUserFunction> userFunctions = getUserFunctions("oneFunctionPerType.json");
		
		assertEquals(1, userFunctions.stream().filter(IUserFunction::isMap).count());
		assertEquals(1, userFunctions.stream().filter(IUserFunction::isFilter).count());
		assertEquals(1, userFunctions.stream().filter(IUserFunction::isFlatMap).count());
		assertEquals(1, userFunctions.stream().filter(IUserFunction::isReduce).count());
	}
	
	@Test
	void A()
	{
		boolean rightExceptionWasThrow = false;
		try
		{
			getUserFunctions("emptyBodyFunctions.json");
		}
		catch (IllegalArgumentException ex)
		{
			rightExceptionWasThrow = ex.getMessage().endsWith("has no body.");
		}
		assertTrue(rightExceptionWasThrow);
	}
	
	@Test
	void B()
	{
		boolean rightExceptionWasThrow = false;
		try
		{
			getUserFunctions("noOutputMapFunction.json");
		}
		catch (IllegalArgumentException ex)
		{
			rightExceptionWasThrow = ex.getMessage().endsWith("specify an output tuple.");
		}
		assertTrue(rightExceptionWasThrow);
	}
	
	@Test
	void C()
	{
		boolean rightExceptionWasThrow = false;
		try
		{
			getUserFunctions("noOutputFlatMapFunction.json");
		}
		catch (IllegalArgumentException ex)
		{
			rightExceptionWasThrow = ex.getMessage().endsWith("specify an output tuple.");
		}
		assertTrue(rightExceptionWasThrow);
	}
}
