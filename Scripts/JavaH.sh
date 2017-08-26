#!/bin/bash
/home/federico/Java/jdk1.8.0_144/bin/javah -d ../Cpp/Code/ -jni -classpath ../Java/Bridge/target/classes ocl.DataConverter

/home/federico/Java/jdk1.8.0_144/bin/javah -d ../Cpp/Code/ -jni -classpath ../Java/Bridge/target/classes ocl.IntArrayOCLCommandRunner

/home/federico/Java/jdk1.8.0_144/bin/javah -d ../Cpp/Code/ -jni -classpath ../Java/Bridge/target/classes ocl.CharArrayOCLCommandRunner

/home/federico/Java/jdk1.8.0_144/bin/javah -d ../Cpp/Code/ -jni -classpath ../Java/Bridge/target/classes ocl.DoubleArrayOCLCommandRunner
