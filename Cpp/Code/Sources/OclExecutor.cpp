#define __CL_ENABLE_EXCEPTIONS
#include <unordered_map>
#include <iostream>
#include <CL/cl.hpp>
#include <jni.h>

// #include "OclKernelParameters.cpp"
// #include "OclKernelInfo.cpp"
// #include <CL/cl.hpp>

#ifndef OclExecutor_CPP
#define OclExecutor_CPP

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

// exec::OclExecutor::OclExecutor ()
// {
// 	cl::Platform::get(&platforms);
//
// 	platforms[0].getDevices(CL_DEVICE_TYPE_ALL, &devices);
//
// 	defaultDevice = devices[0];
//
// 	context = cl::Context(defaultDevice);
//
// 	commandQueue = cl::CommandQueue(context, defaultDevice);
// }
//
//
// exec::OclExecutor::~OclExecutor ()
// {
//
// }
//
// void exec::OclExecutor::PrintClError(cl::Error error)
// {
// 	std::cout << "Error" << std::endl;
// 	std::cout << error.what() << "(" << error.err() << ")" << std::endl;
// }
//
// void exec::OclExecutor::CreateStdKernel(OclKernelInfo& kInfo)
// {
// 	cl::Program::Sources sources(1, std::make_pair( kInfo.GetKernelSource(), kInfo.GetKernelSourceLength()+1));
// 	cl::Program program(context,sources);
//
// 	program.build(devices);
// 	kernelsList[kInfo.GetKernelNameCpp()] = cl::Kernel(program, kInfo.GetKernelName());
// }
//
// std::string exec::OclExecutor::CreateKernelIfNotExists(OclKernelInfo& kInfo)
// {
// 	std::string kernelNameCpp = kInfo.GetKernelNameCpp();
// 	std::unordered_map<std::string,cl::Kernel>::const_iterator iter = kernelsList.find(kernelNameCpp);
// 	if(iter == kernelsList.end())
// 	{
// 		switch (kInfo.GetKernelType())
// 		{
// 			case K_MAP:
// 			case K_TAKE:
// 			case K_UNION:
// 				CreateStdKernel(kInfo);
// 				break;
// 		}
// 	}
//
// 	return kernelNameCpp;
// }
//
// void exec::OclExecutor::Map(OclKernelInfo& kInfo)
// {
// 	try
// 	{
// 		exec::MapParameters* mapParams = (exec::MapParameters*) kInfo.GetKernelParams();
//
// 		void* result = mapParams->GetResult();
//
// 		cl::Kernel kernelMap = kernelsList[CreateKernelIfNotExists(kInfo)];
//
// 		cl::Buffer inputBuffer(context, CL_MEM_READ_WRITE, mapParams->GetDataSize());
// 		commandQueue.enqueueWriteBuffer(inputBuffer, CL_TRUE, 0, mapParams->GetDataSize(), mapParams->GetData());
//
// 		kernelMap.setArg(0, inputBuffer);
//
// 		cl::NDRange global(mapParams->GetDataLength());
//
// 		commandQueue.enqueueNDRangeKernel(kernelMap, cl::NullRange, global, cl::NullRange);
//
// 		commandQueue.enqueueReadBuffer(inputBuffer, CL_TRUE, 0, mapParams->GetResultSize(), result);
//
// 		// env->ReleaseIntArrayElements(data, body);
// 	}
// 	catch (cl::Error error)
// 	{
// 		this->PrintClError(error);
// 	}
// }
//
// void exec::OclExecutor::Union(OclKernelInfo& kInfo)
// {
// 	try
// 	{
// 		exec::UnionParameters* unionParams = (exec::UnionParameters*) kInfo.GetKernelParams();
// 		void* result = unionParams->GetResult();
//
// 		cl::Kernel kernelUnion = kernelsList[CreateKernelIfNotExists(kInfo)];
//
// 		cl::Buffer dataBuffer(context, CL_MEM_READ_ONLY, unionParams->GetDataSize());
// 		cl::Buffer otherDataBuffer(context, CL_MEM_READ_ONLY, unionParams->GetOtherDataSize());
// 		cl::Buffer resultBuffer(context, CL_MEM_WRITE_ONLY, unionParams->GetResultSize());
//
// 		commandQueue.enqueueWriteBuffer(dataBuffer, CL_TRUE, 0, unionParams->GetDataSize(), unionParams->GetData());
// 		commandQueue.enqueueWriteBuffer(otherDataBuffer, CL_TRUE, 0, unionParams->GetOtherDataSize(), unionParams->GetOtherData());
//
// 		kernelUnion.setArg(0, dataBuffer);
// 		kernelUnion.setArg(1, otherDataBuffer);
// 		kernelUnion.setArg(2, resultBuffer);
// 		kernelUnion.setArg(3, unionParams->GetDataLength());
//
// 		cl::NDRange global(unionParams->GetResultLength());
//
// 		commandQueue.enqueueNDRangeKernel(kernelUnion, cl::NullRange, global, cl::NullRange);
//
// 		commandQueue.enqueueReadBuffer(resultBuffer, CL_TRUE, 0, unionParams->GetResultSize(), result);
// 		// env->ReleaseDoubleArrayElements(data, body);
// 	}
// 	catch (cl::Error error)
// 	{
// 		this->PrintClError(error);
// 	}
// }
//
// void exec::OclExecutor::Take(OclKernelInfo& kInfo)
// {
// 	try
// 	{
// 		exec::TakeParameters* takeParams = (exec::TakeParameters*) kInfo.GetKernelParams();
// 		void* result = takeParams->GetResult();
//
// 		cl::Kernel kernelTake = kernelsList[CreateKernelIfNotExists(kInfo)];
//
// 		cl::Buffer dataBuffer(context, CL_MEM_READ_ONLY, takeParams->GetDataSize());
// 		cl::Buffer resultBuffer(context, CL_MEM_WRITE_ONLY, takeParams->GetResultSize());
//
// 		commandQueue.enqueueWriteBuffer(dataBuffer, CL_TRUE, 0, takeParams->GetDataSize(), takeParams->GetData());
//
// 		kernelTake.setArg(0, dataBuffer);
// 		kernelTake.setArg(1, resultBuffer);
//
// 		cl::NDRange global(takeParams->GetResultLength());
//
// 		commandQueue.enqueueNDRangeKernel(kernelTake, cl::NullRange, global, cl::NullRange);
//
// 		commandQueue.enqueueReadBuffer(resultBuffer, CL_TRUE, 0, takeParams->GetResultSize(), result);
// 		// env->ReleaseDoubleArrayElements(data, body);
// 	}
// 	catch (cl::Error error)
// 	{
// 		this->PrintClError(error);
// 	}
// }

