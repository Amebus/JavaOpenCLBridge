#define __CL_ENABLE_EXCEPTIONS
#include "../Headers/oclBridge_AbstractOclBridge.h"
#include "../Headers/OclUtility.h"
//#include <CL/cl.hpp>
#include <cstdio>
#include <sstream>
#include <iomanip>
#include <vector>
#include <CL/cl.h>
#include <iostream>
#include <unordered_map>

void printStatus(const cl_int status, const int line)
{
    if (status != CL_SUCCESS)
    {
        std::stringstream msg;
        msg << "Line " << std::setw(4) << line << ": ";
        switch (status)
        {
        case CL_DEVICE_NOT_FOUND:
            msg << "Device not found.";
            break;
        case CL_DEVICE_NOT_AVAILABLE:
            msg << "Device not available";
            break;
        case CL_COMPILER_NOT_AVAILABLE:
            msg << "Compiler not available";
            break;
        case CL_MEM_OBJECT_ALLOCATION_FAILURE:
            msg << "Memory object allocation failure";
            break;
        case CL_OUT_OF_RESOURCES:
            msg << "Out of resources";
            break;
        case CL_OUT_OF_HOST_MEMORY:
            msg << "Out of host memory";
            break;
        case CL_PROFILING_INFO_NOT_AVAILABLE:
            msg << "Profiling information not available";
            break;
        case CL_MEM_COPY_OVERLAP:
            msg << "Memory copy overlap";
            break;
        case CL_IMAGE_FORMAT_MISMATCH:
            msg << "Image format mismatch";
            break;
        case CL_IMAGE_FORMAT_NOT_SUPPORTED:
            msg << "Image format not supported";
            break;
        case CL_BUILD_PROGRAM_FAILURE:
            msg << "Program build failure";
            break;
        case CL_MAP_FAILURE:
            msg << "Map failure";
            break;
        case CL_INVALID_VALUE:
            msg << "Invalid value";
            break;
        case CL_INVALID_DEVICE_TYPE:
            msg << "Invalid device type";
            break;
        case CL_INVALID_PLATFORM:
            msg << "Invalid platform";
            break;
        case CL_INVALID_DEVICE:
            msg << "Invalid device";
            break;
        case CL_INVALID_CONTEXT:
            msg << "Invalid context";
            break;
        case CL_INVALID_QUEUE_PROPERTIES:
            msg << "Invalid queue properties";
            break;
        case CL_INVALID_COMMAND_QUEUE:
            msg << "Invalid command queue";
            break;
        case CL_INVALID_HOST_PTR:
            msg << "Invalid host pointer";
            break;
        case CL_INVALID_MEM_OBJECT:
            msg << "Invalid memory object";
            break;
        case CL_INVALID_IMAGE_FORMAT_DESCRIPTOR:
            msg << "Invalid image format descriptor";
            break;
        case CL_INVALID_IMAGE_SIZE:
            msg << "Invalid image size";
            break;
        case CL_INVALID_SAMPLER:
            msg << "Invalid sampler";
            break;
        case CL_INVALID_BINARY:
            msg << "Invalid binary";
            break;
        case CL_INVALID_BUILD_OPTIONS:
            msg << "Invalid build options";
            break;
        case CL_INVALID_PROGRAM:
            msg << "Invalid program";
            break;
        case CL_INVALID_PROGRAM_EXECUTABLE:
            msg << "Invalid program executable";
            break;
        case CL_INVALID_KERNEL_NAME:
            msg << "Invalid kernel name";
            break;
        case CL_INVALID_KERNEL_DEFINITION:
            msg << "Invalid kernel definition";
            break;
        case CL_INVALID_KERNEL:
            msg << "Invalid kernel";
            break;
        case CL_INVALID_ARG_INDEX:
            msg << "Invalid argument index";
            break;
        case CL_INVALID_ARG_VALUE:
            msg << "Invalid argument value";
            break;
        case CL_INVALID_ARG_SIZE:
            msg << "Invalid argument size";
            break;
        case CL_INVALID_KERNEL_ARGS:
            msg << "Invalid kernel arguments";
            break;
        case CL_INVALID_WORK_DIMENSION:
            msg << "Invalid work dimension";
            break;
        case CL_INVALID_WORK_GROUP_SIZE:
            msg << "Invalid work group size";
            break;
        case CL_INVALID_WORK_ITEM_SIZE:
            msg << "Invalid work item size";
            break;
        case CL_INVALID_GLOBAL_OFFSET:
            msg << "Invalid global offset";
            break;
        case CL_INVALID_EVENT_WAIT_LIST:
            msg << "Invalid event wait list";
            break;
        case CL_INVALID_EVENT:
            msg << "Invalid event";
            break;
        case CL_INVALID_OPERATION:
            msg << "Invalid operation";
            break;
        case CL_INVALID_GL_OBJECT:
            msg << "Invalid OpenGL object";
            break;
        case CL_INVALID_BUFFER_SIZE:
            msg << "Invalid buffer size";
            break;
        case CL_INVALID_MIP_LEVEL:
            msg << "Invalid mip-map level";
            break;
        default:
            msg << "Unknown";
            break;
        }
        msg << "\n";
        std::cout << msg.str().c_str();
    }
}

#define printStatus(status) printStatus(status, __LINE__)

std::unordered_map<std::string, cl_kernel> gKernelsList;
std::vector<cl_platform_id> gPlatforms;
std::vector<cl_device_id> gDevices;

cl_platform_id gPlatform;
cl_device_id gDefaultDevice;
cl_context gContext;
cl_command_queue gCommandQueue;
cl_int gStatus;


