#include<iostream>

using namespace std;

void task_1(int iSize) {

	if (iSize < 0 || iSize > 0xffffffff) {
		cout << "Incorrect size of the array entered";

	}

	int* arr;
	arr = new int[iSize + 5];
	
	for (int i = 0; i < iSize; i++) {
		arr[i] = i;
	}
	for (int i = 0; i < iSize; i++) {
		cout << arr[i] << endl;
	}
	delete[] arr;

}

bool b_alloc_table_2_dim(int*** piTable, int iSizeX, int iSizeY)
{
	if (iSizeX < 0 || iSizeY < 0 || iSizeX > 0xffffffff || iSizeY > 0xffffffff) {
		return false;
	}
	
	(*piTable) = new int* [iSizeX];

	for (int i = 0; i < iSizeX; i++)
		(*piTable)[i] = new int[iSizeY];
		return true;
	
}
bool b_dealloc_table_2_dim(int*** piTable, int iSizeX)
{
	if (iSizeX > 0)
	{
		for (int i = 0; i < iSizeX; i++)
			
			delete[] (*piTable)[i];
		
		delete[] (*piTable);

		return true;
	}
	else return false;
}

int main() {
	int* foo;
	foo = new int[5];
	
	task_1(4);
	int** arr;
	int*** ptr_arr = &arr;
	
    b_alloc_table_2_dim(ptr_arr,3,5);
	for(int i =0 ; i < 3;i++){
        for(int j =0;j<5;j++){
            arr[i][j] = i*j;
            cout << arr[i][j] << " ";
        }
        cout << endl;
    }
    b_dealloc_table_2_dim(ptr_arr, 3);

    

	return 0;
}