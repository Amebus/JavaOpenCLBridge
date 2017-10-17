
#define __CL_ENABLE_EXCEPTIONS

#include <CL/cl.hpp>
#include <iostream>
#include <unordered_map>
#include "ocl_Ocl.h"
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

void CreateStdKernel(JNIEnv *env, const char* kName, std::string kernelNameCpp, jstring kernelSource)
{
	const char *kSource = env->GetStringUTFChars(kernelSource, NULL);
	std::string kernelCode = std::string(kSource);

	cl::Program::Sources sources(1, std::make_pair(kernelCode.c_str(),kernelCode.length()+1));
	cl::Program program(context,sources);

	program.build(devices);
	kernelsList[kernelNameCpp] = cl::Kernel(program, kName);

	env->ReleaseStringUTFChars(kernelSource, kSource);
}

std::string CreateKernelIfNotExists(JNIEnv *env, jstring kernelName, jstring kernelSource, int kernelType)
{
	const char *kName = env->GetStringUTFChars(kernelName, NULL);
	std::string kernelNameCpp = std::string(kName);
	cl::Kernel *kernel;
	std::unordered_map<std::string,cl::Kernel>::const_iterator iter = kernelsList.find(kernelNameCpp);
	if(iter == kernelsList.end())
	{
		switch (kernelType)
		{
			case K_MAP:
			case K_TAKE:
			case K_UNION:
				CreateStdKernel(env, kName, kernelNameCpp, kernelSource);
				break;
		}
	}
	env->ReleaseStringUTFChars(kernelName, kName);

	return kernelNameCpp;
}

void TryMap(std::string kernelMapName, Map_t *mapParams) {
	try
	{
		cl::Kernel kernelMap = kernelsList[kernelMapName];

		cl::Buffer inputBuffer(context, CL_MEM_READ_WRITE, mapParams->dataSize);
		commandQueue.enqueueWriteBuffer(inputBuffer, CL_TRUE, 0, mapParams->dataSize, mapParams->data);

		kernelMap.setArg(0, inputBuffer);

		cl::NDRange global(mapParams->dataLength);

		commandQueue.enqueueNDRangeKernel(kernelMap, cl::NullRange, global, cl::NullRange);

		commandQueue.enqueueReadBuffer(inputBuffer, CL_TRUE, 0, mapParams->dataSize, mapParams->result);

		// env->ReleaseIntArrayElements(data, body);
	}
	catch (cl::Error error)
	{
		std::cout << "Error" << std::endl;
		std::cout << error.what() << "(" << error.err() << ")" << std::endl;
	}
}

void TryUnion(std::string kernelUnionName, Union_t *unionParams)
{
	try
	{
		cl::Kernel kernelUnion = kernelsList[kernelUnionName];

		cl::Buffer dataBuffer(context, CL_MEM_READ_ONLY, unionParams->dataSize);
		cl::Buffer otherDataBuffer(context, CL_MEM_READ_ONLY, unionParams->otherDataSize);
		cl::Buffer resultBuffer(context, CL_MEM_WRITE_ONLY, unionParams->resultSize);

		commandQueue.enqueueWriteBuffer(dataBuffer, CL_TRUE, 0, unionParams->dataSize, unionParams->data);
		commandQueue.enqueueWriteBuffer(otherDataBuffer, CL_TRUE, 0, unionParams->otherDataSize, unionParams->otherData);

		kernelUnion.setArg(0, dataBuffer);
		kernelUnion.setArg(1, otherDataBuffer);
		kernelUnion.setArg(2, resultBuffer);
		kernelUnion.setArg(3, unionParams->dataLength);

		cl::NDRange global(unionParams->resultLength);

		commandQueue.enqueueNDRangeKernel(kernelUnion, cl::NullRange, global, cl::NullRange);

		commandQueue.enqueueReadBuffer(resultBuffer, CL_TRUE, 0, unionParams->resultSize, unionParams->result);
		// env->ReleaseDoubleArrayElements(data, body);
	}
	catch (cl::Error error)
	{
		std::cout << "Error" << std::endl;
		std::cout << error.what() << "(" << error.err() << ")" << std::endl;
	}
}

