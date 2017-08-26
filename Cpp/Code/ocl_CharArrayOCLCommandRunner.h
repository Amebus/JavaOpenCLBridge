/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class ocl_CharArrayOCLCommandRunner */

#ifndef _Included_ocl_CharArrayOCLCommandRunner
#define _Included_ocl_CharArrayOCLCommandRunner
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     ocl_CharArrayOCLCommandRunner
 * Method:    OclFilter
 * Signature: ([CLjava/lang/String;Ljava/lang/String;)[C
 */
JNIEXPORT jcharArray JNICALL Java_ocl_CharArrayOCLCommandRunner_OclFilter
  (JNIEnv *, jobject, jcharArray, jstring, jstring);

/*
 * Class:     ocl_CharArrayOCLCommandRunner
 * Method:    OclMap
 * Signature: ([CLjava/lang/String;Ljava/lang/String;)[C
 */
JNIEXPORT jcharArray JNICALL Java_ocl_CharArrayOCLCommandRunner_OclMap
  (JNIEnv *, jobject, jcharArray, jstring, jstring);

/*
 * Class:     ocl_CharArrayOCLCommandRunner
 * Method:    OclSample
 * Signature: ([CZF)[C
 */
JNIEXPORT jcharArray JNICALL Java_ocl_CharArrayOCLCommandRunner_OclSample___3CZF
  (JNIEnv *, jobject, jcharArray, jboolean, jfloat);

/*
 * Class:     ocl_CharArrayOCLCommandRunner
 * Method:    OclSample
 * Signature: ([CZFI)[C
 */
JNIEXPORT jcharArray JNICALL Java_ocl_CharArrayOCLCommandRunner_OclSample___3CZFI
  (JNIEnv *, jobject, jcharArray, jboolean, jfloat, jint);

/*
 * Class:     ocl_CharArrayOCLCommandRunner
 * Method:    OclUnion
 * Signature: ([C[C)[C
 */
JNIEXPORT jcharArray JNICALL Java_ocl_CharArrayOCLCommandRunner_OclUnion
  (JNIEnv *, jobject, jcharArray, jcharArray);

/*
 * Class:     ocl_CharArrayOCLCommandRunner
 * Method:    OclIntersection
 * Signature: ([C[C)[C
 */
JNIEXPORT jcharArray JNICALL Java_ocl_CharArrayOCLCommandRunner_OclIntersection
  (JNIEnv *, jobject, jcharArray, jcharArray);

/*
 * Class:     ocl_CharArrayOCLCommandRunner
 * Method:    OclTake
 * Signature: ([CI)[C
 */
JNIEXPORT jcharArray JNICALL Java_ocl_CharArrayOCLCommandRunner_OclTake
  (JNIEnv *, jobject, jcharArray, jint);

/*
 * Class:     ocl_CharArrayOCLCommandRunner
 * Method:    OclTakeSample
 * Signature: ([CZI)[C
 */
JNIEXPORT jcharArray JNICALL Java_ocl_CharArrayOCLCommandRunner_OclTakeSample___3CZI
  (JNIEnv *, jobject, jcharArray, jboolean, jint);

/*
 * Class:     ocl_CharArrayOCLCommandRunner
 * Method:    OclTakeSample
 * Signature: ([CZII)[C
 */
JNIEXPORT jcharArray JNICALL Java_ocl_CharArrayOCLCommandRunner_OclTakeSample___3CZII
  (JNIEnv *, jobject, jcharArray, jboolean, jint, jint);

/*
 * Class:     ocl_CharArrayOCLCommandRunner
 * Method:    OclReduce
 * Signature: ([CLjava/lang/String;Ljava/lang/String;)C
 */
JNIEXPORT jchar JNICALL Java_ocl_CharArrayOCLCommandRunner_OclReduce
  (JNIEnv *, jobject, jcharArray, jstring, jstring);

#ifdef __cplusplus
}
#endif
#endif
