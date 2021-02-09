package main;

import main.nodes.CraftNode;
import main.nodes.EdgevilleNode;
import main.nodes.InventoryCheckNode;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.event.impl.ExperienceEvent;
import org.dreambot.api.script.listener.ExperienceListener;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;

@ScriptManifest(author = "CliveLewis", name = "GoldCrafter", description = "Crafting Gold Jewellery", version = 1.0, category = Category.CRAFTING)
public class Main extends AbstractScript implements InventoryListener, ExperienceListener {

	public final String MOULD = "Ring mould";
	public final String GOLD_BAR = "Gold bar";

//	public final String GEM = "Sapphire";
//	public final String ITEM_NAME = "Sapphire ring";
//	public final int ITEM_CRAFT_WIDGET_ID = 8;

	public final String GEM = "Emerald";
	public final String ITEM_NAME = "Emerald ring";
	public final int ITEM_CRAFT_WIDGET_ID = 9;

	public STATE currentState;
	private Timer runtimeTimer;
	private Node[] nodes;
	private int itemsCrafted = 0;
	private int experienceGained = 0;

	@Override
	public void onStart() {
		super.onStart();
		currentState = STATE.LOGIN;
		nodes = new Node[]{
				new EdgevilleNode(this),
				new InventoryCheckNode(this),
				new CraftNode(this)
		};

		runtimeTimer = new Timer();
	}


	@Override
	public int onLoop() {
		for (Node node : nodes) {
			if (node.validate()) {
				return node.execute();
			}
		}

		if (currentState.equals(STATE.CRAFTING) && getMouse().isMouseInScreen()) {
			getMouse().moveMouseOutsideScreen();
		}

		return Calculations.random(2000, 4000);
	}

	@Override
	public void onPaint(Graphics Label) {

		Label.setColor(new Color(0, 0, 0, 15));
		Label.fillRect(0, 240, 520, 100);

		Label.setColor((Color.GREEN));
		Label.drawString("Gold Crafter v1.0 by CliveLewis", 200, 260); // x,y

		Label.setColor(Color.WHITE);
		Label.drawString("Runtime: " + runtimeTimer.formatTime(), 20, 270);
		Label.drawString("Current State: " + currentState.toString(), 20, 290);
		Label.drawString("Items crafted: " + itemsCrafted, 20, 310);
		Label.drawString("Experience gained: " + experienceGained, 20, 330);

	}

	@Override
	public void onItemChange(Item[] items) {
		for (Item item : items) {
			if (item != null && item.getName().contains(ITEM_NAME) && item.getAmount() > 0) {
				itemsCrafted++;
			}
		}
	}

	@Override
	public void onGained(ExperienceEvent experienceEvent) {
		if (experienceEvent.getSkill().equals(Skill.CRAFTING)) {
			experienceGained += experienceEvent.getChange();
		}
	}

	@Override
	public void onLevel(ExperienceEvent experienceEvent) {

	}

	@Override
	public void onLevelUp(ExperienceEvent experienceEvent) {

	}

	@Override
	public void onLevelChange(ExperienceEvent experienceEvent) {

	}
}
