// Practice.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include "Student.cpp"

int main()
{
    Node<int> myNode(10, 15, 20);

    //std::cout << myNode.getLeft() << std::endl;
    //std::cout << myNode.getRight() << std::endl;
    Person* p1;

    base* p;
    derived obj1;
    p = &obj1;
    p->fun_1();

    // Late binding (RTP)
    p->fun_2();

    // Late binding (RTP)
    p->fun_3();

    // Late binding (RTP)
    p->fun_4();

}

