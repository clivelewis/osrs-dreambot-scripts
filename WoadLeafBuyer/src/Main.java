import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.NPC;

import java.awt.*;

/**
 * @author Clive on 9/24/2019
 */
@ScriptManifest(author = "CliveLewis", category = Category.MISC, version = 1.1, name = "Woad Leaf Buyer")
public class Main extends AbstractScript {
    final String[] OPTIONS = {"Yes please, I need woad leaves.", "How about 20 coins?"};
    final Area WYSON_HOUSE_AREA = new Area(3031, 3383, 3019, 3373);
    final Tile WYSON_HOUSE = new Tile(3028, 3379);
    private Timer timer;

    @Override
    public void onStart() {
        super.onStart();
        timer = new Timer();
    }

    @Override
    public int onLoop() {
        checkForMods();


        if(WYSON_HOUSE_AREA.contains(getLocalPlayer())){
            return buyWoadLeafs();
        }else{
           return goToWysonHouse();
        }
    }

    private int goToWysonHouse() {
        getWalking().walk(WYSON_HOUSE);
        return Calculations.random(500, 2500);
    }

    private int buyWoadLeafs(){
        checkForPlayersAround();
        if(!getDialogues().inDialogue()) {
            NPC wyson = null;
            if (getNpcs().closest(npc -> npc != null && npc.hasAction("Talk-to") && npc.getName().contains("Wyson")) != null) {
                wyson = getNpcs().closest(npc -> npc != null && npc.hasAction("Talk-to") && npc.getName().contains("Wyson"));
            }
            if (wyson != null) {
                wyson.interact("Talk-to");
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
        Label.drawString( "Woad Leaf Buyer v1.1 by CliveLewis", 40, 300);
        Label.drawString("Runtime: " + timer.formatTime(), 40, 335);

    }

    public void checkForMods() {
        if (!getPlayers().all(f -> f != null && f.getName().contains("Mod")).isEmpty()) {
            log("We just found a JMod! Switched worlds, quickly... Time: " + System.currentTimeMillis());
            getWorldHopper().hopWorld(getWorlds().getRandomWorld(world -> world != null && world.isF2P() && !world.isPVP() && world.getMinimumLevel() < 100));
            sleep(Calculations.random(8000, 14000));
        }
    }

    public void checkForPlayersAround(){
        if(getPlayers().all(player -> player != null && !player.equals(getLocalPlayer())&& getLocalPlayer().distance(player) < 5).size() >= 2){
            getWorldHopper().hopWorld(getWorlds().getRandomWorld(world -> world != null && world.isF2P() && !world.isPVP() && world.getMinimumLevel() < 100));
        }
    }
}

