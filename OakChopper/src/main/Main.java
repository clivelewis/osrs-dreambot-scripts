package main;

import main.nodes.*;
import main.nodes.antiban.LongBreakNode;
import main.nodes.antiban.ShortBreakNode;
import main.nodes.antiban.WorldHopperNode;
import main.utils.STATE;
import main.utils.Utils;
import org.dreambot.api.Client;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;

/**
 * @author Clive on 9/21/2019
 */
@ScriptManifest(author = "CliveLewis", category = Category.WOODCUTTING, name = "Oak Chopper", version = 1.0)
public class Main extends AbstractScript implements InventoryListener {

	private Node[] nodes;
	private Utils utils;
	private Timer runtimeTimer;
	private WorldHopperNode worldHopperNode;
	private LongBreakNode longBreakNode;

	public STATE state;
	public boolean shouldHopWorld;
	public int treesChopped;


	public Utils getUtils() {
		return this.utils;
	}

	@Override
	public void onStart() {
		super.onStart();
		state = STATE.LOGIN;

		utils = new Utils(this);
		worldHopperNode = new WorldHopperNode(this);
		longBreakNode = new LongBreakNode(this);

		nodes = new Node[]{
				longBreakNode,
				new ShortBreakNode(this),
				worldHopperNode,
				new BankNode(this),
				new WalkNode(this),
				new ChopNode(this),
		};

		runtimeTimer = new Timer();

	}

	@Override
	public int onLoop() {

		for (Node node : nodes) {
			if (node.validate()) {
				log("Executing " + node.getClass().getName());
				return node.execute();
			}
		}

		return Calculations.random(1000, 3000);
	}


	@Override
	public void onPaint(Graphics label) {
		label.setColor(Color.RED);
		label.setColor(new Color(0, 0, 0, 15));
		label.fillRect(0, 240, 520, 100);

		label.setColor((Color.GREEN));
		label.drawString("Oak Chopper v1.0 by CliveLewis", 200, 260); // x,y

		label.setColor(Color.WHITE);
		label.drawString("Runtime: " + runtimeTimer.formatTime(), 20, 270);
		label.drawString("Until long break: " + longBreakNode.getTimeUntilLongBreak(), 20, 285);
		label.drawString("Current State: " + state.toString(), 20, 300);
		if (Client.isLoggedIn()) {
			label.drawString("Players Around: " + utils.getPlayersAround(), 400, 285);
			label.drawString("Until world hop: " + worldHopperNode.getWorldHopTimeLeft(), 20, 315);
			label.drawString("Oaks collected: " + treesChopped, 400, 300);
		}
	}

	public void setCurrentState(STATE state) {
		this.state = state;
	}

	public STATE getState() {
		return state;
	}

	@Override
	public void onItemChange(Item[] items) {
		for (Item item : items) {
			if (item != null && item.getName().equals("Oak logs") && item.getAmount() > 0) {
				treesChopped += item.getAmount();
			}
		}
	}
}
