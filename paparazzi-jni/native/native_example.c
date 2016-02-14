#include "native_example.h"


int sum(const char* str, int a, int b){
    printf("native function: %s\n", str);
    printf("%d + %d\n", a, b);
    return a+b;
}
