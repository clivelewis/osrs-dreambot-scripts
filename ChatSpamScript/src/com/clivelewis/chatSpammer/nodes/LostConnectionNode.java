package com.clivelewis.chatSpammer.nodes;

import com.clivelewis.chatSpammer.Main;
import com.clivelewis.chatSpammer.Node;

/**
 * @author Clive on 10/9/2019
 */
public class LostConnectionNode extends Node {

    public LostConnectionNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public int execute() {
        return 0;
    }
}
