#pragma once
#include<iostream>
#include<vector>

template<typename T> class CNodeDynamic
{
public:
	CNodeDynamic(){ i_val = {}; };

	~CNodeDynamic();
	void vSetValue(T iNewVal){ i_val = iNewVal; };
	int iGetChildrenNumber(){ return (v_children.size()); };

	void vAddNewChild(){ v_children.push_back(new CNodeDynamic<T>()); };

	CNodeDynamic* pcGetChild(int iChildOffset);

	void vPrint(){ std::cout << " " << i_val; };
	void vPrintAllBelow();
	T getVal(){ return i_val; };

	
	
private:
	std::vector<CNodeDynamic*> v_children;
	T i_val;
};




template <typename T> class CTreeDynamic
{
public:
	CTreeDynamic() { pc_root = new CNodeDynamic<T>(); };
	~CTreeDynamic() { pc_root->~CNodeDynamic<T>(); };

	CNodeDynamic<T>* pcGetRoot() { return pc_root; };
	void vPrintTree() { pc_root->vPrintAllBelow(); };
	bool bMoveSubtree(CNodeDynamic<T>* pcParentNode, CNodeDynamic<T>* pcNewChildNode, CNodeDynamic<T>* pc2ParentNode);

private:
	CNodeDynamic<T>* pc_root;
};

template <typename T>
CNodeDynamic<T>::~CNodeDynamic()
{
	for (int i = 0; i < iGetChildrenNumber(); i++) {
		delete pcGetChild(i);
	}
	v_children.clear();
}




template <typename T>
CNodeDynamic<T>* CNodeDynamic<T> ::pcGetChild(int iChildOffset) {
	if (v_children[iChildOffset] == NULL) {
		return NULL;
	}
	return v_children[iChildOffset];
};


template <typename T>
void CNodeDynamic<T> ::vPrintAllBelow() {
	std::cout << "Node " << getVal() << ":";
	for (int i = 0; i < iGetChildrenNumber(); i++) {
		std::cout << " " << v_children[i]->i_val;
	}
	std::cout << std::endl;
	for (int i = 0; i < iGetChildrenNumber(); i++) {
		pcGetChild(i)->vPrintAllBelow();
	}
}
// -----------------------------------------------




