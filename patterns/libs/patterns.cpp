#include <stdio.h>

#ifdef WIN32
#define EXPORT __declspec(dllexport)
#else
#define EXPORT extern "C" __attribute__((visibility("default"))) __attribute__((used))
#endif

void printSpace(int patternNumber)
{
  switch (patternNumber)
  {
  case 0:
  case 9:
  case 10:
    printf("%c", '\0');
    break;
  case 2:
  case 6:
  case 11:
  case 12:
    printf("  ");
    break;
  case 3:
    printf("   ");
    break;
  default:
    printf(" ");
  }
}

void generateSpace(int numberOfRows, int size, int patternNumber)
{
  switch (patternNumber)
  {
  case 4:
    while (size--)
      printSpace(patternNumber);
    break;
  default:
    while (numberOfRows-- > size)
      printSpace(patternNumber);
    break;
  }
}

void printSymbol(int size, int isStar, int patternNumber)
{
  do
  {
    switch (patternNumber)
    {
    case 5:
    case 6:
    {
      if (isStar)
        printf(" * ");
      else
        printf(" - ");
    }
    break;
    default:
    {
      if (isStar)
        printf("* ");
      else
        printf("- ");
    }
    break;
    }
    isStar = isStar ? 0 : 1;
  } while (size--);
  printf("\n");
}

void printPattern(int size, int numberOfRows, int isStar, int patternNumber)
{
  if (size % 2 == 0)
  {
    generateSpace(numberOfRows, size, patternNumber);
    printSymbol(size, isStar, patternNumber);
  }
}

void generatePattern(int numberOfRows, int patternNumber)
{
  printf("\n");
  for (int size = 0; size < numberOfRows + (patternNumber >= 7 ? 1 : 0); size++)
  {
    switch (patternNumber)
    {
    case 8:
    case 10:
    case 11:
      continue;
    }
    printPattern(size, numberOfRows, 1, patternNumber);
  }
  for (int size = numberOfRows; size >= 0; size--)
  {
    switch (patternNumber)
    {
    case 7:
    case 9:
    case 12:
      continue;
    }
    printPattern(size, numberOfRows, 1, patternNumber);
  }
}

int main()
{
  int numberOfRows = 8;

  printf("Enter number of rows: ");
  scanf("%d", &numberOfRows);

  for (int patternNumber = 0; patternNumber <= 12; patternNumber++)
    generatePattern(numberOfRows * 2, patternNumber);

  return 0;
}

/***************************

Enter number of rows: 5

1.
*
* - *
* - * - *
* - * - * - *
* - * - * - * - *
* - * - * - * - * - *
* - * - * - * - *
* - * - * - *
* - * - *
* - *
*

2.
          *
        * - *
      * - * - *
    * - * - * - *
  * - * - * - * - *
* - * - * - * - * - *
  * - * - * - * - *
    * - * - * - *
      * - * - *
        * - *
          *

3.
                    *
                * - *
            * - * - *
        * - * - * - *
    * - * - * - * - *
* - * - * - * - * - *
    * - * - * - * - *
        * - * - * - *
            * - * - *
                * - *
                    *

4.
                              *
                        * - *
                  * - * - *
            * - * - * - *
      * - * - * - * - *
* - * - * - * - * - *
      * - * - * - * - *
            * - * - * - *
                  * - * - *
                        * - *
                              *
5.
*
  * - *
    * - * - *
      * - * - * - *
        * - * - * - * - *
          * - * - * - * - * - *
        * - * - * - * - *
      * - * - * - *
    * - * - *
  * - *
*

6.
           *
         *  -  *
       *  -  *  -  *
     *  -  *  -  *  -  *
   *  -  *  -  *  -  *  -  *
 *  -  *  -  *  -  *  -  *  -  *
   *  -  *  -  *  -  *  -  *
     *  -  *  -  *  -  *
       *  -  *  -  *
         *  -  *
           *

7.
                     *
                 *  -  *
             *  -  *  -  *
         *  -  *  -  *  -  *
     *  -  *  -  *  -  *  -  *
 *  -  *  -  *  -  *  -  *  -  *
     *  -  *  -  *  -  *  -  *
         *  -  *  -  *  -  *
             *  -  *  -  *
                 *  -  *
                     *

8.
          *
        * - *
      * - * - *
    * - * - * - *
  * - * - * - * - *
* - * - * - * - * - *

9.
* - * - * - * - * - *
  * - * - * - * - *
    * - * - * - *
      * - * - *
        * - *
          *
10.
*
* - *
* - * - *
* - * - * - *
* - * - * - * - *
* - * - * - * - * - *

11.
* - * - * - * - * - *
* - * - * - * - *
* - * - * - *
* - * - *
* - *
*

12.
* - * - * - * - * - *
    * - * - * - * - *
        * - * - * - *
            * - * - *
                * - *
                    *

13.
                    *
                * - *
            * - * - *
        * - * - * - *
    * - * - * - * - *
* - * - * - * - * - *

*****************************/