#define K_MAP 101
#define K_UNION 102

#define K_TAKE 201

#define INT_TYPE 302
#define DOUBLE_TYPE 303

#define INT_SIZE sizeof(int)
#define DOUBLE_SIZE sizeof(double)

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
	Tern_t* tern;
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
	MapParameters_t* params;
	Tern_t* tern;

	params->data = CreateDataTern(env, data, dataType);

	tern->length = params->data->length;
	tern->size = params->data->size;

	CreateResultData(tern, dataType);

	params->result = tern;
	return params;
}

UnionParameters_t* CreateUnionParams(JNIEnv *env, void* data, void* otherData, int dataType)
{
	UnionParameters_t* params;
	Tern_t* tern;

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
	TakeParameters_t* params;
	Tern_t* tern;

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
	OclKernelInfo_t* kInfo;

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

typedef struct
{
	std::unordered_map<std::string, cl::Kernel> kernelsList;
	std::vector<cl::Platform> platforms;
	std::vector<cl::Device> devices;
	cl::Device defaultDevice;
	cl::Context context;
	cl::CommandQueue commandQueue;

} OclExecutor_t;

OclExecutor_t* CreateExecutor()
{
	OclExecutor_t* executor;

	cl::Platform::get(&(executor->platforms));

	executor->platforms[0].getDevices(CL_DEVICE_TYPE_ALL, &(executor->devices));

	executor->defaultDevice = executor->devices[0];

	executor->context = cl::Context(executor->defaultDevice);

	executor->commandQueue = cl::CommandQueue(executor->context, executor->defaultDevice);
}

void CreateStdKernel(OclExecutor_t& executor, OclKernelInfo_t& kInfo)
{
	cl::Program::Sources sources(1, std::make_pair( kInfo.kSource, kInfo.kernelSourceLength+1));
	cl::Program program(executor.context, sources);

	program.build(executor.devices);
	executor.kernelsList[kInfo.kernelNameCpp] = cl::Kernel(program, kInfo.kName);
}

std::string CreateKernelIfNotExists(OclExecutor_t& executor, OclKernelInfo_t& kInfo)
{
	std::string kernelNameCpp = kInfo.kernelNameCpp;
	std::unordered_map<std::string,cl::Kernel>::const_iterator iter = executor.kernelsList.find(kernelNameCpp);
	if(iter == executor.kernelsList.end())
	{
		switch (kInfo.kernelType)
		{
			case K_MAP:
			case K_TAKE:
			case K_UNION:
				CreateStdKernel(executor, kInfo);
				break;
		}
	}

	return kernelNameCpp;
}

void Map(OclExecutor_t& executor, OclKernelInfo_t& kInfo)
{
	try
	{
		MapParameters_t* mapParams = (MapParameters_t*) kInfo.kParams;

		cl::Kernel kernelMap = executor.kernelsList[CreateKernelIfNotExists(executor, kInfo)];

		cl::Buffer inputBuffer(executor.context, CL_MEM_READ_WRITE, mapParams->data->size);
		executor.commandQueue.enqueueWriteBuffer(inputBuffer, CL_TRUE, 0, mapParams->data->size, mapParams->data->data);

		kernelMap.setArg(0, inputBuffer);

		cl::NDRange global(mapParams->result->length);

		executor.commandQueue.enqueueNDRangeKernel(kernelMap, cl::NullRange, global, cl::NullRange);

		executor.commandQueue.enqueueReadBuffer(inputBuffer, CL_TRUE, 0, mapParams->result->size, mapParams->result->data);

		// env->ReleaseIntArrayElements(data, body);
	}
	catch (cl::Error error)
	{
		PrintClError(error);
	}
}

void Union(OclExecutor_t& executor, OclKernelInfo_t& kInfo)
{
	try
	{
		UnionParameters_t* unionParams = (UnionParameters_t*) kInfo.kParams;

		cl::Kernel kernelUnion = executor.kernelsList[CreateKernelIfNotExists(executor, kInfo)];

		cl::Buffer dataBuffer(executor.context, CL_MEM_READ_ONLY, unionParams->data->size);
		cl::Buffer otherDataBuffer(executor.context, CL_MEM_READ_ONLY, unionParams->otherData->size);
		cl::Buffer resultBuffer(executor.context, CL_MEM_WRITE_ONLY, unionParams->result->size);

		executor.commandQueue.enqueueWriteBuffer(dataBuffer, CL_TRUE, 0, unionParams->data->size, unionParams->data->data);
		executor.commandQueue.enqueueWriteBuffer(otherDataBuffer, CL_TRUE, 0, unionParams->otherData->size, unionParams->otherData->data);

		kernelUnion.setArg(0, dataBuffer);
		kernelUnion.setArg(1, otherDataBuffer);
		kernelUnion.setArg(2, resultBuffer);
		kernelUnion.setArg(3, unionParams->data->length);

		cl::NDRange global(unionParams->result->length);

		executor.commandQueue.enqueueNDRangeKernel(kernelUnion, cl::NullRange, global, cl::NullRange);

		executor.commandQueue.enqueueReadBuffer(resultBuffer, CL_TRUE, 0, unionParams->result->size, unionParams->result->data);
		// env->ReleaseDoubleArrayElements(data, body);
	}
	catch (cl::Error error)
	{
		PrintClError(error);
	}
}

void Take(OclExecutor_t& executor, OclKernelInfo_t& kInfo)
{
	try
	{
		TakeParameters_t* takeParams = (TakeParameters_t*) kInfo.kParams;

		cl::Kernel kernelTake = executor.kernelsList[CreateKernelIfNotExists(executor, kInfo)];

		cl::Buffer dataBuffer(executor.context, CL_MEM_READ_ONLY, takeParams->data->size);
		cl::Buffer resultBuffer(executor.context, CL_MEM_WRITE_ONLY, takeParams->result->size);

		executor.commandQueue.enqueueWriteBuffer(dataBuffer, CL_TRUE, 0, takeParams->data->size, takeParams->data->data);

		kernelTake.setArg(0, dataBuffer);
		kernelTake.setArg(1, resultBuffer);

		cl::NDRange global(takeParams->result->length);

		executor.commandQueue.enqueueNDRangeKernel(kernelTake, cl::NullRange, global, cl::NullRange);

		executor.commandQueue.enqueueReadBuffer(resultBuffer, CL_TRUE, 0, takeParams->result->size, takeParams->result->data);
		// env->ReleaseDoubleArrayElements(data, body);
	}
	catch (cl::Error error)
	{
		PrintClError(error);
	}
}



#endif
