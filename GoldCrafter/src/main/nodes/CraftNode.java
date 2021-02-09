package main.nodes;

import main.Main;
import main.Node;
import main.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.WidgetChild;

public class CraftNode extends Node {
	public CraftNode(Main main) {
		super(main);
	}

	@Override
	public boolean validate() {
		return !main.getLocalPlayer().isAnimating();
	}

	@Override
	public int execute() {
		if(doingSomething()){
			return Calculations.random(500, 1000);
		}
		main.currentState = STATE.CRAFTING;
		Widget craftMenu = main.getWidgets().getWidget(446);

		if(craftMenu != null && craftMenu.isVisible()){
			craftMenu.getChild(main.ITEM_CRAFT_WIDGET_ID).interact();
			return Calculations.random(2000, 4000);
		}else{
			GameObject furnace = main.getGameObjects().closest(g -> g != null && g.getID() == 16469);
			furnace.interact();
			return Calculations.random(500, 2000);
		}

	}
	private boolean doingSomething() {
		for (int i = 0; i < 20; i++) {
			if (main.getLocalPlayer().isAnimating()) { return true; }
			MethodProvider.sleep(100);
		}
		return false;
	}
}
