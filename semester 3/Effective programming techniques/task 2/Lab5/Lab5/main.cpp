#include <iostream>
//#include "CNodeStatic.cpp"
#include "CTreeStatic.cpp"
#include "CTreeDynamic.cpp"


void task2_test()
{
	CNodeStatic c_root;
	
	c_root.vAddNewChild();
	c_root.vAddNewChild();
	c_root.pcGetChild(0)->vSetValue(1);
	c_root.pcGetChild(1)->vSetValue(2);
	c_root.pcGetChild(0)->vAddNewChild();
	c_root.pcGetChild(0)->vAddNewChild();
	c_root.pcGetChild(0)->pcGetChild(0)->vSetValue(11);
	c_root.pcGetChild(0)->pcGetChild(1)->vSetValue(12);
	c_root.pcGetChild(1)->vAddNewChild();
	c_root.pcGetChild(1)->vAddNewChild();
	c_root.pcGetChild(1)->pcGetChild(0)->vSetValue(21);
	c_root.pcGetChild(1)->pcGetChild(1)->vSetValue(22);

	//c_root.vPrintAllBelow();
	//std::cout << "------------------------" << std::endl;
	//c_root.pcGetChild(1)->pcGetChild(1)->vPrintUp();
	
	//std::cout << std::endl;
	CNodeStatic* node = c_root.pcGetChild(1)->pcGetChild(1);

	std::cout << "Distance:" << getDistance(node, 0) << std::endl;
	//std::cout << "Distance:" << getDistance(c_root.pcGetChild(0)->pcGetChild(0)) << std::endl;
	//std::cout << "Distance:" << getDistance(c_root.pcGetChild(1)) << std::endl;
	
	
}

void v_tree_test_dynamic()
{
	CNodeDynamic c_root;

	c_root.vAddNewChild();
	c_root.vAddNewChild();
	c_root.pcGetChild(0)->vSetValue(1);
	c_root.pcGetChild(1)->vSetValue(2);
	c_root.pcGetChild(0)->vAddNewChild();
	c_root.pcGetChild(0)->vAddNewChild();
	c_root.pcGetChild(0)->pcGetChild(0)->vSetValue(11);
	c_root.pcGetChild(0)->pcGetChild(1)->vSetValue(12);
	c_root.pcGetChild(1)->vAddNewChild();
	c_root.pcGetChild(1)->vAddNewChild();
	c_root.pcGetChild(1)->pcGetChild(0)->vSetValue(21);
	c_root.pcGetChild(1)->pcGetChild(1)->vSetValue(22);

	c_root.vPrintAllBelow();
	std::cout << "------------------" << std::endl;
	c_root.pcGetChild(1)->pcGetChild(0)->vSetValue(55);
	c_root.vPrintAllBelow();
	
}
void task4_test()
{

	CTreeStatic tree;
	CNodeStatic* c_root = tree.pcGetRoot();
	
	c_root->vAddNewChild();
	c_root->vAddNewChild();
	c_root->pcGetChild(0)->vSetValue(1);
	c_root->pcGetChild(1)->vSetValue(2);

	c_root->pcGetChild(0)->vAddNewChild();
	c_root->pcGetChild(0)->vAddNewChild();

	c_root->pcGetChild(0)->pcGetChild(0)->vSetValue(11);
	c_root->pcGetChild(0)->pcGetChild(1)->vSetValue(12);

	c_root->pcGetChild(1)->vAddNewChild();
	c_root->pcGetChild(1)->vAddNewChild();

	c_root->pcGetChild(1)->pcGetChild(0)->vSetValue(21);
	c_root->pcGetChild(1)->pcGetChild(1)->vSetValue(22);

	tree.vPrintTree();

}
void task5_test() {
	CTreeDynamic tree;
	CNodeDynamic* c_root = tree.pcGetRoot();

	c_root->vAddNewChild();
	c_root->vAddNewChild();
	c_root->pcGetChild(0)->vSetValue(1);
	c_root->pcGetChild(1)->vSetValue(2);

	c_root->pcGetChild(0)->vAddNewChild();
	c_root->pcGetChild(0)->vAddNewChild();

	c_root->pcGetChild(0)->pcGetChild(0)->vSetValue(11);
	c_root->pcGetChild(0)->pcGetChild(1)->vSetValue(12);

	c_root->pcGetChild(1)->vAddNewChild();
	c_root->pcGetChild(1)->vAddNewChild();

	c_root->pcGetChild(1)->pcGetChild(0)->vSetValue(21);
	c_root->pcGetChild(1)->pcGetChild(1)->vSetValue(22);

	tree.vPrintTree();
}
void task6_testDynamic() {

	CTreeDynamic tree1;
	CNodeDynamic* root_1 = tree1.pcGetRoot();

	root_1->vAddNewChild();
	root_1->vAddNewChild();
	root_1->vAddNewChild();
	root_1->pcGetChild(0)->vSetValue(1);
	root_1->pcGetChild(1)->vSetValue(2);
	root_1->pcGetChild(2)->vSetValue(3);

	root_1->pcGetChild(2)->vAddNewChild();
	root_1->pcGetChild(2)->pcGetChild(0)->vSetValue(4);


	CTreeDynamic tree2;
	CNodeDynamic* root_2 = tree2.pcGetRoot();
	
	root_2->vSetValue(50);

	root_2->vAddNewChild();
	root_2->vAddNewChild();
	root_2->pcGetChild(0)->vSetValue(54);
	root_2->pcGetChild(1)->vSetValue(55);

	root_2->pcGetChild(0)->vAddNewChild();
	root_2->pcGetChild(0)->vAddNewChild();

	root_2->pcGetChild(0)->pcGetChild(0)->vSetValue(56);
	root_2->pcGetChild(0)->pcGetChild(1)->vSetValue(57);

	root_2->pcGetChild(0)->pcGetChild(0)->vAddNewChild();
	root_2->pcGetChild(0)->pcGetChild(0)->pcGetChild(0)->vSetValue(58);

	//tree1.vPrintTree();
	//cout << endl;
	//tree2.vPrintTree();

	tree1.bMoveSubtree(root_1->pcGetChild(2), root_2->pcGetChild(0), root_2);
	
	tree1.vPrintTree();
	std::cout << std::endl;
	tree2.vPrintTree();
}