void TryTake(std::string kernelTakeName, Take_t *takeParams)
{
	try
	{
		cl::Kernel kernelTake = kernelsList[kernelTakeName];

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

JNIEXPORT jintArray JNICALL Java_ocl_Ocl_OclMap___3ILjava_lang_String_2Ljava_lang_String_2
  (JNIEnv *env, jobject obj, jintArray data, jstring kernelName, jstring kernelSource)
{
	Map_t mapParams;
	mapParams.dataLength = env->GetArrayLength(data);
	mapParams.dataSize = sizeof(int)*mapParams.dataLength;
	mapParams.data = env->GetIntArrayElements(data, 0);
	mapParams.result = new int[mapParams.dataLength];
	jintArray ret = env->NewIntArray(mapParams.dataLength);

	std::string kernelMapName = CreateKernelIfNotExists(env, kernelName, kernelSource, K_MAP);

	TryMap(kernelMapName, &mapParams);

	env->SetIntArrayRegion(ret, 0, mapParams.dataLength, (int *)mapParams.result);

	return ret;
}

JNIEXPORT jdoubleArray JNICALL Java_ocl_Ocl_OclMap___3DLjava_lang_String_2Ljava_lang_String_2
  (JNIEnv *env, jobject obj, jdoubleArray data, jstring kernelName, jstring kernelSource)
{
	Map_t mapParams;
	mapParams.dataLength = env->GetArrayLength(data);
	mapParams.dataSize = sizeof(double)*mapParams.dataLength;
	mapParams.data = env->GetDoubleArrayElements(data, 0);
	mapParams.result = new double[mapParams.dataLength];
	jdoubleArray ret = env->NewDoubleArray(mapParams.dataLength);

	std::string kernelMapName = CreateKernelIfNotExists(env, kernelName, kernelSource, K_MAP);

	TryMap(kernelMapName, &mapParams);

	env->SetDoubleArrayRegion(ret, 0, mapParams.dataLength, (double *)mapParams.result);
	return ret;
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

	std::string kernelTakeName = CreateKernelIfNotExists(env, env->NewStringUTF("takeInt"), kernelSource, K_TAKE);

	TryTake(kernelTakeName, &params);

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

	std::string kernelTakeName = CreateKernelIfNotExists(env, env->NewStringUTF("takeDouble"), kernelSource, K_TAKE);

	TryTake(kernelTakeName, &params);

	env->SetDoubleArrayRegion(ret, 0, params.nToTake, (double *)params.result);
	return ret;

}

JNIEXPORT jintArray JNICALL Java_ocl_Ocl_OclUnion___3I_3I
  (JNIEnv *env, jobject obj, jintArray data, jintArray otherData)
{
	Union_t params;
	params.dataLength = env->GetArrayLength(data);
	params.data = env->GetIntArrayElements(data, 0);
	params.dataSize = sizeof(int)*params.dataLength;

	params.otherDataLength = env->GetArrayLength(otherData);
	params.otherData = env->GetIntArrayElements(otherData, 0);
	params.otherDataSize = sizeof(int)*params.otherDataLength;

	params.resultLength = params.dataLength + params.otherDataLength;
	params.result = new int[params.resultLength];
	params.resultSize = sizeof(int)*params.resultLength;

	jintArray ret = env->NewIntArray(params.resultLength);

	jstring kernelSource = env->NewStringUTF("__kernel void unionInt(__global int* _data, __global int* _otherData , __global int* _result, __private int _dataLength)\n"
											"{\n"
											"\tint _gId = get_global_id(0);\n"
											"\tif (_gId < _dataLength)\n"
											"\t{\n"
											"\t\t_result[_gId] = _data[_gId];\n"
											"\t}\n"
											"\telse\n"
											"\t{\n"
											"\t\t_result[_gId] = _otherData[_gId - _dataLength];\n"
											"\t}\n"
											"\n"
											"}\n"
											"\n");

	std::string kernelUnionName = CreateKernelIfNotExists(env, env->NewStringUTF("unionInt"), kernelSource, K_UNION);

	TryUnion(kernelUnionName, &params);

	env->SetIntArrayRegion(ret, 0, params.resultLength, (int *)params.result);
	return ret;
}

JNIEXPORT jdoubleArray JNICALL Java_ocl_Ocl_OclUnion___3D_3D
  (JNIEnv *env, jobject jobj, jdoubleArray data, jdoubleArray otherData)
{
	Union_t params;
	params.dataLength = env->GetArrayLength(data);
	params.data = env->GetDoubleArrayElements(data, 0);
	params.dataSize = DOUBLE_SIZE*params.dataLength;

	params.otherDataLength = env->GetArrayLength(otherData);
	params.otherData = env->GetDoubleArrayElements(otherData, 0);
	params.otherDataSize = DOUBLE_SIZE*params.otherDataLength;

	params.resultLength = params.dataLength + params.otherDataLength;
	params.result = new double[params.resultLength];
	params.resultSize = DOUBLE_SIZE*params.resultLength;

	jdoubleArray ret = env->NewDoubleArray(params.resultLength);

	jstring kernelSource = env->NewStringUTF("__kernel void unionDouble(__global double* _data, __global double* _otherData , __global double* _result, __private int _dataLength)\n"
											"{\n"
											"\tint _gId = get_global_id(0);\n"
											"\tif (_gId < _dataLength)\n"
											"\t{\n"
											"\t\t_result[_gId] = _data[_gId];\n"
											"\t}\n"
											"\telse\n"
											"\t{\n"
											"\t\t_result[_gId] = _otherData[_gId - _dataLength];\n"
											"\t}\n"
											"\n"
											"}\n"
											"\n");

	std::string kernelUnionName = CreateKernelIfNotExists(env, env->NewStringUTF("unionDouble"), kernelSource, K_UNION);

	TryUnion(kernelUnionName, &params);

	env->SetDoubleArrayRegion(ret, 0, params.resultLength, (double *)params.result);
	return ret;
}
