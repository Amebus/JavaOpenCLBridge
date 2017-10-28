
#define __CL_ENABLE_EXCEPTIONS

#include "OclKernelInfo.h"
#include <unordered_map>
#include <iostream>
#include <CL/cl.hpp>

#ifndef OclExecutor_H
#define OclExecutor_H

#ifdef __cplusplus
extern "C" {
#endif

namespace exec
{
	class OclExecutor
	{
		private:
			std::unordered_map<std::string, cl::Kernel> kernelsList;
			std::vector<cl::Platform> platforms;
			std::vector<cl::Device> devices;
			cl::Device defaultDevice;
			cl::Context context;
			cl::CommandQueue commandQueue;

			void CreateStdKernel(OclKernelInfo&);
			std::string CreateKernelIfNotExists(OclKernelInfo&);

			void PrintClError(cl::Error error);

		public:
			OclExecutor ();
			virtual ~OclExecutor ();

			void Initialize();

			//Transformations
			void Map(OclKernelInfo&);
			void Union(OclKernelInfo&);

			//Actions
			void Take(OclKernelInfo&);
	};

} /* exec */

#ifdef __cplusplus
}
#endif

#endif //OclExecutor_H
