package main.nodes.farmhide;

import main.Main;
import main.Node;
import main.Utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.wrappers.items.GroundItem;

/**
 * @author Clive on 9/18/2019
 */
public class PickupHideNode extends Node {
    public PickupHideNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        if(main.getLocalPlayer().isInCombat()) return false;
        return main.getGroundItems().closest("Cowhide") != null && main.getLocalPlayer()
                .distance(main.getGroundItems().closest("Cowhide")) < 5;

    }

    @Override
    public int execute() {

        GroundItem hide = main.getGroundItems().closest("Cowhide");
        if(hide != null) hide.interact("Take");
        return Calculations.random(500, 2000);

    }


}
