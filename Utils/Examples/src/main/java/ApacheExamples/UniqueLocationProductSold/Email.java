package ApacheExamples.UniqueLocationProductSold;

import scala.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Created by Karat on 06/04/2017.
 */
public class Email
		implements Serializable
{
	private static final String AT = "@";

	private String attUser;
	private String attDomain;

	Email (@NotNull String prmEmail)
	{
		String[] wvSplit = prmEmail.split(AT);
		attUser = wvSplit[0];
		attDomain = wvSplit[1];
	}

	Email(@NotNull String prmUser, @NotNull String prmDomain)
	{
		attUser = prmUser;
		attDomain = prmDomain;
	}

	@Override
	public String toString()
	{
		return attUser + AT + attDomain;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!obj.getClass().equals(this.getClass()))
			return false;

		Email wvObj = (Email) obj;
		return this.toString().equals(wvObj.toString());
	}
}