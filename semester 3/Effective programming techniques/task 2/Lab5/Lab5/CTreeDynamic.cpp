#include "CNodeDynamic.cpp"

class CTreeDynamic
{
public:
	CTreeDynamic() {
		c_root = new CNodeDynamic();
	};
	~CTreeDynamic() {
		c_root->~CNodeDynamic();
	};
	CNodeDynamic* pcGetRoot() { 
		return c_root; 
	}
	void vPrintTree() {
		c_root->vPrintAllBelow();
	};
	bool bMoveSubtree(CNodeDynamic* pcParentNode, CNodeDynamic* pcNewChildNode, CNodeDynamic* pc2ParentNode) {


		for (int i = 0; i < pc2ParentNode->iGetChildrenNumber(); i++) {
			if (pc2ParentNode->pcGetChild(i) == (pcNewChildNode)) {

				pcParentNode->v_children.push_back(pcNewChildNode);
				pc2ParentNode->v_children.erase(pc2ParentNode->v_children.begin() + i);

				return true;
			}
		}
		return false;
	}
private:
	CNodeDynamic* c_root;
};