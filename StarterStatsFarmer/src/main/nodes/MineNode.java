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

public class MineNode extends Node {

	public MineNode(Main main) {
		super(main);
	}

	@Override
	public boolean validate() {

		return !main.getLocalPlayer().isAnimating() && main.currentSkill == Skill.MINING;
	}

	@Override
	public int execute() {
		main.state = STATE.MINING;

		MethodProvider.sleep(Calculations.random(500, 2000));
		GameObjects.closest("Rocks").interact("Mine");

		int nextInt = new Random().nextInt(10) + 1;
		if(nextInt == 10) Mouse.moveMouseOutsideScreen();

		return Calculations.random(1000, 3000);
	}
}
