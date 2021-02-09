package main.nodes;

import main.Main;
import main.Node;
import main.Utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Area;

public class WalkToPvpBankNode extends Node {
	private final Area PVP_BANK_AREA = new Area(0,0,0,0);
	public WalkToPvpBankNode(Main main) {
		super(main);
	}
	@Override
	public boolean validate() {
		return main.getInventory().isFull() && !PVP_BANK_AREA.contains(main.getLocalPlayer());
	}

	@Override
	public int execute() {
		main.setCurrentState(STATE.WALK);
		main.getWalking().walk(PVP_BANK_AREA.getRandomTile());
		return Calculations.random(2000, 6000);


	}
}

