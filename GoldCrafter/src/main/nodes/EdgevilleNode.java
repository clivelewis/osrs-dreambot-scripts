package main.nodes;

import main.Main;
import main.Node;
import main.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Area;

public class EdgevilleNode extends Node {
	private final Area EDGEVILLE_AREA = new Area(3111,3506,3088, 3485);

	public EdgevilleNode(Main main) {
		super(main);
	}

	@Override
	public boolean validate() {
		return !EDGEVILLE_AREA.contains(main.getLocalPlayer());
	}

	@Override
	public int execute() {
		main.currentState = STATE.WALKING_TO_EDGEVILLE;

		main.getWalking().walk(EDGEVILLE_AREA.getRandomTile());
		return Calculations.random(2000, 4000);
	}
}
