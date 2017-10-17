
#define K_MAP 101
#define K_UNION 102

#define K_TAKE 201

#include "ocl_Ocl.h"

typedef struct
{
	int dataLength;
	size_t dataSize;
	void *data;

	void *result;
} Map_t;

typedef struct
{
	int dataLength;
	void *data;
	size_t dataSize;

	int otherDataLength;
	void *otherData;
	size_t otherDataSize;

	int resultLength;
	void *result;
	size_t resultSize;
} Union_t;


typedef struct
{
	void *data;
	size_t dataSize;

	void *result;
	size_t resultSize;

	int nToTake;
} Take_t;
