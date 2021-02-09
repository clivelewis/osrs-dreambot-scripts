package main.nodes;

import main.Main;
import main.Node;
import main.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.walking.impl.Walking;

/**
 * @author Clive on 9/21/2019
 */
public class WalkNode extends Node {
    private static final Area TREE_AREA = new Area(3177, 3237, 3199, 3245);
    private static final Area FISH_AREA = new Area(3240, 3149, 3245, 3155);
    private static final Area MINE_AREA = new Area(3222, 3149, 3231, 3145);


    public WalkNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        if(main.currentSkill == Skill.WOODCUTTING) return !TREE_AREA.contains(main.getLocalPlayer());
        if(main.currentSkill == Skill.FISHING) return !FISH_AREA.contains(main.getLocalPlayer());
        if(main.currentSkill == Skill.MINING) return !MINE_AREA.contains(main.getLocalPlayer());
        return false;
    }

    @Override
    public int execute() {
        main.state = STATE.WALKING;

        if(main.currentSkill == Skill.WOODCUTTING) Walking.walk(TREE_AREA.getRandomTile());
        if(main.currentSkill == Skill.FISHING) Walking.walk(FISH_AREA.getRandomTile());
        if(main.currentSkill == Skill.MINING) Walking.walk(MINE_AREA.getRandomTile());
        return Calculations.random(1000, 5000);
    }
}
