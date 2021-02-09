package com.clivelewis.chaosdruid.nodes.walking;

import com.clivelewis.chaosdruid.Main;
import com.clivelewis.chaosdruid.Node;
import com.clivelewis.chaosdruid.utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

public class GoToBankNode extends Node {
	private Tile dungeonExitTile = new Tile(3096, 9867, 0);
	private Tile dungeonExitStairsTile = new Tile(3097, 9867, 0);
	private Area dungeonExitArea = new Area(3098, 9869, 3096, 9867);

	public GoToBankNode(Main main) {
		super(main);
	}

	@Override
	public boolean validate() {
		return main.getInventory().isFull() && main.isInsideDungeon;
	}

	@Override
	public int execute() {
		main.currentState = STATE.WALK_TO_BANK;

		if(!dungeonExitArea.contains(main.getLocalPlayer())){
			main.getWalking().walk(dungeonExitTile);
			Main.log("Walking to bank (in dungeon)");
		}else{
//			Optional<GameObject> ladder = main.getGameObjects().all().stream().filter(g -> g.getRealID() == 17385).findFirst();
//			Main.log(String.valueOf(ladder.get().getTile().getY()));
//			Main.log(String.valueOf(ladder.get().getTile().getX()));
			main.getMouse().drag(dungeonExitStairsTile);
			MethodProvider.sleep(2000);
			main.getMouse().click();

		}
		return Calculations.random(2000, 5000);
	}
}
