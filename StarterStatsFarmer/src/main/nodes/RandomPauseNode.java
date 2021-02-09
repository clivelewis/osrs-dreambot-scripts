package main.nodes;

import main.Main;
import main.Node;
import main.STATE;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;

import java.util.Random;

public class RandomPauseNode extends Node {

	public RandomPauseNode(Main main) {
		super(main);
	}

	@Override
	public boolean validate() {
		return new Random().nextInt(300) < 5;
	}

	@Override
	public int execute() {
		main.state = STATE.SHORT_PAUSE;
		Mouse.moveMouseOutsideScreen();
		return Calculations.random(10000, 60000);
	}
}
