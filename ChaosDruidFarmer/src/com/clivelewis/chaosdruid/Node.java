package com.clivelewis.chaosdruid;

/**
 * @author Clive on 10/20/2019
 */
public abstract class Node {
    protected final Main main;

    public Node(Main main){
        this.main = main;
    }
    public abstract boolean validate();
    public abstract int execute();
}
