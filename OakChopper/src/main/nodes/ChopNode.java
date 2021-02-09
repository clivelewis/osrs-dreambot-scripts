package main.nodes;

import main.Main;
import main.Node;
import main.utils.STATE;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.interactive.GameObject;

/**
 * @author Clive on 9/21/2019
 */
public class ChopNode extends Node {

	public ChopNode(Main main) {
		super(main);
	}

	@Override
	public boolean validate() {

		return !main.getLocalPlayer().isAnimating();
	}

	@Override
	public int execute() {
		main.state = STATE.WOODCUTTING;
		main.getUtils().checkPlayersAround();
		main.getUtils().checkForMods();

		final GameObject oakTree = GameObjects.closest(g -> g.getName().equals("Oak") && g.getTile().equals(new Tile(3106, 3247)));

		if (oakTree != null) {
			oakTree.interact();
			MethodProvider.sleep(Calculations.random(500, 1500));
			Mouse.moveMouseOutsideScreen();
			return 5000;
		} else {
			return 2500;
		}

	}
}