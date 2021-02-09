package main.nodes;

import main.Main;
import main.Node;
import main.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

import java.util.Random;


public class InventoryCheckNode extends Node {

	private final Area BANK_AREA = new Area(3098, 3494, 3095, 3497);
	private boolean firstExecute = true;


	public InventoryCheckNode(Main main) {
		super(main);
	}

	@Override
	public boolean validate() {
		return !main.getLocalPlayer().isAnimating() && (!main.getInventory().contains(main.MOULD) ||
						!main.getInventory().contains(main.GEM) ||
						!main.getInventory().contains(main.GOLD_BAR));
	}

	@Override
	public int execute() {
		main.currentState = STATE.BANKING;
		if(firstExecute){
			firstExecute = false;
			int smallPause = new Random().nextInt(10) + 1;
			if(smallPause == 1){
				return Calculations.random(1000, 15000);
			}else{
				return Calculations.random(500, 3000);
			}
		}

		if(main.getBank().isOpen()){
			main.getBank().depositAllExcept(item -> item != null && item.getName().equals(main.MOULD));

			if(!main.getInventory().contains(main.MOULD)) main.getBank().withdraw(main.MOULD);
			if(!main.getInventory().contains(main.GEM)) main.getBank().withdraw(main.GEM, 13);
			if(!main.getInventory().contains(main.GOLD_BAR)) main.getBank().withdraw(main.GOLD_BAR, 13);
			MethodProvider.sleep(1000, 4000);
			main.getBank().close();
			firstExecute = true;
			return 500;

		}else{
			if(BANK_AREA.contains(main.getLocalPlayer())){
				main.getGameObjects().closest(g -> g.getID() == 10355).interact();
			}else{
				main.getWalking().walk(BANK_AREA.getRandomTile());
				MethodProvider.sleepUntil(() -> BANK_AREA.contains(main.getLocalPlayer()), 6000);
				if(BANK_AREA.contains(main.getLocalPlayer())){
					main.getGameObjects().closest(g -> g.getID() == 10355).interact();
				}
				return Calculations.random(1000, 2500);
			}

			firstExecute = false;
		}
		return Calculations.random(2000, 3500);
	}
}
