package main.nodes.statsfarm;

import main.Main;
import main.Node;
import main.Utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;

/**
 * @author Clive on 9/25/2019
 */
public class WalkToChickensNode extends Node {
    private static final Area CHICKEN_AREA = new Area(3171, 3290, 3183, 3301);


    public WalkToChickensNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        return !CHICKEN_AREA.contains(main.getLocalPlayer()) && !main.getLocalPlayer().isInCombat();
    }

    @Override
    public int execute() {
        main.setCurrentState(STATE.WALK);
        if(main.getUtils().gotMinimalStats()){
            main.setHideFarmNodes();
            return 1000;
        }

        main.getWalking().walk(CHICKEN_AREA.getRandomTile());
        return Calculations.random(2000, 4000);


    }
}
