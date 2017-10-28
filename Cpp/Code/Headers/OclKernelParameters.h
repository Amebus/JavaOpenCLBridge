#include <jni.h>
#include <CL/cl.hpp>

#ifndef OclKernelParameters_H
#define OclKernelParameters_H

#ifdef __cplusplus
extern "C" {
#endif

namespace exec
{
	#define K_MAP 101
	#define K_UNION 102

	#define K_TAKE 201

	#define INT_SIZE sizeof(int)
	#define DOUBLE_SIZE sizeof(double)

	class OclKernelParameters
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
			OclKernelParameters(JNIEnv *, jintArray);
			OclKernelParameters(JNIEnv *, jdoubleArray);

			int GetDataLength();
			void* GetData();
			size_t GetDataSize();

			int GetResultLength();
			void* GetResult();
			size_t GetResultSize();

			void SetResult(void*);
	};

	class MapParameters : public OclKernelParameters
	{
		public:
			MapParameters(JNIEnv *, jintArray);
			MapParameters(JNIEnv *, jdoubleArray);
	};

	class UnionParameters : public OclKernelParameters
	{
		protected:
			int otherDataLength;
			void *otherData;
			size_t otherDataSize;

		public:
			UnionParameters(JNIEnv *, jintArray, jintArray);
			UnionParameters(JNIEnv *, jdoubleArray, jdoubleArray);

			int GetOtherDataLength();
			void* GetOtherData();
			size_t GetOtherDataSize();
	};

	class TakeParameters : public OclKernelParameters
	{
		public:
			TakeParameters(JNIEnv *, jintArray, int);
			TakeParameters(JNIEnv *, jdoubleArray, int);
	};

} /* exec */

#ifdef __cplusplus
}
#endif
#endif
