#include <iostream>
#include <vector>



class CNodeStatic {

public:
	CNodeStatic() {
		i_val = 0;
	};
	~CNodeStatic() {
		
		for (int i = 0; i < iGetChildrenNumber(); i++) {
			pcGetChild(i)->~CNodeStatic();
		}
		v_children.clear();
		
		
	};
	int getDepth() {
		CNodeStatic* node = parentNode;
		int depth = 0;
		while (node != NULL) {
			depth += 1;
			node = node->parentNode;
		}
		return depth;
	}

	void vSetValue(int iNewVal) {
		i_val = iNewVal;
	};

	int iGetChildrenNumber() {
		return v_children.size();
	};

	void vAddNewChild() {

		CNodeStatic node;
		node.parentNode = this;
		v_children.push_back(node);

	};
	void vPrintUp() {

		vPrint();

		if (parentNode != NULL) {
			parentNode->vPrintUp();
		}
	}
	CNodeStatic* pcGetChild(int iChildOffset) {
		
		if (iChildOffset >= iGetChildrenNumber()) {
			return NULL;
		}
		return &(v_children[iChildOffset]);
	};

	void vPrint() {
		std::cout << " " << i_val;
	};
	void vPrintAllBelow() {
		
		std::cout << "Node " << getVal() << ":";
		for(int i = 0; i < iGetChildrenNumber(); i++) {
			std::cout << " " << v_children[i].i_val;
		}
		std::cout << std::endl;
		for (int i = 0; i < iGetChildrenNumber(); i++) {
			pcGetChild(i)->vPrintAllBelow();
		}
	};
	int getVal() {
		return i_val;
	};

	std::vector<CNodeStatic> v_children;
	CNodeStatic* parentNode = NULL;

private:
	
	int i_val;
	


};