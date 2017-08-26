/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class ocl_DoubleArrayOCLCommandRunner */

#ifndef _Included_ocl_DoubleArrayOCLCommandRunner
#define _Included_ocl_DoubleArrayOCLCommandRunner
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     ocl_DoubleArrayOCLCommandRunner
 * Method:    OclFilter
 * Signature: ([DLjava/lang/String;Ljava/lang/String;)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_ocl_DoubleArrayOCLCommandRunner_OclFilter
  (JNIEnv *, jobject, jdoubleArray, jstring, jstring);

/*
 * Class:     ocl_DoubleArrayOCLCommandRunner
 * Method:    OclMap
 * Signature: ([DLjava/lang/String;Ljava/lang/String;)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_ocl_DoubleArrayOCLCommandRunner_OclMap
  (JNIEnv *, jobject, jdoubleArray, jstring, jstring);

/*
 * Class:     ocl_DoubleArrayOCLCommandRunner
 * Method:    OclSample
 * Signature: ([DZF)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_ocl_DoubleArrayOCLCommandRunner_OclSample___3DZF
  (JNIEnv *, jobject, jdoubleArray, jboolean, jfloat);

/*
 * Class:     ocl_DoubleArrayOCLCommandRunner
 * Method:    OclSample
 * Signature: ([DZFI)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_ocl_DoubleArrayOCLCommandRunner_OclSample___3DZFI
  (JNIEnv *, jobject, jdoubleArray, jboolean, jfloat, jint);

/*
 * Class:     ocl_DoubleArrayOCLCommandRunner
 * Method:    OclUnion
 * Signature: ([D[D)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_ocl_DoubleArrayOCLCommandRunner_OclUnion
  (JNIEnv *, jobject, jdoubleArray, jdoubleArray);

/*
 * Class:     ocl_DoubleArrayOCLCommandRunner
 * Method:    OclIntersection
 * Signature: ([D[D)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_ocl_DoubleArrayOCLCommandRunner_OclIntersection
  (JNIEnv *, jobject, jdoubleArray, jdoubleArray);

/*
 * Class:     ocl_DoubleArrayOCLCommandRunner
 * Method:    OclTake
 * Signature: ([DI)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_ocl_DoubleArrayOCLCommandRunner_OclTake
  (JNIEnv *, jobject, jdoubleArray, jint);

/*
 * Class:     ocl_DoubleArrayOCLCommandRunner
 * Method:    OclTakeSample
 * Signature: ([DZI)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_ocl_DoubleArrayOCLCommandRunner_OclTakeSample___3DZI
  (JNIEnv *, jobject, jdoubleArray, jboolean, jint);

/*
 * Class:     ocl_DoubleArrayOCLCommandRunner
 * Method:    OclTakeSample
 * Signature: ([DZII)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_ocl_DoubleArrayOCLCommandRunner_OclTakeSample___3DZII
  (JNIEnv *, jobject, jdoubleArray, jboolean, jint, jint);

/*
 * Class:     ocl_DoubleArrayOCLCommandRunner
 * Method:    OclReduce
 * Signature: ([DLjava/lang/String;Ljava/lang/String;)D
 */
JNIEXPORT jdouble JNICALL Java_ocl_DoubleArrayOCLCommandRunner_OclReduce
  (JNIEnv *, jobject, jdoubleArray, jstring, jstring);

#ifdef __cplusplus
}
#endif
#endif
