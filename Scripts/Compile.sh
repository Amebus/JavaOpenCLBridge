#!/bin/bash

#Sources directory
SDir=../Cpp/Code/Sources
OutFile=../Cpp/Out/libOCL.so

#$SDir/OclKernelInfoBuilder.cpp

echo "compiling..."
g++ -std=c++11 -fPIC -shared $SDir/Ocl.cpp $SDir/OclKernelInfo.cpp $SDir/OclExecutor.cpp $SDir/KernelParameters.cpp -o $OutFile -lOpenCL -I$JAVA_HOME/include -I$JAVA_HOME/include/linux
echo "done"

echo "execstack..."
sudo execstack -c $OutFile
echo "done"
