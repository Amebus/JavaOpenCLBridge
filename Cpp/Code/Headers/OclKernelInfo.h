#include "OclKernelParameters.h"

#ifndef OclKernelInfo_H
#define OclKernelInfo_H

#ifdef __cplusplus
extern "C" {
#endif

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
		std::string kernelNameCpp;
		int kernelNameLength;

		const char* kSource;
		std::string kernelSourceCpp;
		int kernelSourceLength;

		exec::OclKernelParameters* params;

	public:
		OclKernelInfo (const char*, int, exec::OclKernelParameters*);
		virtual ~OclKernelInfo ();

		int GetKernelType();

		const char* GetKernelName();
		std::string GetKernelNameCpp();
		int GetKernelNameLength();

		const char* GetKernelSource();
		int GetKernelSourceLength();

		void SetKernelSource(const char*);

		exec::OclKernelParameters* GetKernelParams();

};

#ifdef __cplusplus
}
#endif
#endif // OclKernelInfo_H
