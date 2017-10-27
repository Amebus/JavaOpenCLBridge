
// #include "../Headers/OclKernelParameters.h"
//
// namespace exec
// {
// 	void OclKernelParameters::SetResultToDefault()
// 	{
// 		this->resultLength = 0;
// 		this->result = NULL;
// 		this->resultSize = 0;
// 	}
//
// 	OclKernelParameters::OclKernelParameters(JNIEnv *env, jintArray data)
// 	{
// 		this->dataLength = env->GetArrayLength(data);
// 		this->dataSize = INT_SIZE*dataLength;
// 		this->data = env->GetIntArrayElements(data, 0);
//
// 		SetResultToDefault();
// 	}
//
// 	OclKernelParameters::OclKernelParameters(JNIEnv *env, jdoubleArray data)
// 	{
// 		this->dataLength = env->GetArrayLength(data);
// 		this->dataSize = DOUBLE_SIZE*dataLength;
// 		this->data = env->GetDoubleArrayElements(data, 0);
//
// 		SetResultToDefault();
// 	}
//
// 	int OclKernelParameters::GetDataLength()
// 	{
// 		return dataLength;
// 	}
// 	void* OclKernelParameters::GetData()
// 	{
// 		return data;
// 	}
// 	size_t OclKernelParameters::GetDataSize()
// 	{
// 		return dataSize;
// 	}
//
// 	int OclKernelParameters::GetResultLength()
// 	{
// 		return resultSize;
// 	}
// 	void* OclKernelParameters::GetResult()
// 	{
// 		return result;
// 	}
// 	size_t OclKernelParameters::GetResultSize()
// 	{
// 		return resultSize;
// 	}
//
// 	void OclKernelParameters::SetResult(void* computationResult)
// 	{
// 		result = computationResult;
// 	}
// }
//
//
// //MapParameters implementation
// namespace exec
// {
// 	MapParameters::MapParameters(JNIEnv *env, jintArray data)
// 			: OclKernelParameters(env, data)
// 	{
// 		this->resultSize = dataSize;
// 		this->resultLength = dataLength;
// 		this->result = new int[resultLength];
// 	}
// 	MapParameters::MapParameters(JNIEnv *env, jdoubleArray data)
// 			: OclKernelParameters(env, data)
// 	{
// 		this->resultSize = dataSize;
// 		this->resultLength = dataLength;
// 		this->result = new double[resultLength];
// 	}
// }
//
// //UnionParameters Implementation
// namespace exec
// {
// 	UnionParameters::UnionParameters(JNIEnv *env, jintArray data, jintArray otherData)
// 			: OclKernelParameters(env, data)
// 	{
//
// 		this->otherDataLength = env->GetArrayLength(otherData);
// 		this->otherDataSize = INT_SIZE*dataLength;
// 		this->otherData = env->GetIntArrayElements(otherData, 0);
//
// 		this->resultLength = dataLength + otherDataLength;
// 		this->resultSize = INT_SIZE*resultLength;
// 		this->result = new int[resultLength];
// 	}
// 	UnionParameters::UnionParameters(JNIEnv *env, jdoubleArray data, jdoubleArray otherData)
// 			: OclKernelParameters(env, data)
// 	{
// 		this->otherDataLength = env->GetArrayLength(otherData);
// 		this->otherDataSize = DOUBLE_SIZE*dataLength;
// 		this->otherData = env->GetDoubleArrayElements(otherData, 0);
//
// 		this->resultLength = dataLength + otherDataLength;
// 		this->resultSize = DOUBLE_SIZE*resultLength;
// 		this->result = new double[resultLength];
// 	}
//
// 	int UnionParameters::GetOtherDataLength()
// 	{
// 		return otherDataLength;
// 	}
// 	void* UnionParameters::GetOtherData()
// 	{
// 		return otherData;
// 	}
// 	size_t UnionParameters::GetOtherDataSize()
// 	{
// 		return otherDataSize;
// 	}
// }
//
// //TakeParameters implamentation
// namespace exec
// {
// 	TakeParameters::TakeParameters(JNIEnv *env, jintArray data, int nToTake)
// 			: OclKernelParameters(env, data)
// 	{
// 		this->resultLength = nToTake;
// 		this->resultSize = INT_SIZE*nToTake;
// 		this->result = new int[resultLength];
// 	}
// 	TakeParameters::TakeParameters(JNIEnv *env, jdoubleArray data, int nToTake)
// 			: OclKernelParameters(env, data)
// 	{
// 		this->resultLength = nToTake;
// 		this->resultSize = DOUBLE_SIZE*nToTake;
// 		this->result = new double[resultLength];
// 	}
//
// }

#include <jni.h>
#include <CL/cl.hpp>

#ifndef OclKernelParameters_CPP
#define OclKernelParameters_CPP


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


#endif
