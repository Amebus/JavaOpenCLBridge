
#include "../Headers/OclExecutor.h"
// #include <CL/cl.hpp>

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

exec::OclExecutor::OclExecutor ()
{
	cl::Platform::get(&platforms);

	platforms[0].getDevices(CL_DEVICE_TYPE_ALL, &devices);

	defaultDevice = devices[0];

	context = cl::Context(defaultDevice);

	commandQueue = cl::CommandQueue(context, defaultDevice);
}


exec::OclExecutor::~OclExecutor ()
{

}

void exec::OclExecutor::PrintClError(cl::Error error)
{
	std::cout << "Error" << std::endl;
	std::cout << error.what() << "(" << error.err() << ")" << std::endl;
}

void exec::OclExecutor::CreateStdKernel(OclKernelInfo& kInfo)
{
	cl::Program::Sources sources(1, std::make_pair( kInfo.GetKernelSource(), kInfo.GetKernelSourceLength()+1));
	cl::Program program(context,sources);

	program.build(devices);
	kernelsList[kInfo.GetKernelNameCpp()] = cl::Kernel(program, kInfo.GetKernelName());
}

std::string exec::OclExecutor::CreateKernelIfNotExists(OclKernelInfo& kInfo)
{
	std::string kernelNameCpp = kInfo.GetKernelNameCpp();
	std::unordered_map<std::string,cl::Kernel>::const_iterator iter = kernelsList.find(kernelNameCpp);
	if(iter == kernelsList.end())
	{
		switch (kInfo.GetKernelType())
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

void exec::OclExecutor::Map(OclKernelInfo& kInfo)
{
	try
	{
		exec::MapParameters* mapParams = (exec::MapParameters*) kInfo.GetKernelParams();

		void* result = mapParams->GetResult();

		cl::Kernel kernelMap = kernelsList[CreateKernelIfNotExists(kInfo)];

		cl::Buffer inputBuffer(context, CL_MEM_READ_WRITE, mapParams->GetDataSize());
		commandQueue.enqueueWriteBuffer(inputBuffer, CL_TRUE, 0, mapParams->GetDataSize(), mapParams->GetData());

		kernelMap.setArg(0, inputBuffer);

		cl::NDRange global(mapParams->GetDataLength());

		commandQueue.enqueueNDRangeKernel(kernelMap, cl::NullRange, global, cl::NullRange);

		commandQueue.enqueueReadBuffer(inputBuffer, CL_TRUE, 0, mapParams->GetResultSize(), result);

		// env->ReleaseIntArrayElements(data, body);
	}
	catch (cl::Error error)
	{
		this->PrintClError(error);
	}
}

void exec::OclExecutor::Union(OclKernelInfo& kInfo)
{
	try
	{
		exec::UnionParameters* unionParams = (exec::UnionParameters*) kInfo.GetKernelParams();
		void* result = unionParams->GetResult();

		cl::Kernel kernelUnion = kernelsList[CreateKernelIfNotExists(kInfo)];

		cl::Buffer dataBuffer(context, CL_MEM_READ_ONLY, unionParams->GetDataSize());
		cl::Buffer otherDataBuffer(context, CL_MEM_READ_ONLY, unionParams->GetOtherDataSize());
		cl::Buffer resultBuffer(context, CL_MEM_WRITE_ONLY, unionParams->GetResultSize());

		commandQueue.enqueueWriteBuffer(dataBuffer, CL_TRUE, 0, unionParams->GetDataSize(), unionParams->GetData());
		commandQueue.enqueueWriteBuffer(otherDataBuffer, CL_TRUE, 0, unionParams->GetOtherDataSize(), unionParams->GetOtherData());

		kernelUnion.setArg(0, dataBuffer);
		kernelUnion.setArg(1, otherDataBuffer);
		kernelUnion.setArg(2, resultBuffer);
		kernelUnion.setArg(3, unionParams->GetDataLength());

		cl::NDRange global(unionParams->GetResultLength());

		commandQueue.enqueueNDRangeKernel(kernelUnion, cl::NullRange, global, cl::NullRange);

		commandQueue.enqueueReadBuffer(resultBuffer, CL_TRUE, 0, unionParams->GetResultSize(), result);
		// env->ReleaseDoubleArrayElements(data, body);
	}
	catch (cl::Error error)
	{
		this->PrintClError(error);
	}
}

void exec::OclExecutor::Take(OclKernelInfo& kInfo)
{
	try
	{
		exec::TakeParameters* takeParams = (exec::TakeParameters*) kInfo.GetKernelParams();
		void* result = takeParams->GetResult();

		cl::Kernel kernelTake = kernelsList[CreateKernelIfNotExists(kInfo)];

		cl::Buffer dataBuffer(context, CL_MEM_READ_ONLY, takeParams->GetDataSize());
		cl::Buffer resultBuffer(context, CL_MEM_WRITE_ONLY, takeParams->GetResultSize());

		commandQueue.enqueueWriteBuffer(dataBuffer, CL_TRUE, 0, takeParams->GetDataSize(), takeParams->GetData());

		kernelTake.setArg(0, dataBuffer);
		kernelTake.setArg(1, resultBuffer);

		cl::NDRange global(takeParams->GetResultLength());

		commandQueue.enqueueNDRangeKernel(kernelTake, cl::NullRange, global, cl::NullRange);

		commandQueue.enqueueReadBuffer(resultBuffer, CL_TRUE, 0, takeParams->GetResultSize(), result);
		// env->ReleaseDoubleArrayElements(data, body);
	}
	catch (cl::Error error)
	{
		this->PrintClError(error);
	}
}
