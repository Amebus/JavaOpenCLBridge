
#include "../Headers/OclKernelInfo.h"

exec::OclKernelInfo::OclKernelInfo (const char* name, int type, exec::OclKernelParameters* kParams)
{
	kName = name;
	kernelType = type;
	kernelNameCpp = std::string(kName);
	kernelNameLength = kernelNameCpp.length();
	params = kParams;
}

exec::OclKernelInfo::~OclKernelInfo()
{
	// delete kName;
	// delete kSource;
}

int exec::OclKernelInfo::GetKernelType()
{
	return kernelType;
}

const char* exec::OclKernelInfo::GetKernelName()
{
	return kSource;
}

std::string exec::OclKernelInfo::GetKernelNameCpp()
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
	kernelSourceLength = std::string(kSource).length();
}

exec::OclKernelParameters* exec::OclKernelInfo::GetKernelParams()
{
	return params;
}
