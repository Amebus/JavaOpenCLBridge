#include "baseInclusion.h"
#include <jni.h>

#ifndef JniUtility_H
#define JniUtility_H


std::string GetStringFromJavaString(JNIEnv *pEnv, jstring pString);



#endif //JniUtility_H