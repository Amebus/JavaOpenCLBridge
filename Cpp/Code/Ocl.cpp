#define __CL_ENABLE_EXCEPTIONS


#include "ocl_Ocl.h"
#include <CL/cl.hpp>



std::vector<cl::Platform> platforms;
std::vector<cl::Device> devices;
cl::Context context;
cl::CommandQueue commandQueue;

JNIEXPORT void JNICALL Java_ocl_Ocl_Open
  (JNIEnv *env, jobject obj)
{

	cl::Platform::get(&platforms);
	platforms[0].getDevices(CL_DEVICE_TYPE_ALL, &devices);

	//Create a context for the devices
	context = cl::Context(devices);

	commandQueue = cl::CommandQueue(context, devices[0]);

}

JNIEXPORT void JNICALL Java_ocl_Ocl_Close
  (JNIEnv *, jobject)
{
	commandQueue = NULL;

	context = NULL;
}
