package main.nodes.farmhide;

import main.Main;
import main.Node;
import main.Utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;


public class WalkToCowsNode extends Node {
    private static final Area COW_AREA = new Area(3194, 3300, 3212, 3283);


    public WalkToCowsNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        return !COW_AREA.contains(main.getLocalPlayer()) && !main.getLocalPlayer().isInCombat();
    }

    @Override
    public int execute() {
        main.setCurrentState(STATE.WALK);
        if(main.getBank().isOpen()) main.getBank().close();

        main.getWalking().walk(COW_AREA.getRandomTile());
        return Calculations.random(100, 4000);


    }
}
