package main;

import main.nodes.*;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import java.awt.*;

/**
 * @author Clive on 9/21/2019
 */
@ScriptManifest(author = "CliveLewis", category = Category.WOODCUTTING, name = "Starter Stats Farmer", version = 1.0)
public class Main extends AbstractScript {

	private Node[] nodes;
	private StatsChecker statsChecker;
	public Skill currentSkill;
	public STATE state;


	@Override
	public void onStart() {
		super.onStart();
		state = STATE.LOGIN;

		statsChecker = new StatsChecker(this);
		assignNewSkill();

		nodes = new Node[]{
				new BankNode(this),
				new WalkNode(this),
				new RandomPauseNode(this),
				new FishNode(this),
				new MineNode(this),
				new ChopNode(this),
		};
	}

	@Override
	public int onLoop() {
		if (statsChecker.gotRequiredLevel(currentSkill)) {
			currentSkill = statsChecker.getRandomSkill();
		}

		if (currentSkill == null) {
			Tabs.logout();
			this.stop();
		}
		for (Node node : nodes) {
			if (node.validate()) {
				log("Executing " + node.getClass().getName());
				return node.execute();
			}
		}

		log("No main.main.nodes are valid");
		return Calculations.random(1000, 3000);
	}


	@Override
	public void onPaint(Graphics Label) {

		Label.setColor(new Color(0, 0, 0, 15));
		Label.fillRect(0, 320, 520, 20);

		Label.setColor((Color.WHITE));
		Label.drawString("Starter Stats Farmer v1.0 by CliveLewis. STATE: " + state , 25, 335);
	}

	public void assignNewSkill() {
		if (!statsChecker.statsGoalReached()) {
			currentSkill = statsChecker.getRandomSkill();
		} else {
			Tabs.logout();
			this.stop();
		}
	}
}
