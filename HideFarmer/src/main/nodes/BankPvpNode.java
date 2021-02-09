package main.nodes;

import main.Main;
import main.Node;
import main.Utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;

public class BankPvpNode extends Node {
	public BankPvpNode(Main main) {
		super(main);
	}

	@Override
	public boolean validate() {
		return main.getInventory().isFull() || main.getBank().isOpen();
	}

	@Override
	public int execute() {
		main.setCurrentState(STATE.BANK);

		if(main.getBank().isOpen()){
			boolean b = main.getBank().depositAllItems();
			MethodProvider.sleepUntil(() -> b, 10000);
			main.getBank().close();

		}else{
			main.getBank().openClosest();
		}
		return Calculations.random(2000, 4000);

	}
}
