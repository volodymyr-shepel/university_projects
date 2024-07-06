#include <iostream>
#include <vector>

#include "CNodeStatic.cpp"


class CTreeStatic
{
public:
	CTreeStatic() {
		c_root = CNodeStatic();
	};
	~CTreeStatic() {
		c_root.~CNodeStatic();
	};
	CNodeStatic* pcGetRoot() { 
		return(&c_root); 
	}
	void vPrintTree() {
		c_root.vPrintAllBelow();
	};

	bool bMoveSubtree(CNodeStatic* pcParentNode, CNodeStatic* pcNewChildNode, CNodeStatic* pc2ParentNode) {
		
		
		for (int i = 0; i < pc2ParentNode->iGetChildrenNumber(); i++) {
			if (pc2ParentNode->pcGetChild(i) == (pcNewChildNode)) {
				
				pcParentNode->v_children.push_back(*pcNewChildNode);
				pcNewChildNode->parentNode = pcParentNode;
				
				pc2ParentNode->v_children.erase(pc2ParentNode->v_children.begin() + i);
				
				return true;
			}
		}
		return false;
	}
private:
	CNodeStatic c_root;
};