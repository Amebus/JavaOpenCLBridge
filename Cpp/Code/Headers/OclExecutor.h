

#ifndef OclExecutor_H
#define OclExecutor_H

#include <iostream>

#include "OclKernelInfo.h"
#include "OclKernelInfoBuilder.h"

using namespace std;

namespace src {

	const char* unionIntName = "unionInt";
	const char* unionIntSource = "__kernel void unionInt(__global int* _data, __global int* _otherData , __global int* _result, __private int _dataLength)\n"
											"{\n"
											"\tint _gId = get_global_id(0);\n"
											"\tif (_gId < _dataLength)\n"
											"\t{\n"
											"\t\t_result[_gId] = _data[_gId];\n"
											"\t}\n"
											"\telse\n"
											"\t{\n"
											"\t\t_result[_gId] = _otherData[_gId - _dataLength];\n"
											"\t}\n"
											"\n"
											"}\n"
											"\n";

	const char* unionDoubleName = "unionDouble";
	const char* unionDoubleSource = "__kernel void unionDouble(__global double* _data, __global double* _otherData , __global double* _result, __private int _dataLength)\n"
											"{\n"
											"\tint _gId = get_global_id(0);\n"
											"\tif (_gId < _dataLength)\n"
											"\t{\n"
											"\t\t_result[_gId] = _data[_gId];\n"
											"\t}\n"
											"\telse\n"
											"\t{\n"
											"\t\t_result[_gId] = _otherData[_gId - _dataLength];\n"
											"\t}\n"
											"\n"
											"}\n"
											"\n";

	const char* takeIntName = "takeInt";
	const char* takeIntSource = "__kernel void takeInt(__global int* _data, __global int* _result)\n"
											"{\n"
											"\tint _gId = get_global_id(0);\n"
											"\t_result[_gId] = _data[_gId];"
											"\n"
											"}"
											"\n";

	const char* takeDoubleName = "takeDouble";
	const char* takeDoubleSource = "__kernel void takeDouble(__global double* _data, __global double* _result)\n"
											"{\n"
											"\tint _gId = get_global_id(0);\n"
											"\t_result[_gId] = _data[_gId];"
											"\n"
											"}"
											"\n";
} /* src */

namespace exec
{
	class OclExecutor
	{
		private:
			unordered_map<std::string, cl::Kernel> kernelsList;
			vector<cl::Platform> platforms;
			vector<cl::Device> devices;
			cl::Device defaultDevice;
			cl::Context context;
			cl::CommandQueue commandQueue;

			void CreateStdKernel(OclKernelInfo&);
			string CreateKernelIfNotExists(OclKernelInfo&);

			void PrintClError(cl::Error);

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

} /* sprt */

#endif //OclExecutor_H
