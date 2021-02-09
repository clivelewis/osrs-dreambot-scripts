package main;

import main.Utils.STATE;
import main.Utils.Utils;
import main.nodes.BankNode;
import main.nodes.antiban.LongBreakNode;
import main.nodes.antiban.WorldHopperNode;
import main.nodes.antiban.ShortBreakNode;
import main.nodes.farmhide.CowCombatNode;
import main.nodes.farmhide.PickupHideNode;
import main.nodes.farmhide.WalkToCowsNode;
import main.nodes.statsfarm.ChickenCombatNode;
import main.nodes.statsfarm.PickupFeathersNode;
import main.nodes.statsfarm.WalkToChickensNode;
import org.dreambot.api.data.GameState;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;
import java.util.Random;

@ScriptManifest(author = "CliveLewis", category = Category.MISC, name = "Hide Farmer", version = 2.3)
public class Main extends AbstractScript implements InventoryListener{
    private Node[] hideFarmNodes;
    private Node[] statFarmNodes;
    private Node[] currentNodes;
    private Utils utils;
    private Timer runtimeTimer;
    private STATE currentState;

    private WorldHopperNode worldHopperNode;
    private LongBreakNode longBreakNode;

    public boolean shouldHopWorld = false;

    public Utils getUtils(){
        return this.utils;
    }

    @Override
    public void onStart() {
        currentState = STATE.START;
        utils = new Utils(this);

        worldHopperNode = new WorldHopperNode(this);
        longBreakNode = new LongBreakNode(this);

        hideFarmNodes = new Node[]{
                longBreakNode,
                new ShortBreakNode(this),
                worldHopperNode,
                new BankNode(this),
                new WalkToCowsNode(this),
                new PickupHideNode(this),
                new CowCombatNode(this),
        };

        statFarmNodes = new Node[]{
                longBreakNode,
                new ShortBreakNode(this),
                worldHopperNode,
                new BankNode(this),
                new WalkToChickensNode(this),
                new PickupFeathersNode(this),
                new ChickenCombatNode(this),
//                new MouseAntiban(this)
        };

        if(getClient().isLoggedIn()){
            if(utils.gotMinimalStats()) currentNodes = hideFarmNodes;
            else currentNodes = statFarmNodes;
        }

        runtimeTimer = new Timer();
    }


    @Override
    public GameState onGameState(GameState gameState) {
        if(getClient().isLoggedIn() && gameState.equals(GameState.LOGGED_IN)){
            if(utils.gotMinimalStats()) currentNodes = hideFarmNodes;
            else currentNodes = statFarmNodes;
        }

        return super.onGameState(gameState);
    }

    @Override
    public void onPaint(Graphics Label) {

        Label.setColor(Color.RED);
        Label.setColor(new Color(0, 0, 0, 15));
        Label.fillRect( 0,  240, 520, 100);

        Label.setColor((Color.GREEN));
        Label.drawString( "Hide Farmer v2.3 by CliveLewis", 200, 260); // x,y

        Label.setColor(Color.WHITE);
        Label.drawString("Runtime: " + runtimeTimer.formatTime(), 20, 270);
        Label.drawString("Until long break: " + longBreakNode.getTimeUntilLongBreak(), 20, 285);
        Label.drawString("Current State: " + currentState.toString(), 20, 300);
        if(getClient().isLoggedIn()) {
            Label.drawString("Players Around: " + utils.getPlayersAround(), 400, 285);
            Label.drawString("Until world hop: " + worldHopperNode.getWorldHopTimeLeft(), 20, 315);
            Label.drawString("Hides collected: " + utils.hidesCollected, 400, 300);
        }

    }


    @Override
    public int onLoop() {
        utils.checkForMods();

        if(currentNodes != null) {
            for (Node node : currentNodes) {
                if (node.validate()) {
                    return node.execute();
                }
            }
        }

        getMouse().move(new Point(getMouse().getX() + new Random().nextInt(3) - 1,
                getMouse().getY() + new Random().nextInt(3) - 1));
        return 2000;

    }


    public void setHideFarmNodes(){
        currentNodes = hideFarmNodes;
        utils.switchCombat(1, 9, "Strength");
    }

    @Override
    public void onItemChange(Item[] items) {
        for (Item item : items) {
            if (item != null && item.getName().contains("Cowhide") && item.getAmount() > 0) {
                getUtils().hidesCollected += item.getAmount();
            }
        }
    }

    public void setCurrentState(STATE state){
        currentState = state;
    }

    public STATE getState(){
        return currentState;
    }

}
