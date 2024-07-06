#include <iostream>
#include "CFileLastError.cpp"

int main()
{
    std::string filePath = "C:\\Users\\vshep\Desktop\\PWR\\Effective programming techniques\\lab 4\\test_file.txt";
    CFileLastError obj(filePath);
    return 0;
}
