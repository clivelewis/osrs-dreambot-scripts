package com.clivelewis.chaosdruid.nodes.antiban;

import com.clivelewis.chaosdruid.Main;
import com.clivelewis.chaosdruid.Node;
import com.clivelewis.chaosdruid.utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.utilities.Timer;

public class LongBreakNode extends Node {
	private Timer longBreakTimer;

	private final long MINUTES = 60000;


	public LongBreakNode(Main main) {
		super(main);
		longBreakTimer = new Timer();

		setNextLongBreakTimer();
		getRandomOfflineTime();
	}

	@Override
	public boolean validate() {
		return longBreakTimer.finished()
				&& !main.getBank().isOpen()
				&& !main.getLocalPlayer().isInCombat();
	}

	@Override
	public int execute() {
		main.currentState = STATE.LONG_BREAK;
		MethodProvider.sleep(10000);
		if(!main.getLocalPlayer().isInCombat()){
			logout();
			MethodProvider.sleep(getRandomOfflineTime());
			login();
			setNextLongBreakTimer();
			return 0;
		}

		return 1000;
	}

	private void login(){
		main.getRandomManager().enableSolver(RandomEvent.LOGIN);
		main.getLoginUtility().login();
	}

	private void logout(){
		main.getTabs().logout();
		main.getRandomManager().disableSolver(RandomEvent.LOGIN);
	}

	private void setNextLongBreakTimer(){
		longBreakTimer = new Timer();
		longBreakTimer.setRunTime(Calculations.random(45, 75) * MINUTES);
	}

	private int getRandomOfflineTime(){
		return (int) (Calculations.random(15, 25) * MINUTES);
	}

	public String getTimeUntilLongBreak(){
		if(longBreakTimer != null){
			return main.getUtils().timerToString(longBreakTimer);
		}
		return "";
	}
}
