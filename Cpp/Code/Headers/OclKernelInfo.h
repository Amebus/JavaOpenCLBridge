
#ifndef OclKernelInfo_H
#define OclKernelInfo_H

#include "KernelParameters.h"

using namespace std;

namespace exec
{
	class OclKernelInfo;
} /* exec */

class exec::OclKernelInfo
{
	private:
		/* data */
		int kernelType;

		const char* kName;
		string kernelNameCpp;
		int kernelNameLength;

		const char* kSource;
		string kernelSourceCpp;
		int kernelSourceLength;

		exec::KernelParameters& params;

	public:
		OclKernelInfo (const char*, int, exec::KernelParameters&);
		virtual ~OclKernelInfo ();

		int GetKerneltype();

		const char* GetKernelName();
		string GetKernelNameCpp();
		int GetKernelNameLength();

		const char* GetKernelSource();
		int GetKernelSourceLength();

		void SetKernelSource(const char*);

		exec::KernelParameters& GetKernelParams();

};

#endif // OclKernelInfo_H
