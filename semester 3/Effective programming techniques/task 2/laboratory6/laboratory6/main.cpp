#include <iostream>
#include "CTreeDynamic.h"
#include <string>



int main() {
	CTreeDynamic<int> tree1;
	CNodeDynamic<int>* c_root1 = tree1.pcGetRoot();
	
	std::cout << "Tree with int" << std::endl;
	c_root1->vSetValue(0);
	c_root1->vAddNewChild();
	c_root1->vAddNewChild();
	c_root1->pcGetChild(0)->vSetValue(1);
	c_root1->pcGetChild(1)->vSetValue(2);
	c_root1->pcGetChild(0)->vAddNewChild();
	c_root1->pcGetChild(0)->vAddNewChild();
	c_root1->pcGetChild(0)->pcGetChild(0)->vSetValue(11);
	c_root1->pcGetChild(0)->pcGetChild(1)->vSetValue(12);
	c_root1->pcGetChild(1)->vAddNewChild();
	c_root1->pcGetChild(1)->vAddNewChild();
	c_root1->pcGetChild(1)->pcGetChild(0)->vSetValue(21);
	c_root1->pcGetChild(1)->pcGetChild(1)->vSetValue(22);

	
	c_root1->vPrintAllBelow();

	std::cout << "------------------" << std::endl;
	std::cout << "Tree with string" << std::endl;

	CTreeDynamic<std::string> tree2;
	CNodeDynamic<std::string>* c_root2 = tree2.pcGetRoot();

	
	c_root2->vSetValue("Root node");
	c_root2->vAddNewChild();
	c_root2->vAddNewChild();
	c_root2->pcGetChild(0)->vSetValue("child1");
	c_root2->pcGetChild(1)->vSetValue("child2");
	c_root2->pcGetChild(0)->vAddNewChild();
	c_root2->pcGetChild(0)->vAddNewChild();
	c_root2->pcGetChild(0)->pcGetChild(0)->vSetValue("child3 ");
	c_root2->pcGetChild(0)->pcGetChild(1)->vSetValue("child4");
	c_root2->pcGetChild(1)->vAddNewChild();
	c_root2->pcGetChild(1)->vAddNewChild();
	c_root2->pcGetChild(1)->pcGetChild(0)->vSetValue("child5");
	c_root2->pcGetChild(1)->pcGetChild(1)->vSetValue("child6");

	c_root2->vPrintAllBelow();
	


}