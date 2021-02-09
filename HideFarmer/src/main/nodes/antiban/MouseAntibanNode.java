package main.nodes.antiban;

import main.Main;
import main.Node;

import java.awt.*;
import java.util.Random;

/**
 * @author Clive on 9/22/2019
 */
public class MouseAntibanNode extends Node{


    public MouseAntibanNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public int execute() {
        main.getMouse().move(new Point(main.getMouse().getX() + new Random().nextInt(3) - 1,
                main.getMouse().getY() + new Random().nextInt(3) - 1));
        return 100;
    }
}
