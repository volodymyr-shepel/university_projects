#include <iostream>

using namespace std;

class CTable {
	private:

		const string defaultValueNameField = "Default";
		const int defautlSizeOfArray = 10;

		void setSize(int size) {
			arraySize = size;
		}
		void fillArray() {
			for (int i = 0; i < getArraySize(); i++) {
				array[i] = i;
			}
		}
		
	public:
		
		string tableName;
		int arraySize;
		int *array;

		void displayArray() {
			for (int i = 0; i < arraySize; i++) {
				cout << array[i] << endl;
			}
		}
		
		CTable() {
			tableName = defaultValueNameField;
			arraySize = defautlSizeOfArray;
			array = new int[arraySize];
			fillArray();
			cout << "without <" + getTableName() + ">" << endl;

		}
		CTable(string sName,int iTableLen) {
			tableName = sName;
			arraySize = iTableLen;
			array = new int[arraySize];
			fillArray();
			cout << "parameter <" + getTableName() + ">" << endl;

		}
		//destructor
		~CTable(){
			cout << "removing <" + getTableName() + ">" << endl;
			delete[] array;
		}
		
		
		CTable* pcClone() {
			
			return new CTable(*this);
		}
		CTable(CTable& pcOther) {
				
			arraySize = pcOther.getArraySize();
			tableName = pcOther.getTableName() + "_copy";
			array = new int[arraySize];
			
			for (int i = 0; i < pcOther.getArraySize(); i++) {
				array[i] = pcOther.array[i];
			}
			cout << "copy of class" << endl;

		}

		int getArraySize() {
			return arraySize;
		}
		
		string getTableName() {
			return tableName;
		}


		void vSetName(string newName) {
			tableName = newName;
		}
		
		bool bSetNewSize(int iTableLen) {
			
			if (iTableLen < 0 || iTableLen > 0xffffffff || iTableLen < getArraySize()){
				return false;
			}
			else {
				int* newArray = new int[iTableLen];
				setSize(iTableLen);

				for (int i = 0; i < getArraySize(); i++) {
					newArray[i] = array[i];
				}
				delete[] array;
				array = newArray;
				
				
				return true;
			}
		}

		void v_mod_tab(CTable* pcTab, int iNewSize) {
			pcTab->bSetNewSize(iNewSize);
		}

		void v_mod_tab(CTable cTab, int iNewSize) {
			cTab.bSetNewSize(iNewSize);
		}


};