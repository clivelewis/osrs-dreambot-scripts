package com.clivelewis.chaosdruid.nodes;

import com.clivelewis.chaosdruid.Main;
import com.clivelewis.chaosdruid.Node;
import com.clivelewis.chaosdruid.utils.STATE;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.wrappers.items.Item;

import java.util.Collection;

public class RunFromDeathNode extends Node {
	private final String AMULET_OF_GLORY = "Amulet of glory";
	private final String TELEPORT_LOCATION = "Edgeville";
	private final Tile OUTSIDE_WILDERNESS_TILE = new Tile(3132, 9915);
	private Area druidsArea = new Area(3104, 9943, 3132, 9923);


	public RunFromDeathNode(Main main) {
		super(main);
	}

	@Override
	public boolean validate() {
		return druidsArea.contains(main.getLocalPlayer()) &&
				main.getPlayers().closest(p -> p != null && !p.equals(main.getLocalPlayer()) && p.isSkulled()) != null;
	}

	@Override
	public int execute() {
		main.currentState = STATE.ANTI_PK;

		Item amuletOfGlory = main.getEquipment().get(item -> item != null && item.getName().startsWith(AMULET_OF_GLORY + "("));

		if(amuletOfGlory != null && !main.isTeleportBlocked){
			main.getTabs().open(Tab.EQUIPMENT);
			MethodProvider.log("Trying to teleport");
			amuletOfGlory.interact(TELEPORT_LOCATION);
			main.getUtils().shouldHopWorld = true;
		}else{
			MethodProvider.log("Trying to run");
			main.getWalking().toggleRun();
			main.getWalking().walk(OUTSIDE_WILDERNESS_TILE);
			main.getUtils().shouldHopWorld = true;
		}

		return 2000;
	}

	public int getHPPercent(){
		return (100* main.getSkills().getBoostedLevels(Skill.HITPOINTS) / main.getSkills().getRealLevel(Skill.HITPOINTS));
	}
}
