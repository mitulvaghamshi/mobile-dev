#include <stdint.h>
#include <cctype>
#include <cstring>

#ifdef WIN32
#define EXPORT __declspec(dllexport)
#else
#define EXPORT extern "C" __attribute__((visibility("default"))) __attribute__((used))
#endif

EXPORT
int32_t addition(int32_t x, int32_t y)
{
    return x + y;
}

EXPORT
char *capitalize(char *str)
{
    static char buffer[1024];
    strcpy(buffer, str);
    buffer[0] = toupper(buffer[0]);
    return buffer;
}

EXPORT
char *pattern(char *rows)
{
    static char buffer[100];
    int size = (rows[0] - '0') * 10 + (rows[1] - '0');
    int k = 0;
    for (size_t i = 0; i < size; i++)
    {
        for (size_t j = 0; j < size; j++)
        {
            buffer[k++] = '#';
        }
        buffer[k++] = '\n';
    }
    return buffer;
}
