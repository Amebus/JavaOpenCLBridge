#!/bin/bash

#echo "compiling..."
#g++ -fPIC -shared ../Cpp/Code/DataConverter.cpp ../Cpp/Code/IntArrayOCLCommandRunner.cpp ../Cpp/Code/CharArrayOCLCommandRunner.cpp ../Cpp/Code/DoubleArrayOCLCommandRunner.cpp -o ../Cpp/Out/libOCL.so -lOpenCL -I/home/federico/Java/jdk1.8.0_144/include -I/home/federico/Java/jdk1.8.0_144/include/linux
#echo "done"

echo "compiling..."
g++ -fPIC -shared ../Cpp/Code/Ocl.cpp -o ../Cpp/Out/libOCL.so -lOpenCL -I$JAVA_HOME/include -I$JAVA_HOME/include/linux
echo "done"

echo "execstack..."
sudo execstack -c ../Cpp/Out/libOCL.so
echo "done"
