package main;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;


/**
 * @author Clive on 10/5/2019
 */
@ScriptManifest(author = "CliveLewis", category = Category.MONEYMAKING, name = "Kebab Buyer", version = 1.0, description = "Buy kebabs, store in bank")
public class Main extends AbstractScript {
    private final String[] OPTIONS = {"Yes please."};
    private final Tile SHOP_LOCATION = new Tile(3274,3180);
    private Timer timer;

    @Override
    public void onStart() {
        timer = new Timer();
        super.onStart();
    }

    @Override
    public int onLoop() {
        checkForMods();

        if(getInventory().isFull()){
            return doBanking();
        }else if(!SHOP_LOCATION.getArea(5).contains(getLocalPlayer())){
            return walkToShop();
        }else{
            buyKebabs();
        }
        return 0;
    }

    private int doBanking(){
        if(getBank().isOpen()){
            getBank().depositAllExcept(item -> item != null && item.getName().equals("Coins"));
        }else{
            getBank().openClosest();
        }

        return Calculations.random(1000, 4000);
    }

    private int walkToShop(){
        getWalking().walk(SHOP_LOCATION);
        return Calculations.random(1000, 4000);
    }

    private int buyKebabs(){
        if(!getDialogues().inDialogue()) {
            NPC karim = null;
            if (getNpcs().closest(npc -> npc != null && npc.hasAction("Talk-to") && npc.getName().contains("Karim")) != null) {
                karim = getNpcs().closest(npc -> npc != null && npc.hasAction("Talk-to") && npc.getName().contains("Karim"));
            }
            if (karim != null) {
                karim.interact("Talk-to");
                sleep(1500);
            }
        }else if (getDialogues().inDialogue() && !getDialogues().continueDialogue()) {
            for(String option : OPTIONS) {
                if (getDialogues().chooseOption(option)) {
                    break;
                }
            }
        }
        return Calculations.random(300, 1000);
    }

    @Override
    public void onPaint(Graphics Label) {

        Label.setColor(new Color(0, 0, 0, 15));
        Label.fillRect( 0,  290, 520, 50);

        Label.setColor((Color.WHITE));
        Label.drawString( "Kebab Buyer v1.0 by CliveLewis", 40, 300);
        Label.drawString("Runtime: " + timer.formatTime(), 40, 335);

    }

    public void checkForMods() {
        if (!getPlayers().all(f -> f != null && f.getName().contains("Mod")).isEmpty()) {
            log("We just found a JMod! Switched worlds, quickly... Time: " + System.currentTimeMillis());
            getWorldHopper().hopWorld(getWorlds().getRandomWorld(world -> world != null && world.isF2P() && !world.isPVP() && world.getMinimumLevel() < 100));
            sleep(Calculations.random(8000, 14000));
        }
    }

}
