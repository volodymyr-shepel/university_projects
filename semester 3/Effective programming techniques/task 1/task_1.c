#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <string.h>

// asuumptions variables
const int numberOfPeople = 4; // 1-4
const int numberOfProducts = 5; // 1-5
const int maxNumberOfslips = 5; // 0-5
const int numberOfDaysInMonth = 30;
const int minValue = 25;
const int maxValue = 100;
char delim[] = " ";


//generate data
void generateData(){ 

    FILE *pF = fopen("data.txt","w");

    for(int i = 0; i < numberOfDaysInMonth; i++){
        
        
        for(int person = 1;person<=numberOfPeople;person++){//for every person generate data
            
            int numberOfSlips = (rand() % 5);  

            for(int k = 0; k < numberOfSlips;k++){
                
                int productNumber = (rand() % 5) + 1; // natural number between 1 and 5(included)    
                int valueOfProductSales = (rand() % 100) + 25;  // natural number between 25 and 100(included)   

                // create Line
                char line[25];
                
                sprintf (line, "%d %d %d\n",person,productNumber,valueOfProductSales); // create a line
                
                fprintf(pF,line); // write a line to the file

            }
            

        }
        

    }
    fclose(pF); 
    
}

//Draw table 

void drawTable(int dataToDisplay[][5]){
    printf("             Person 1   Person 2    Person 3    Person 4    Total\n");
    int columnSums[4] = {0};
    int rowSum;
    for(int i = 0;i<5;i++){
        rowSum = 0;
        printf("Product%d         ",i);
        for(int j =0;j<4;j++){
            rowSum+=dataToDisplay[j][i];
            printf("%-11d",dataToDisplay[j][i]);
            columnSums[j] += dataToDisplay[j][i];
        }
        printf("%d",rowSum);
        printf("\n");
    }
    printf("Total            %-11d%-11d%-11d%-11d",columnSums[0],columnSums[1],columnSums[2],columnSums[3]);
}


//read and processing the data from the file

void readAndProcessData(){
    
    int mainData[numberOfPeople][numberOfProducts];  // variable-sized object may not be initialized  ={0}
    memset(mainData, 0, sizeof mainData );
    
    
    int arrOfReadData[3];
    int iterator = 0;
    FILE *pF = fopen("data.txt","r");
    
    if(pF == NULL){
        printf("unable to open file\n");
    }
    else{
        
        char buffer[25];
        while(fgets(buffer,25,pF) != NULL){
            
            

          char *ptr = strtok(buffer, delim); // split the line

          while(ptr != NULL)
          {
            arrOfReadData[iterator] = atoi(ptr);

                

                ptr = strtok(NULL, delim); 
                
                iterator+=1;
          }
            int salesPersonNumber = arrOfReadData[0] - 1;
            int productNumber = arrOfReadData[1] - 1;
            int dolarValue = arrOfReadData[2];

            mainData[salesPersonNumber][productNumber] += dolarValue;
            iterator = 0;
            
        }
        
    }
    

    fclose(pF); 

    drawTable(mainData);

}



int main(){

    srand(time(0)); // random seed

    generateData();
    readAndProcessData();

    return 0;
    
}