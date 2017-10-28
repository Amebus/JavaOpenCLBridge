
#define __CL_ENABLE_EXCEPTIONS
#include "../Headers/ocl_Ocl.h"
// #include "OclKernelParameters.cpp"
// #include "OclKernelInfo.cpp"
// #include "OclExecutor.cpp"
#include <CL/cl.hpp>
#include <iostream>
#include <unordered_map>

#define K_MAP 101
#define K_UNION 102

#define K_TAKE 201

#define INT_TYPE 302
#define DOUBLE_TYPE 303

#define INT_SIZE sizeof(int)
#define DOUBLE_SIZE sizeof(double)

const char* unionIntName = "unionInt";
const char* unionIntSource = "__kernel void unionInt(__global int* _data, __global int* _otherData , __global int* _result, __private int _dataLength)\n"
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
										"\n";

const char* unionDoubleName = "unionDouble";
const char* unionDoubleSource = "__kernel void unionDouble(__global double* _data, __global double* _otherData , __global double* _result, __private int _dataLength)\n"
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
										"\n";

const char* takeIntName = "takeInt";
const char* takeIntSource = "__kernel void takeInt(__global int* _data, __global int* _result)\n"
										"{\n"
										"\tint _gId = get_global_id(0);\n"
										"\t_result[_gId] = _data[_gId];"
										"\n"
										"}"
										"\n";

const char* takeDoubleName = "takeDouble";
const char* takeDoubleSource = "__kernel void takeDouble(__global double* _data, __global double* _result)\n"
										"{\n"
										"\tint _gId = get_global_id(0);\n"
										"\t_result[_gId] = _data[_gId];"
										"\n"
										"}"
										"\n";

std::unordered_map<std::string, cl::Kernel> kernelsList;
std::vector<cl::Platform> platforms;
std::vector<cl::Device> devices;
cl::Device defaultDevice;
cl::Context context;
cl::CommandQueue commandQueue;

typedef struct
{
	int length;
	void *data;
	size_t size;
} Tern_t;

typedef struct
{
	Tern_t* data;
	Tern_t* result;
} MapParameters_t;

typedef struct
{
	Tern_t* data;
	Tern_t* otherData;
	Tern_t* result;
} UnionParameters_t;

typedef struct
{
	Tern_t* data;
	Tern_t* result;
} TakeParameters_t;

void CreateResultData(Tern_t* resultTern, int dataType)
{
	switch (dataType)
	{
		case INT_TYPE:
		resultTern->data = new int[resultTern->length];
		break;
		case DOUBLE_TYPE:
		resultTern->data = new double[resultTern->length];
		break;
	}
}

Tern_t* CreateDataTern (JNIEnv *env, void* data, int dataType)
{
	Tern_t* tern = new Tern_t;
	tern->length = env->GetArrayLength((jarray)data);

	switch (dataType)
	{
		case INT_TYPE:
			tern->size = INT_SIZE*tern->length;
			tern->data = env->GetIntArrayElements((jintArray)data, 0);
			break;
		case DOUBLE_TYPE:
			tern->size = DOUBLE_SIZE*tern->length;
			tern->data = env->GetDoubleArrayElements((jdoubleArray)data, 0);
			break;
	}

}

MapParameters_t* CreateMapParams(JNIEnv *env, void* data, int dataType)
{
	MapParameters_t* params = new MapParameters_t;
	Tern_t* tern = new Tern_t;

	params->data = CreateDataTern(env, data, dataType);

	tern->length = params->data->length;
	tern->size = params->data->size;

	CreateResultData(tern, dataType);

	params->result = tern;
	return params;
}

UnionParameters_t* CreateUnionParams(JNIEnv *env, void* data, void* otherData, int dataType)
{
	UnionParameters_t* params = new UnionParameters_t;
	Tern_t* tern = new Tern_t;

	params->data=CreateDataTern(env, data, dataType);
	params->otherData=CreateDataTern(env, otherData, dataType);

	tern->length = params->data->length + params->otherData->length;
	tern->size = params->data->size + params->otherData->size;

	CreateResultData(tern, dataType);

	params->result = tern;
	return params;
}

