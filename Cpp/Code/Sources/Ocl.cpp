
#include "../Headers/OclExecutor.h"
#include "../Headers/ocl_Ocl.h"
#include <CL/cl.hpp>
#include <iostream>
#include <unordered_map>

exec::OclExecutor executor;

JNIEXPORT void JNICALL Java_ocl_Ocl_Open
  (JNIEnv *env, jobject obj)
{
	executor = exec::OclExecutor();
}

JNIEXPORT void JNICALL Java_ocl_Ocl_Close
  (JNIEnv *, jobject obj)
{
}

JNIEXPORT jintArray JNICALL Java_ocl_Ocl_OclMap___3ILjava_lang_String_2Ljava_lang_String_2
  (JNIEnv *env, jobject obj, jintArray data, jstring kernelName, jstring kernelSource)
{
	exec::MapParameters mapParams(env, data);
	const char *kName = env->GetStringUTFChars(kernelName, NULL);
	const char *kSource = env->GetStringUTFChars(kernelSource, NULL);
	exec::OclKernelInfo kInfo(kName, K_MAP, &mapParams);
	kInfo.SetKernelSource(kSource);

	executor.Map(kInfo);

	env->ReleaseStringUTFChars(kernelName, kName);
	env->ReleaseStringUTFChars(kernelSource, kSource);

	jintArray ret = env->NewIntArray(mapParams.GetResultLength());
	env->SetIntArrayRegion(ret, 0, mapParams.GetResultLength(), (int *)mapParams.GetResult());
	return ret;
}

JNIEXPORT jdoubleArray JNICALL Java_ocl_Ocl_OclMap___3DLjava_lang_String_2Ljava_lang_String_2
  (JNIEnv *env, jobject obj, jdoubleArray data, jstring kernelName, jstring kernelSource)
{
	exec::MapParameters mapParams(env, data);
	const char *kName = env->GetStringUTFChars(kernelName, NULL);
	const char *kSource = env->GetStringUTFChars(kernelSource, NULL);
	exec::OclKernelInfo kInfo(kName, K_MAP, &mapParams);
	kInfo.SetKernelSource(kSource);

	executor.Map(kInfo);

	env->ReleaseStringUTFChars(kernelName, kName);
	env->ReleaseStringUTFChars(kernelSource, kSource);

	jdoubleArray ret = env->NewDoubleArray(mapParams.GetResultLength());
	env->SetDoubleArrayRegion(ret, 0, mapParams.GetResultLength(), (double *)mapParams.GetResult());
	return ret;
}

JNIEXPORT jintArray JNICALL Java_ocl_Ocl_OclTake___3II
  (JNIEnv *env, jobject obj, jintArray data, jint nToTake)
{

	std::cout << "TakeInt" << '\n';

	exec::TakeParameters takeParams(env, data, nToTake);
	exec::OclKernelInfo kInfo("takeInt", K_TAKE, &takeParams);

	executor.Take(kInfo);

	jintArray ret = env->NewIntArray(nToTake);
	env->SetIntArrayRegion(ret, 0, takeParams.GetResultLength(), (int *)takeParams.GetResult());
	return ret;

}

JNIEXPORT jdoubleArray JNICALL Java_ocl_Ocl_OclTake___3DI
  (JNIEnv *env, jobject obj, jdoubleArray data, jint nToTake)
{
	exec::TakeParameters takeParams(env, data, nToTake);
	exec::OclKernelInfo kInfo("takeDouble", K_TAKE, &takeParams);

	executor.Take(kInfo);

	jdoubleArray ret = env->NewDoubleArray(nToTake);
	env->SetDoubleArrayRegion(ret, 0, takeParams.GetResultLength(), (double *)takeParams.GetResult());
	return ret;
}

JNIEXPORT jintArray JNICALL Java_ocl_Ocl_OclUnion___3I_3I
  (JNIEnv *env, jobject obj, jintArray data, jintArray otherData)
{
	exec::UnionParameters unionParams(env, data, otherData);
	exec::OclKernelInfo kInfo("unionInt", K_TAKE, &unionParams);

	executor.Union(kInfo);

	jintArray ret = env->NewIntArray(unionParams.GetResultLength());
	env->SetIntArrayRegion(ret, 0, unionParams.GetResultLength(), (int *)unionParams.GetResult());
	return ret;
}

JNIEXPORT jdoubleArray JNICALL Java_ocl_Ocl_OclUnion___3D_3D
  (JNIEnv *env, jobject jobj, jdoubleArray data, jdoubleArray otherData)
{

	exec::UnionParameters unionParams(env, data, otherData);
	exec::OclKernelInfo kInfo("unionInt", K_TAKE, &unionParams);

	executor.Union(kInfo);

	jdoubleArray ret = env->NewDoubleArray(unionParams.GetResultLength());
	env->SetDoubleArrayRegion(ret, 0, unionParams.GetResultLength(), (double *)unionParams.GetResult());
	return ret;
}
