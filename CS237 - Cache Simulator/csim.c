#include "cachelab.h"
#include "csim.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
  //Flags for verbose and help arguments
  int ver = 0;
  
  //Not Enough Arguments Case
  if(argc < 9){

    printf("Too few arguments! Fully argumentalise!");

    return 0;
  }else if(argc > 10){
    //Too Many Arguments Check
    
    printf("Too many arguments! Check your arguments!");

    return 0;
    
  }

  //Normal argument processing and Cache initialisation
  struct Cache * cache = malloc(sizeof(struct Cache));
  
  initArgs(argc, argv, ver, cache);

  struct TraceLog * trace = malloc(sizeof(struct TraceLog));
  
  readTrace(cache->traceName, trace, cache);

  simulate(cache, trace);

  if(cache->verbose == 1){
    printTrace(trace);
  }
  
  printSummary(cache->hit, cache->miss, cache->eviction);
    
  free(cache);
  free(trace);
  
  return 0;
} 

void simulate(struct Cache * cache, struct TraceLog * trace){
  
  for(int i = 0; i < trace->numOps; i++){

    struct CacheOp * op = &trace->operations[i];

    int setN = op->set;

    //printf("SetN: %d opSet: %d\n", cache->S, setN);
    
    struct CacheSet * cset = &cache->setArray[setN];
    
    switch(op->opKind){
      //Modify calls twice to memory, read then write
      //store and Load calls once
    case 'M':
      getCache(cache, cset, op);
    case 'S':
      getCache(cache, cset, op);
      break;
    case 'L':
      getCache(cache, cset, op);
      break;
    default:
      break;
      
    }
    
  }
  
}

void getCache(struct Cache * cache, struct CacheSet * cset, struct CacheOp * op){

  //printCache(cache);
  
  //printf("setNum: %d setOp: %llx \n", cset->e, op->set);
  
  int hitFlag = 0;
  
  for(int i = 0; i < cset->e; i++){

    //if tag matches and is valid, then Hit!
    //update evict index and hit stat
    if(cset->lineArray[i].tag == op->tag && cset->lineArray[i].valid == 1){

      hitFlag = 1;

      evictUpdate(cache, cset, op, i, hitFlag);
      
      hit(cache, op);

      break;
    }
    
  }

  //If Miss, update miss stat
  //call writeCache method to load the line into the set
  if(hitFlag == 0){

    miss(cache, op);

    //Update the cache, i.e. write the missed cache
    writeCache(cache, cset, op, hitFlag);
  }

  return;
}


void evictUpdate(struct Cache * cache, struct CacheSet * cset,
		 struct CacheOp * op, int accessIndex, int hitFlag){

  //printf("hitF: %d AccIn: %d TimeSt: %d AccTag: %llx OpTag: %llx\n",
  //	 hitFlag, accessIndex, cset->access[accessIndex],
  //	 cset->lineArray[accessIndex].tag, op->tag);
  
  //if there was a miss, no hit
  //then check for eviction
  if(hitFlag == 0){

    //if the line is not empty, i.e. was accessed before
    //AND the tags do not match
    //then call evict
    if(cset->access[accessIndex] != 0){
      //  printf("I'm in evict if\n");
      evict(cache, op);
    }
  }
  
  //increase the number of access counter
  //then assign the access number to the most recent Cline
  cset->numAcc++;
  cset->access[accessIndex] = cset->numAcc;
    
  int smlInd = cset->evictIndex;
    
  //cycle through the lines
  //find the least recently accessed line,i.e. smallest timestamp
  //then update evictIndex
  //For first time access to the cache, they will all be 0,
  //hence the indexing will go in order as the alg only updates
  //smlInd when smaller and not small/equal
  for(int i = 0; i < cset->e; i++){
    
    //if ith line timestamp earlier than current latest access
    //then update latest access
    if(cset->access[i] < cset->access[smlInd]){
      smlInd = i;
    }
  }
  
  //update evictIndex
  cset->evictIndex = smlInd;


  //Full Check
  //if fullflag is not set AND the last cacheLine's timestamp is not 0,
  //i.e. has been accessed and written in
  //then set the fullFlag
  if(cset->fullFlag == 0 && cset->access[cset->e - 1] != 0){
    cset->fullFlag = 1;
  }
  
  return;
}