TakeParameters_t* CreateTakeParams(JNIEnv *env, void* data, int nToTake, int dataType)
{
	TakeParameters_t* params = new TakeParameters_t;
	Tern_t* tern = new Tern_t;

	params->data = CreateDataTern(env, data, dataType);
	tern->length = nToTake;

	switch (dataType) {
		case INT_TYPE:
			tern->size = INT_SIZE*nToTake;
			tern->data = new int[nToTake];
			break;
		case DOUBLE_TYPE:
			tern->size = DOUBLE_SIZE*nToTake;
			tern->data = new double[nToTake];
			break;
	}

	params->result = tern;
	return params;
}

typedef struct
{
	int kernelType;

	const char* kName;
	std::string kernelNameCpp;
	int kernelNameLength;

	const char* kSource;
	std::string kernelSourceCpp;
	int kernelSourceLength;

	void* kParams;

} OclKernelInfo_t;

OclKernelInfo_t* CreateKernelInfo(const char* name, int type, void* kParams)
{
	OclKernelInfo_t* kInfo = new OclKernelInfo_t;

	kInfo->kName = name;
	kInfo->kernelType = type;
	kInfo->kernelNameCpp = std::string(name);
	kInfo->kernelNameLength = kInfo->kernelNameCpp.length();
	kInfo->kParams = kParams;

	return kInfo;
}

void SetKernelSource(OclKernelInfo_t* kInfo, const char* source)
{
	kInfo->kSource = source;
	kInfo->kernelSourceCpp = std::string(source);
	kInfo->kernelSourceLength = kInfo->kernelSourceCpp.length();
}


void PrintClError(cl::Error error)
{
	std::cout << "Error" << std::endl;
	std::cout << error.what() << "(" << error.err() << ")" << std::endl;
}

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
}

void PrintTakeParams(TakeParameters_t* takeParams)
{
	std::cout << "takeParams->data->length" << takeParams->data->length << '\n';
	std::cout << "takeParams->result->length" << takeParams->result->length << '\n';
}

void PrintKInfo(OclKernelInfo_t* kInfo)
{
	std::cout << "kInfo->kName: " << kInfo->kName << '\n';
	std::cout << "kInfo->kernelType: " << kInfo->kernelType << '\n';
	std::cout << "kInfo->kernelNameCpp: " << kInfo->kernelNameCpp << '\n';
	std::cout << "kInfo->kernelNameLength" << kInfo->kernelNameLength << '\n';
	std::cout << "kInfo->kParams: " << kInfo->kParams << '\n';
	std::cout << "kInfo->kSource" << kInfo->kSource << '\n';
}

void CreateStdKernel(OclKernelInfo_t* kInfo)
{
	cl::Program::Sources sources(1, std::make_pair( kInfo->kSource, kInfo->kernelSourceLength+1));
	cl::Program program(context, sources);

	program.build(devices);
	kernelsList[kInfo->kernelNameCpp] = cl::Kernel(program, kInfo->kName);
}

std::string CreateKernelIfNotExists(OclKernelInfo_t* kInfo)
{
	std::string kernelNameCpp = kInfo->kernelNameCpp;
	std::unordered_map<std::string,cl::Kernel>::const_iterator iter = kernelsList.find(kernelNameCpp);
	if(iter == kernelsList.end())
	{
		switch (kInfo->kernelType)
		{
			case K_MAP:
			case K_TAKE:
			case K_UNION:
				CreateStdKernel(kInfo);
				break;
		}
	}

	return kernelNameCpp;
}

