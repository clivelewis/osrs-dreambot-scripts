package main.nodes.farmhide;

import main.Main;
import main.Node;
import main.Utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.wrappers.interactive.NPC;

/**
 * @author Clive on 9/18/2019
 */
public class CowCombatNode extends Node {

    private static final String COW = "Cow";
    private static final String COW_CALF = "Cow calf";
    private static final Filter<NPC> COW_FILTER = npc -> {
        if (npc == null) return false;
        return (npc.getName().equals(COW) || npc.getName().equals(COW_CALF)) && !npc.isHealthBarVisible();
    };

    public CowCombatNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        return !main.getLocalPlayer().isInCombat();
    }

    private int getHPPercent(){
        return (100* main.getSkills().getBoostedLevels(Skill.HITPOINTS) / main.getSkills().getRealLevel(Skill.HITPOINTS));
    }

    @Override
    public int execute() {
        main.setCurrentState(STATE.FIGHT);
        main.getUtils().checkPlayersAround();

        if(getHPPercent() < 50){
            main.setCurrentState(STATE.RECOVER);

            return Calculations.random(3000, 5000);
        }

        NPC cow = main.getNpcs().closest(COW_FILTER);
        if (cow != null) {
            cow.interact("Attack");
        }
        return Calculations.random(2000, 4000);

    }
}
