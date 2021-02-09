package main.nodes.antiban;

import main.Main;
import main.Node;
import main.utils.STATE;
import org.dreambot.api.input.Mouse;

import java.util.Random;

/**
 * @author Clive on 9/22/2019
 */
public class ShortBreakNode extends Node {

    public ShortBreakNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        int number = new Random().nextInt(1000) + 1;
        return number == 1;
    }

    @Override
    public int execute() {
        main.setCurrentState(STATE.SHORT_PAUSE);
        Mouse.moveMouseOutsideScreen();
        return new Random().nextInt(30000) + 30000;
    }
}
