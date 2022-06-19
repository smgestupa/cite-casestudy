package com.college.casestudy.model;

import com.college.casestudy.model.parent.Account;

public class Node {

    public Node left, right;
    final public Account value;

    public Node( Account value ) {
        this.value = value;
        left = null;
        right = null;
    }
}
