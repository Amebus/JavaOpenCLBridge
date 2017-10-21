
#include <iostream>
#include "../Headers/OclKernelInfo.h"

using namespace std;

exec::OclKernelInfo::OclKernelInfo (const char* name, int type, exec::KernelParameters& kParams)
{
	kName = name;
	kernelType = type;
	kernelNameCpp = string(kName);
	kernelNameLength = kernelNameCpp.length();
	params = kParams;
}

exec::OclKernelInfo::~OclKernelInfo()
{
	// delete kName;
	// delete kSource;
}

int exec::OclKernelInfo::GetKerneltype()
{
	return kernelType;
}

const char* exec::OclKernelInfo::GetKernelName()
{
	return kSource;
}

string exec::OclKernelInfo::GetKernelNameCpp()
{
	return kernelNameCpp;
}

int exec::OclKernelInfo::GetKernelNameLength()
{
	return kernelNameLength;
}

const char* exec::OclKernelInfo::GetKernelSource()
{
	return kSource;
}

int exec::OclKernelInfo::GetKernelSourceLength()
{
	return kernelSourceLength;
}

void exec::OclKernelInfo::SetKernelSource(const char* source)
{
	kSource = source;
	kernelSourceLength = string(kSource).length();
}

exec::KernelParameters& exec::OclKernelInfo::GetKernelParams()
{
	return params;
}
