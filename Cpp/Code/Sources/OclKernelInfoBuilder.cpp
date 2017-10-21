
#include <iostream>
#include "../Headers/OclKernelInfoBuilder.h"

using namespace std;

exec::OclKernelInfoBuilder::OclKernelInfoBuilder (const char* name, int type)
{
	kName = name;
	kernelType = type;
}

exec::OclKernelInfoBuilder::~OclKernelInfoBuilder()
{
	delete kName;
	delete kSource;
}

exec::OclKernelInfoBuilder exec::OclKernelInfoBuilder::SetKernelSource(const char* source)
{
	kSource = source;
	return this;
}

exec::OclKernelInfo exec::OclKernelInfoBuilder::Build()
{
	exec::OclKernelInfo info = exec::OclKernelInfo(kName, kernelType);

	info.SetKernelSource(kSource);

	return info;
}
