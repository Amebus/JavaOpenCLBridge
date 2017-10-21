
#ifndef KernelParameters_H
#define KernelParameters_H

#include <CL/cl.hpp>
#include <iostream>
#include <jni.h>

namespace exec
{

	#define K_MAP 101
	#define K_UNION 102

	#define K_TAKE 201

	#define INT_SIZE sizeof(int)
	#define DOUBLE_SIZE sizeof(double)

	// jdoubleArray data, jstring kernelName, jstring kernelSource
	class KernelParameters
	{
		private:
			void SetResultToDefault();

		protected:
			int dataLength;
			void *data;
			size_t dataSize;

			int resultLength;
			void *result;
			size_t resultSize;

		public:
			KernelParameters(JNIEnv *, jintArray);
			KernelParameters(JNIEnv *, jdoubleArray);
			virtual ~KernelParameters();

			int GetDataLength();
			void* GetData();
			size_t GetDataSize();

			int GetResultLength();
			void* GetResult();
			size_t GetResultSize();

			void SetResult(void*);
	};

	class MapParameters : public KernelParameters
	{
		public:
			MapParameters(JNIEnv *, jintArray);
			MapParameters(JNIEnv *, jdoubleArray);
	};

	class UnionParameters : public KernelParameters
	{
		protected:
			int otherDataLength;
			void *otherData;
			size_t otherDataSize;

		public:
			UnionParameters(JNIEnv *, jintArray, jintArray);
			UnionParameters(JNIEnv *, jdoubleArray, jdoubleArray);
			virtual ~UnionParameters();

			int GetOtherDataLength();
			void* GetOtherData();
			size_t GetOtherDataSize();
	};

	class TakeParameters : public KernelParameters
	{
		public:
			TakeParameters(JNIEnv *, jintArray, int);
			TakeParameters(JNIEnv *, jdoubleArray, int);
			virtual ~TakeParameters();
	};

} /* DT */

#endif
