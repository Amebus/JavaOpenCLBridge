package ApacheExamples.UniqueLocationProductSold;

import ApacheExamples.BaseApacheTask;
import Utils.DefaultValues;
import Utils.FileWriter;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import scala.Tuple2;

import java.util.*;


/**
 * Created by Karat on 31/03/2017.
 */
public class UniqueLocationProductSold
		extends BaseApacheTask
{
	private static final String INPUT_TRANSACTIONS_FILE = "transactions.csv";
	private static final String INPUT_USERS_FILE = "users.csv";

	private static final String INPUT_TRANSACTIONS_FULL_PATH = DefaultValues.INPUT_PATH + INPUT_TRANSACTIONS_FILE;
	private static final String INPUT_USERS_FULL_PATH = DefaultValues.INPUT_PATH + INPUT_USERS_FILE;

	private static final String OUTPUT_FILE_NAME = "";


	private String attOutputFileName;

	private SparkConf attSparkConf;
	private JavaSparkContext attJavaSparkContext;

	private JavaPairRDD<Integer, CustomerEntity> attCustomers;
	private JavaPairRDD<Integer, TransactionEntity> attTransactions;

	private FileWriter attFileWriter;

	public UniqueLocationProductSold(SparkConf prmSparkConf, JavaSparkContext javaSparkContext)
	{
		attSparkConf = prmSparkConf;
		attJavaSparkContext = javaSparkContext;
		prepareOutputFolder();
		attFileWriter = FileWriter.getInstance();
	}

	@Override
	public void executeTask()
	{
		loadData();

		SimpleTask wvTask;

		switch (ETasks.ProductListPerUser)
		{
			case NumberOfProductPerLocation:
				wvTask = new NumberOfProductPerLocation();
				break;
			case ProductListPerUser:
				wvTask = new ProductListPerUser();
				break;
			case _DEFAULT:
			default:
				wvTask = new DefaultTask();
		}

		wvTask.doTask();
		attJavaSparkContext.close();
	}

	private void loadData()
	{
		JavaRDD<String> wvCustomerInputFile = attJavaSparkContext.textFile(INPUT_USERS_FULL_PATH);
		attCustomers =
				wvCustomerInputFile.mapToPair(content ->
											  {
												  String[] wvCustomerSplit = content.split(";");
												  Integer wvID = Integer.valueOf(wvCustomerSplit[0]);
												  Email wvEmail = new Email(wvCustomerSplit[1]);
												  CustomerEntity wvCustomerEntity =
														  new CustomerEntity(wvID,
																			 wvEmail,
																			 wvCustomerSplit[2],
																			 wvCustomerSplit[3]);
												  return new Tuple2<>(Integer.valueOf(wvCustomerSplit[0]),
																	  wvCustomerEntity);
											  });

		JavaRDD<String> wvTransactionInputFile = attJavaSparkContext.textFile(INPUT_TRANSACTIONS_FULL_PATH);
		attTransactions =
				wvTransactionInputFile.mapToPair(
						content ->
						{
							String[] wvTransactionSplit = content.split(";");
							Integer wvID = Integer.valueOf(wvTransactionSplit[0]);
							Integer wvProductID = Integer.valueOf(wvTransactionSplit[1]);
							Integer wvUserId = Integer.valueOf(wvTransactionSplit[2]);
							Integer wvPurchaseAmount = Integer.valueOf(wvTransactionSplit[3]);
							TransactionEntity wvTransactionEntity =
									new TransactionEntity(
											wvID,
											wvProductID,
											wvUserId,
											wvPurchaseAmount,
											wvTransactionSplit[4]
									);
							return new Tuple2<>(wvID, wvTransactionEntity);
						});
	}

	private abstract class SimpleTask
	{
		abstract void doTask();
	}

	private class DefaultTask
			extends SimpleTask
	{

		void doTask()
		{

			JavaPairRDD<Integer, Integer> wvProductUserBinding =
					attTransactions.mapToPair(data -> new Tuple2<>(data._2().getCustomerID(),
																   data._2().getProductId())
											 );
			JavaRDD<Tuple2<Integer, Optional<CustomerEntity>>> wvJoin =
					wvProductUserBinding.leftOuterJoin(attCustomers).values().distinct();


			JavaPairRDD<Integer, String > wvModifiedData =
					wvJoin.mapToPair( data ->
											  new Tuple2<>(data._1(), data._2().get().getLocation())
									);

			attFileWriter.addFileToWrite(getWritableResult(generateLines(wvModifiedData.countByKey())));
			attFileWriter.writeFiles();

		}

		private FileWriter.FileLines generateLines(Map<Integer, Long> prmMap)
		{
			attOutputFileName = OUTPUT_FILE_NAME + this.getClass().getSimpleName();
			FileWriter.FileLines.Builder wvBuilder = new FileWriter.FileLines.Builder()
					.withSeparationChar(FileWriter.FileLines.ESeparationChar.SEMI_COLUMN)
					.withHeaderLine(new FileWriter.FileLines.FileHeaderLine.Builder()
											.addColumnHeader("Product")
											.addColumnHeader("Unique Location")
											.build());


			for (Map.Entry<Integer, Long> entry : prmMap.entrySet())
			{
				wvBuilder.addFileLine(new FileWriter.FileLines.FileLine.Builder()
											  .addCell(entry.getKey())
											  .addCell(entry.getValue())
											  .build());
			}

			return wvBuilder.build();
		}
	}


	private class NumberOfProductPerLocation
			extends SimpleTask
	{
		void doTask()
		{

			JavaPairRDD<Integer, TransactionEntity> wvTransactionUserBinding =
					attTransactions.mapToPair(data -> new Tuple2<>(data._2().getCustomerID(),
																   data._2())
											 );

			JavaRDD<Tuple2<TransactionEntity, Optional<CustomerEntity>>> wvJoin =
					wvTransactionUserBinding.leftOuterJoin(attCustomers).values();


			//Nazione, Id prodotto
			JavaPairRDD<String, Integer> wvModifiedData =
					wvJoin.mapToPair( data ->
											  new Tuple2<>(data._2().get().getLocation(), data._1().getPurchaseAmount())
									);

			//conto
			attFileWriter.addFileToWrite(getWritableResult(generateLines(wvModifiedData.reduceByKey((a , b) -> a + b ).collectAsMap().entrySet())));


			JavaRDD<Integer> wvSum = attTransactions.map(data -> data._2().getPurchaseAmount());

			Integer wvTotalProductSold = wvSum.reduce(( a, b )-> a + b);

			attFileWriter.addFileToWrite(getWritableResult(generateLines(wvTotalProductSold, "TotalProductSold")));

			attFileWriter.writeFiles();
		}


		private FileWriter.FileLines generateLines(Integer prmCount, String prmAppendFileName)
		{
			attOutputFileName = OUTPUT_FILE_NAME + this.getClass().getSimpleName() + "_count_" + prmAppendFileName;
			return new FileWriter.FileLines.Builder()
					.withSeparationChar(FileWriter.FileLines.ESeparationChar.SEMI_COLUMN)
					.withHeaderLine(new FileWriter.FileLines.FileHeaderLine.Builder()
											.addColumnHeader(prmAppendFileName)
											.build())
					.addFileLine(new FileWriter.FileLines.FileLine.Builder()
										 .addCell(prmCount)
										 .build())
					.build();
		}

		private FileWriter.FileLines generateLines(Set<Map.Entry<String,Integer>> prmSet)
		{
			attOutputFileName = OUTPUT_FILE_NAME + this.getClass().getSimpleName();
			FileWriter.FileLines.Builder wvBuilder = new FileWriter.FileLines.Builder()
					.withSeparationChar(FileWriter.FileLines.ESeparationChar.SEMI_COLUMN)
					.withHeaderLine(new FileWriter.FileLines.FileHeaderLine.Builder()
											.addColumnHeader("Location")
											.addColumnHeader("Number of product")
											.build());


			for (Map.Entry entry : prmSet)
			{
				wvBuilder.addFileLine(new FileWriter.FileLines.FileLine.Builder()
											  .addCell(entry.getKey())
											  .addCell(entry.getValue())
											  .build());
			}

			return wvBuilder.build();
		}
	}

	private class ProductListPerUser
			extends SimpleTask
	{
		@Override
		void doTask()
		{
			JavaRDD<TransactionEntity> wvCustomerIDTransactionList =
					attTransactions.map(Tuple2::_2);



			attFileWriter.addFileToWrite(getWritableResult(generateCustomerTransactionBindingLines(wvCustomerIDTransactionList.collect())));

			JavaRDD<Tuple2<TransactionEntity, CustomerEntity>> wvTransactionUserBinding = wvCustomerIDTransactionList.mapToPair( data -> new Tuple2<>(data.getCustomerID(), data))
					.join(attCustomers)
					.values();


			attFileWriter.addFileToWrite(getWritableResult(generateCustomerTransactionBindingLines(wvTransactionUserBinding.collect(), 'a')));


			JavaPairRDD<Email, String> wvEmailProdListBinding = wvTransactionUserBinding.mapToPair( data  -> new Tuple2<>(data._2().getEmail(), data._1().getItemDescription()))
					.reduceByKey( (a,b) -> a + "\t" + b);

			attFileWriter.addFileToWrite(getWritableResult(generateEmailProdListLines(wvEmailProdListBinding.collectAsMap().entrySet(), "EmailProdListBinding")));


			//trovo i prodotti invenduti

			attFileWriter.addFileToWrite
					(
							getWritableResult
									(
											generateUnsoldProductLines
													(
															attTransactions.mapToPair
																	(
																			data -> new Tuple2<>(
																					data._2().getItemDescription(),
																					data._2().getPurchaseAmount())
																	)
																	.reduceByKey((a, b) -> a + b)
																	.filter( data -> data._2() <= 0 )
																	.collectAsMap()
																	.entrySet()
													)
									)
					);


			List<Integer> wvList = attCustomers.keys()
					.collect();
			final Set<Integer> wvSet = new HashSet<>(wvList);
			//trovo tutti i prodotti con PurchaseAmount > 0 ma non assegnati ad alcun Customer esistente
			//Map<?,?> wvMap =
			attFileWriter.addFileToWrite(
					getWritableResult(
							generateUnassignedProductLines(attTransactions.filter(data -> data._2().getPurchaseAmount() > 0)
																   .mapToPair(data -> new Tuple2<>(data._2().getItemDescription(), data._2().getCustomerID().toString()))
																   .reduceByKey((a,b) -> a + ";" + b )
																   .filter( data ->
																			{
																				String[] wvSplit = data._2().split(";");
																				for(String s : wvSplit)
																				{
																					if (wvSet.contains(Integer.valueOf(s)))
																						return false;
																				}
																				return true;
																			})
																   .collectAsMap()
																   .entrySet())
									 ));

			//trovo tutte le transazioni errate
			Map<?,?> wvMap2 =
					attTransactions.filter( data ->
											{
												TransactionEntity wvEntity = data._2();
												return wvEntity.getPurchaseAmount()<0 ||
													   !wvSet.contains(wvEntity.getCustomerID());
											})
							.collectAsMap();

			attFileWriter.addFileToWrite(getWritableResult(generateBrokenTransactionsLines(wvMap2.values())));

			attFileWriter.writeFiles();


		}

		private FileWriter.FileLines generateCustomerTransactionBindingLines(List<Tuple2<TransactionEntity, CustomerEntity>> prmTransactionEntityList, char a)
		{
			attOutputFileName = OUTPUT_FILE_NAME + this.getClass().getSimpleName() + "_CustomerTransactionBinding";
			FileWriter.FileLines.Builder wvBuilder = new FileWriter.FileLines.Builder()
					.withSeparationChar(FileWriter.FileLines.ESeparationChar.SEMI_COLUMN)
					.withHeaderLine(new FileWriter.FileLines.FileHeaderLine.Builder()
											.addColumnHeader("CustomerEntity")
											.addColumnHeader(" ")
											.addColumnHeader("TransactionEntity")
											.build());


			for (Tuple2<TransactionEntity, CustomerEntity> entry : prmTransactionEntityList)
			{
				wvBuilder.addFileLine(new FileWriter.FileLines.FileLine.Builder()
											  .addCell(entry._2())
											  .addCell("\t")
											  .addCell(entry._1())
											  .build());
			}

			return wvBuilder.build();
		}

		private FileWriter.FileLines generateCustomerTransactionBindingLines(List<TransactionEntity> prmTransactionEntityList)
		{
			attOutputFileName = OUTPUT_FILE_NAME + this.getClass().getSimpleName() + "_CustomerIDTransactionList";
			FileWriter.FileLines.Builder wvBuilder = new FileWriter.FileLines.Builder()
					.withSeparationChar(FileWriter.FileLines.ESeparationChar.SEMI_COLUMN)
					.withHeaderLine(new FileWriter.FileLines.FileHeaderLine.Builder()
											.addColumnHeader("CustomerID")
											.addColumnHeader("TransactionEntity")
											.build());


			for (TransactionEntity entry : prmTransactionEntityList)
			{
				wvBuilder.addFileLine(new FileWriter.FileLines.FileLine.Builder()
											  .addCell(entry.getCustomerID())
											  .addCell(entry)
											  .build());
			}

			return wvBuilder.build();
		}

		private FileWriter.FileLines generateEmailProdListLines(Set<Map.Entry<Email,String >> prmSet, String prmAppendNameFile)
		{
			attOutputFileName = OUTPUT_FILE_NAME + this.getClass().getSimpleName() + "_" + prmAppendNameFile;

			String wvKeyName = "key";
			String wvValueName = "value";

			Iterator<Map.Entry<Email,String>> wvIterator = prmSet.iterator();

			if (wvIterator.hasNext())
			{
				Map.Entry wvEntry = wvIterator.next();
				wvKeyName = wvEntry.getKey().getClass().getSimpleName();
				wvValueName = wvEntry.getValue().getClass().getSimpleName();
			}

			FileWriter.FileLines.Builder wvBuilder = new FileWriter.FileLines.Builder()
					.withSeparationChar(FileWriter.FileLines.ESeparationChar.SEMI_COLUMN)
					.withHeaderLine(new FileWriter.FileLines.FileHeaderLine.Builder()
											.addColumnHeader(wvKeyName)
											.addColumnHeader(" ")
											.addColumnHeader(wvValueName)
											.build());


			for (Map.Entry entry : prmSet)
			{
				wvBuilder.addFileLine(new FileWriter.FileLines.FileLine.Builder()
											  .addCell(entry.getKey())
											  .addCell("\t")
											  .addCell(entry.getValue())
											  .build());
			}

			return wvBuilder.build();
		}

		private FileWriter.FileLines generateUnsoldProductLines(Set<Map.Entry<String,Integer >> prmSet)
		{
			attOutputFileName = OUTPUT_FILE_NAME + this.getClass().getSimpleName() + "_UnsoldProductList" ;

			FileWriter.FileLines.Builder wvBuilder = new FileWriter.FileLines.Builder()
					.withSeparationChar(FileWriter.FileLines.ESeparationChar.SEMI_COLUMN)
					.withHeaderLine(new FileWriter.FileLines.FileHeaderLine.Builder()
											.addColumnHeader("ProductDesc")
											.addColumnHeader("\t")
											.addColumnHeader("SoldAmount")
											.build());


			for (Map.Entry entry : prmSet)
			{
				wvBuilder.addFileLine(new FileWriter.FileLines.FileLine.Builder()
											  .addCell(entry.getKey())
											  .addCell("\t")
											  .addCell(entry.getValue())
											  .build());
			}

			return wvBuilder.build();
		}

		private FileWriter.FileLines generateUnassignedProductLines(Set<Map.Entry<String,String>> prmSet)
		{
			attOutputFileName = OUTPUT_FILE_NAME + this.getClass().getSimpleName() + "_UnassignedProducts" ;

			FileWriter.FileLines.Builder wvBuilder = new FileWriter.FileLines.Builder()
					.withSeparationChar(FileWriter.FileLines.ESeparationChar.SEMI_COLUMN)
					.withHeaderLine(new FileWriter.FileLines.FileHeaderLine.Builder()
											.addColumnHeader("ProductDesc")
											.addColumnHeader("\t")
											.addColumnHeader("WrongCustomerID")
											.build());


			for (Map.Entry entry : prmSet)
			{
				wvBuilder.addFileLine(new FileWriter.FileLines.FileLine.Builder()
											  .addCell(entry.getKey())
											  .addCell("\t")
											  .addCell(entry.getValue())
											  .build());
			}

			return wvBuilder.build();
		}

		private FileWriter.FileLines generateBrokenTransactionsLines(Collection<?> prmBrokenTransactionCollection)
		{
			attOutputFileName = OUTPUT_FILE_NAME + this.getClass().getSimpleName() + "_BrokenTransactions" ;

			FileWriter.FileLines.Builder wvBuilder = new FileWriter.FileLines.Builder()
					.withSeparationChar(FileWriter.FileLines.ESeparationChar.SEMI_COLUMN)
					.withHeaderLine(new FileWriter.FileLines.FileHeaderLine.Builder()
											.addColumnHeader("Transaction")
											.build());


			for (Object entry : prmBrokenTransactionCollection)
			{
				wvBuilder.addFileLine(new FileWriter.FileLines.FileLine.Builder()
											  .addCell(entry)
											  .build());
			}

			return wvBuilder.build();
		}

		private FileWriter.FileLines generateFileLines(FileWriter.FileLines.FileHeaderLine prmHeaderLine, List<FileWriter.FileLines.FileLine> prmLineList)
		{
			return new FileWriter.FileLines.Builder()
					.withSeparationChar(FileWriter.FileLines.ESeparationChar.SEMI_COLUMN)
					.withHeaderLine(prmHeaderLine)
					.addFileLines(prmLineList)
					.build();
		}
	}

	@Override
	protected FileWriter.File getWritableResult(FileWriter.FileLines prmLines)
	{
		FileWriter.FileBuilder wvBuilder =
				new FileWriter.FileBuilder()
						.setFileName(attOutputFileName)
						.withFilePath(OUTPUT_FILES_PATH)
						.withExtension(FileWriter.File.EExtensions.CSV)
						.withLines(prmLines);
		return wvBuilder.build();
	}

	private enum ETasks
	{
		_DEFAULT,
		NumberOfProductPerLocation,
		ProductListPerUser
	}

}
