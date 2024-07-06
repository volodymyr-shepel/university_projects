#include <iostream>
#include <vector>

class CNodeDynamic
{
public:
	CNodeDynamic() { 
		i_val = 0; 
	};

	~CNodeDynamic() {
		for (int i = 0; i < iGetChildrenNumber(); i++) {
			delete pcGetChild(i);
		}
		v_children.clear(); 

	};
	void vSetValue(int iNewVal) { 
		i_val = iNewVal; 
	};
	int iGetChildrenNumber() { 
		return (v_children.size()); 
	};

	void vAddNewChild() {
		v_children.push_back(new CNodeDynamic());
	};

	CNodeDynamic* pcGetChild(int iChildOffset) {
		if (v_children[iChildOffset] == NULL) {
			return NULL;
		}
		return v_children[iChildOffset];
	};

	void vPrint() { 
		std::cout << " " << i_val; 
	};
	void vPrintAllBelow() {
		
		std::cout << "Node " << getVal() << ":";
		for (int i = 0; i < iGetChildrenNumber(); i++) {
			std::cout << " " << v_children[i]->i_val;
		}
		std::cout << std::endl;
		for (int i = 0; i < iGetChildrenNumber(); i++) {
			pcGetChild(i)->vPrintAllBelow();
		}
	};
	int getVal() {
		return i_val;
	}
	
	bool bMoveSubtree(CNodeDynamic* pcParentNode, CNodeDynamic* pcNewChildNode, CNodeDynamic* pc2ParentNode) {
		pcParentNode->v_children.push_back(pcNewChildNode);
		for (int i = 0; i < pc2ParentNode->iGetChildrenNumber(); i++) {
			if (pc2ParentNode->pcGetChild(i) == (pcNewChildNode)) {
				pc2ParentNode->v_children.erase(pc2ParentNode->v_children.begin() + i);
				return true;
			}
		}
		return false;
	}

	std::vector<CNodeDynamic*> v_children;
private:
	int i_val;
};