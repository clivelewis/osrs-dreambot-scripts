package com.clivelewis.chatSpammer.nodes;

import com.clivelewis.chatSpammer.Main;
import com.clivelewis.chatSpammer.Node;
import org.dreambot.api.methods.Calculations;

/**
 * @author Clive on 10/1/2019
 */
public class WalkToLocationNode extends Node {

    public WalkToLocationNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        return !main.currentSpamArea.getArea(10).contains(main.getLocalPlayer());
    }

    @Override
    public int execute() {
        main.getWalking().walk(main.currentSpamArea);
        return Calculations.random(2000, 5000);
    }
}
