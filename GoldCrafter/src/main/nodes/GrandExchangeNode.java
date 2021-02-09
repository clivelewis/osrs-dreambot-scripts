package main.nodes;

import main.Main;
import main.Node;
import main.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.map.Area;

public class GrandExchangeNode extends Node {
	private final Area GRAND_EXCHANGE_AREA = new Area(3160, 3488, 3169, 3484);
	private INNER_STATE currentInnerState = INNER_STATE.SELL;
	private GrandExchange grandExchange = main.getGrandExchange();

	private int gemPrice;
	private int goldBarPrice;

	public GrandExchangeNode(Main main) {
		super(main);
	}

	@Override
	public boolean validate() {
		return main.currentState == STATE.GRAND_EXCHANGE;
	}

	@Override
	public int execute() {
		if (GRAND_EXCHANGE_AREA.contains(main.getLocalPlayer())) {

			if (currentInnerState.equals(INNER_STATE.SELL)) {
				if (!main.getInventory().contains(main.ITEM_NAME)) currentInnerState = INNER_STATE.BUY;
				else sellCraftedItems();

			} else if (currentInnerState.equals(INNER_STATE.WAIT_SELL)) {
				if (!grandExchange.isOpen()) {
					grandExchange.open();
					MethodProvider.sleepUntil(() -> grandExchange.isOpen(), 5000);
				}

				if (grandExchange.isReadyToCollect(0)) {
					grandExchange.collect();
					currentInnerState = INNER_STATE.BUY;
				}
			} else if (currentInnerState.equals(INNER_STATE.BUY)) {
				if (!grandExchange.isOpen()) {
					grandExchange.open();
					MethodProvider.sleepUntil(() -> grandExchange.isOpen(), 5000);
				}
				getCurrentPrices();
				buyItems();

			} else if (currentInnerState.equals(INNER_STATE.WAIT_BUY)) {
				if (!grandExchange.isOpen()) {
					grandExchange.open();
					MethodProvider.sleepUntil(() -> grandExchange.isOpen(), 5000);
				}

				if (grandExchange.isReadyToCollect(0) && grandExchange.isReadyToCollect(1)) {
					grandExchange.collect();
					MethodProvider.sleep(1000, 2500);
					grandExchange.close();
					MethodProvider.sleepUntil(() -> main.getBank().openClosest(), 5000);
					MethodProvider.sleep(500, 1500);
					main.getBank().depositAllItems();

					currentInnerState = INNER_STATE.SELL;
					main.currentState = STATE.WALKING_TO_EDGEVILLE;
				}
			}

		} else {
			main.getWalking().walk(GRAND_EXCHANGE_AREA.getRandomTile());
		}


		return Calculations.random(1500, 3000);
	}

	private void buyItems() {
		int buyCount = getBuyCount();
		MethodProvider.sleepUntil(() -> grandExchange.openBuyScreen(0), 5000);
		MethodProvider.sleep(500, 1000);
		MethodProvider.sleepUntil(() -> grandExchange.addBuyItem(main.GEM), 5000);
		MethodProvider.sleep(500, 1000);
		MethodProvider.sleepUntil(() -> grandExchange.setQuantity(buyCount), 5000);
		MethodProvider.sleep(500, 1000);
		main.getWidgets().getWidget(465).getChild(24).getChild(13).interact();
		MethodProvider.sleep(500, 1000);
		main.getWidgets().getWidget(465).getChild(24).getChild(13).interact();
		MethodProvider.sleep(500, 1000);
		grandExchange.confirm();
		MethodProvider.sleepUntil(() -> grandExchange.openBuyScreen(1), 5000);
		MethodProvider.sleep(500, 1000);
		MethodProvider.sleepUntil(() -> grandExchange.addBuyItem(main.GOLD_BAR), 5000);
		MethodProvider.sleep(500, 1000);
		MethodProvider.sleepUntil(() -> grandExchange.setQuantity(buyCount), 5000);
		main.getWidgets().getWidget(465).getChild(24).getChild(13).interact();
		MethodProvider.sleep(500, 1000);
		main.getWidgets().getWidget(465).getChild(24).getChild(13).interact();
		grandExchange.confirm();
		currentInnerState = INNER_STATE.WAIT_BUY;
	}

	private void sellCraftedItems() {
		// Sell all amulets
		grandExchange.open();
		MethodProvider.sleepUntil(() -> grandExchange.isOpen(), 5000);
		grandExchange.addSellItem(main.ITEM_NAME);
		MethodProvider.sleep(1500, 3000);
		grandExchange.getEnterAllButton().interact();
		MethodProvider.sleep(1000, 1500);
		main.getWidgets().getWidget(465).getChild(24).getChild(10).interact();
		MethodProvider.sleep(500, 1000);
		grandExchange.confirm();
		currentInnerState = INNER_STATE.WAIT_SELL;
	}

	private void getCurrentPrices() {
		grandExchange.openBuyScreen(0);
		MethodProvider.sleepUntil(() -> main.getGrandExchange().addBuyItem(main.GEM), 5000);
		MethodProvider.sleep(500, 1500);
		gemPrice = main.getGrandExchange().getCurrentPrice();
		MethodProvider.log("New gem price: " + gemPrice);
		MethodProvider.sleep(500, 1000);
		grandExchange.goBack();
		MethodProvider.sleep(500, 1000);
		grandExchange.openBuyScreen(0);
		MethodProvider.sleepUntil(() -> main.getGrandExchange().addBuyItem(main.GOLD_BAR), 5000);
		MethodProvider.sleep(500, 1500);
		goldBarPrice = main.getGrandExchange().getCurrentPrice();
		MethodProvider.log("New gold price: " + goldBarPrice);
		MethodProvider.sleep(500, 1000);
		grandExchange.goBack();
		MethodProvider.sleep(500, 1000);
	}

	private int getBuyCount() {
		int coins = main.getInventory().count("Coins");
		MethodProvider.log("Coins: " + coins);
		int approxPrice = (gemPrice + gemPrice / 10) + (goldBarPrice + goldBarPrice / 10);
		MethodProvider.log("Approx Price: " + approxPrice);
		MethodProvider.log("Result: " + coins / approxPrice);
		return (coins - coins / 5 ) / approxPrice; // save 5%
	}

	enum INNER_STATE {
		SELL,
		BUY,
		WAIT_SELL,
		WAIT_BUY
	}
}
