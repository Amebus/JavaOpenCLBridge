#!/bin/bash
javah -d ../Cpp/Code/Headers -jni -classpath ../Java/Bridge/target/classes ocl.Ocl

javah -d ../Cpp/Code/Headers -jni -classpath ../Java/Bridge/target/classes serialization.SerializationBridge

javah -d ../Cpp/Code/Headers -jni -classpath ../Java/Bridge/target/classes oclBridge.AbstractOclBridge

