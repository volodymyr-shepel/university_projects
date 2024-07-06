#include <iostream>
#include "CFileLastError.cpp"
#include "CFileErrCode.cpp"
#include "CFileThrowEx.cpp"
#include <string>
int main()
{
    std::string filePath = "C:\\Users\\vshep\\Desktop\\PWR\\Effective programming techniques\\lab 4\\test_file.txt";
    std::string writeLine = "Hello,it's me. I was wondering if after all these years you'd like to meet";
    std::vector<std::string> writeVector = { "To go over everything","There's such a difference between us" };
    
    
    CFileLastError lastError(writeLine);
    CFileLastError invelidLastError;

    CFileThrowEx throwEx(filePath);
    CFileThrowEx invalidThrowEx;
    
    CFileErrCode errorCode(filePath);
    CFileErrCode invalidErrorCode;

    
    

    
    std::cout << "Last error:(0 - no error, 1 - error occured)" << std::endl;
    
    invelidLastError.vPrintLine(writeLine);
    std::cout << invelidLastError.bGetLastError() << std::endl;

    invelidLastError.vOpenFile(filePath);
    invelidLastError.vPrintLine(writeLine);
    std::cout << invelidLastError.bGetLastError() << std::endl;

    lastError.vPrintLine(writeLine);
    std::cout << invelidLastError.bGetLastError() << std::endl;

    std::cout << "error code(0 - false, 1 - true):" << std::endl;

    std::cout << invalidErrorCode.bPrintLine(writeLine) << std::endl;
    

    invalidErrorCode.bOpenFile(filePath);
    std::cout << invalidErrorCode.bPrintLine(writeLine) << std:: endl;
    

    std :: cout << errorCode.bPrintLine(writeLine) << std::endl;
    
    std::cout << "Exceptions:" << std::endl;

    invalidThrowEx.vPrintLine(writeLine);


    invalidThrowEx.vOpenFile(filePath);
    invalidThrowEx.vPrintLine(writeLine);
    throwEx.vPrintLine(writeLine);

    CFileLastError obj1;

    CFileThrowEx obj2;

    CFileErrCode obj3;

    
 
    return 0;
}
