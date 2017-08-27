package ApacheExamples.UniqueLocationProductSold;

import scala.Serializable;
import scala.Tuple4;

import javax.validation.constraints.NotNull;

/**
 * Created by Karat on 06/04/2017.
 */
public class CustomerEntity
		implements Serializable
{

	private Tuple4<Integer, Email, String, String> attCustomer;

	CustomerEntity (int prmID, @NotNull Email prmEmail, @NotNull String prmLanguage, @NotNull String prmLocation)
	{
		attCustomer = new Tuple4<>(prmID, prmEmail, prmLanguage, prmLocation);
	}

	@NotNull Integer getID()
	{
		return attCustomer._1();
	}

	@NotNull Email getEmail()
	{
		return attCustomer._2();
	}

	@NotNull String getLanguage()
	{
		return attCustomer._3();
	}

	@NotNull String getLocation()
	{
		return attCustomer._4();
	}

	@Override
	public String toString()
	{
		return "ID: " + getID() +
			   "\tEmail: " + getEmail() +
			   "\tLanguage: " + getLanguage() +
			   "\tLocation: " + getLocation();
	}
}