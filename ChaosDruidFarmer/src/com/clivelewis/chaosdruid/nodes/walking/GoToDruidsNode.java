package com.clivelewis.chaosdruid.nodes.walking;

import com.clivelewis.chaosdruid.Main;
import com.clivelewis.chaosdruid.Node;
import com.clivelewis.chaosdruid.utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.walking.pathfinding.impl.web.WebFinder;
import org.dreambot.api.methods.walking.web.node.AbstractWebNode;
import org.dreambot.api.methods.walking.web.node.impl.BasicWebNode;

public class GoToDruidsNode extends Node {
	private Tile druidsLocation = new Tile(3112, 9928);
	private Area druidsArea = new Area(3104, 9943, 3132, 9923);

	public GoToDruidsNode(Main main) {
		super(main);
		setupWebNodes(main);
	}

	private void setupWebNodes(Main main) {
		AbstractWebNode webNode0 = new BasicWebNode(3097, 9869);
		AbstractWebNode webNode1 = new BasicWebNode(3095, 9885);
		AbstractWebNode webNode1_1 = new BasicWebNode(3096, 9908);
		AbstractWebNode webNode2 = new BasicWebNode(3101, 9909);
		AbstractWebNode webNode3 = new BasicWebNode(3122, 9910);
		AbstractWebNode webNode4 = new BasicWebNode(3131, 9917);
		AbstractWebNode webNode5 = new BasicWebNode(3124, 9929);
		AbstractWebNode webNode6 = new BasicWebNode(3115, 9926);
		webNode0.addConnections(webNode1);
		webNode1.addConnections(webNode0, webNode1_1);
		webNode1_1.addConnections(webNode1, webNode2);
		webNode2.addConnections(webNode1_1, webNode3);
		webNode3.addConnections(webNode2, webNode4);
		webNode4.addConnections(webNode3, webNode5);
		webNode5.addConnections(webNode4, webNode6);
		webNode6.addConnections(webNode5);
		WebFinder webFinder = main.getWalking().getWebPathFinder();
		AbstractWebNode[] webNodes = {webNode0, webNode1, webNode2, webNode3, webNode4, webNode5, webNode6};
		for (AbstractWebNode webNode : webNodes) {
			webFinder.addWebNode(webNode);
		}
	}

	@Override
	public boolean validate() {
		return main.isInsideDungeon && !druidsArea.contains(main.getLocalPlayer());
	}

	@Override
	public int execute() {
		main.currentState = STATE.WALK_TO_DRUIDS;

		checkForWildernessWarning();
		main.getWalking().walk(druidsLocation);

		Main.log("Walking to druids");
		return Calculations.random(2000, 5000);
	}

	private void checkForWildernessWarning() {
		if (main.getWidgets().getWidgetChild(475, 11) != null) {
			main.getWidgets().getWidgetChild(475, 11).interact();
			MethodProvider.sleep(1000,1200);
		}
	}
}
