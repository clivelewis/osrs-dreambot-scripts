package main.nodes;

import main.Main;
import main.Node;
import main.STATE;

import java.util.Random;

public class ShortBreakNode extends Node {

	public ShortBreakNode(Main main) {
		super(main);
	}

	@Override
	public boolean validate() {
		int number = new Random().nextInt(1000) + 1;
		return number == 1;
	}

	@Override
	public int execute() {
		main.currentState = STATE.AFK;

		main.getMouse().moveMouseOutsideScreen();
		return new Random().nextInt(30000) + 30000;
	}
}