void Map(OclKernelInfo_t* kInfo)
{
	try
	{
		MapParameters_t* mapParams = (MapParameters_t*) kInfo->kParams;

		cl::Kernel kernelMap = kernelsList[CreateKernelIfNotExists(kInfo)];

		cl::Buffer inputBuffer(context, CL_MEM_READ_WRITE, mapParams->data->size);
		commandQueue.enqueueWriteBuffer(inputBuffer, CL_TRUE, 0, mapParams->data->size, mapParams->data->data);

		kernelMap.setArg(0, inputBuffer);

		cl::NDRange global(mapParams->result->length);

		commandQueue.enqueueNDRangeKernel(kernelMap, cl::NullRange, global, cl::NullRange);

		commandQueue.enqueueReadBuffer(inputBuffer, CL_TRUE, 0, mapParams->result->size, mapParams->result->data);

		// env->ReleaseIntArrayElements(data, body);
	}
	catch (cl::Error error)
	{
		PrintClError(error);
	}
}

void Union(OclKernelInfo_t* kInfo)
{
	try
	{
		UnionParameters_t* unionParams = (UnionParameters_t*) kInfo->kParams;

		cl::Kernel kernelUnion = kernelsList[CreateKernelIfNotExists(kInfo)];

		cl::Buffer dataBuffer(context, CL_MEM_READ_ONLY, unionParams->data->size);
		cl::Buffer otherDataBuffer(context, CL_MEM_READ_ONLY, unionParams->otherData->size);
		cl::Buffer resultBuffer(context, CL_MEM_WRITE_ONLY, unionParams->result->size);

		commandQueue.enqueueWriteBuffer(dataBuffer, CL_TRUE, 0, unionParams->data->size, unionParams->data->data);
		commandQueue.enqueueWriteBuffer(otherDataBuffer, CL_TRUE, 0, unionParams->otherData->size, unionParams->otherData->data);

		kernelUnion.setArg(0, dataBuffer);
		kernelUnion.setArg(1, otherDataBuffer);
		kernelUnion.setArg(2, resultBuffer);
		kernelUnion.setArg(3, unionParams->data->length);

		cl::NDRange global(unionParams->result->length);

		commandQueue.enqueueNDRangeKernel(kernelUnion, cl::NullRange, global, cl::NullRange);

		commandQueue.enqueueReadBuffer(resultBuffer, CL_TRUE, 0, unionParams->result->size, unionParams->result->data);
		// env->ReleaseDoubleArrayElements(data, body);
	}
	catch (cl::Error error)
	{
		PrintClError(error);
	}
}

void Take(OclKernelInfo_t* kInfo)
{
	try
	{
		TakeParameters_t* takeParams = (TakeParameters_t*) kInfo->kParams;

		cl::Kernel kernelTake = kernelsList[CreateKernelIfNotExists(kInfo)];

		cl::Buffer dataBuffer(context, CL_MEM_READ_ONLY, takeParams->data->size);
		cl::Buffer resultBuffer(context, CL_MEM_WRITE_ONLY, takeParams->result->size);

		commandQueue.enqueueWriteBuffer(dataBuffer, CL_TRUE, 0, takeParams->data->size, takeParams->data->data);

		kernelTake.setArg(0, dataBuffer);
		kernelTake.setArg(1, resultBuffer);

		cl::NDRange global(takeParams->result->length);

		commandQueue.enqueueNDRangeKernel(kernelTake, cl::NullRange, global, cl::NullRange);

		commandQueue.enqueueReadBuffer(resultBuffer, CL_TRUE, 0, takeParams->result->size, takeParams->result->data);
		// env->ReleaseDoubleArrayElements(data, body);
	}
	catch (cl::Error error)
	{
		PrintClError(error);
	}
}

JNIEXPORT jintArray JNICALL Java_ocl_Ocl_OclMap___3ILjava_lang_String_2Ljava_lang_String_2
  (JNIEnv *env, jobject obj, jintArray data, jstring kernelName, jstring kernelSource)
{
	const char *kName = env->GetStringUTFChars(kernelName, NULL);
	const char *kSource = env->GetStringUTFChars(kernelSource, NULL);
	MapParameters_t* mapParams = CreateMapParams(env, data, INT_TYPE);
	OclKernelInfo_t* kInfo = CreateKernelInfo(kName, K_MAP, mapParams);
	SetKernelSource(kInfo, kSource);

	Map(kInfo);

	env->ReleaseStringUTFChars(kernelName, kName);
	env->ReleaseStringUTFChars(kernelSource, kSource);

	jintArray ret = env->NewIntArray(mapParams->result->length);
	env->SetIntArrayRegion(ret, 0, mapParams->result->length, (int *)mapParams->result->data);
	return ret;
}

