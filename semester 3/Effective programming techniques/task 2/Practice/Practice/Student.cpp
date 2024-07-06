#include <iostream>
#include <string>

class Person {
public:
	void introduce() {
		std::cout << "Hello Jennifer" << std::endl;
	};
	void walk() {
		std::cout << "Godlike speed" << std::endl;
	};
};

class Student : Person {
public: 
	std::string firstName;
	std::string lastName;
	
	Student(std::string fName, std::string lName) : firstName(fName), lastName(lName){};
	
	void introduce() {
		std::cout << "Hello world" << std::endl;
	}
	void walk() {
		std::cout << "Amaterasu" << std::endl;
	}
};


template<typename T>

class Node {
public:
	T leftValue;
	T rightValue;
	T value;

	Node(T lValue, T rValue, T val) : leftValue(lValue), rightValue(rValue), value(val) {};

	T getValue() {
		return value;
	}
	T getLeft() {
		return leftValue;
	}
	T getRight() {
		return rightValue;
	}
};

class base {
public:
	void fun_1() { std::cout << "base-1\n"; }
	virtual void fun_2() { std::cout << "base-2\n"; }
	virtual void fun_3() { std::cout << "base-3\n"; }
	virtual void fun_4() { std::cout << "base-4\n"; }
};

class derived : public base {
public:
	void fun_1() { std::cout << "derived-1\n"; }
	void fun_2() { std::cout << "derived-2\n"; }
	void fun_4(int x) { std::cout << "derived-4\n"; }
};

