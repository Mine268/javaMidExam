package edu.mine268.numStructure.concrete;

import edu.mine268.numStructure.NumberNode;

public class Digital extends NumberNode {

    private double num = Double.NaN;

    // 返回这个是数字的值
    public double getValue() {
        return this.num;
    }

    // 根据输入创建这一个数字
    public Digital(double num) {
        this.num = num;
    }
}
