package main.nodes.statsfarm;

import main.Main;
import main.Node;
import main.Utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.wrappers.interactive.NPC;

/**
 * @author Clive on 9/18/2019
 */
public class ChickenCombatNode extends Node {

    private static final String CHICKEN = "Chicken";
    private static final Filter<NPC> CHICKEN_FILTER = npc -> {
        if (npc == null) return false;
        return npc.getName().equals(CHICKEN) && !npc.isHealthBarVisible();
    };

    public ChickenCombatNode (Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        return !main.getLocalPlayer().isInCombat();
    }

    @Override
    public int execute() {
        main.setCurrentState(STATE.FIGHT);

        MethodProvider.sleep(1000);
        main.getUtils().checkPlayersAround();
        if(main.getUtils().gotMinimalStats()){
            main.setHideFarmNodes();
            return 1000;
        }

        main.getUtils().checkPlayerMinimalStats();

        NPC chicken = main.getNpcs().closest(CHICKEN_FILTER);
        if (chicken != null) {
            chicken.interact("Attack");
        }

        return Calculations.random(2000, 4000);
    }
}
