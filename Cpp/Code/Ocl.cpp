
#define __CL_ENABLE_EXCEPTIONS

#define K_MAP 101


#define K_TAKE 201

#include "ocl_Ocl.h"
#include <CL/cl.hpp>
#include <iostream>
#include <unordered_map>

std::vector<cl::Platform> platforms;
std::vector<cl::Device> devices;
cl::Device defaultDevice;
cl::Context context;
cl::CommandQueue commandQueue;
std::unordered_map<std::string, cl::Kernel> kernelsList;

JNIEXPORT void JNICALL Java_ocl_Ocl_Open
  (JNIEnv *env, jobject obj)
{

	cl::Platform::get(&platforms);

	platforms[0].getDevices(CL_DEVICE_TYPE_ALL, &devices);

	defaultDevice = devices[0];

	context = cl::Context(defaultDevice);

	commandQueue = cl::CommandQueue(context, defaultDevice);

}

JNIEXPORT void JNICALL Java_ocl_Ocl_Close
  (JNIEnv *, jobject obj)
{
	commandQueue = NULL;
	context = NULL;
}

cl::Kernel CreateStdKernel(JNIEnv *env, const char* kName, jstring kernelSource)
{
	const char *kSource = env->GetStringUTFChars(kernelSource, NULL);
	std::string kernelCode = std::string(kSource);

	cl::Program::Sources sources(1, std::make_pair(kernelCode.c_str(),kernelCode.length()+1));
	cl::Program program(context,sources);

	program.build(devices);
	cl::Kernel kernel = cl::Kernel(program, kName);

	env->ReleaseStringUTFChars(kernelSource, kSource);

	return kernel;
}

cl::Kernel GetKernel(JNIEnv *env, jstring kernelName, jstring kernelSource, int kernelType)
{
	const char *kName = env->GetStringUTFChars(kernelName, NULL);
	std::string kernelNameCpp = std::string(kName);
	cl::Kernel kernel;
	std::unordered_map<std::string,cl::Kernel>::const_iterator iter = kernelsList.find(kernelNameCpp);
	if(iter != kernelsList.end())
	{
		kernel = kernelsList[kernelNameCpp];
	}
	else
	{
		switch (kernelType)
		 {
			case K_MAP:
				kernel = CreateStdKernel(env, kName, kernelSource);
				kernelsList[kernelNameCpp] = kernel;
				break;
			case K_TAKE:
				kernel = CreateStdKernel(env, kName, kernelSource);
				kernelsList[kernelNameCpp] = kernel;
				break;
		}
	}
	env->ReleaseStringUTFChars(kernelName, kName);

	return kernel;
}

JNIEXPORT jintArray JNICALL Java_ocl_Ocl_OclMap___3ILjava_lang_String_2Ljava_lang_String_2
  (JNIEnv *env, jobject obj, jintArray data, jstring kernelName, jstring kernelSource)
{
    int len = env->GetArrayLength(data);
	size_t dataSize = sizeof(int)*len;
	int *body = env->GetIntArrayElements(data, 0);
	int *result = new int[len];
	jintArray ret = env->NewIntArray(len);

	try
	{
		cl::Kernel map = GetKernel(env, kernelName, kernelSource, K_MAP);

		cl::Buffer inputBuffer(context, CL_MEM_READ_WRITE, dataSize);
		commandQueue.enqueueWriteBuffer(inputBuffer, CL_TRUE, 0, dataSize, body);

		map.setArg(0, inputBuffer);

		cl::NDRange global(len);

		commandQueue.enqueueNDRangeKernel(map, cl::NullRange, global, cl::NullRange);

		commandQueue.enqueueReadBuffer(inputBuffer, CL_TRUE, 0, dataSize, result);

		env->SetIntArrayRegion(ret, 0, len, result);
		// env->ReleaseIntArrayElements(data, body);
	}
	catch (cl::Error error)
	{
		std::cout << "Error" << std::endl;
		std::cout << error.what() << "(" << error.err() << ")" << std::endl;
	}
	return ret;
}

JNIEXPORT jdoubleArray JNICALL Java_ocl_Ocl_OclMap___3DLjava_lang_String_2Ljava_lang_String_2
  (JNIEnv *env, jobject obj, jdoubleArray data, jstring kernelName, jstring kernelSource)
{
	int len = env->GetArrayLength(data);
	size_t dataSize = sizeof(double)*len;
	double *body = env->GetDoubleArrayElements(data, 0);
	double *result = new double[len];
	jdoubleArray ret = env->NewDoubleArray(len);

	try
	{
		cl::Kernel map = GetKernel(env, kernelName, kernelSource, K_MAP);

		cl::Buffer inputBuffer(context, CL_MEM_READ_WRITE, dataSize);
		commandQueue.enqueueWriteBuffer(inputBuffer, CL_TRUE, 0, dataSize, body);

		map.setArg(0, inputBuffer);

		cl::NDRange global(len);

		commandQueue.enqueueNDRangeKernel(map, cl::NullRange, global, cl::NullRange);

		commandQueue.enqueueReadBuffer(inputBuffer, CL_TRUE, 0, dataSize, result);

		env->SetDoubleArrayRegion(ret, 0, len, result);
		// env->ReleaseDoubleArrayElements(data, body);
	}
	catch (cl::Error error)
	{
		std::cout << "Error" << std::endl;
		std::cout << error.what() << "(" << error.err() << ")" << std::endl;
	}
	return ret;
}

JNIEXPORT jintArray JNICALL Java_ocl_Ocl_OclTake
  (JNIEnv *env, jobject obj, jintArray data, jint nToTake)
{
	int len = env->GetArrayLength(data);
	size_t dataSize = sizeof(int)*len;
	size_t resultSize = sizeof(int)*nToTake;
	int *body = env->GetIntArrayElements(data, 0);
	int *result = new int[nToTake];
	jintArray ret = env->NewIntArray(nToTake);

	try
	{
		jstring kernelSource = env->NewStringUTF("__kernel void takeInt(__global int* _data, __global int* _result)\n"
												"{\n"
												"\tint _gId = get_global_id(0);\n"
												"\t_result[_gId] = _data[_gId];"
												"\n"
												"}"
												"\n");

		cl::Kernel kernelTake = GetKernel(env, env->NewStringUTF("takeInt"), kernelSource, K_TAKE);

		cl::Buffer dataBuffer(context, CL_MEM_READ_ONLY, dataSize);
		cl::Buffer resultBuffer(context, CL_MEM_WRITE_ONLY, resultSize);

		commandQueue.enqueueWriteBuffer(dataBuffer, CL_TRUE, 0, dataSize, body);

		kernelTake.setArg(0, dataBuffer);
		kernelTake.setArg(1, resultBuffer);

		cl::NDRange global(nToTake);

		commandQueue.enqueueNDRangeKernel(kernelTake, cl::NullRange, global, cl::NullRange);

		commandQueue.enqueueReadBuffer(resultBuffer, CL_TRUE, 0, resultSize, result);

		env->SetIntArrayRegion(ret, 0, nToTake, result);
		// env->ReleaseDoubleArrayElements(data, body);
	}
	catch (cl::Error error)
	{
		std::cout << "Error" << std::endl;
		std::cout << error.what() << "(" << error.err() << ")" << std::endl;
	}
	return ret;

}
