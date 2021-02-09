package main.nodes;

import main.Main;
import main.Node;
import main.utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.walking.impl.Walking;

/**
 * @author Clive on 9/21/2019
 */
public class WalkNode extends Node {
    private static final Area TREE_AREA = new Area(3107, 3245, 3109, 3250);
    private static final Tile TREE_TILE = new Tile(3107, 3250, 0);
    private static final Area FISH_AREA = new Area(3240, 3149, 3245, 3155);
    private static final Area MINE_AREA = new Area(3222, 3149, 3231, 3145);


    public WalkNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
         return !TREE_AREA.contains(main.getLocalPlayer());
    }

    @Override
    public int execute() {
        main.state = STATE.WALKING;

        Walking.walk(TREE_TILE);
        return Calculations.random(1000, 5000);
    }
}
