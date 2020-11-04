package edu.mine268.numStructure;

import edu.mine268.exprMgr.ConstSome;

public class Node implements ConstSome {
    // 是否为子树根节点
    public boolean sealed = false;
    // 父节点
    public Node parent;
    // 子节点集合
    public Node[] children = new Node[ConstSome.CHILD_SUM];
}
