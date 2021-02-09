package main.nodes;

import main.Main;
import main.Node;
import main.STATE;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.skills.Skill;

import java.util.Random;

/**
 * @author Clive on 9/21/2019
 */
public class ChopNode extends Node {

    public ChopNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {

        return !main.getLocalPlayer().isAnimating() && main.currentSkill == Skill.WOODCUTTING;
    }

    @Override
    public int execute() {
        main.state = STATE.WOODCUTTING;
        GameObjects.closest("Tree").interact("Chop down");
        MethodProvider.sleep(Calculations.random(500, 2000));
        int nextInt = new Random().nextInt(10) + 1;
        if(nextInt == 10) Mouse.moveMouseOutsideScreen();
        return 5000;
    }
}