void printCache(struct Cache * cache){

  printf("\n");
  for(int i = 0; i < cache->S; i++){

    struct CacheSet * set = &cache->setArray[i];

    printf("----------Set Num: %d------------\n", i);
    
    for(int j = 0; j < set->e; j++){

      struct CacheLine * line = &set->lineArray[j];
      
      printf("Element: %d Tag: %llx Valid: %d LRU: %d\n", j, line->tag, line->valid,
	     set->access[j]);
    }
  }
  printf("---------------------------------\n");
  return;
}

void writeCache(struct Cache * cache, struct CacheSet * cset, struct CacheOp * op,		int hitFlag){
  
  // printf("EIndex: %d E: %d FullFlag: %d\n", cset->evictIndex, cset->e,
  //	 cset->fullFlag);

  struct CacheLine * line = &cset->lineArray[cset->evictIndex];
  
  line->tag = op->tag;

  line->valid = 1;

  //update the evictIndex
  evictUpdate(cache, cset, op, cset->evictIndex, hitFlag);
  
  return;
}

void hit(struct Cache * cache, struct CacheOp * op){

  cache->hit++;

  op->hit = 1;
  
  return;
}

void miss(struct Cache * cache, struct CacheOp * op){

  cache->miss++;

  op->miss = 1;
  
  return;
}

void evict(struct Cache * cache, struct CacheOp * op){

  cache->eviction++;

  op->evict = 1;

  return;
}

void readTrace(char * traceName, struct TraceLog * opLog, struct Cache * cache){

  FILE *file;

  //set the num of Ops to 0
  opLog->numOps = 0;

  //allocates 1 ops worth of space, later will be expended
  //opLog->operations = calloc(1, sizeof(struct CacheOp));

  //a buffer string to store each op
  char buff[32] = " "; // = malloc(32*sizeof(char));
    
  char temp[16] = " ";
  
  //Opens the trace file to read
  if((file = fopen(traceName,"r")) == NULL){
    printf("File Open Error.\n");
  }

  //Brute Force count number of lines to alloc memory for op log
  while(fgets(buff, 16, file) != NULL){
    //printf("Line No: %d\n", opLog->numOps);
    if(strcmp(&buff[1], " ") == 0){
      opLog->numOps++;
    }
  }

  rewind(file);
  
  opLog->operations = calloc(opLog->numOps, sizeof(struct CacheOp));

  int index = 0;
  
  //Read all the ops and store into the trace as long as there are ops
  while(fgets(buff, 32, file) != NULL){

    if(strcmp(&buff[1], " ") == 0){
    
      //an operation struct
      struct CacheOp op;

      //init the values
      op.opKind = ' ';
      op.adress = 0;
      op.size = 0;
      op.hit = 0;
      op.miss = 0;
      op.evict = 0;
      //First elem is space, 2nd elem is mem instruction
      op.opKind = buff[1];
      
      //where the address starts in buff
      int i = 3;
      
      //copy the adress to a diff string
      for(; buff[i] != ','; i++){
	
	temp[i-3] = buff[i];
      }
      
      //convert adress from string to int
      unsigned long long adress = strtoul(temp, NULL, 16);

      op.adress = adress;
      
      //Decode adress into tag, set and byte offset values
      decodeAdress(cache, &op, adress);
      
      //store size that is after the comma
      op.size = atoi(&buff[i+1]);

      //Store the op in the array of operations
      opLog->operations[index] = op;

      index++;
    }
  }
  fclose(file);

  return;  
}

