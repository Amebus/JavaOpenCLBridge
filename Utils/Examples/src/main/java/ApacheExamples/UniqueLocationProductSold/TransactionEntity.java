package ApacheExamples.UniqueLocationProductSold;

import scala.Serializable;
import scala.Tuple5;

import javax.validation.constraints.NotNull;

/**
 * Created by Karat on 06/04/2017.
 */
public class TransactionEntity
		implements Serializable
{
	//transaction-id, product-id, user-id, purchase-amount, item-description
	private Tuple5<Integer, Integer, Integer, Integer, String> attTransaction;

	TransactionEntity(
			@NotNull int prmID,
			@NotNull int prmProductID,
			@NotNull int prmCustomerID,
			@NotNull int prmPurchaseAmount,
			@NotNull String prmItemDescription)
	{
		attTransaction = new Tuple5<>(
				prmID,
				prmProductID,
				prmCustomerID,
				prmPurchaseAmount,
				prmItemDescription
		);
	}


	@NotNull Integer getID()
	{
		return attTransaction._1();
	}

	@NotNull Integer getProductId()
	{
		return attTransaction._2();
	}

	@NotNull Integer getCustomerID()
	{
		return attTransaction._3();
	}

	@NotNull Integer getPurchaseAmount()
	{
		return attTransaction._4();
	}

	@NotNull String getItemDescription()
	{
		return attTransaction._5();
	}

	@Override
	public String toString()
	{
		return "ID: " + getID() +
			   "\tPID: " + getProductId() +
			   "\tCID: " + getCustomerID() +
			   "\tPAmount: " + getPurchaseAmount() +
			   "\tItemDesc: " + getItemDescription();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!this.getClass().equals(obj.getClass()))
			return false;

		TransactionEntity wvObj = (TransactionEntity) obj;

		return wvObj.getID().equals(wvObj.getID());
	}

	public boolean isContentEqualTo(TransactionEntity prmEntity)
	{
		return 	getProductId().equals(prmEntity.getProductId())
				  && getCustomerID().equals(prmEntity.getCustomerID())
				  && getPurchaseAmount().equals(prmEntity.getPurchaseAmount())
				  && getItemDescription().equals(prmEntity.getItemDescription());


	}
}
