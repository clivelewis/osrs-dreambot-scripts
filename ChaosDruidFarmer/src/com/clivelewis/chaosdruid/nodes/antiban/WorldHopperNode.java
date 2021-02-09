package com.clivelewis.chaosdruid.nodes.antiban;

import com.clivelewis.chaosdruid.Main;
import com.clivelewis.chaosdruid.Node;
import com.clivelewis.chaosdruid.utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.Timer;

public class WorldHopperNode extends Node {
	private Timer worldHopTimer;
	private final long MINUTES = 60000;

	public WorldHopperNode(Main main) {
		super(main);
//		worldHopTimer = new Timer();
//		worldHopTimer.setRunTime(30 * MINUTES);
//		main.getUtils().setWorldHopTimer(worldHopTimer);
	}

	@Override
	public boolean validate() {
		return main.getUtils().shouldHopWorld
				&& !main.getLocalPlayer().isInCombat()
				&& !main.getBank().isOpen()
				&& main.isInsideDungeon;
	}

	@Override
	public int execute() {
		main.currentState = STATE.WORLD_HOP;

		MethodProvider.sleep(10000);
		if(!main.getLocalPlayer().isInCombat()) {
			main.getWorldHopper().hopWorld(main.getWorlds().getRandomWorld(world -> world != null
					&& !world.isF2P() && !world.isPVP() && !world.isDeadmanMode() && !world.isTwistedLeague()
					&& world.getMinimumLevel() < 100));

//			worldHopTimer = new Timer();
//			worldHopTimer.setRunTime(Calculations.random(25 * MINUTES, 35 * MINUTES));
//			main.getUtils().setWorldHopTimer(worldHopTimer);
			main.getUtils().shouldHopWorld = false;
		}
		return Calculations.random(1000, 3000);
	}



}