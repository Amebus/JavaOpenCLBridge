
// #include "../Headers/OclKernelInfo.h"
//
// exec::OclKernelInfo::OclKernelInfo (const char* name, int type, exec::OclKernelParameters* kParams)
// {
// 	kName = name;
// 	kernelType = type;
// 	kernelNameCpp = std::string(kName);
// 	kernelNameLength = kernelNameCpp.length();
// 	params = kParams;
// }
//
// exec::OclKernelInfo::~OclKernelInfo()
// {
// 	// delete kName;
// 	// delete kSource;
// }
//
// int exec::OclKernelInfo::GetKernelType()
// {
// 	return kernelType;
// }
//
// const char* exec::OclKernelInfo::GetKernelName()
// {
// 	return kSource;
// }
//
// std::string exec::OclKernelInfo::GetKernelNameCpp()
// {
// 	return kernelNameCpp;
// }
//
// int exec::OclKernelInfo::GetKernelNameLength()
// {
// 	return kernelNameLength;
// }
//
// const char* exec::OclKernelInfo::GetKernelSource()
// {
// 	return kSource;
// }
//
// int exec::OclKernelInfo::GetKernelSourceLength()
// {
// 	return kernelSourceLength;
// }
//
// void exec::OclKernelInfo::SetKernelSource(const char* source)
// {
// 	kSource = source;
// 	kernelSourceLength = std::string(kSource).length();
// }
//
// exec::OclKernelParameters* exec::OclKernelInfo::GetKernelParams()
// {
// 	return params;
// }

#include "OclKernelParameters.cpp"

#ifndef OclKernelParameters_CPP
#define OclKernelParameters_CPP


typedef struct
{
	int kernelType;

	const char* kName;
	std::string kernelNameCpp;
	int kernelNameLength;

	const char* kSource;
	std::string kernelSourceCpp;
	int kernelSourceLength;

	void* kParams;

} OclKernelInfo_t;

OclKernelInfo_t* CreateKernelInfo(const char* name, int type, void* kParams)
{
	OclKernelInfo_t* kInfo;

	kInfo->kName = name;
	kInfo->kernelType = type;
	kInfo->kernelNameCpp = std::string(name);
	kInfo->kernelNameLength = kInfo->kernelNameCpp.length();
	kInfo->kParams = kParams;

	return kInfo;
}

void SetKernelSource(OclKernelInfo_t* kInfo, const char* source)
{
	kInfo->kSource = source;
	kInfo->kernelSourceCpp = std::string(source);
	kInfo->kernelSourceLength = kInfo->kernelSourceCpp.length();
}



#endif
