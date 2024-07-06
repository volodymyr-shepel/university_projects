#include <iostream>
#include "Ctable.cpp"

using namespace std;

int main() {
    cout << "\n" << endl;
    CTable c_table1;
    c_table1.displayArray();

    CTable c_table2("Assigned Name", 15);
    c_table2.displayArray();

    CTable c_table3(c_table1);
    c_table3.displayArray();

    c_table1.bSetNewSize(15);
    cout << c_table1.getArraySize() << endl;
    //c_table1.displayArray();

    

	
	return 0;
}