void task6_testStatic() {

	CTreeStatic tree1;
	CNodeStatic* root_1 = tree1.pcGetRoot();

	root_1->vAddNewChild();
	root_1->vAddNewChild();
	root_1->vAddNewChild();
	root_1->pcGetChild(0)->vSetValue(1);
	root_1->pcGetChild(1)->vSetValue(2);
	root_1->pcGetChild(2)->vSetValue(3);

	root_1->pcGetChild(2)->vAddNewChild();
	root_1->pcGetChild(2)->pcGetChild(0)->vSetValue(4);



	CTreeStatic tree2;
	CNodeStatic* root_2 = tree2.pcGetRoot();

	root_2->vSetValue(50);

	root_2->vAddNewChild();
	root_2->vAddNewChild();
	root_2->pcGetChild(0)->vSetValue(54);
	root_2->pcGetChild(1)->vSetValue(55);

	root_2->pcGetChild(0)->vAddNewChild();
	root_2->pcGetChild(0)->vAddNewChild();

	root_2->pcGetChild(0)->pcGetChild(0)->vSetValue(56);
	root_2->pcGetChild(0)->pcGetChild(1)->vSetValue(57);

	root_2->pcGetChild(0)->pcGetChild(0)->vAddNewChild();
	root_2->pcGetChild(0)->pcGetChild(0)->pcGetChild(0)->vSetValue(58);

	//tree1.vPrintTree();
	//cout << endl;
	//tree2.vPrintTree();

	tree1.bMoveSubtree(root_1->pcGetChild(2), root_2->pcGetChild(0), root_2);

	tree1.vPrintTree();
	std::cout << std::endl;
	tree2.vPrintTree();
}

int main(){

	task2_test();
	//v_tree_test_dynamic();
	//task4_test();
	//task5_test();
	//task6_testStatic();
	//task6_testDynamic();
	
	
}