package ocl;

public class DataConverter
{

	public native int[] toIntArray(char[] data, String parameterDefinition, String convertLogic);

	public native int[] toIntArray(double[] data, String parameterDefinition, String convertLogic);



	public native char[] toCharArray(int[] data, String parameterDefinition, String convertLogic);

	public native char[] toCharArray(double[] data, String parameterDefinition, String convertLogic);



	public native double[] toDoubleArray(int[] data, String parameterDefinition, String convertLogic);

	public native double[] toDoubleArray(char[] data, String parameterDefinition, String convertLogic);
}
