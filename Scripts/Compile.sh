#!/bin/bash

#Headers directory
HDir=../Cpp/code/Headers
#Sources directory
SDir=../Cpp/Code/Sources
#OutputFile
OutFile=../Cpp/Out/libOCL.so

#$SDir/OclKernelInfoBuilder.cpp

clear

#-Wl,-no-undefined

echo "compiling..."
g++ -std=c++11 -fPIC -shared $SDir/Ocl.cpp $SDir/OclExecutor.cpp -o $OutFile -lOpenCL -I$JAVA_HOME/include -I$JAVA_HOME/include/linux
echo "done"

echo "execstack..."
sudo execstack -c $OutFile
echo "done"
