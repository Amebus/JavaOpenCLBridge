#define __CL_ENABLE_EXCEPTIONS


#include "ocl_Ocl.h"
#include <CL/cl.hpp>
#include <iostream>
#include <string>
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



	// for (size_t i = 0; i < devices.size(); i++) {
	// 	devices[i]
	// }

	//Create a context for the devices
	context = cl::Context(defaultDevice);

	commandQueue = cl::CommandQueue(context, defaultDevice);

}

JNIEXPORT void JNICALL Java_ocl_Ocl_Close
  (JNIEnv *, jobject obj)
{
	commandQueue = NULL;

	context = NULL;
}

cl::Kernel GetKernel(JNIEnv *env, jstring kernelName, jstring kernelSource)
{
	const char *kName = env->GetStringUTFChars(kernelName, NULL);
	const char *kSource;
	std::string kernelNameCpp = std::string(kName);
	cl::Kernel kernel;
	std::unordered_map<std::string,cl::Kernel>::const_iterator iter = kernelsList.find(kernelNameCpp);
	if(iter != kernelsList.end())
	{
		kernel = kernelsList[kernelNameCpp];
	}
	else
	{
		kSource = env->GetStringUTFChars(kernelSource, NULL);
		std::string kernelCode = std::string(kSource);

		cl::Program::Sources sources(1, std::make_pair(kernelCode.c_str(),kernelCode.length()+1));
		cl::Program program(context,sources);

		program.build(devices);
		kernel = cl::Kernel(program, kName);

		kernelsList[kernelNameCpp] = kernel;
		env->ReleaseStringUTFChars(kernelSource, kSource);
	}
	env->ReleaseStringUTFChars(kernelName, kName);

	return kernel;
}

JNIEXPORT jintArray JNICALL Java_ocl_Ocl_OclMap
  (JNIEnv *env, jobject obj, jintArray data, jstring kernelName, jstring kernelSource)
{

	int i, sum = 0;
    int len = env->GetArrayLength(data);
	size_t dataSize = sizeof(int)*len;
	int *body = env->GetIntArrayElements(data, 0);
	int *result = new int[len];
	jintArray ret = env->NewIntArray(len);

	try
	{
		cl::Kernel map = GetKernel(env, kernelName, kernelSource);

		cl::Buffer inputBuffer(context,CL_MEM_READ_WRITE,sizeof(int)*len);
		commandQueue.enqueueWriteBuffer(inputBuffer,CL_TRUE,0,dataSize,body);

		map.setArg(0, inputBuffer);

		cl::NDRange global(len);

		commandQueue.enqueueNDRangeKernel(map, cl::NullRange, global, cl::NullRange);

		commandQueue.enqueueReadBuffer(inputBuffer,CL_TRUE,0,dataSize,result);

		env->SetIntArrayRegion(ret, 0, len, result);

	}
	catch (cl::Error error)
	{
		std::cout << "Error" << std::endl;
		std::cout << error.what() << "(" << error.err() << ")" << std::endl;
	}
	return ret;
}


JNIEXPORT jintArray JNICALL Java_ocl_Ocl_OclMapToIntArray___3ILjava_lang_String_2Ljava_lang_String_2
  (JNIEnv *env, jobject obj, jintArray intArray, jstring a, jstring b)
{




}
