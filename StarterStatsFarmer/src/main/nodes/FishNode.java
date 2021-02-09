package main.nodes;

import main.Main;
import main.Node;
import main.STATE;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.skills.Skill;

import java.util.Random;

public class FishNode extends Node {

	public FishNode(Main main) {
		super(main);
	}

	@Override
	public boolean validate() {
		return !main.getLocalPlayer().isAnimating() && main.currentSkill == Skill.FISHING;
	}

	@Override
	public int execute() {
		main.state = STATE.FISHING;

		NPCs.closest("Fishing spot").interact("Net");
		MethodProvider.sleep(Calculations.random(500, 2000));
		Mouse.moveMouseOutsideScreen();
		return 5000;
	}
}
