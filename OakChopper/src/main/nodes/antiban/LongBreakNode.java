package main.nodes.antiban;

import main.Main;
import main.Node;
import main.utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.login.LoginUtility;
import org.dreambot.api.methods.tabs.Tabs;
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
		return longBreakTimer.finished() && !Bank.isOpen() && !main.getLocalPlayer().isInCombat();
	}

	@Override
	public int execute() {
		main.setCurrentState(STATE.LONG_BREAK);
		MethodProvider.sleep(10000);
		logout();
		MethodProvider.sleep(getRandomOfflineTime());
		login();
		setNextLongBreakTimer();
		return 3000;
	}

	private void login(){
		main.getRandomManager().enableSolver(RandomEvent.LOGIN);
		main.getRandomManager().enableSolver(RandomEvent.WELCOME_SCREEN);

		LoginUtility.login();
	}

	private void logout(){
		main.getRandomManager().disableSolver(RandomEvent.LOGIN);
		main.getRandomManager().disableSolver(RandomEvent.WELCOME_SCREEN);

		Tabs.logout();
	}

	private void setNextLongBreakTimer(){
		longBreakTimer = new Timer();
		longBreakTimer.setRunTime(Calculations.random(45, 90) * MINUTES);
	}

	private int getRandomOfflineTime(){
		return (int) (Calculations.random(8, 15) * MINUTES);
	}

	public String getTimeUntilLongBreak(){
		if(longBreakTimer != null){
			return main.getUtils().timerToString(longBreakTimer);
		}
		return "";
	}
}