void decodeAdress(struct Cache * cache, struct CacheOp * op,
		  unsigned long long adress){
  //Decode adress into tag, set and byte offset values
  //Tag
  unsigned long long mask = 1 << (cache->t+1);
  mask--;
  unsigned long long temp = adress;
  temp = temp >> (64 - cache->t);
  temp = temp & mask;
  op->tag = temp;

  //Set
  mask = 0;
  mask = 1 << (cache->s);
  mask--;
  temp = adress;
  temp = temp >> (cache->b);
  temp = temp & mask;
  op->set = temp;
  //printf("s%d ad%llx mask%llx temp%llx\n",cache->s,adress,mask,temp);
  
  //Byte Offset
  mask = 0;
  mask = 1 << (cache->b + 1);
  mask--;
  temp = adress;
  temp = temp & mask;
  op->bOffset = temp;

  //printf("Ad: %llx Tag: %llx Set: %llx\n", adress, op->tag, op->set);

  return;
}

void printTrace(struct TraceLog * trace){

  for(int i = 0; i < trace->numOps; i++){
    opPrint(&trace->operations[i]);
  }

  return;
}

void opPrint(struct CacheOp * op){

  printf("%c %llx, %d ", op->opKind, op->adress, op->size);

  if(op->miss == 1){
    printf("Miss ");
  }
  if(op->evict == 1){
    printf("Evict ");
  }
  if(op->hit == 1){
    printf("Hit");
  }
  
  printf("\n");

  //printf("Tag: %llx Set: %llx Byte Offset: %llx\n", op->tag, op->set, op->bOffset);
  
  return;
}

void initArgs(int argc, char * argv[], int v, struct Cache * cache){

  char * trace;
  
  int b,s,E,ver;
  
  for(int i = 1; i < argc; i++){

    char * temp = argv[i];

    if(temp[0] == '-'){
      switch(temp[1]) {
      case 'h':
      case 'v':
	ver = 1;
	break;
      case 's': //get S
	s = atoi(argv[i+1]);
	i++;
	break;
      case 'b': //get b
	b = atoi(argv[i+1]);
	i++;
	break;
      case 'E': //get E
	E = atoi(argv[i+1]);
	i++;
	break;
      case 't': // get Trace
	trace = argv[i+1];
	i++;
	break;
      default:
	break;
      }
    }
  }

  initCache(s, E, b, ver, trace, cache);

  //  printCache(cache);
  return;
}

  
void initCache(int s, int E, int b, int v, char * trace, struct Cache * cache){

  int S = pow(2, s);
  int B = pow(2, b);

  // printf("setSize: %d\n", S);
  
  //Init the traceName
  cache->traceName = trace;
  
  //Init the number of cache sets
  cache->S = S;
  //init block size
  cache->B = B;
  //init set bit num
  cache->s = s;
  //init block bit num;
  cache->b = b;
  //init num lines in a set
  cache->e = E;
  //init number of tag bits
  cache->t = 64 - s - b;
  //Init verbose state
  cache->verbose = v;

  //Init the stats
  cache->hit = 0;
  cache->miss = 0;
  cache->eviction = 0;


  //printf("SetSize: %d setBits: %d\n", S, s);
  //allocate space for S number of cache sets and get the pointer
  //of the first element to setArray
  cache->setArray = calloc(S, sizeof(struct CacheSet));
  
  //Loops and initialises the Cache Sets
  for(int i = 0; i <  S; i++){

    //init a Cache struct
    struct CacheSet * set = &cache->setArray[i];

    //Store number of Lines in the cacheSet
    set->e = E;

    //init access logs
    set->access = calloc(E, sizeof(int));

    for(int in = 0; in < E; in++){
      set->access[in] = 0;
    }
      
    //Init evict index and full flag
    set->evictIndex = 0;

    set->fullFlag = 0;
    
    //allocate E number of memory spaces of size CacheLine
    set->lineArray = calloc(E, sizeof(struct CacheLine));

    //Initialise the CacheLines
    for(int j = 0; j < E; j++){

      //init a Cache Line and put the pointer to the cacheLine
      //to the lineArray of cacheSet
      struct CacheLine * l = &set->lineArray[j];
      l->b = B;
      l->valid = 0;
      l->tag = 0;
      
    }

  }

  return;
  
}

void freeMem(struct Cache * cache){

  for(int i = 0; i < cache->s; i++){
    
    for(int j = 0; j < cache->setArray[i].e; j++){

      free(&cache->setArray[i].lineArray[j]);
    }

    free(&cache->setArray[i]);
    
  }
  
  free(cache);

  return;
}
