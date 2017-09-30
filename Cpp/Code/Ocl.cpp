#define __CL_ENABLE_EXCEPTIONS


#include "ocl_Ocl.h"
#include <CL/cl.hpp>
#include <iostream>
#include <string>


std::vector<cl::Platform> platforms;
std::vector<cl::Device> devices;
cl::Device defaultDevice;
cl::Context context;
cl::CommandQueue commandQueue;

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

JNIEXPORT jintArray JNICALL Java_ocl_Ocl_OclMap
  (JNIEnv *env, jobject obj, jintArray data, jstring kernelSource)
{
	try
	{
		int i, sum = 0;
	    int len = env->GetArrayLength(data);
		size_t dataSize = sizeof(int)*len;
		int *body = env->GetIntArrayElements(data, 0);
		int *result = new int[len];

		std::cout << "/* Before */" << '\n';
	    for (i=0; i<len; i++)
		{
	        std::cout << "index:" << i << " elem:" << body[i] << '\n';
	    }
		std::string kernelCode=
				"__kernel void map(global const int* inputData)\n"
				"{\n"
				"    inputData[get_global_id(0)]*=10;\n"
				"}\n";

		cl::Program::Sources sources(1, std::make_pair(kernelCode.c_str(),kernelCode.length()+1));

		cl::Program program(context,sources);

		program.build(devices);

		cl::Kernel map(program, "map");

		cl::Buffer inputBuffer(context,CL_MEM_READ_WRITE,sizeof(int)*len);
		commandQueue.enqueueWriteBuffer(inputBuffer,CL_TRUE,0,dataSize,body);

		map.setArg(0, inputBuffer);

		cl::NDRange global(len);

		commandQueue.enqueueNDRangeKernel(map, cl::NullRange, global, cl::NullRange);

		commandQueue.enqueueReadBuffer(inputBuffer,CL_TRUE,0,dataSize,result);


		std::cout << "/* After */" << '\n';
	    for (i=0; i<len; i++)
		{
	        std::cout << "index:" << i << " elem:" << result[i] << std::endl;
	    }
	}
	catch (cl::Error error)
	{
		std::cout << "Error" << std::endl;
		std::cout << error.what() << "(" << error.err() << ")" << std::endl;
	}

	return NULL;
}


JNIEXPORT jintArray JNICALL Java_ocl_Ocl_OclMapToIntArray___3ILjava_lang_String_2Ljava_lang_String_2
  (JNIEnv *env, jobject obj, jintArray intArray, jstring a, jstring b)
{




}
