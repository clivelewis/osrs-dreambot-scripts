package com.clivelewis.chaosdruid.utils;

import com.clivelewis.chaosdruid.Main;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.items.Item;

public class Utils {
	private Main main;
	private Timer worldHopTimer;
	public boolean shouldHopWorld = false;

	public Utils(Main main){
		this.main = main;
	}

	public void setWorldHopTimer(Timer worldHopTimer) {
		this.worldHopTimer = worldHopTimer;
	}

	public void checkLocation() {
		int playerY = main.getLocalPlayer().getTile().getY();
		main.isInsideDungeon = playerY > 9800 && playerY < 10000;
	}

	public void checkForMods() {
		if (!main.getPlayers().all(f -> f != null && f.getName().contains("Mod")).isEmpty()) {
			MethodProvider.log("We just found a JMod! Switched worlds, quickly... Time: " + System.currentTimeMillis());
			shouldHopWorld = true;
			MethodProvider.sleep(Calculations.random(2000, 10000));
		}
	}

	public String getWorldHopTimeLeft(){
		long timeLeft = worldHopTimer.remaining();
		long second = (timeLeft / 1000) % 60;
		long minute = (timeLeft / (1000 * 60)) % 60;
		long hour = (timeLeft / (1000 * 60 * 60)) % 24;

		return String.format("%02d:%02d:%02d", hour, minute, second);
	}

	public void checkPlayersAround() {
		if(main.getPlayers().all(player -> player != null && !player.equals(main.getLocalPlayer())&& main.getLocalPlayer().distance(player) < 7).size() >= 3){
			shouldHopWorld = true;
		}
	}

	public void checkForDeath() {
		Item itemInSlot = main.getEquipment().getItemInSlot(EquipmentSlot.WEAPON.getSlot());
		if(itemInSlot == null){
			main.getTabs().logout();
			main.stop();
		}

	}

	public String timerToString(Timer timer){
		long timeLeft = timer.remaining();
		long second = (timeLeft / 1000) % 60;
		long minute = (timeLeft / (1000 * 60)) % 60;
		long hour = (timeLeft / (1000 * 60 * 60)) % 24;

		return String.format("%02d:%02d:%02d", hour, minute, second);
	}
}
