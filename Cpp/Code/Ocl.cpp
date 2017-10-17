
#define __CL_ENABLE_EXCEPTIONS

#include "ocl_Ocl.h"
#include <iostream>
#include <CL/cl.hpp>
#include <unordered_map>
#include "DataTypes.cpp"

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

void TryTake(cl::Kernel kernelTake, Take_t *takeParams)
{
	try
	{
		cl::Buffer dataBuffer(context, CL_MEM_READ_ONLY, takeParams->dataSize);
		cl::Buffer resultBuffer(context, CL_MEM_WRITE_ONLY, takeParams->resultSize);

		commandQueue.enqueueWriteBuffer(dataBuffer, CL_TRUE, 0, takeParams->dataSize, takeParams->data);

		kernelTake.setArg(0, dataBuffer);
		kernelTake.setArg(1, resultBuffer);

		cl::NDRange global(takeParams->nToTake);

		commandQueue.enqueueNDRangeKernel(kernelTake, cl::NullRange, global, cl::NullRange);

		commandQueue.enqueueReadBuffer(resultBuffer, CL_TRUE, 0, takeParams->resultSize, takeParams->result);
		// env->ReleaseDoubleArrayElements(data, body);
	}
	catch (cl::Error error)
	{
		std::cout << "Error" << std::endl;
		std::cout << error.what() << "(" << error.err() << ")" << std::endl;
	}
}

JNIEXPORT jintArray JNICALL Java_ocl_Ocl_OclTake___3II
  (JNIEnv *env, jobject obj, jintArray data, jint nToTake)
{
	Take_t params;
	int len = env->GetArrayLength(data);
	jintArray ret = env->NewIntArray(nToTake);

	params.dataSize = sizeof(int)*len;
	params.resultSize = sizeof(int)*nToTake;
	params.data = env->GetIntArrayElements(data, 0);
	params.result = new int[nToTake];
	params.nToTake = nToTake;

	jstring kernelSource = env->NewStringUTF("__kernel void takeInt(__global int* _data, __global int* _result)\n"
											"{\n"
											"\tint _gId = get_global_id(0);\n"
											"\t_result[_gId] = _data[_gId];"
											"\n"
											"}"
											"\n");

	cl::Kernel kernelTake = GetKernel(env, env->NewStringUTF("takeInt"), kernelSource, K_TAKE);

	TryTake(kernelTake, &params);

	env->SetIntArrayRegion(ret, 0, params.nToTake, (int *)params.result);
	return ret;

}

JNIEXPORT jdoubleArray JNICALL Java_ocl_Ocl_OclTake___3DI
  (JNIEnv *env, jobject obj, jdoubleArray data, jint nToTake)
{
	Take_t params;
	int len = env->GetArrayLength(data);
	jdoubleArray ret = env->NewDoubleArray(nToTake);

	params.dataSize = sizeof(double)*len;
	params.resultSize = sizeof(double)*nToTake;
	params.data = env->GetDoubleArrayElements(data, 0);
	params.result = new double[nToTake];
	params.nToTake = nToTake;

	jstring kernelSource = env->NewStringUTF("__kernel void takeDouble(__global double* _data, __global double* _result)\n"
											"{\n"
											"\tint _gId = get_global_id(0);\n"
											"\t_result[_gId] = _data[_gId];"
											"\n"
											"}"
											"\n");

	cl::Kernel kernelTake = GetKernel(env, env->NewStringUTF("takeDouble"), kernelSource, K_TAKE);

	TryTake(kernelTake, &params);

	env->SetDoubleArrayRegion(ret, 0, params.nToTake, (double *)params.result);
	return ret;

}
