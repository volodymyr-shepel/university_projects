#include <stdio.h>
#include <iostream>
#include <vector>
#include <string>

class CFileErrCode
{
public:
	
	CFileErrCode() {

	};
	CFileErrCode(std::string sFileName) {
		bOpenFile(sFileName);
	};
	
	~CFileErrCode() {
		
		if (pf_file != NULL) {
			fclose(pf_file);
		}
		else {
			delete pf_file;
		}
		
	};
	
	bool bOpenFile(std::string sFileName) {
		#pragma warning(suppress : 4996)
		pf_file = fopen(sFileName.c_str(), "w+");
		return true;
	};

	bool bPrintLine(std::string sText) {
		if (pf_file != NULL) {
			fprintf(pf_file, sText.c_str());
			fprintf(pf_file, "\n");
			return true;
		}
		return false;
	};
	bool bPrintManyLines(std::vector<std::string> sText) {
		if (pf_file != NULL) {
			for (int i = 0; i < sText.size(); i++) {
				fprintf(pf_file, sText[i].c_str());
				fprintf(pf_file, "\n");
			}
			return true;
		}
		return false;
	};
private:
	FILE* pf_file;
};