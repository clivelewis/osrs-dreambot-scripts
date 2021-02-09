package main;

/**
 * @author Clive on 9/21/2019
 */
public abstract class Node {
    protected final Main main;

    public Node(Main main){
        this.main = main;
    }
    public abstract boolean validate();
    public abstract int execute();
}
