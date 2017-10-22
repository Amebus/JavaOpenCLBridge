
#ifndef OclKernelInfoBuilder_H
#define OclKernelInfoBuilder_H

#include "OclKernelInfo.h"

namespace exec
{
	class OclKernelInfoBuilder;
} /* exec */

class exec::OclKernelInfoBuilder
{
	private:
		/* data */
		int kernelType;

		const char* kName;
		const char* kSource;

	public:
		OclKernelInfoBuilder (const char*, int);
		virtual ~OclKernelInfoBuilder ();

		OclKernelInfoBuilder SetKernelSource(const char*);

		OclKernelInfo Build();
};


#endif //OclKernelInfoBuilder_H
