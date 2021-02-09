package main.nodes.statsfarm;

import main.Main;
import main.Node;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.wrappers.items.GroundItem;

/**
 * @author Clive on 9/27/2019
 */
public class PickupFeathersNode extends Node {

    public PickupFeathersNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        if(main.getLocalPlayer().isInCombat()) return false;
        return main.getGroundItems().closest("Feather") != null && main.getLocalPlayer()
                .distance(main.getGroundItems().closest("Feather")) < 5;
    }

    @Override
    public int execute() {
        GroundItem hide = main.getGroundItems().closest("Feather");
        if(hide != null) hide.interact("Take");
        return Calculations.random(500, 2000);
    }
}
