package com.clivelewis.chatSpammer;

import com.clivelewis.chatSpammer.gui.GUIBuilder;
import com.clivelewis.chatSpammer.nodes.AccountChangeNode;
import com.clivelewis.chatSpammer.nodes.SpamNode;
import com.clivelewis.chatSpammer.nodes.WalkToLocationNode;
import com.clivelewis.chatSpammer.utils.Utils;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;


import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * @author Clive on 9/3/2019
 */

@ScriptManifest(author = "CliveLewis", category = Category.MISC, name = "ChatSpammer", version = 1.2)
public class Main extends AbstractScript {

    private Node[] nodes;
    private Timer globalTimer;
    private Timer accountChangeTimer;

    public boolean timeToChangeAccount;

    public boolean isRunning = false;
    public long accountChangeInterval;
    public int messageDelay;
    public Tile currentSpamArea;
    public List<String> messageList = new ArrayList<>();

    public List<String> accountList = new ArrayList<>();
    public String currentAccount;


    @Override
    public void onStart() {
        GUIBuilder builder = new GUIBuilder(this);
        builder.initGUI();

        nodes = new Node[]{
                new AccountChangeNode(this),
                new WalkToLocationNode(this),
                new SpamNode(this)
        };
    }

    public void setupInitialValues(){
        globalTimer = new Timer();
        accountChangeTimer = new Timer();
        accountChangeTimer.setRunTime(accountChangeInterval * Utils.MINUTES);
        timeToChangeAccount = true;

    }

    public void resetAccountChangeTimer(){
        accountChangeTimer = new Timer();
        accountChangeTimer.setRunTime(accountChangeInterval * Utils.MINUTES);
    }
    @Override
    public int onLoop() {

        if (isRunning) {
            if(accountChangeTimer.finished()) timeToChangeAccount = true;

            for (Node node : nodes) {
                if (node.validate()) {
                    log("Executing " + node.getClass().getName());
                    return node.execute();
                }
            }
        }
        return 1000;

    }

    @Override
    public void onPaint(Graphics Label) {
        if(isRunning) {
            Label.setColor(Color.RED);
            Label.setColor(new Color(0, 0, 0, 15));
            Label.fillRect(0, 280, 520, 60);

            Label.setColor((Color.GREEN));
            Label.drawString("Chat Spammer v1.1 by CliveLewis", 200, 295);

            Label.setColor(Color.WHITE);
            Label.drawString("Runtime: " + globalTimer.formatTime(), 20, 310);
            if (getClient().isLoggedIn()) {
                Label.drawString("Next account change: " + Utils.getFormattedTimeLeft(accountChangeTimer), 20, 325);
            }
        }
    }

    public String getCurrentAccountUsername(){
        return currentAccount.substring(0, currentAccount.indexOf(":"));
    }

    public String getCurrentAccountPassword(){
        return currentAccount.substring(currentAccount.indexOf(":") + 1);
    }

    public void setNextAccount(){
        for(int i = 0; i < accountList.size(); i++){
            if(accountList.get(i).equals(currentAccount)){
                 if(i + 1 < accountList.size())  currentAccount = accountList.get(i+1);
                 else currentAccount = accountList.get(0);

                 break;
            }
        }
    }


}
