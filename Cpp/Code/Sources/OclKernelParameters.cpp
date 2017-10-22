
#include "../Headers/OclKernelParameters.h"

namespace exec
{
	void OclKernelParameters::SetResultToDefault()
	{
		this->resultLength = 0;
		this->result = NULL;
		this->resultSize = 0;
	}

	OclKernelParameters::OclKernelParameters(JNIEnv *env, jintArray data)
	{
		this->dataLength = env->GetArrayLength(data);
		this->dataSize = INT_SIZE*dataLength;
		this->data = env->GetIntArrayElements(data, 0);

		SetResultToDefault();
	}

	OclKernelParameters::OclKernelParameters(JNIEnv *env, jdoubleArray data)
	{
		this->dataLength = env->GetArrayLength(data);
		this->dataSize = DOUBLE_SIZE*dataLength;
		this->data = env->GetDoubleArrayElements(data, 0);

		SetResultToDefault();
	}

	int OclKernelParameters::GetDataLength()
	{
		return dataLength;
	}
	void* OclKernelParameters::GetData()
	{
		return data;
	}
	size_t OclKernelParameters::GetDataSize()
	{
		return dataSize;
	}

	int OclKernelParameters::GetResultLength()
	{
		return resultSize;
	}
	void* OclKernelParameters::GetResult()
	{
		return result;
	}
	size_t OclKernelParameters::GetResultSize()
	{
		return resultSize;
	}

	void OclKernelParameters::SetResult(void* computationResult)
	{
		result = computationResult;
	}
}


//MapParameters implementation
namespace exec
{
	MapParameters::MapParameters(JNIEnv *env, jintArray data)
			: OclKernelParameters(env, data)
	{
		this->resultSize = dataSize;
		this->resultLength = dataLength;
		this->result = new int[resultLength];
	}
	MapParameters::MapParameters(JNIEnv *env, jdoubleArray data)
			: OclKernelParameters(env, data)
	{
		this->resultSize = dataSize;
		this->resultLength = dataLength;
		this->result = new double[resultLength];
	}
}

//UnionParameters Implementation
namespace exec
{
	UnionParameters::UnionParameters(JNIEnv *env, jintArray data, jintArray otherData)
			: OclKernelParameters(env, data)
	{

		this->otherDataLength = env->GetArrayLength(otherData);
		this->otherDataSize = INT_SIZE*dataLength;
		this->otherData = env->GetIntArrayElements(otherData, 0);

		this->resultLength = dataLength + otherDataLength;
		this->resultSize = INT_SIZE*resultLength;
		this->result = new int[resultLength];
	}
	UnionParameters::UnionParameters(JNIEnv *env, jdoubleArray data, jdoubleArray otherData)
			: OclKernelParameters(env, data)
	{
		this->otherDataLength = env->GetArrayLength(otherData);
		this->otherDataSize = DOUBLE_SIZE*dataLength;
		this->otherData = env->GetDoubleArrayElements(otherData, 0);

		this->resultLength = dataLength + otherDataLength;
		this->resultSize = DOUBLE_SIZE*resultLength;
		this->result = new double[resultLength];
	}

	int UnionParameters::GetOtherDataLength()
	{
		return otherDataLength;
	}
	void* UnionParameters::GetOtherData()
	{
		return otherData;
	}
	size_t UnionParameters::GetOtherDataSize()
	{
		return otherDataSize;
	}
}

//TakeParameters implamentation
namespace exec
{
	TakeParameters::TakeParameters(JNIEnv *env, jintArray data, int nToTake)
			: OclKernelParameters(env, data)
	{
		this->resultLength = nToTake;
		this->resultSize = INT_SIZE*nToTake;
		this->result = new int[resultLength];
	}
	TakeParameters::TakeParameters(JNIEnv *env, jdoubleArray data, int nToTake)
			: OclKernelParameters(env, data)
	{
		this->resultLength = nToTake;
		this->resultSize = DOUBLE_SIZE*nToTake;
		this->result = new double[resultLength];
	}

}
