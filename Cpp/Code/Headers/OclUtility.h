
#include "baseInclusion.h"
#include <CL/cl.h>

#ifndef OclUtility_H
#define OclUtility_H

void printStatus(const cl_int status, const int line);

std::string GetKernelNameFromKernelFileName(std::string pKernelName);

std::vector<std::string> GetKernelsSourceFiles(std::string pKernelsFolder);

std::string GetKernelSourceCode(std::string pFile);

cl_kernel CompileKernel(std::string pSourceCode, std::string pKernelName);

void StoreKernel(std::string pKernelName, cl_kernel pKernel);

void CompileAndStoreOclKernel(std::string pKernelsFolder, std::string pKernelName);

void CompileAndStoreOclKernels(std::string pKernelsFolder, std::vector<std::string> pKernelsFiles);

#endif //OclUtility_H