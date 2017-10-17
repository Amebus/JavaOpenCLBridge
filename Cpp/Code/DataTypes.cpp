
#define K_MAP 101


#define K_TAKE 201

#include "ocl_Ocl.h"

typedef struct
{
	size_t dataSize;
	size_t resultSize;
	void *data;
	void *result;
	int nToTake;
} Take_t;

typedef struct
{
	size_t dataSize;
	void *data;
	void *result;
} Map_t;
