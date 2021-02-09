package com.clivelewis.chaosdruid.nodes.walking;

import com.clivelewis.chaosdruid.Main;
import com.clivelewis.chaosdruid.Node;
import com.clivelewis.chaosdruid.utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.util.Optional;

public class GoToDungeonNode extends Node {
	private Tile edgevilleEntrance = new Tile(3096, 3468, 0);

	public GoToDungeonNode(Main main) {
		super(main);
	}

	@Override
	public boolean validate() {
		return !main.isInsideDungeon;
	}

	@Override
	public int execute() {
		main.currentState = STATE.WALK_TO_DUNGEON;

		if(!main.getLocalPlayer().getTile().equals(edgevilleEntrance)){
			main.getWalking().walk(edgevilleEntrance);
			Main.log("Walking to dungeon entrance");
		}else{
			Main.log("Should be near Trapdoor. Trying to enter");
			Optional<GameObject> trapdoor = main.getGameObjects().all().stream().filter(g -> g.getRealID() == 1581 || g.getRealID() == 1579).findFirst();
			main.getCamera().rotateToEntity(trapdoor.get());

			if(trapdoor.get().getRealID() == 1581){
				trapdoor.get().interact("Climb-down");
			}else{
				trapdoor.get().interact("Open");
			}

		}

		return Calculations.random(2000, 5000);
	}
}
