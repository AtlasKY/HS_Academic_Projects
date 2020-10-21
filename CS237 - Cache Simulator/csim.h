
#ifndef CSIM_H
#define CSIM_H

typedef unsigned char byte;

//A single CacheLine
//stores block size, has a tag and valid flag
struct CacheLine{

  //Block of Data
  // byte * data;
  int b;
  
  //Tag of CacheLine
  unsigned long long tag;
  
  //Valid Bit
  unsigned int valid:1;
  
};

//Represents a single cache set
//has access logs, fullFlag, an array of CacheLines and the evictIndex
struct CacheSet{

  //Variable to store the number of lines in a set
  int e;

  //least recent used line index
  int * access;

  int numAcc;
  
  int evictIndex;
  
  //flag for when the set is full, i.e. time to evict
  int fullFlag;
  
  //An array of CacheLines with a pointer access
  struct CacheLine * lineArray;
};

//The Big Cache Struct
struct Cache{

  //Store num of set bits
  int s;
  //store num sets
  int S;
  //Lines in a set
  int e;
  //Blocks in a line
  int B;
  //num block bits
  int b;
  //Number of tag bits
  int t;
  
  //Counter for number of hits, misses, and evictions
  int hit;
  int miss;
  int eviction;
  
  //Flag for verbose print
  int verbose;

  //store the name of the trace file
  char * traceName;
  
  //An array of Cache Sets
  struct CacheSet * setArray;
  
};

//Struct to store cache access operation
//has operation kind, adress, size decoded tag, set, offset
//miss and hit flags
struct CacheOp {

  char opKind;

  long long adress;
  
  unsigned long long tag;

  unsigned long long bOffset;

  unsigned long long set;

  byte miss;

  byte hit;

  byte evict;
  
  short size;

};

//Logs the operations in an array
struct TraceLog {

  int numOps;
  
  struct CacheOp * operations;
  
};

//Initialises the Cache with the parameters passed from command line
//calls initCache method
void initArgs(int argc, char * argv[], int v, struct Cache * cache);

//allocates memory for the lines and sets of the cache
//Initialises the values and variables in the Cache, CacheSet and CacheLine...
//...structures
void initCache(int s, int E, int b, int v, char * trace, struct Cache * cache);

//Reads the file traceName, initialises the struct TraceLog
//and stores the operations in the traceLog
//also decodes the tags sets and offests from the addresses
void readTrace(char * traceName, struct TraceLog * opLog, struct Cache * cache);

//a helper method to decode the given adress and store the tag, set and offset
//values into the CacheOp op
void decodeAdress(struct Cache * cache, struct CacheOp * op, unsigned long long adress);

//Prints the current cache
void printCache(struct Cache * cache);

//prints the traceLogs
void printTrace(struct TraceLog * trace);

//Prints and individual Operation, helper method to printTrace
void opPrint(struct CacheOp * op);

//the simulation method, runs through the ops in trace
//executes cache access simulation
void simulate(struct Cache * cache, struct TraceLog * trace);

//cache accesser method, looks for the tag in set
//if hit, update
//if miss update then call writeCache to write the line into the set
void getCache(struct Cache * cache, struct CacheSet * cset, struct CacheOp * op);

//writes the given tag into the set
void writeCache(struct Cache * cache, struct CacheSet * cset,
		struct CacheOp * op, int hitFlag);

//updates the access logs in CacheSet
//sets the least recent accessed CacheLine index
void evictUpdate(struct Cache * cache, struct CacheSet * cset,
		 struct CacheOp * op, int accessIndex, int hitFlag);

//Update stat, update Op hit flag
void hit(struct Cache * cache, struct CacheOp * op);

//update stat, opmiss flag
void miss(struct Cache * cache, struct CacheOp * op);

//update stat
void evict(struct Cache * cache, struct CacheOp * op);

void freeMem(struct Cache * cache);

#endif
