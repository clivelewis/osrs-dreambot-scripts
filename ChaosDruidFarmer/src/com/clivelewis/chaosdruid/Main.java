package com.clivelewis.chaosdruid;

import com.clivelewis.chaosdruid.nodes.*;
import com.clivelewis.chaosdruid.nodes.antiban.LongBreakNode;
import com.clivelewis.chaosdruid.nodes.antiban.ShortBreakNode;
import com.clivelewis.chaosdruid.nodes.antiban.WorldHopperNode;
import com.clivelewis.chaosdruid.nodes.walking.GoToBankNode;
import com.clivelewis.chaosdruid.nodes.walking.GoToDruidsNode;
import com.clivelewis.chaosdruid.nodes.walking.GoToDungeonNode;
import com.clivelewis.chaosdruid.utils.STATE;
import com.clivelewis.chaosdruid.utils.Utils;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.MessageListener;
import org.dreambot.api.wrappers.widgets.message.Message;

import java.awt.*;
import java.util.Random;

/**
 * @author Clive on 10/20/2019
 */
@ScriptManifest(name = "Chaos Druids", version = 2.3, category = Category.COMBAT, author = "CliveLewis")
public class Main extends AbstractScript implements MessageListener {
    public STATE currentState;
    public int ranarrCounter;
    public int itemCounter;
    public boolean isInsideDungeon = false;
    public boolean isTeleportBlocked = false;

    private Utils utils;
    private Node[] nodes;
    private LongBreakNode longBreakNode;

    @Override
    public void onStart() {
        super.onStart();
        currentState = STATE.START;
        utils = new Utils(this);
        longBreakNode = new LongBreakNode(this);

        nodes = new Node[]{
                new RunFromDeathNode(this),
                longBreakNode,
                new ShortBreakNode(this),
                new WorldHopperNode(this),
                new BankNode(this),
                new GoToBankNode(this),
                new GoToDungeonNode(this),
                new GoToDruidsNode(this),
                new LootNode(this),
                new CombatNode(this),
        };
        getBank().close();
    }



    @Override
    public int onLoop() {
        utils.checkLocation();
        utils.checkForMods();
        utils.checkForDeath();

        for(Node node: nodes){
            if(node.validate()){
                return node.execute();
            }
        }

        getMouse().move(new Point(getMouse().getX() + new Random().nextInt(3) - 1,
                getMouse().getY() + new Random().nextInt(3) - 1));
        return 500;
    }



    @Override
    public void onPaint(Graphics Label) {
        Label.setColor(Color.RED);
        Label.setColor(new Color(0, 0, 0, 15));
        Label.fillRect( 0,  240, 520, 100);

        Label.setColor((Color.GREEN));
        Label.drawString( "Chaos Druids v2.0 by CliveLewis", 200, 260);

        Label.setColor(Color.WHITE);
        Label.drawString("Ranarr Collected: " + ranarrCounter, 20, 270);
        Label.drawString("Items Collected: " + itemCounter, 20, 285);
        Label.drawString("Current State: " + currentState.getDescription(), 20, 315);
        Label.drawString("Until long break: " + longBreakNode.getTimeUntilLongBreak(), 20, 330);

        Label.drawString("In Dungeon: " + isInsideDungeon, 400, 270);
//        Label.drawString("Until world hop: " + utils.getWorldHopTimeLeft(), 400, 285);

    }

//    @Override
//    public void onItemChange(Item[] items) {
//        final String RANARR_WEED = "Grimy ranarr weed";
//
//        for (Item item : items) {
//            if (item != null && item.getName().equals(RANARR_WEED) && item.getAmount() > 0) {
//                ranarrCounter += item.getAmount();
//            }
//            if(item != null && item.getName().startsWith("Grimy") && item.getAmount() > 0){
//                itemCounter += item.getAmount();
//            }
//        }
//    }

    public Utils getUtils(){
        return utils;
    }

    @Override
    public void onGameMessage(Message message) {
        if(message.getMessage().contains("A Tele Block spell has been cast on you")){
            isTeleportBlocked = true;
        }
        if(message.getMessage().contains("Your Tele Block has expired")){
            isTeleportBlocked = false;
        }
    }

    @Override
    public void onPlayerMessage(Message message) {

    }

    @Override
    public void onTradeMessage(Message message) {

    }

    @Override
    public void onPrivateInMessage(Message message) {

    }

    @Override
    public void onPrivateOutMessage(Message message) {

    }
}
