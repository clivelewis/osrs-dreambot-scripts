package com.clivelewis.chaosdruid.nodes.antiban;

import com.clivelewis.chaosdruid.Main;
import com.clivelewis.chaosdruid.Node;
import com.clivelewis.chaosdruid.utils.STATE;

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
		main.currentState = STATE.SHORT_BREAK;
		main.getMouse().moveMouseOutsideScreen();
		return new Random().nextInt(30000) + 30000;
	}
}