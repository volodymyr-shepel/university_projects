#include <stdio.h>
#include <iostream>
#include <vector>
#include <string>



class CFileLastError {
public:
	CFileLastError() {

	};
	CFileLastError(std::string sFileName) {
		b_last_error = false;
		vOpenFile(sFileName);
	}

	void vOpenFile(std::string sFileName) {
		b_last_error = false;
		#pragma warning(suppress : 4996)
		pf_file = fopen(sFileName.c_str(), "w+");


	}

	~CFileLastError() {
		b_last_error = false;

		if (pf_file != NULL) {

			fclose(pf_file);
		}
		else {
			delete pf_file;
		}

	}

	void vPrintLine(std::string sText) {
		b_last_error = false;
		if (pf_file != NULL) {
			fprintf(pf_file, sText.c_str());
			fprintf(pf_file, "\n");
		}
		else {
			b_last_error = true;
		}

	}


	void vPrintManyLines(std::vector<std::string> sText) {
		b_last_error = false;
		if (pf_file != NULL) {
			for (int i = 0; i < sText.size(); i++) {
				fprintf(pf_file, sText[i].c_str());
				fprintf(pf_file, "\n");
			}

		}
		else {
			b_last_error = true;

		}
	}
	bool bGetLastError() {
		return(b_last_error);
	}
private:
	FILE* pf_file;
	bool b_last_error;

};