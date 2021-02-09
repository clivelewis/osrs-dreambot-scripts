package com.clivelewis.chaosdruid.nodes;

import com.clivelewis.chaosdruid.Main;
import com.clivelewis.chaosdruid.Node;
import com.clivelewis.chaosdruid.utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.wrappers.interactive.NPC;

/**
 * @author Clive on 10/20/2019
 */
public class CombatNode extends Node {

    private static final String DRUID = "Chaos druid";
    private static final Filter<NPC> DRUID_FILTER = npc -> {
        if (npc == null) return false;
        return npc.getName().equals(DRUID) && !npc.isHealthBarVisible();
    };

    public CombatNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        return !main.getLocalPlayer().isInCombat();
    }

    @Override
    public int execute() {
        main.currentState = STATE.FIGHT;

        main.getUtils().checkPlayersAround();
        NPC druid = main.getNpcs().closest(DRUID_FILTER);
        if (druid != null) {
            druid.interact("Attack");
        }
        return Calculations.random(2000, 4000);
    }
}