JNIEXPORT jdoubleArray JNICALL Java_ocl_Ocl_OclMap___3DLjava_lang_String_2Ljava_lang_String_2
  (JNIEnv *env, jobject obj, jdoubleArray data, jstring kernelName, jstring kernelSource)
{
	const char *kName = env->GetStringUTFChars(kernelName, NULL);
	const char *kSource = env->GetStringUTFChars(kernelSource, NULL);
	MapParameters_t* mapParams = CreateMapParams(env, data, DOUBLE_TYPE);
	OclKernelInfo_t* kInfo = CreateKernelInfo(kName, K_MAP, mapParams);
	SetKernelSource(kInfo, kSource);

	Map(kInfo);

	env->ReleaseStringUTFChars(kernelName, kName);
	env->ReleaseStringUTFChars(kernelSource, kSource);

	jdoubleArray ret = env->NewDoubleArray(mapParams->result->length);
	env->SetDoubleArrayRegion(ret, 0, mapParams->result->length, (double *)mapParams->result->data);
	return ret;
}

JNIEXPORT jintArray JNICALL Java_ocl_Ocl_OclTake___3II
  (JNIEnv *env, jobject obj, jintArray data, jint nToTake)
{
	TakeParameters_t* takeParams = CreateTakeParams(env, data, nToTake, INT_TYPE);
	OclKernelInfo_t* kInfo = CreateKernelInfo(takeIntName, K_TAKE, takeParams);
	SetKernelSource(kInfo, takeIntSource);

	Take(kInfo);

	jintArray ret = env->NewIntArray(nToTake);
	env->SetIntArrayRegion(ret, 0, takeParams->result->length, (int *)takeParams->result->data);
	return ret;

}

JNIEXPORT jdoubleArray JNICALL Java_ocl_Ocl_OclTake___3DI
  (JNIEnv *env, jobject obj, jdoubleArray data, jint nToTake)
{
	TakeParameters_t* takeParams = CreateTakeParams(env, data, nToTake, DOUBLE_TYPE);
	OclKernelInfo_t* kInfo = CreateKernelInfo(takeDoubleName, K_TAKE, takeParams);
	SetKernelSource(kInfo, takeDoubleSource);

	Take(kInfo);

	jdoubleArray ret = env->NewDoubleArray(nToTake);
	env->SetDoubleArrayRegion(ret, 0, takeParams->result->length, (double *)takeParams->result->data);
	return ret;
}

JNIEXPORT jintArray JNICALL Java_ocl_Ocl_OclUnion___3I_3I
  (JNIEnv *env, jobject obj, jintArray data, jintArray otherData)
{
	UnionParameters_t* unionParams = CreateUnionParams(env, data, otherData, INT_TYPE);
	OclKernelInfo_t* kInfo = CreateKernelInfo(unionIntName, K_TAKE, unionParams);
	SetKernelSource(kInfo, unionIntSource);

	Union(kInfo);

	jintArray ret = env->NewIntArray(unionParams->result->length);
	env->SetIntArrayRegion(ret, 0, unionParams->result->length, (int *)unionParams->result->data);
	return ret;
}

JNIEXPORT jdoubleArray JNICALL Java_ocl_Ocl_OclUnion___3D_3D
  (JNIEnv *env, jobject jobj, jdoubleArray data, jdoubleArray otherData)
{
	UnionParameters_t* unionParams = CreateUnionParams(env, data, otherData, DOUBLE_TYPE);
	OclKernelInfo_t* kInfo = CreateKernelInfo(unionDoubleName, K_TAKE, unionParams);
	SetKernelSource(kInfo, unionDoubleSource);

	Union(kInfo);

	jdoubleArray ret = env->NewDoubleArray(unionParams->result->length);
	env->SetDoubleArrayRegion(ret, 0, unionParams->result->length, (double *)unionParams->result->data);
	return ret;
}