JNIEXPORT void Java_oclBridge_AbstractOclBridge_ListDevices(JNIEnv *pEnv, jobject pObj)
{
    cl_uint numPlatforms = 0;
    gStatus = CL_SUCCESS;

    // Get (in numPlatforms) the number of OpenCL platforms available
    // No platform ID will be return, since platforms is NULL
    gStatus = clGetPlatformIDs(0, NULL, &numPlatforms);
	printStatus(gStatus);

	std::cout << "numPlatforms:" << numPlatforms << '\n' << '\n';

	std::vector<cl_platform_id> platforms(numPlatforms);

    // Now, obtains a list of numPlatforms OpenCL platforms available
    // The list of platforms available will be returned in platforms
    gStatus = clGetPlatformIDs(numPlatforms, &platforms[0], NULL);
	printStatus(gStatus);

	size_t stringLength = 0;
	for (cl_uint i = 0; i < numPlatforms; i++)
    {
		cl_platform_id platformId = platforms[i];
		cl_uint numDevices = 0;

		// In order to read the platform's name, we first read the platform's name string length (param_value is NULL).
		// The value returned in stringLength
		gStatus = clGetPlatformInfo(platformId, CL_PLATFORM_NAME, 0, NULL, &stringLength);
		printStatus(gStatus);

		// Now, that we know the platform's name string length, we can allocate enough space before read it
	    std::vector<char> platformName(stringLength);

		// Read the platform's name string
	    // The read value returned in platformName
	    gStatus = clGetPlatformInfo(platformId, CL_PLATFORM_NAME, stringLength, &platformName[0], NULL);
		printStatus(gStatus);

		std::cout << "\tplatformName: ";

		for (size_t i = 0; i < stringLength - 1; i++) {
			std::cout << platformName[i];
		}

		std::cout << '\n';

		// Obtains the number of deviceType devices available on platform
		// When the function failed we expect numDevices to be zero.
		// We ignore the function return value since a non-zero error code
		// could happen if this platform doesn't support the specified device type.
		gStatus = clGetDeviceIDs(platformId, CL_DEVICE_TYPE_ALL , 0, NULL, &numDevices);
		printStatus(gStatus);

		std::vector<cl_device_id> devices(numDevices);

		gStatus = clGetDeviceIDs(platformId, CL_DEVICE_TYPE_ALL , numDevices, &devices[0], NULL);
		printStatus(gStatus);

		gStatus = clGetPlatformInfo(platformId, CL_PLATFORM_VERSION, 0, NULL, &stringLength);
		printStatus(gStatus);

		// Now, that we know the platform's version string length, we can allocate enough space before read it
	    std::vector<char> platformVersion(stringLength);

		// Read the platform's version string
	    // The read value returned in platformVersion
	    gStatus = clGetPlatformInfo(platformId, CL_PLATFORM_VERSION, stringLength, &platformVersion[0], NULL);
		printStatus(gStatus);

		std::cout << "\tplatformVersion: ";

		for (size_t i = 0; i < stringLength - 1; i++) {
			std::cout << platformVersion[i];
		}

		std::cout << '\n';

		std::cout << "\t devices: " << numDevices << '\n';

		for (size_t j = 0; j < numDevices; j++) {
			cl_device_id device = devices[j];
			// Read the device's version string length (param_value is NULL).
		    gStatus = clGetDeviceInfo(device, CL_DEVICE_VERSION, 0, NULL, &stringLength);
			printStatus(gStatus);

			std::vector<char> version(stringLength);

			// Read the device's version string
		    // The read value returned in deviceVersion
		    gStatus = clGetDeviceInfo(device, CL_DEVICE_VERSION, stringLength, &version[0], NULL);
			printStatus(gStatus);

			std::cout << "\t\tdeviceVersion: ";

			for (size_t i = 0; i < stringLength - 1; i++) {
				std::cout << version[i];
			}

			std::cout << '\n';

			// Read the device's OpenCL C version string length (param_value is NULL).
		    gStatus = clGetDeviceInfo(device, CL_DEVICE_OPENCL_C_VERSION, 0, NULL, &stringLength);
			printStatus(gStatus);

			// Now, that we know the device's OpenCL C version string length, we can allocate enough space before read it
			std::vector<char> compilerVersion(stringLength);

			// Read the device's OpenCL C version string
			// The read value returned in compilerVersion
			gStatus = clGetDeviceInfo(device, CL_DEVICE_OPENCL_C_VERSION, stringLength, &compilerVersion[0], NULL);
			printStatus(gStatus);

			std::cout << "\t\tdeviceCompilerVersion: ";

			for (size_t i = 0; i < stringLength - 1; i++) {
				std::cout << compilerVersion[i];
			}

			std::cout << '\n';

		}


		std::cout << '\n';
	}
}

JNIEXPORT void Java_oclBridge_AbstractOclBridge_Initialize(JNIEnv *pEnv, jobject pObj, jstring pKernelName)
{
    //TODO improve to accepet external parameters
    gStatus = clGetPlatformIDs(1, &gPlatform, NULL);
    printStatus(gStatus);
    gStatus = clGetDeviceIDs(gPlatform, CL_DEVICE_TYPE_ALL, 1, &gDefaultDevice, NULL);
    printStatus(gStatus);
    gContext = clCreateContext(NULL, 1, &gDefaultDevice, NULL, NULL, &gStatus);
    printStatus(gStatus);
    gCommandQueue = clCreateCommandQueueWithProperties(gContext, gDefaultDevice, 0, &gStatus);
    printStatus(gStatus);
}

JNIEXPORT void Java_oclBridge_AbstractOclBridge_Dispose(JNIEnv *pEnv, jobject pObj)
{
    //delete kernelsList;
    //delete platforms;
    //delete devices;

    clReleaseCommandQueue(gCommandQueue);
    clReleaseContext(gContext);
}