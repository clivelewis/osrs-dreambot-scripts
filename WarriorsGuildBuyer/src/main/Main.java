package main;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.bank.BankType;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;

import java.util.List;

@ScriptManifest(name = "WGuildBuyer", author = "CliveLewis", category = Category.MISC, description = "Buyer for Warriors Guild", version = 1.0)
public class Main extends AbstractScript {
	private STATE currentState = STATE.START;

	@Override
	public void onStart() {
		super.onStart();

		currentState = STATE.OPEN_SHOP;
	}

	@Override
	public int onLoop() {
		NPC lidio = getNpcs().closest(npc -> npc != null && npc.getName().equals("Lidio"));

		switch (currentState){
			case START:
				break;
			case OPEN_SHOP:
				if(lidio != null){
					lidio.interact("Trade");
					MethodProvider.sleepUntil(() -> getShop().isOpen(), 5000);
					if(getShop().isOpen()){
						currentState = STATE.BUY;
					}
				}
				break;
			case BUY:
				if(lidio != null && getShop().isOpen()){
					List<Item> all = getShop().all(item -> item != null && (item.getName().equals("Plain pizza") || item.getName().equals("Potato with cheese")
							|| item.getName().equals("Stew")));
					all.forEach(item -> {
						boolean purchase = getShop().purchaseFifty(item);
						MethodProvider.sleepUntil(() -> purchase, 3000);
					});

					getShop().close();
					currentState = STATE.BANK;
				}
				break;
			case BANK:
				if(getBank().isOpen()){
					boolean deposited = getBank().depositAllItems();
					MethodProvider.sleepUntil(() -> deposited, 5000);
					getBank().close();
					currentState = STATE.WORLD_HOP;
				}else{
					getBank().getClosestBank(BankType.BOOTH).interact("Bank");
					MethodProvider.sleepUntil(() -> getBank().isOpen(), 5000);
				}
				break;
			case WORLD_HOP:
				boolean b = getWorldHopper().hopWorld(getWorlds().getRandomWorld(world -> world != null
						&& !world.isF2P() && !world.isPVP() && !world.isDeadmanMode() && !world.isTwistedLeague()
						&& world.getMinimumLevel() < 250));
				MethodProvider.sleepUntil(() -> b, 10000);
				MethodProvider.sleep(3000);
				currentState = STATE.OPEN_SHOP;
				break;
		}
		return Calculations.random(1500, 3000);
	}
}
