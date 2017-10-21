// dt::Map_t mapParams;
// mapParams.dataLength = env->GetArrayLength(data);
// mapParams.dataSize = sizeof(double)*mapParams.dataLength;
// mapParams.data = env->GetDoubleArrayElements(data, 0);
// mapParams.result = new double[mapParams.dataLength];
// jdoubleArray ret = env->NewDoubleArray(mapParams.dataLength);


#include "../Headers/KernelParameters.h"

using namespace std;

namespace exec
{
	void KernelParameters::SetResultToDefault()
	{
		resultLength = 0;
		result = NULL;
		resultSize = 0;
	}

	KernelParameters::KernelParameters(JNIEnv *env, jintArray data)
	{
		dataLength = env->GetArrayLength(data);
		dataSize = INT_SIZE*dataLength;
		data = env->GetIntArrayElements(data, 0);

		SetResultToDefault();
	}

	KernelParameters::KernelParameters(JNIEnv *env, jdoubleArray data)
	{
		dataLength = env->GetArrayLength(data);
		dataSize = DOUBLE_SIZE*dataLength;
		data = env->GetDoubleArrayElements(data, 0);

		SetResultToDefault();
	}

	int KernelParameters::GetDataLength()
	{
		return dataLength;
	}
	void* KernelParameters::GetData()
	{
		return data;
	}
	size_t KernelParameters::GetDataSize()
	{
		return dataSize;
	}

	int KernelParameters::GetResultLength()
	{
		return resultSize;
	}
	void* KernelParameters::GetResult()
	{
		return result;
	}
	size_t KernelParameters::GetResultSize()
	{
		return resultSize;
	}

	void KernelParameters::SetResult(void* computationResult)
	{
		result = computationResult;
	}

}

//MapParameters implementation
namespace exec
{
	MapParameters::MapParameters(JNIEnv *env, jintArray data)
			: KernelParameters(env, data)
	{
		resultSize = dataSize;
		resultLength = dataLength;
		result = new int[resultLength];
	}
	MapParameters::MapParameters(JNIEnv *env, jdoubleArray data)
			: KernelParameters(env, data)
	{
		resultSize = dataSize;
		resultLength = dataLength;
		result = new double[resultLength];
	}
}

//UnionParameters Implementation
namespace exec
{
	UnionParameters::UnionParameters(JNIEnv *env, jintArray data, jintArray otherData)
			: KernelParameters(env, data)
	{

		otherDataLength = env->GetArrayLength(otherData);
		otherDataSize = INT_SIZE*dataLength;
		otherData = env->GetIntArrayElements(otherData, 0);

		resultLength = dataLength + otherDataLength;
		resultSize = INT_SIZE*resultLength;
		result = new int[resultLength];
	}
	UnionParameters::UnionParameters(JNIEnv *env, jdoubleArray data, jdoubleArray otherData)
			: KernelParameters(env, data)
	{
		otherDataLength = env->GetArrayLength(otherData);
		otherDataSize = DOUBLE_SIZE*dataLength;
		otherData = env->GetDoubleArrayElements(otherData, 0);

		resultLength = dataLength + otherDataLength;
		resultSize = DOUBLE_SIZE*resultLength;
		result = new double[resultLength];
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
			: KernelParameters(env, data)
	{
		resultLength = nToTake;
		resultSize = INT_SIZE*nToTake;
		result = new int[resultLength];
	}
	TakeParameters::TakeParameters(JNIEnv *env, jdoubleArray data, int nToTake)
			: KernelParameters(env, data)
	{
		resultLength = nToTake;
		resultSize = DOUBLE_SIZE*nToTake;
		result = new double[resultLength];
	}
}
