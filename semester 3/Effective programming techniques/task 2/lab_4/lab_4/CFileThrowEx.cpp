#include <stdio.h>
#include <iostream>
#include <vector>
#include <string>


class CFileThrowEx
{
public:
	CFileThrowEx() {

	};
	
	CFileThrowEx(std::string sFileName) {
		vOpenFile(sFileName);
		
	};

	void vOpenFile(std::string sFileName) {
		#pragma warning(suppress : 4996)
		pf_file = fopen(sFileName.c_str(), "w+");
	};

	~CFileThrowEx() {
		
		if (pf_file != NULL) {
			fclose(pf_file);
		}
		else {
			delete pf_file;
		}
		
	};

	void vPrintLine(std::string sText) {
		
		try {
			if (pf_file == NULL) {
				throw 993;
			}
			fprintf(pf_file, sText.c_str());
			fprintf(pf_file, "\n");
			std::cout << "No exception" << std::endl;
		}
		catch (int e) {
			std::cout << "An exception # " << e << " thrown" <<  std::endl;
		}
	};


	void vPrintManyLines(std::vector<std::string> sText) {
		
		for (int i = 0; i < sText.size(); i++) {
			vPrintLine(sText[i].c_str());
		}
	};
private:
	FILE* pf_file;
};

